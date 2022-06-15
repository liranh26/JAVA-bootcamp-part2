package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.Item;
import models.ItemLocation;
import models.Location;

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

	public ItemLocation getItemLocationByItem(Connection connection, String itemId) {

		ItemLocation itemLocation = null;
		String query = "select * from ItemLocation where itemId = " + itemId;

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

	public ItemLocation getItemLocationByLocation(Connection connection, String locationId) {

		ItemLocation itemLocation = null;
		String query = "select * from ItemLocation where locationId = " + locationId;

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
	
	
//	public ItemLocation updateItemLocationByItem(Connection connection, ItemLocation itemLocation, String itemId) {
//		
//		Item itm = itemService.getItem(connection, itemId);
//		
//		if(itm != null) {
//			
//			try (Statement statment = connection.createStatement()){
//			
//				String query = "UPDATE ItemLocation " +
//				"set itemId=%d, locationId= %d".formatted(itemLocation.getItemId(), 
//						itemLocation.getLocationId()) + "\n where itemId = " + itemId;
//				int rowsAffected = statment.executeUpdate(query);
//				
//				System.out.println("Success ! " + (rowsAffected) + " rows affected");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return itemLocation;
//	}
	

	public ItemLocation deleteItemLocationByItem(Connection connection, Item item) {
		ItemLocation itmLoc = getItemLocationByItem(connection, "%d".formatted(item.getItemId()));

		if (itmLoc == null)
			System.err.println("The Item does not exist in the DB!");

		try (Statement statment = connection.createStatement()) {

			String query = "Delete from ItemLocation where itemId = %d".formatted(item.getItemId());
			int rowsAffected = statment.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return itmLoc;
	}
	
	
	
	public void addListOfItemLocation() {
		
	}
	
	
	
}
