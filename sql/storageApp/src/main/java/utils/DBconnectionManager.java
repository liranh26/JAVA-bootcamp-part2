package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnectionManager {

	private String url;
	private String serverAddress;
	private String port;
	private String databaseName;
	private String serverName;
	private String userName;
	private String password;
	
	public DBconnectionManager( String serverAddress, String port, String databaseName, String serverName,
									String userName, String password) {
		setServerAddress(serverAddress);
		setPort(port); 
		setDatabaseName(databaseName);
		setServerName(serverName);
		setUserName(userName);
		setPassword(password);
		buildUrl();
	}
	
	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url, userName, password);
	}

	private void buildUrl() {
		this.url = "jdbc:sqlserver://" + serverAddress + ":" + 
					port + ";databaseName=" + databaseName + ";servername=" +
					serverName + ";encrypt=false;" ;
	}
	
	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "DBconnectionManager [url=" + url + "]";
	}
	
	
	
	
	
}
