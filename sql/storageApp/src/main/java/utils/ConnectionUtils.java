package utils;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {

	public static void setConnectionCommitTrue(Connection connection) {
		try {
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void setConnectionCommitFalse(Connection connection) {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void rollBackCon(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
