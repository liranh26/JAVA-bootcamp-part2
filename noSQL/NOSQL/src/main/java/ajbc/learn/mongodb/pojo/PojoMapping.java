package ajbc.learn.mongodb.pojo;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import com.mongodb.ConnectionString;
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
import com.mongodb.client.result.InsertOneResult;

import ajbc.learn.mongodb.models.Exam;
import ajbc.learn.mongodb.models.Student;
import ajbc.learn.util.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class PojoMapping {

	public static void main(String[] args) {
		// set logger to Errors only
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// prepare codec registry
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(MyConnectionString.uri())
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
				.codecRegistry(codecRegistry).build();
		
		try(MongoClient mongoClient = MongoClients.create(settings)){
			//db
			MongoDatabase myDB = mongoClient.getDatabase("DB_NAME");
			MongoCollection<Student> studentsCollection = myDB.getCollection("myCollection", Student.class);
			
			/* CRUD operations */
			//CREATE
			List<Exam> grades = Arrays.asList(
					new Exam("Java", 89), new Exam("Greek", 3));
			Student student = new Student(123456, 789, "Pojo", "Class test", grades);
//			System.out.println(student);
			
//			InsertOneResult result = studentsCollection.insertOne(student);
//			System.out.println(result.wasAcknowledged());
			
			
			//READ
			Student student1 = studentsCollection.find(Filters.eq("_id", new ObjectId("62b1666b4119e7298f38e607"))).first();
			System.out.println(student1);
			
			
			//UPDATE
			//remove all exams of student1
			student1.setExams(new ArrayList<Exam>()); 
			//setting option of returing from the db the object after the change to student2
			FindOneAndReplaceOptions returnAfterOption = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
			Student student2 = studentsCollection.findOneAndReplace(Filters.eq("_id", student1.getId()), student1);
			System.out.println(student2);
			
			//DELETE
			Bson filter = Filters.eq("_id", student1.getId());
			studentsCollection.deleteOne(filter);
		}
	}

}
