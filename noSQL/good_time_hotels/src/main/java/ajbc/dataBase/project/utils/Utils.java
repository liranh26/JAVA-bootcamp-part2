package ajbc.dataBase.project.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;

import ajbc.dataBase.project.models.Address;
import ajbc.dataBase.project.models.Customer;
import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Order;
import ajbc.dataBase.project.models.Room;
import ajbc.dataBase.project.services.CustomerDAO;
import ajbc.dataBase.project.services.HotelDAO;
import ajbc.dataBase.project.services.OrdersDAO;

import java.util.Random;

public class Utils {

	public static String seedCustomers(MongoClient mongoClient) {
		MongoDatabase myDB = mongoClient.getDatabase("good_times_hotels");

		MongoCollection<Customer> collection = myDB.getCollection("customers", Customer.class);
		List<Customer> customers = Arrays.asList(
				new Customer(new ObjectId(), "Liran", "Hadad", "Italy", Arrays.asList()),
				new Customer(new ObjectId(), "Sapir", "Hadad", "Poland", Arrays.asList()),
				new Customer(new ObjectId(), "Guy", "Tordjman", "USA", Arrays.asList()),
				new Customer(new ObjectId(), "Amir", "Shachar", "Israel", Arrays.asList()),
				new Customer(new ObjectId(), "Hodaya", "David", "Israel", Arrays.asList()));

		collection.drop();
		String msg = collection.insertMany(customers).wasAcknowledged() ? "Succeeded" : "Failed";

		return msg + " inserting chair list to DB!";
	}

	public static String seedHotels(MongoClient mongoClient) {
		MongoDatabase myDB = mongoClient.getDatabase("good_times_hotels");

		MongoCollection<Hotel> collection = myDB.getCollection("hotels", Hotel.class);
		List<Hotel> hotels = Arrays.asList(
				new Hotel(new ObjectId(), "Hermoso", new Address("Gordon", "12", "Tel-Aviv", "Israel"), 8,
						Arrays.asList(new Room(new ObjectId(), 2, false), new Room(new ObjectId(), 2, false),
								new Room(new ObjectId(), 2, false), new Room(new ObjectId(), 2, false)),
						1234.99f, new ArrayList<ObjectId>()),
				new Hotel(new ObjectId(), "Lindo", new Address("Herzel", "1", "Haifa", "Israel"), 7,
						Arrays.asList(new Room(new ObjectId(), 4, true), new Room(new ObjectId(), 4, true),
								new Room(new ObjectId(), 4, true)),
						2000f, new ArrayList<ObjectId>()),
				new Hotel(new ObjectId(), "Bello", new Address("Hanasi", "1", "Eilat", "Israel"), 7,
						Arrays.asList(new Room(new ObjectId(), 3, true), new Room(new ObjectId(), 3, true)), 1500f,
						new ArrayList<ObjectId>()));
		collection.drop();
		String msg = collection.insertMany(hotels).wasAcknowledged() ? "Succeeded" : "Failed";

		return msg + " inserting chair list to DB!";
	}

	public static void seedOrders(MongoClient mongoClient) {
		HotelDAO hotelsDAO = new HotelDAO();
		CustomerDAO customerDAO = new CustomerDAO();

		MongoDatabase myDB = mongoClient.getDatabase("good_times_hotels");

		MongoCollection<Order> ordersColl = myDB.getCollection("orders", Order.class);
		MongoCollection<Hotel> hotelColl = myDB.getCollection("hotels", Hotel.class);
		MongoCollection<Customer> customerColl = myDB.getCollection("customers", Customer.class);

		Hotel hermoso = hotelsDAO.getHotelByName(hotelColl, "Hermoso");
		Hotel lindo = hotelsDAO.getHotelByName(hotelColl, "Lindo");
		Hotel bello = hotelsDAO.getHotelByName(hotelColl, "Bello");

		Customer liran = customerDAO.getCustomerByName(customerColl, "Liran");
		Customer guy = customerDAO.getCustomerByName(customerColl, "Guy");
		Customer sapir = customerDAO.getCustomerByName(customerColl, "Sapir");
		Customer amir = customerDAO.getCustomerByName(customerColl, "Amir");

		List<Order> orders = Arrays.asList(
				new Order(new ObjectId(), hermoso.getId(), liran.getId(), LocalDate.of(2022, 1, 1),
						LocalDate.of(2022, 10, 10), 5, hermoso.getPricePerNight() * 5),

				new Order(new ObjectId(), bello.getId(), guy.getId(), LocalDate.of(2022, 5, 6),
						LocalDate.of(2022, 12, 12), 5, bello.getPricePerNight() * 5),

				new Order(new ObjectId(), bello.getId(), liran.getId(), LocalDate.of(2021, 11, 12),
						LocalDate.of(2022, 8, 9), 4, bello.getPricePerNight() * 4),

				new Order(new ObjectId(), bello.getId(), amir.getId(), LocalDate.of(2021, 11, 12),
						LocalDate.of(2022, 8, 9), 4, bello.getPricePerNight() * 4),

				new Order(new ObjectId(), lindo.getId(), sapir.getId(), LocalDate.of(2022, 5, 6),
						LocalDate.of(2022, 12, 12), 5, lindo.getPricePerNight() * 5));

		ordersColl.drop();

		OrdersDAO ordersDAO = new OrdersDAO();

		for (Order order : orders)
			ordersDAO.addOrder(ordersColl, hotelColl, customerColl, order);

	}

}
