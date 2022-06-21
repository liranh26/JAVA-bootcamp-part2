package mongo.ajbc.exercise.main;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import mongo.ajbc.exercise.models.Chair;
import mongo.ajbc.exercise.models.Measurement;
import mongo.ajbc.exercise.services.ChairsDAO;


public class Runner {

	public static void main(String[] args) {
		
		ChairsDAO chairDao = new ChairsDAO();
		JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
		//connect to db
		MongoDatabase furnitureDB = chairDao.getDb("Furniture");
		
		//get chairs collection
		MongoCollection<Document> chairCollection = chairDao.getCollection("Chairs", furnitureDB);
		
		//create a new chair to insert
		Chair chair1 = new Chair( "liran", "classic", false, 150.5f, new Measurement(1.2, 0.5, 0.7));
		Chair chair2 = new Chair( "Snir", "vintage", false, 219.99f, new Measurement(1.1, 0.8, 0.8));
		Chair chair3 = new Chair( "vini", "old", true, 55f, new Measurement( 0.5, 0.5, 0.5));
		//CEATE - insert chair to db
//		System.out.println(chairDao.insertChair(chair3, chairCollection));
		
		//CREATE - insert list of chairs
		List<Chair> chairs = Arrays.asList(chair1, chair2, chair3);
//		System.out.println(chairDao.insertChairList(chairs, chairCollection));
		
		
		//READ - get a chair by id
		System.out.println(chairDao.getChairById(new ObjectId("62aee97d164245124b9d71de"), chairCollection));
//		System.out.println(chairDao.getStools(chairCollection));
//		System.out.println(chairDao.getChairsByPrice(chairCollection, 75, 160));
//		System.out.println(chairDao.getChairsByManufacturer(chairCollection, "Snir"));
//		System.out.println(chairDao.getChairsManufacturerList(chairCollection, Arrays.asList("Snir", "vini")));
//		System.out.println(chairDao.getChairsByMaxHeight(chairCollection, 1));
		
		//close the connection
		chairDao.closeMongoClient();
	}

}
