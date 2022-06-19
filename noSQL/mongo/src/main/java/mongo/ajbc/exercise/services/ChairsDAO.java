package mongo.ajbc.exercise.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import mongo.ajbc.exercise.models.Chair;
import mongo.ajbc.exercise.models.Measurement;
import mongo.ajbc.exercise.utils.MyConnectionString;

public class ChairsDAO {

	MongoClient mongoClient;

	public MongoDatabase getDb(String db) {
		ConnectionString connectionString = MyConnectionString.uri();
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
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
		for (Chair chair : chairs) {
			chairsDoc.add(createChairDoc(chair));
		}
		String msg = collection.insertMany(chairsDoc).wasAcknowledged() ? "Succeeded" : "Failed";

		return msg + " inserting chair list to DB!";
	}

	public Document createChairDoc(Chair chair) {

		return new Document("_id", new ObjectId()).append("Manufacturer", chair.getManufacturer())
				.append("model", chair.getModel()).append("is_tool", chair.isStool()).append("price", chair.getPrice())
				.append("measurment", createMeasureDoc(chair.getMeasurement()));
	}

	public Document createMeasureDoc(Measurement measurment) {
		return new Document("_id", new ObjectId()).append("height", measurment.getHieght())
				.append("width", measurment.getWidth()).append("depth", measurment.getDepth());
	}

	public void closeMongoClient() {
		mongoClient.close();
	}
}
