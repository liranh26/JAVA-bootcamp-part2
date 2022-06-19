package ajbc.learn.mongodb.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import ajbc.learn.util.MyConnectionString;

public class Basics {


	public static void main(String[] args) {

		ConnectionString connectionString = MyConnectionString.uri();
//		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
//				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
//
//				.applyToSocketSettings(builder -> {
//					builder.connectTimeout(1, TimeUnit.MINUTES);
//					builder.readTimeout(1, TimeUnit.MINUTES);
//				})
//
//				.applyToClusterSettings(builder -> builder.serverSelectionTimeout(1, TimeUnit.MINUTES)).build();
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
		
		try(MongoClient mongoClient = MongoClients.create(settings)){
			
			//print databases
//			mongoClient.listDatabaseNames().forEach(System.out::println);
			
			final String DB_NAME = "my_own_db", COLLECTION="myCollection";
			MongoDatabase database = mongoClient.getDatabase("DB_NAME");

			//print databases
//			System.out.println("----------- After DB Creation ------------");
//			mongoClient.listDatabaseNames().forEach(System.out::println);
			
			//create a collection
			MongoCollection<Document> myCollection = database.getCollection(COLLECTION);	
			System.out.println("----------- After creating a Collection ------------");
//			mongoClient.listDatabaseNames().forEach(System.out::println);
			
			Document studentDoc = createStudentDoc(1, 2, "Liran", "Hadad");
			
			InsertOneResult insertResult = myCollection.insertOne(studentDoc);
			System.out.println(insertResult.wasAcknowledged());
			
			
		}

		
	}

	public static Document createStudentDoc(int studentId, int classId, String firstName, String lastName) {
		
		Document studDoc = new Document("student_id", studentId).append("class_id", classId)
				.append("first_name", firstName).append("last_name", lastName);
		List<Document> scores = new ArrayList<Document>();
		
		scores.add(new Document("topic","java").append("score", 99));
		scores.add(new Document("topic","english").append("score", 95));
		scores.add(new Document("topic","c").append("score", 90));
		
		studDoc.append("exams", scores);
		
		
		return studDoc;
	}
	
}
