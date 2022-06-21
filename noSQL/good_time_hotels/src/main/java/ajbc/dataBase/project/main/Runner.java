package ajbc.dataBase.project.main;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ajbc.dataBase.project.models.Customer;
import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Order;
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
			
			MongoCollection<Hotel> hotelColl = myDB.getCollection("hotels", Hotel.class);
			MongoCollection<Order> ordersColl = myDB.getCollection("orders", Order.class);
			MongoCollection<Customer> customerColl = myDB.getCollection("customers", Customer.class);
			
			/**** Q1 - get all the orders of a customer by id ****/
			Customer liran = customerDAO.getCustomerByName(customerColl, "Liran");
			System.out.println(orderDAO.getAllOrderByCustomerId(ordersColl, liran.getId()) );
			
			
			/**** Q2 - find hotels by a city name ****/
			System.out.println(hotelDAO.getHotelsByCity(hotelColl, "tel-aviv"));
			
			
			/**** Q3 - check if a hotel(id) has an available room in a specific date ****/
			
		}

	}

}
