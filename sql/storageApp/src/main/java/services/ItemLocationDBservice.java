package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Item;
import models.ItemLocation;
import models.Location;
import utils.ConnectionUtils;

public class ItemLocationDBservice {

	ItemDBservices itemService = new ItemDBservices();
	LocationDBservice locationService = new LocationDBservice();

	public void addItemLocation(Connection connection, Item item, Location location) {

		if (itemService.getItem(connection, "%d".formatted(item.getItemId())) == null
				|| locationService.getLocation(connection, "%d".formatted(location.getLocationId())) == null)
			return;

		try (Statement statment = connection.createStatement()) {

			String query = "INSERT into ItemLocation (itemId, locationId)"
					+ "values(%d, %d);".formatted(item.getItemId(), location.getLocationId());
			int rowsAffected = statment.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public ItemLocation getItemLocation(Connection connection, ItemLocation itmLoc) {

		ItemLocation itemLocation = null;
		String query = "select * from ItemLocation where itemId = %d and locationId = %d".formatted(itmLoc.getItemId(), itmLoc.getLocationId()) ;

		try (Statement statment = connection.createStatement(); ResultSet resultSet = statment.executeQuery(query);) {

			if (resultSet.next()) {

				itemLocation = new ItemLocation();
				itemLocation.setItemId(resultSet.getInt(1));
				itemLocation.setLocationId(resultSet.getInt(2));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemLocation;
	}

	
	public ItemLocation getItemLocation(Connection connection, Item item, Location location) {

		ItemLocation itemLocation = null;
		String query = "select * from ItemLocation where itemId = %d and locationId = %d".formatted(item.getItemId(), location.getLocationId()) ;

		try (Statement statment = connection.createStatement(); ResultSet resultSet = statment.executeQuery(query);) {

			if (resultSet.next()) {

				itemLocation = new ItemLocation();
				itemLocation.setItemId(resultSet.getInt(1));
				itemLocation.setLocationId(resultSet.getInt(2));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemLocation;
	}
	
	
	public ItemLocation updateItemLocation(Connection connection, ItemLocation itemLocation, String newLocation) {
		
		Location loc = locationService.getLocation(connection, newLocation);
		
		if(loc != null) {
			
			try (Statement statment = connection.createStatement()){
			
				String query = "UPDATE ItemLocation " +
				"set itemId=%d, locationId= %s".formatted(itemLocation.getItemId(), 
						newLocation) + "\n where itemId = %s and locationId = %s".formatted(itemLocation.getItemId(), itemLocation.getLocationId());
				int rowsAffected = statment.executeUpdate(query);
				
				System.out.println("Success ! " + (rowsAffected) + " rows affected");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return itemLocation;
	}
	
	
	public ItemLocation deleteItemLocationByItem(Connection connection, ItemLocation itemLocation) {
		
		if (getItemLocation(connection, itemLocation) == null) {
			System.err.println("The Item does not exist in the DB!");
			return null;
		}

		try (Statement statment = connection.createStatement()) {

			String query = "Delete from ItemLocation where itemId = %d and location id = %d".formatted(itemLocation.getItemId(), itemLocation.getLocationId());
			int rowsAffected = statment.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return itemLocation; 
	}
	
	
	public List<ItemLocation> addListOfItemLocation(Connection connection, List<Item> items, List<Location> locations) {
		
		if(items.size() != locations.size())
			return null;
		
		ConnectionUtils.setConnectionCommitFalse(connection);

		String query = "INSERT into ItemLocation (itemId, locationId)" + "values(?,?)" ;
		List<ItemLocation> itmLoc = new ArrayList<ItemLocation>();
		
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)) {
			for (int i = 0; i < items.size(); i++) {
				
				preparedStatment.setInt(1, items.get(i).getItemId());
				preparedStatment.setInt(2, locations.get(i).getLocationId());

				itmLoc.add(new ItemLocation(items.get(i).getItemId(), locations.get(i).getLocationId()));
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

		return itmLoc;
		
	}
	

	public List<ItemLocation> updateListOfItemLocation(Connection connection, List<Item> items, List<Location> locations, List<String> updatedLocations) {
		
		ConnectionUtils.setConnectionCommitFalse(connection);

		if(items.size() != locations.size()) 
			return null;
		
		
		List<ItemLocation> itemLocations = new ArrayList<>();
		String query = "UPDATE ItemLocation " + "set itemId=? , locationId= ? " + " where itemid = ? and locationId = ?";

		try (PreparedStatement preparedStatment = connection.prepareStatement(query)) {

			for (int i = 0; i < items.size(); i++) {
				
				Item currItm = items.get(i);
				Location currLoc = locations.get(i);
				
				if (currItm != null && currLoc != null) {
					preparedStatment.setInt(1, currItm.getItemId());
					preparedStatment.setString(2, updatedLocations.get(i));
					preparedStatment.setInt(3, currItm.getItemId());
					preparedStatment.setInt(4, currLoc.getLocationId());

					preparedStatment.addBatch();
					itemLocations.add( getItemLocation(connection, currItm, currLoc) );
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
			itemLocations.removeAll(itemLocations);
		}

		ConnectionUtils.setConnectionCommitTrue(connection);

		return itemLocations;
	}
	
}
