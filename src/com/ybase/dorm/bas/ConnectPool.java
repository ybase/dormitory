package com.ybase.dorm.bas;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Driver;

/**
 * 管理类DBConnectionManager支持对一个或多个由属性文件定义的数据库连接
 * 池的访问.客户程序可以调用getInstance()方法访问本类的唯一实例.
 */
public class ConnectPool {
	private static final Logger log = Logger.getLogger(ConnectPool.class.getName());
	private static ThreadLocal<Connection> connectionHandler = new ThreadLocal<Connection>();
	static public ConnectPool instance; // 唯一实例
	static public int clients;
	public String defaultPoolName = "";
	public Vector<Driver> drivers = new Vector<Driver>(); // 驱动
	public Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>(); // 连接

	/**
	 * 返回唯一实例.如果是第一次调用此方法,则创建实例
	 * 
	 * @return DBConnectionManager 唯一实例
	 */
	static synchronized public ConnectPool getInstance() {
		if (instance == null) {
			instance = new ConnectPool();
		}

		clients++;
		return instance;
	}

	/**
	 * 建构函数私有以防止其它对象创建本类实例
	 */
	private ConnectPool() {
		init();
	}

	/**
	 * 将连接对象返回给由名字指定的连接池
	 * 
	 * @param name
	 *            在属性文件中定义的连接池名字
	 * @param con
	 *            连接对象
	 */
	public void freeConnection(String name, Connection con) {
		if (name == null || "".equals(name.trim())) {
			name = this.defaultPoolName;
		}

		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			pool.freeConnection(con);
		} else {
			log.info("pool ==null");
		}
		clients--;
	}

	/**
	 * 获得一个可用的(空闲的)连接.如果没有可用连接,且已有连接数小于最大连接数 限制,则创建并返回新连接
	 * 
	 * @param name
	 *            在属性文件中定义的连接池名字
	 * @return Connection 可用连接或null
	 */
	public Connection getConnection(String name) {
		if (name == null || "".equals(name.trim())) {
			name = this.defaultPoolName;
		}

		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			return pool.returnConnection();
		}
		return null;
	}

	/**
	 * 获得一个可用连接.若没有可用连接,且已有连接数小于最大连接数限制, 则创建并返回新连接.否则,在指定的时间内等待其它线程释放连接.
	 * 
	 * @param name
	 *            连接池名字
	 * @param time
	 *            以毫秒计的等待时间
	 * @return Connection 可用连接或null
	 */
	public Connection getConnection(String name, long time) {
		if (name == null || "".equals(name.trim())) {
			name = this.defaultPoolName;
		}

		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			return pool.getConnection(time);
		}
		return null;
	}

	/**
	 * 关闭所有连接,撤销驱动程序的注册
	 */
	public synchronized void release() {
		// 等待直到最后一个客户程序调用
		if (--clients != 0) {
			return;
		}

		Enumeration<DBConnectionPool> allPools = pools.elements();
		while (allPools.hasMoreElements()) {
			DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
			pool.release();
		}
		Enumeration<Driver> allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements()) {
			Driver driver = (Driver) allDrivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				log.info("撤销JDBC驱动程序 " + driver.getClass().getName() + "的注册");
			} catch (SQLException e) {
				log.error("无法撤销下列JDBC驱动程序的注册: " + driver.getClass().getName(), e);
			}
		}
	}

	/**
	 * 根据指定属性创建连接池实例.
	 * 
	 * @param props
	 *            连接池属性
	 */
	private void createPools(Properties props) {
		Enumeration<?> propNames = props.propertyNames();
		while (propNames.hasMoreElements()) {
			String name = (String) propNames.nextElement();
			if (name.endsWith(".url")) {
				String poolName = name.substring(0, name.lastIndexOf("."));
				String url = props.getProperty(poolName + ".url");
				if (url == null) {
					log.error("没有为连接池" + poolName + "指定URL");
					continue;
				}
				String user = props.getProperty(poolName + ".user");
				String password = props.getProperty(poolName + ".password");
				String maxconn = props.getProperty(poolName + ".maxconn", "0");
				int max;
				try {
					max = Integer.valueOf(maxconn).intValue();
				} catch (NumberFormatException e) {
					log.error("错误的最大连接数限制: " + maxconn + " .连接池: " + poolName);
					max = 0;
				}
				DBConnectionPool pool = new DBConnectionPool(poolName, url, user, password, max);
				pools.put(poolName, pool);
				log.info("成功创建连接池" + poolName);
			} else if (name.endsWith("pool")) {
				this.defaultPoolName = props.getProperty(name);
			}
		}
	}

	/**
	 * 读取属性完成初始化
	 */
	private void init() {
		try {
			InputStream in = this.getClass().getResourceAsStream("db.properties");
			Properties dbProps = new Properties();
			try {
				dbProps.load(in);
			} catch (Exception e) {
				log.error("不能读取属性文件. " + "请确保db.properties在CLASSPATH指定的路径中");
				return;
			}
			loadDrivers(dbProps);
			createPools(dbProps);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 装载和注册所有JDBC驱动程序
	 * 
	 * @param props
	 *            属性
	 */
	private void loadDrivers(Properties props) {
		String driverClasses = props.getProperty("drivers");
		StringTokenizer st = new StringTokenizer(driverClasses);
		while (st.hasMoreElements()) {
			String driverClassName = st.nextToken().trim();
			try {
				Driver driver = (Driver) Class.forName(driverClassName).newInstance();
				DriverManager.registerDriver(driver);
				drivers.addElement(driver);
				log.info("成功注册JDBC驱动程序" + driverClassName);
			} catch (Exception e) {
				log.error("无法注册JDBC驱动程序: " + driverClassName, e);
			}
		}
	}

	class DBConnectionPool {
		private Vector<Connection> freeConnections = new Vector<Connection>();
		private int maxConn;
		private String name;
		private String password;
		private String URL;
		private String user;

		/**
		 * 创建新的连接池
		 * 
		 * @param name
		 *            连接池名字
		 * @param URL
		 *            数据库的JDBC URL
		 * @param user
		 *            数据库帐号,或 null
		 * @param password
		 *            密码,或 null
		 * @param maxConn
		 *            此连接池允许建立的最大连接数
		 */
		public DBConnectionPool(String name, String URL, String user, String password, int maxConn) {
			this.name = name;
			this.URL = URL;
			this.user = user;
			this.password = password;
			this.maxConn = maxConn;
		}

		/**
		 * 将不再使用的连接返回给连接池
		 * 
		 * @param con
		 *            客户程序释放的连接
		 */
		public synchronized void freeConnection(Connection con) {
			// 将指定连接加入到向量末尾
			try {
				if (con == connectionHandler.get()) {
					connectionHandler.remove();
				}

				if (con.isClosed()) {
					freeConnections.remove(con);
					log.info("before freeConnection con is closed");
					return;
				}
				freeConnections.addElement(con);

				Connection contest = (Connection) freeConnections.lastElement();
				if (contest.isClosed()) {
					freeConnections.remove(contest);
					log.info("after freeConnection contest is closed");
				}
				notifyAll();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}

		/**
		 * 从连接池获得一个可用连接.如没有空闲的连接且当前连接数小于最大连接 数限制,则创建新连接.<br/>
		 * 如原来登记为可用的连接不再有效,则从向量删除之, 然后递归调用自己以尝试新的可用连接.
		 */
		public synchronized Connection getConnection() {
			Connection con = connectionHandler.get();
			if (con == null) {
				if (freeConnections.size() > 0) {
					// 获取向量中第一个可用连接
					con = (Connection) freeConnections.firstElement();
					freeConnections.removeElementAt(0);
					try {
						if (con.isClosed()) {
							log.info("从连接池" + name + "删除一个无效连接");
							// 递归调用自己,尝试再次获取可用连接
							con = getConnection();
						}
					} catch (SQLException e) {
						log.info("从连接池" + name + "删除一个无效连接时错误");
						// 递归调用自己,尝试再次获取可用连接
						con = getConnection();
					}

					if (freeConnections.size() > maxConn) {
						log.info("删除一个溢出连接 ");
						releaseOne();
					}
				} else if ((maxConn == 0) || (freeConnections.size() < maxConn)) {
					con = newConnection();
				}
				connectionHandler.set(con);
			}
			return con;
		}

		public synchronized Connection returnConnection() {
			Connection con = connectionHandler.get();
			if (con == null) {
				// 如果闲置小于最大连接,返回一个新连接
				if (freeConnections.size() + clients < maxConn) {
					con = newConnection();
				} else if (freeConnections.size() + clients >= maxConn) {
					// 如果闲置大于最大连接，返回一个可用的旧连接
					con = (Connection) freeConnections.firstElement();
					log.info(" [a 连接池可用连接数 ] : " + "[ " + freeConnections.size() + " ]");
					freeConnections.removeElementAt(0);
					log.info(" [b 连接池可用连接数 ] : " + "[ " + freeConnections.size() + " ]");
					try {
						if (con.isClosed()) {
							log.info("从连接池" + name + "删除一个无效连接");
							returnConnection();
						}
					} catch (SQLException e) {
						log.info("从连接池" + name + "删除一个无效连接时错误");
						returnConnection();
					}
				}
				connectionHandler.set(con);
			}
			return con;
		}

		/**
		 * 从连接池获取可用连接.可以指定客户程序能够等待的最长时间 参见前一个getConnection()方法.
		 * 
		 * @param timeout
		 *            以毫秒计的等待时间限制
		 */
		public synchronized Connection getConnection(long timeout) {
			long startTime = new Date().getTime();
			Connection con;
			while ((con = getConnection()) == null) {
				try {
					wait(timeout);
				} catch (InterruptedException e) {
					log.error(e.getMessage(), e);
				}

				if ((new Date().getTime() - startTime) >= timeout) {
					// wait()返回的原因是超时
					return null;
				}
			}
			return con;
		}

		/**
		 * 关闭所有连接
		 */
		public synchronized void release() {
			Enumeration<?> allConnections = freeConnections.elements();
			while (allConnections.hasMoreElements()) {
				Connection con = (Connection) allConnections.nextElement();
				try {
					if (con == connectionHandler.get()) {
						connectionHandler.remove();
					}
					con.close();
					log.info("关闭连接池" + name + "中的一个连接");
				} catch (SQLException e) {
					log.error("无法关闭连接池" + name + "中的连接", e);
				}
			}
			freeConnections.removeAllElements();
		}

		/**
		 * 关闭一个连接
		 */
		public synchronized void releaseOne() {
			if (freeConnections.firstElement() != null) {
				Connection con = (Connection) freeConnections.firstElement();
				try {
					if (con == connectionHandler.get()) {
						connectionHandler.remove();
					}
					con.close();
					log.info("关闭连接池" + name + "中的一个连接");
					freeConnections.remove(con);
				} catch (SQLException e) {
					log.error("无法关闭连接池" + name + "中的连接", e);
				}
			} else {
				log.info("连接池中，无可用连接!");
			}
		}

		/**
		 * 创建新的连接
		 */
		private Connection newConnection() {
			Connection con = null;
			try {
				if (user == null) {
					con = DriverManager.getConnection(URL);
				} else {
					con = DriverManager.getConnection(URL, user, password);
				}
				log.info("连接池" + name + "创建一个新的连接");
			} catch (SQLException e) {
				log.error("无法创建下列URL的连接: " + URL, e);
				return null;
			}
			return con;
		}
	}

}