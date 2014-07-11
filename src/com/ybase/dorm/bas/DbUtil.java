package com.ybase.dorm.bas;

import java.sql.*;

import org.apache.log4j.Logger;

public class DbUtil {
	private static final Logger log = Logger.getLogger(DbUtil.class);
	private PreparedStatement pstmt = null;

	private Connection con = null;

	private static Connection longCon = null;

	public DbUtil() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/dormitory", "root", "password");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getCon() {
		return con;
	}

	public static Connection getConAuto() {
		try {
			if (longCon == null || longCon.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				longCon = DriverManager.getConnection("jdbc:mysql://localhost/dormitory", "root", "password");
				longCon.setAutoCommit(false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return longCon;
	}

	public static void closeLongConn(ResultSet rs, PreparedStatement pstmt) {
		try {
			if (longCon != null && !longCon.isClosed()) {
				longCon.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			if (rs != null) {
				rs.close();
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public static void closeLongConn2(ResultSet rs, PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}

			if (rs != null) {
				rs.close();
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void close() {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public void close(ResultSet rs, PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}

		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	public static void main(String args[]){
		Connection conn = DbUtil.getConAuto();
		System.out.println(conn);
	}
}
