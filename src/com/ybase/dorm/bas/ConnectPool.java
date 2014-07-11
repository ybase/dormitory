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
 * ������DBConnectionManager֧�ֶ�һ�������������ļ���������ݿ�����
 * �صķ���.�ͻ�������Ե���getInstance()�������ʱ����Ψһʵ��.
 */
public class ConnectPool {
	private static final Logger log = Logger.getLogger(ConnectPool.class.getName());
	private static ThreadLocal<Connection> connectionHandler = new ThreadLocal<Connection>();
	static public ConnectPool instance; // Ψһʵ��
	static public int clients;
	public String defaultPoolName = "";
	public Vector<Driver> drivers = new Vector<Driver>(); // ����
	public Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>(); // ����

	/**
	 * ����Ψһʵ��.����ǵ�һ�ε��ô˷���,�򴴽�ʵ��
	 * 
	 * @return DBConnectionManager Ψһʵ��
	 */
	static synchronized public ConnectPool getInstance() {
		if (instance == null) {
			instance = new ConnectPool();
		}

		clients++;
		return instance;
	}

	/**
	 * ��������˽���Է�ֹ�������󴴽�����ʵ��
	 */
	private ConnectPool() {
		init();
	}

	/**
	 * �����Ӷ��󷵻ظ�������ָ�������ӳ�
	 * 
	 * @param name
	 *            �������ļ��ж�������ӳ�����
	 * @param con
	 *            ���Ӷ���
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
	 * ���һ�����õ�(���е�)����.���û�п�������,������������С����������� ����,�򴴽�������������
	 * 
	 * @param name
	 *            �������ļ��ж�������ӳ�����
	 * @return Connection �������ӻ�null
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
	 * ���һ����������.��û�п�������,������������С���������������, �򴴽�������������.����,��ָ����ʱ���ڵȴ������߳��ͷ�����.
	 * 
	 * @param name
	 *            ���ӳ�����
	 * @param time
	 *            �Ժ���Ƶĵȴ�ʱ��
	 * @return Connection �������ӻ�null
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
	 * �ر���������,�������������ע��
	 */
	public synchronized void release() {
		// �ȴ�ֱ�����һ���ͻ��������
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
				log.info("����JDBC�������� " + driver.getClass().getName() + "��ע��");
			} catch (SQLException e) {
				log.error("�޷���������JDBC���������ע��: " + driver.getClass().getName(), e);
			}
		}
	}

	/**
	 * ����ָ�����Դ������ӳ�ʵ��.
	 * 
	 * @param props
	 *            ���ӳ�����
	 */
	private void createPools(Properties props) {
		Enumeration<?> propNames = props.propertyNames();
		while (propNames.hasMoreElements()) {
			String name = (String) propNames.nextElement();
			if (name.endsWith(".url")) {
				String poolName = name.substring(0, name.lastIndexOf("."));
				String url = props.getProperty(poolName + ".url");
				if (url == null) {
					log.error("û��Ϊ���ӳ�" + poolName + "ָ��URL");
					continue;
				}
				String user = props.getProperty(poolName + ".user");
				String password = props.getProperty(poolName + ".password");
				String maxconn = props.getProperty(poolName + ".maxconn", "0");
				int max;
				try {
					max = Integer.valueOf(maxconn).intValue();
				} catch (NumberFormatException e) {
					log.error("������������������: " + maxconn + " .���ӳ�: " + poolName);
					max = 0;
				}
				DBConnectionPool pool = new DBConnectionPool(poolName, url, user, password, max);
				pools.put(poolName, pool);
				log.info("�ɹ��������ӳ�" + poolName);
			} else if (name.endsWith("pool")) {
				this.defaultPoolName = props.getProperty(name);
			}
		}
	}

	/**
	 * ��ȡ������ɳ�ʼ��
	 */
	private void init() {
		try {
			InputStream in = this.getClass().getResourceAsStream("db.properties");
			Properties dbProps = new Properties();
			try {
				dbProps.load(in);
			} catch (Exception e) {
				log.error("���ܶ�ȡ�����ļ�. " + "��ȷ��db.properties��CLASSPATHָ����·����");
				return;
			}
			loadDrivers(dbProps);
			createPools(dbProps);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * װ�غ�ע������JDBC��������
	 * 
	 * @param props
	 *            ����
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
				log.info("�ɹ�ע��JDBC��������" + driverClassName);
			} catch (Exception e) {
				log.error("�޷�ע��JDBC��������: " + driverClassName, e);
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
		 * �����µ����ӳ�
		 * 
		 * @param name
		 *            ���ӳ�����
		 * @param URL
		 *            ���ݿ��JDBC URL
		 * @param user
		 *            ���ݿ��ʺ�,�� null
		 * @param password
		 *            ����,�� null
		 * @param maxConn
		 *            �����ӳ������������������
		 */
		public DBConnectionPool(String name, String URL, String user, String password, int maxConn) {
			this.name = name;
			this.URL = URL;
			this.user = user;
			this.password = password;
			this.maxConn = maxConn;
		}

		/**
		 * ������ʹ�õ����ӷ��ظ����ӳ�
		 * 
		 * @param con
		 *            �ͻ������ͷŵ�����
		 */
		public synchronized void freeConnection(Connection con) {
			// ��ָ�����Ӽ��뵽����ĩβ
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
		 * �����ӳػ��һ����������.��û�п��е������ҵ�ǰ������С��������� ������,�򴴽�������.<br/>
		 * ��ԭ���Ǽ�Ϊ���õ����Ӳ�����Ч,�������ɾ��֮, Ȼ��ݹ�����Լ��Գ����µĿ�������.
		 */
		public synchronized Connection getConnection() {
			Connection con = connectionHandler.get();
			if (con == null) {
				if (freeConnections.size() > 0) {
					// ��ȡ�����е�һ����������
					con = (Connection) freeConnections.firstElement();
					freeConnections.removeElementAt(0);
					try {
						if (con.isClosed()) {
							log.info("�����ӳ�" + name + "ɾ��һ����Ч����");
							// �ݹ�����Լ�,�����ٴλ�ȡ��������
							con = getConnection();
						}
					} catch (SQLException e) {
						log.info("�����ӳ�" + name + "ɾ��һ����Ч����ʱ����");
						// �ݹ�����Լ�,�����ٴλ�ȡ��������
						con = getConnection();
					}

					if (freeConnections.size() > maxConn) {
						log.info("ɾ��һ��������� ");
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
				// �������С���������,����һ��������
				if (freeConnections.size() + clients < maxConn) {
					con = newConnection();
				} else if (freeConnections.size() + clients >= maxConn) {
					// ������ô���������ӣ�����һ�����õľ�����
					con = (Connection) freeConnections.firstElement();
					log.info(" [a ���ӳؿ��������� ] : " + "[ " + freeConnections.size() + " ]");
					freeConnections.removeElementAt(0);
					log.info(" [b ���ӳؿ��������� ] : " + "[ " + freeConnections.size() + " ]");
					try {
						if (con.isClosed()) {
							log.info("�����ӳ�" + name + "ɾ��һ����Ч����");
							returnConnection();
						}
					} catch (SQLException e) {
						log.info("�����ӳ�" + name + "ɾ��һ����Ч����ʱ����");
						returnConnection();
					}
				}
				connectionHandler.set(con);
			}
			return con;
		}

		/**
		 * �����ӳػ�ȡ��������.����ָ���ͻ������ܹ��ȴ����ʱ�� �μ�ǰһ��getConnection()����.
		 * 
		 * @param timeout
		 *            �Ժ���Ƶĵȴ�ʱ������
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
					// wait()���ص�ԭ���ǳ�ʱ
					return null;
				}
			}
			return con;
		}

		/**
		 * �ر���������
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
					log.info("�ر����ӳ�" + name + "�е�һ������");
				} catch (SQLException e) {
					log.error("�޷��ر����ӳ�" + name + "�е�����", e);
				}
			}
			freeConnections.removeAllElements();
		}

		/**
		 * �ر�һ������
		 */
		public synchronized void releaseOne() {
			if (freeConnections.firstElement() != null) {
				Connection con = (Connection) freeConnections.firstElement();
				try {
					if (con == connectionHandler.get()) {
						connectionHandler.remove();
					}
					con.close();
					log.info("�ر����ӳ�" + name + "�е�һ������");
					freeConnections.remove(con);
				} catch (SQLException e) {
					log.error("�޷��ر����ӳ�" + name + "�е�����", e);
				}
			} else {
				log.info("���ӳ��У��޿�������!");
			}
		}

		/**
		 * �����µ�����
		 */
		private Connection newConnection() {
			Connection con = null;
			try {
				if (user == null) {
					con = DriverManager.getConnection(URL);
				} else {
					con = DriverManager.getConnection(URL, user, password);
				}
				log.info("���ӳ�" + name + "����һ���µ�����");
			} catch (SQLException e) {
				log.error("�޷���������URL������: " + URL, e);
				return null;
			}
			return con;
		}
	}

}