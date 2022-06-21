package ajbc.dataBase.project.services;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ajbc.dataBase.project.utils.MyConnString;


public class OrdersDAO {
	
	MongoClient mongoClient;
	JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
	
	public MongoDatabase getDb(String db) {
		ConnectionString connectionString = MyConnString.uri();
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
}
