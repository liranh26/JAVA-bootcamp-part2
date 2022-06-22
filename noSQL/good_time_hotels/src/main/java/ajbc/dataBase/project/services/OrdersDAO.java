package ajbc.dataBase.project.services;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import ajbc.dataBase.project.models.Customer;
import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Order;
import ajbc.dataBase.project.utils.MyConnString;

public class OrdersDAO {



	HotelDAO hotelDAO = new HotelDAO();
	CustomerDAO customerDAO = new CustomerDAO();
	
	public Order getOrderById(MongoCollection<Order> collection, ObjectId id) {
		return collection.find(Filters.eq("_id", id)).first();
	}

	
	public List<Order> getAllOrderByCustomerId(MongoCollection<Order> collection, ObjectId id) {

		return collection.find(Filters.eq("customer_id", id)).into(new ArrayList<>());
	}
	
	

	public void addOrder(MongoCollection<Order> orderColl, MongoCollection<Hotel> hotelColl,
			MongoCollection<Customer> customerColl, Order order) {

		if(hotelDAO.insertOrder(hotelColl, order) == null) {
			System.out.println("Dates not availble!");
		}
			
		customerDAO.insertOrder(customerColl, order);

		orderColl.insertOne(order);
	}
	
	
	
}













	
	

//	public void deleteOrder(MongoCollection<Order> orderColl, MongoCollection<Hotel> hotelColl, MongoCollection<Customer> customerColl, Order order) {
//		
//		customerDAO.deleteOrder(customerColl, order);
//		hotelDAO.deleteOrder(hotelColl, order);
//		
//		orderColl.deleteOne(Filters.eq("_id", order.getId()));
//	}


//MongoClient mongoClient;
//JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
//
//public MongoDatabase getDb(String db) {
//	ConnectionString connectionString = MyConnString.uri();
//	MongoClientSettings settings = MongoClientSettings.builder()
//			.applyConnectionString(connectionString)
//			.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
//
//	mongoClient = MongoClients.create(settings);
//
//	MongoDatabase myDB = mongoClient.getDatabase(db);
//	System.out.println("Connected to : " + db);
//
//	return myDB;
//
//}
//
//public MongoCollection<Document> getCollection(String collectionName, MongoDatabase db) {
//
//	MongoCollection<Document> myCollection = db.getCollection(collectionName);
//	System.out.println(myCollection);
//
//	return myCollection;
//}