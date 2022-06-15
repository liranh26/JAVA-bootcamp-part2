package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Location;
import utils.ConnectionUtils;

public class LocationDBservice {

	public void addLocation(Connection connection, Location location) {
		
		try (Statement statment = connection.createStatement()) {
			if(isAccessCodeExist(location, connection))
				throw new SQLException("Location already exists in DB!"); //in real throw here custom exception

			String query = "INSERT into Location (name, accessCode)"
					+ "values('%s', '%s');".formatted(location.getName(), location.getAccessCode());
			int rowsAffected = statment.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public Location getLocation(Connection connection, String locationId){

		Location location = null;
		String query = "select * from Location where locationId = " + locationId;
		
		try (Statement statment = connection.createStatement(); ResultSet resultSet = statment.executeQuery(query);) {

			if(!isLocationExist(connection, locationId))
				throw new SQLException("Location doesnt exists in DB!"); //in real throw here custom exception
			
			if (resultSet.next()) {

				location = new Location();
				location.setLocationId(resultSet.getInt(1));
				location.setName(resultSet.getString(2));
				location.setAccessCode(resultSet.getString(3));
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
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

		ConnectionUtils.setConnectionCommitFalse(connection);

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
			ConnectionUtils.rollBackCon(connection);
		}

		ConnectionUtils.setConnectionCommitTrue(connection);

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

	
	public List<Location> updateListOfLocations(Connection connection, List<Location> locations) {

		ConnectionUtils.setConnectionCommitFalse(connection);

		List<String> idsUpdated = new ArrayList<>();
		String query = "UPDATE Location " + "set name=? , accessCode= ? \n where locationid = ?";

		try (PreparedStatement preparedStatment = connection.prepareStatement(query)) {

			for (Location location : locations) {
				if (location != null) {
					preparedStatment.setString(1, location.getName());
					preparedStatment.setString(2, location.getAccessCode());
					preparedStatment.setInt(3, location.getLocationId());

					preparedStatment.addBatch();
					idsUpdated.add("%s".formatted(location.getLocationId()));
				}else { // Forced error - only to present error handling 
					addFakeLocationToStatment(preparedStatment);
				}
			}

			int[] rowsAffected = preparedStatment.executeBatch();

			for (int i : rowsAffected) {
				if (i == 0)
					throw new SQLException("something went wrong!");
				System.out.println("Success ! " + i + " rows affected");
			}

		} catch (SQLException e) {
			System.err.println("Rolling back - " + e.getMessage());
			ConnectionUtils.rollBackCon(connection);
			idsUpdated.removeAll(idsUpdated);
		}

		ConnectionUtils.setConnectionCommitTrue(connection);

		return getListLocationsByIds(connection, idsUpdated);
	}
	
	
	public List<Location> getListLocationsByIds(Connection connection, List<String> ids) {

		List<Location> items = new ArrayList<Location>();
		try (Statement statement = connection.createStatement()) {

			for (String id : ids) {
				if (checkLocationExistById(connection, id))
					items.add(getLocation(connection, id));
				else
					items.add(null); // only to see the error msg & rollback
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}
	
	
	private void addFakeLocationToStatment(PreparedStatement preparedStatment) {
		try {
			preparedStatment.setString(1, "lala");
			preparedStatment.setString(2, "abc");
			preparedStatment.setString(3, "-1");
			preparedStatment.addBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean isAccessCodeExist(Location location, Connection connection) {

		String query = "select * from Location where accessCode = '%s'".formatted(location.getAccessCode());
		
		boolean exist = false;
		
		try (Statement statment = connection.createStatement(); 
				ResultSet resultSet = statment.executeQuery(query);) {
			exist = resultSet.next() ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}
	
	public boolean isLocationExist(Connection connection, String locationId) {

		String query = "select * from Location where locationId = '" + locationId +"'";
		
		boolean exist = false;
		
		try (Statement statment = connection.createStatement(); 
				ResultSet resultSet = statment.executeQuery(query);) {
			exist = resultSet.next() ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}
	

	private boolean checkLocationExistById(Connection connection, String id) {
		return getLocation(connection, id) != null;
	}
	
}
