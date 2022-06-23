package ajbc.learn.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JdbcProductDao implements ProductDao {

	private String serverAddress;
	private String port;
	private String databaseName;
	private String serverName;
	private String userName;
	private String password;

	// add a connection to db
	@Override
	public long count() {

		String sql = "select count(*) from customers";

		try (Connection connection = connection(); 
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();) {
			
			if(resultSet.next()) {
				return resultSet.getLong(1); //gets  the first column
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	private Connection connection() throws SQLException {

		return DriverManager.getConnection(url(), userName, password);
	}

	private String url() {
		return "jdbc:sqlserver://" + serverAddress + ":" + port + ";databaseName=" + databaseName + ";servername="
				+ serverName + ";" + ";encrypt=false";
	}

}
