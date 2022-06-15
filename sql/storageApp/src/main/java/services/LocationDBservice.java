package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Location;

public class LocationDBservice {

	public void addLocation(Connection connection, Location location) {

		try (Statement statment = connection.createStatement()) {

			String query = "INSERT into Location (name, accessCode)"
					+ "values('%s', '%s');".formatted(location.getName(), location.getAccessCode());
			int rowsAffected = statment.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Location getLocation(Connection connection, String locationId) {

		Location location = null;
		String query = "select * from Location where locationId = " + locationId;

		try (Statement statment = connection.createStatement(); ResultSet resultSet = statment.executeQuery(query);) {

			if (resultSet.next()) {

				location = new Location();
				location.setLocationId(resultSet.getInt(1));
				location.setName(resultSet.getString(2));
				location.setAccessCode(resultSet.getString(3));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return location;
	}

	public Location updateLocation(Connection connection, Location location) {
		Location loc = getLocation(connection, "%d".formatted(location.getLocationId()));
		if (loc == null) {
			System.err.println("The Item does not exist in the DB!");
			return null;
		}

		if (!location.equals(loc)) {
//			ItemLocationDBservice itmLocService = new ItemLocationDBservice();
			try (Statement statment = connection.createStatement()) {
				String query = "UPDATE Location "
						+ "set name='%s', accessCode= '%s'".formatted(location.getName(), location.getAccessCode())
						+ "\n where locationid = %d".formatted(location.getLocationId());

//				ItemLocation itmLoc = itmLocService.getItemLocationByLocation(connection, "%d".formatted(location.getLocationId()));
//				
//				String query2 = "UPDATE ItemLocation " +
//						"set itemId=%d, locationId= %d".formatted(itmLoc.getItemId(), 
//								itmLoc.getLocationId()) + "\n where locationId = %d".formatted(location.getLocationId());

				int rowsAffected = statment.executeUpdate(query);
//				int rowsAffected2 = statment.executeUpdate(query2);

				System.out.println("Success ! " + (rowsAffected) + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return location;
	}

	public Location deleteLocation(Connection connection, Location location) {
		Location loc = getLocation(connection, "%d".formatted(location.getLocationId()));

		if (loc == null)
			System.err.println("The Item does not exist in the DB!");

		try (Statement statment = connection.createStatement()) {

			String query = "Delete from Location where locationId = %d".formatted(location.getLocationId());
			int rowsAffected = statment.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return loc;
	}

	public List<Location> addListOfLocation(Connection connection, List<Location> locations) {

		setConnectionCommitFalse(connection);

		String query = "INSERT into Location (name, unitPrice, purchaseDate, quantity)" + "values(?, ?, ?, ?);";

		try (PreparedStatement preparedStatment = connection.prepareStatement(query)) {
			for (Location location : locations) {

				preparedStatment.setString(1, location.getName());
				preparedStatment.setString(2, location.getAccessCode());

				preparedStatment.addBatch();

			}

			int[] rowsAffected = preparedStatment.executeBatch();

			for (int i : rowsAffected) {
				System.out.println("Success ! " + i + " rows affected");
				if (i == 0)
					throw new SQLException("something went wrong!");
			}

		} catch (SQLException e) {
			System.err.println("Rolling back" + e.getMessage());
			rollBackCon(connection);
		}

		setConnectionCommitTrue(connection);

		return getLastLocations(connection, locations.size());
	}

	public List<Location> getLastLocations(Connection connection, int lastLocationsNum) {

		Location location = null;
		String query = "select * from Location";
		List<Location> lastLocations = new ArrayList<>();

		try (Statement statment = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY); ResultSet resultSet = statment.executeQuery(query);) {

			System.out.println(resultSet);

			resultSet.afterLast();
			int index = 1;
			while (resultSet.previous() && lastLocationsNum >= index) {

				location = new Location();
				location.setLocationId(resultSet.getInt(1));
				location.setName(resultSet.getString(2));
				location.setAccessCode(resultSet.getString(3));

				lastLocations.add(location);
				index++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lastLocations;
	}

	private void setConnectionCommitFalse(Connection connection) {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setConnectionCommitTrue(Connection connection) {
		try {
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void rollBackCon(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
