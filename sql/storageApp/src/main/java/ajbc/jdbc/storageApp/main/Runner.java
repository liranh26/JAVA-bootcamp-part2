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
//			itemService.createTable(connection);
//			itemService.seedDB3Items(connection);
	
//			Item item = new Item("computer", 1299.99, LocalDate.of(2019, 7, 10), 4);
//			itemService.addItem(connection, item);
			
			Location location = new Location("Ashdod", "992K");
//			locationService.addLocation(connection, location);
			
			
//			System.out.println(itemService.getItem(connection, "1002"));
//			System.out.println(locationService.getLocation(connection, "110"));
			
//			ItemLocationDBservice itemLocationService = new ItemLocationDBservice();
//			itemLocationService.addItem(connection, itemService.getItem(connection, "1004"), locationService.getLocation(connection, "140"));
			
//			Item itemToChange = itemService.getItem(connection, "1001");
//			Location locationToChange = locationService.getLocation(connection, "150");
//			itemToChange.setName("JDBC!");
//			locationToChange.setLocationId(160);
//			locationService.updateLocation(connection, locationToChange);
			
//			itemService.updateItem(connection, itemToChange);
//			itemLocationService.deleteItemLocationByItem(connection, itemToChange);
//			locationService.deleteLocation(connection, locationToChange);
//			itemService.deleteItem(connection, itemToChange);
			
			
			List<String> ids = Arrays.asList("1002","1003","1040");
			List<Item> itemList = itemService.getListItemsByIds(connection, ids);
			itemList.get(0).setName("4444");
			itemList.get(1).setName("5555");
//			itemList.get(2).setName("6666");
			List<Item> res = itemService.updateListOfItems(connection, itemList);
					
	
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
	
	
	

}
