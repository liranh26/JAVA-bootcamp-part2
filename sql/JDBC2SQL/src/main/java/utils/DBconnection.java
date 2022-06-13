package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

private String url;
private String serverAddress;
private String port;
private String databaseName;
private String serverName;

public DBconnection(String serverAddress, String port, String databaseName, String serverName) {
	setServerAddress(serverAddress);
	setPort(port);
	setDatabaseName(databaseName);
	setServerAddress(serverAddress);
	buildUrl();
}

public Connection createConnection(String userName, String password) {
	Connection connection = null;
	try {
		connection = DriverManager.getConnection(url, userName, password);
		if(connection.isValid(5))
			System.out.println("Connected to database");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	return connection;
}

private void buildUrl() {
	this.url = "jdbc:sqlserver://" + serverAddress + ":" + 
				port + ";databaseName=" + databaseName + ";servername=" +
				serverName + ";encrypt=false;" ;
}

public String getUrl() {
	return url;
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

@Override
public String toString() {
	return "DBconnection [url=" + url + ", serverAddress=" + serverAddress + ", port=" + port + ", databaseName="
			+ databaseName + ", serverName=" + serverName + "]";
}



}
