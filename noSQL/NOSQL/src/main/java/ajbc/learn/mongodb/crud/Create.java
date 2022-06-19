package ajbc.learn.mongodb.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;

import ajbc.learn.mongodb.models.Exam;
import ajbc.learn.mongodb.models.Student;
import ajbc.learn.util.MyConnectionString;

public class Create {

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

			JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
			
			final String DB_NAME = "my_own_db", COLLECTION="myCollection";
			MongoDatabase database = mongoClient.getDatabase("DB_NAME");

			//create a collection
			MongoCollection<Document> myCollection = database.getCollection(COLLECTION);	

			Document studentDoc = createStudentDoc(1, 2, "Liran", "Hadad");
			InsertOneResult insertResult = myCollection.insertOne(studentDoc);
			System.out.println(insertResult.wasAcknowledged());

			//create a POJO 
			List<Exam> exams = new ArrayList<>();
			exams.add(new Exam("Java", 99));
			exams.add(new Exam("Dishes", 54));
			Student student = new Student(1234, 5678, "Moshe", "Ufnik", exams);
			
			//convert POJO to JSON
			Gson gson = new Gson();
			String studJson = gson.toJson(student);
			System.out.println(studJson);
			
			//parse JSON to Document
			Document studentDoc1 = Document.parse(studJson);
			
			//parsing back to json to check everything ok...
			System.out.println(studentDoc1.toJson(prettyPrint));
			
			//insert the new student to the db
			InsertOneResult insertResult1 = myCollection.insertOne(studentDoc1);
			System.out.println(insertResult1.wasAcknowledged());
			
			
			//creating a list of student stright to doc
			List<Document> studDocs = new ArrayList<Document>();
			
			Student student2 = new Student(222, 444, "Shimi", "Tavory", exams);
			Student student3 = new Student(333, 555, "Avi", "Bitter", exams);
			
			String studJson2 = gson.toJson(student2);
			String studJson3 = gson.toJson(student3);
			
			Document studentDoc2 = Document.parse(studJson2);
			Document studentDoc3 = Document.parse(studJson3);
			
			studDocs.add(studentDoc2);
			studDocs.add(studentDoc3);
			
			//insert many to db 
			InsertManyResult manyResult = myCollection.insertMany(studDocs);
			boolean ack = manyResult.wasAcknowledged();
			System.out.println(ack);
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
