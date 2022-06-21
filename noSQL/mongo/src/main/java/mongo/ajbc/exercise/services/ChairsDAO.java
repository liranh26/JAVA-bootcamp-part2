package mongo.ajbc.exercise.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import mongo.ajbc.exercise.models.Chair;
import mongo.ajbc.exercise.models.Measurement;
import mongo.ajbc.exercise.utils.MyConnectionString;

public class ChairsDAO {

	MongoClient mongoClient;
	JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
	
	public MongoDatabase getDb(String db) {
		ConnectionString connectionString = MyConnectionString.uri();
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();

		mongoClient = MongoClients.create(settings);

		MongoDatabase myDB = mongoClient.getDatabase(db);
		System.out.println("Connected to : " + db);

		return myDB;

	}

	public MongoCollection<Document> getCollection(String collectionName, MongoDatabase db) {

		MongoCollection<Document> myCollection = db.getCollection(collectionName);
		System.out.println(myCollection);

		return myCollection;
	}

	public String insertChair(Chair chair, MongoCollection<Document> collection) {
		String msg = collection.insertOne(createChairDoc(chair)).wasAcknowledged() ? "Succeeded" : "Failed";

		return msg + " inserting chair to DB!";
	}

	public String insertChairList(List<Chair> chairs, MongoCollection<Document> collection) {

		List<Document> chairsDoc = new ArrayList<Document>();
		for (Chair chair : chairs)
			chairsDoc.add(createChairDoc(chair));

		String msg = collection.insertMany(chairsDoc).wasAcknowledged() ? "Succeeded" : "Failed";

		return msg + " inserting chair list to DB!";
	}

	public Chair getChairById(ObjectId id, MongoCollection<Document> collection) {
		Document chairDoc = collection.find(eq("_id", id)).first();
		// convert by Gson
		Gson gson = new Gson();
		Type gsonType = new TypeToken<Measurement>(){}.getType();
		System.out.println(chairDoc.toJson(prettyPrint));
		return gson.fromJson(chairDoc.toJson(), Chair.class);
	}

	public List<Chair> getStools(MongoCollection<Document> collection) {
		FindIterable<Document> stoolsDoc = collection.find(eq("is_tool", true));
		MongoCursor<Document> cursor = stoolsDoc.iterator();
		List<Chair> stools = new ArrayList<>();

		Gson gson = new Gson();
		while (cursor.hasNext())
			stools.add(gson.fromJson(cursor.next().toJson(), Chair.class));

		return stools;
	}

	public List<Chair> getChairsManufacturerList(MongoCollection<Document> collection, List<String> manufacturers) {
		List<Chair> chairs = new ArrayList<Chair>();
		for (String manufacturer : manufacturers)
			chairs.addAll(getChairsByManufacturer(collection, manufacturer));

		return chairs;
	}

	public List<Chair> getChairsByManufacturer(MongoCollection<Document> collection, String manufacturer) {
		FindIterable<Document> chairDoc = collection.find(eq("Manufacturer", manufacturer));
		MongoCursor<Document> cursor = chairDoc.iterator();
		List<Chair> stools = new ArrayList<>();

		Gson gson = new Gson();
		while (cursor.hasNext())
			stools.add(gson.fromJson(cursor.next().toJson(), Chair.class));

		return stools;
	}

	public List<Chair> getChairsByPrice(MongoCollection<Document> collection, int minPrice, int maxPrice) {
		FindIterable<Document> stoolsDoc = collection.find(and(lte("price", maxPrice), gte("price", minPrice)));
		MongoCursor<Document> cursor = stoolsDoc.iterator();
		List<Chair> stools = new ArrayList<>();

		Gson gson = new Gson();
		while (cursor.hasNext())
			stools.add(gson.fromJson(cursor.next().toJson(), Chair.class));

		return stools;
	}

	public List<Chair> getChairsByMaxHeight(MongoCollection<Document> collection, int maxHeight) {
		FindIterable<Document> stoolsDoc = collection.find(lte("measurment.height", maxHeight));
		MongoCursor<Document> cursor = stoolsDoc.iterator();
		List<Chair> stools = new ArrayList<>();

		Gson gson = new Gson();
		while (cursor.hasNext())
			stools.add(gson.fromJson(cursor.next().toJson(), Chair.class));

		return stools;
	}
	
	
	public Document createChairDoc(Chair chair) {

		return new Document("_id", chair.getId()).append("Manufacturer", chair.getManufacturer())
				.append("model", chair.getModel()).append("is_tool", chair.isStool()).append("price", chair.getPrice())
				.append("measurment", createMeasureDoc(chair.getMeasurement()));
	}

	public Document createMeasureDoc(Measurement measurment) {
		return new Document("_id", measurment.getId()).append("height", measurment.getHieght())
				.append("width", measurment.getWidth()).append("depth", measurment.getDepth());
	}

	
	
	
	public void closeMongoClient() {
		mongoClient.close();
	}

}
