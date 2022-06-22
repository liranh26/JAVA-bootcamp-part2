package ajbc.dataBase.project.services;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Room;

public class RoomDAO {

	
	public Room getRoomById(MongoCollection<Room> collection, ObjectId id) {
		return collection.find(Filters.eq("rooms._id", id)).first();
	}
	
	
}
//CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
//CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
//
//MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(MyConnString.uri())
//		.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).codecRegistry(codecRegistry)
//		.build();
//try (MongoClient mongoClient = MongoClients.create(settings)) {
//	RoomDAO roomDAO = new RoomDAO();
//	MongoDatabase myDB = mongoClient.getDatabase("good_times_hotels");
//	MongoCollection<Room> hotelColl = myDB.getCollection("hotels", Room.class);
//	return roomDAO.getRoomById(hotelColl, id);
//}