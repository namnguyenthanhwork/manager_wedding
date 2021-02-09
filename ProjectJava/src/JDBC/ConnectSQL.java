package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSQL {
	private Connection conn;
	
	/**
	 * connect to mysql after call function
	 */
	public ConnectSQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String DB_URL = "jdbc:mysql://localhost:3306/your_schemas?autoReconnect=true&useSSL=false";
			String USER_NAME = "root";
			String PASSWORD = "your password";
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		} catch (Exception ex) {
			System.out.println("Connect failure!");
			ex.printStackTrace();
		}
	}
	
	/**
	 * get connection
	 * @return object Connection
	 */
	public Connection getConnection() {
		return this.conn;
	}
	
	/**
	 * close connection after finish
	 */
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}