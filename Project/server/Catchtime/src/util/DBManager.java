package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	private final String DRIVER_STR = "com.mysql.jdbc.Driver";
	private final String CONN_STR = "jdbc:mysql://localhost:3306/catchtime";
	private final String USER = "root";
	private final String PWD = "";
	private static DBManager dbManager;
	
	private Connection conn;
	
	public DBManager() {
		
	}

	static {
		
	}
	public static DBManager getInstance(){
		if(null == dbManager) {
			dbManager = new DBManager();
		}
		return dbManager;
	}
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if(null == conn || conn.isClosed()) {
			Class.forName(DRIVER_STR);
			conn = DriverManager.getConnection(
					CONN_STR, USER, PWD);
		}
		return conn;
		
	}
	public void closeConnection() throws SQLException {
		if(null != conn || !conn.isClosed()) {
			conn.close();
		}
	}
}