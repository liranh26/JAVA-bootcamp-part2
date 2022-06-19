package mongo.ajbc.exercise.main;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import mongo.ajbc.exercise.models.Chair;
import mongo.ajbc.exercise.models.Measurement;
import mongo.ajbc.exercise.services.ChairsDAO;


public class Runner {

	public static void main(String[] args) {
		ChairsDAO chairDao = new ChairsDAO();
		
		//connect to db
		MongoDatabase furnitureDB = chairDao.getDb("Furniture");
		
		//get chairs collection
		MongoCollection<Document> chairCollection = chairDao.getCollection("Chairs", furnitureDB);
		
		//create a new chair to insert
		Chair chair1 = new Chair("", "liran", "classic", false, 150.5f, new Measurement("", 1.2, 0.5, 0.7));
		Chair chair2 = new Chair("", "Snir", "vintage", false, 219.99f, new Measurement("", 1.1, 0.8, 0.8));
		
		//insert chair to db
		System.out.println(chairDao.insertChair(chair1, chairCollection));
		
		//insert list of chairs
		List<Chair> chairs = Arrays.asList(chair1, chair2);
		System.out.println(chairDao.insertChairList(chairs, chairCollection));
		
		
		//close the connection
		chairDao.closeMongoClient();
	}

}
