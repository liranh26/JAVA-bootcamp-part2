package ajbc.learn.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JdbcProductDao implements ProductDao {

	@Autowired
	private Connection connection;
	
	// add a connection to db
	@Override
	public long count() {

		String sql = "select count(*) from PRODUCTS";

		try (PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();) {
			
			if(resultSet.next()) {
				return resultSet.getLong(1); //gets  the first column
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

}
