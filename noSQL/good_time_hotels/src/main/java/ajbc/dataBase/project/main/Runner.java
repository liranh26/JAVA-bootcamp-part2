package ajbc.dataBase.project.main;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ajbc.dataBase.project.models.Customer;
import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Order;
import ajbc.dataBase.project.models.Room;
import ajbc.dataBase.project.services.CustomerDAO;
import ajbc.dataBase.project.services.HotelDAO;
import ajbc.dataBase.project.services.OrdersDAO;
import ajbc.dataBase.project.utils.MyConnString;
import ajbc.dataBase.project.utils.Utils;


public class Runner {

	public static void main(String[] args) {

		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(MyConnString.uri())
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).codecRegistry(codecRegistry)
				.build();

		OrdersDAO orderDAO = new OrdersDAO();
		CustomerDAO customerDAO = new CustomerDAO();
		HotelDAO hotelDAO = new HotelDAO();
		
		try (MongoClient mongoClient = MongoClients.create(settings)) {
			
			MongoDatabase myDB = mongoClient.getDatabase("good_times_hotels");
			
			MongoCollection<Order> ordersColl = myDB.getCollection("orders", Order.class);
			MongoCollection<Hotel> hotelColl = myDB.getCollection("hotels", Hotel.class);
			MongoCollection<Customer> customerColl = myDB.getCollection("customers", Customer.class);
			
			/**** Q1 - get all the orders of a customer by id ****/
			Customer liran = customerDAO.getCustomerByName(customerColl, "Liran");
//			System.out.println(orderDAO.getAllOrderByCustomerId(ordersColl, liran.getId()) );
			
			
			/**** Q2 - find hotels by a city name ****/
//			System.out.println(hotelDAO.getHotelsByCity(hotelColl, "Tel-Aviv"));
			
			
			/**** Q3 - check if a hotel(id) has an available room in a specific date ****/
			System.out.println(hotelDAO.hasAvailbeRoomAtDate(hotelColl,	hotelDAO.getHotelByName(hotelColl, "Bello").getId(), LocalDate.of(2022, 8, 9)));
		
			
			
			
			
			
			
			
			
			
			
//			MongoCollection<Document> collection = myDB.getCollection("hotels");
//		
//			Hotel hotel = hotelDAO.getHotelByName(hotelColl, "Bello");
//			
//			for (Room room : hotel.getRooms()) {
//				Bson match = match(eq("room_id", room.getId()));
//				Bson unwind = unwind("$room_orders");
//				AggregateIterable<Document> doc = collection.aggregate(Arrays.asList(match, unwind));
//				doc.forEach( d -> System.out.println((Date)d.get("dates_reserved")));
//			}
			
			
			

			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			/**** Q4 - create an order for a hotel room in a specific date for x number of nights ****/
//			Hotel bello = hotelDAO.getHotelByName(hotelColl, "Bello");
//			orderDAO.addOrder(ordersColl, hotelColl, customerColl
//					new Order(new ObjectId(), bello.getId(), liran.getId(),
//					LocalDate.of(2022, 5, 6), LocalDate.of(2022, 12, 12), 5, bello.getPricePerNight() * 5));
			
			
			/**** Q5 - cancel an order ****/
//			orderDAO.deleteOrder(ordersColl, hotelColl, customerColl, orderDAO.getOrderById(ordersColl, new ObjectId("62b2faf4b5d8b319ad4f1e52")));
//			hotelDAO.deleteOrder(hotelColl, orderDAO.getOrderById(ordersColl, new ObjectId("62b30ebd9b3dea42b1cf6d72")));
		}

	}

}
