package ajbc.jdbc.storageApp.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import models.Item;
import models.ItemLocation;
import models.Location;
import services.ItemDBservices;
import services.ItemLocationDBservice;
import services.LocationDBservice;
import utils.DBconnectionManager;

public class Runner {

	private static final String PROPERTIES_FILE = "demo.properties";
	
	public static void main(String[] args) {
		
		Properties prop = new Properties();
		String serverAddress="", port="", databaseName="", serverName="", userName="", password="";
		
		try (FileInputStream fileInputStream = new FileInputStream(PROPERTIES_FILE);) {
			prop.load(fileInputStream);
			userName = prop.getProperty("user");
			password = prop.getProperty("password");
			serverAddress = prop.getProperty("server_address");
			port = prop.getProperty("port");
			databaseName = prop.getProperty("db_name");
			serverName = prop.getProperty("server_name");

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try(Connection connection = new DBconnectionManager(serverAddress, port, databaseName, serverName, userName,
				password).connect();) {
			System.out.println("Connection established!");
			
			ItemDBservices itemService = new ItemDBservices();
			LocationDBservice locationService = new LocationDBservice();
			ItemLocationDBservice itemLocationService = new ItemLocationDBservice();
			
			/******* Item & Location CREATE and insert *******/
//			Item item = new Item("computer", 1299.99, LocalDate.of(2019, 7, 10), 4);
//			itemService.addItem(connection, item);

//			Location location = new Location("Ashkelon", "UR10"); //"sdfsdf"
//			locationService.addLocation(connection, location);
			
//			itemLocationService.addItemLocation(connection, itemService.getItem(connection, "1001"), locationService.getLocation(connection, "120"));
			
			
			
			
			/******* Item & Location READ *******/
//			System.out.println(itemService.getItem(connection, "1003"));
//			System.out.println(locationService.getLocation(connection, "130"));
//			System.out.println(itemLocationService.getItemLocation(
//					connection, itemService.getItem(connection, "1003"), locationService.getLocation(connection, "100")));
			
			
			
			
			/******* Item & Location UPDATE *******/
			Item itemToChange = itemService.getItem(connection, "1004");
			Location locationToChange = locationService.getLocation(connection, "130");
//			
//			itemToChange.setName("JDBC!");
//			locationToChange.setName("trilili tralala");
			
//			locationService.updateLocation(connection, locationToChange);
//			itemService.updateItem(connection, itemToChange);
			
//			String newLocation = "200";
//			ItemLocation oldItmLoc = itemLocationService.getItemLocation(connection, itemToChange, locationToChange);
//			itemLocationService.updateItemLocation(connection, oldItmLoc, newLocation);
			
			
			

			/******* Item & Location DELETE *******/
//			itemLocationService.deleteItemLocationByItem(connection, itemLocationService.getItemLocation(connection, itemToChange, locationToChange));
//			locationService.deleteLocation(connection, locationToChange);
//			itemService.deleteItem(connection, itemToChange);
			
			
			
			
			/******* Item Batch READ & UPDATE *******/
			List<String> itmIds = Arrays.asList("1003","1004","1010");
			List<Item> itemList = itemService.getListItemsByIds(connection, itmIds);
			System.out.println(itemList);
			
//			itemList.get(0).setName("2222");
//			itemList.get(1).setName("1111");
//			List<Item> resultItems = itemService.updateListOfItems(connection, itemList);
//			System.out.println(resultItems);
			
			
			
			/******* Location Batch READ & UPDATE *******/
			List<String> locIds = Arrays.asList("130","190","150");
			List<Location> locationList = locationService.getListLocationsByIds(connection, locIds);
			System.out.println(locationList);
			
//			locationList.get(0).setName("Honolulu");
//			locationList.get(1).setName("Eilat");
//			locationList.get(2).setName("Kfar-Saba");
//			List<Location> resultLocations = locationService.updateListOfLocations(connection, locationList);
//			System.out.println(resultLocations);
			
			
			
			/******* ItemLocation Batch READ & UPDATE *******/
//			itemLocationService.addListOfItemLocation(connection, itemList, locationList);

			List<String> updatedLocations = Arrays.asList("190","100","130");
//			itemLocationService.updateListOfItemLocation(connection, itemList, locationList, updatedLocations);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	private static List<Item> createItems(){
		List<Item> items = new ArrayList();
		items.add(new Item("telescope", 1588.99, LocalDate.of(2018, 5, 5), 2));
		items.add(new Item("gyroscope", 211.99, LocalDate.of(2013, 5, 5), 3));
		items.add(new Item("compass", 55, LocalDate.of(2015, 5, 5), 10));
		return items;
	}
	
	
	private static void createAndSeedItemTable(ItemDBservices itemService, Connection connection) {
		itemService.createItemTable(connection);
		itemService.seedDB3Items(connection);
	}
	

}
