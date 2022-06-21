package ajbc.dataBase.project.services;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;

import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Order;
import ajbc.dataBase.project.utils.MyConnString;


public class HotelDAO {
	MongoCollection<Hotel> collection;
	
//	public HotelDAO() {
//		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
//		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
//		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(MyConnString.uri())
//				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).codecRegistry(codecRegistry)
//				.build();
//
//		try (MongoClient mongoClient = MongoClients.create(settings)) {
//			MongoDatabase myDB = mongoClient.getDatabase("good_times_hotels");
//			collection = myDB.getCollection("hotels", Hotel.class);
//		}
//	}
	
	public List<Hotel> getHotles(MongoCollection<Hotel> collection){
		return collection.find().into(new ArrayList<>());
	}
	
	public Hotel getHotelById(MongoCollection<Hotel> collection, ObjectId id) {
		Hotel hotel = collection.find(Filters.eq("_id", id)).first();
		return hotel;
	}
	
	public Hotel getHotelByName(MongoCollection<Hotel> collection, String hotelName) {
		Hotel hotel = collection.find(Filters.eq("name", hotelName)).first();
		return hotel;
	}
	
	public void updateHotelOrder(MongoCollection<Hotel> collection, List<Order> orders) {
		for (Order order : orders) {
			FindOneAndReplaceOptions returnAfterOption = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
			Hotel hotel = collection.findOneAndReplace(Filters.eq("_id", order.getHotelId()), getHotelById(collection, order.getHotelId()));
			System.out.println("The hotel has updated: " + hotel);
		}
	}
	
	public Hotel insertOrder(MongoCollection<Hotel> collection, Order order) {
		Hotel tmp = getHotelById(collection, order.getHotelId());
		tmp.addOrderId(order);
		return collection.findOneAndReplace(Filters.eq("_id", order.getHotelId()), tmp);
	}
}
