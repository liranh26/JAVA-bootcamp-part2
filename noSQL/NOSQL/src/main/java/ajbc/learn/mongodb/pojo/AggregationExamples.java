package ajbc.learn.mongodb.pojo;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;



import ajbc.learn.mongodb.models.Movies;
import ajbc.learn.util.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class AggregationExamples {
	
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
			MongoDatabase myDB = mongoClient.getDatabase("sample_training");
			MongoCollection<Document> zips = myDB.getCollection("zips");
			
//			matchByHawaii(zips);
			
//			groupCityInHawaiiSumPop(zips);
			
			show3mostPopCitiesInHawaii(zips);

		
			
		}
	}
	private static void show3mostPopCitiesInHawaii(MongoCollection<Document> zips) {
		//show 3 most populted cities in Hawaii
		Bson matchFilter =match(eq("state", "HI"));
		Bson groupCity = group("$city",sum("Total_pop", "$pop"), avg("avg_pop", "$pop")); //grouping by the value of the city '$' means value of
		// computed is like 'AS' in sql
		Bson projectFields = project(fields(excludeId(), include("Total_pop", "avg_pop"), computed("city", "$_id")));
		//sort by pop
		Bson sortByPopDesc = sort(Sorts.descending("total_pop"));
		Bson limit3 = limit(3);
		
		zips.aggregate(Arrays.asList(matchFilter));
		
		List<Document> zipsByMatch = zips.aggregate(Arrays.asList(matchFilter, groupCity, projectFields, sortByPopDesc, limit3)).into(new ArrayList<>());
		
		zipsByMatch.forEach(printDocuments());
		
	}
	private static void groupCityInHawaiiSumPop(MongoCollection<Document> zips) {
		//sums resdinets in honlulu
		Bson matchFilter =match(eq("state", "HI"));
		Bson groupCity = group("$city", sum("Total_pop", "$pop")); //grouping by the value of the city '$' means value of
		// computed is like 'AS' in sql
		Bson projectFields = project(fields(excludeId(), include("Total_pop"), computed("city", "$id")));
		
		zips.aggregate(Arrays.asList(matchFilter));
		
		List<Document> zipsByMatch = zips.aggregate(Arrays.asList(matchFilter, groupCity)).into(new ArrayList<>());
		
		zipsByMatch.forEach(printDocuments());
	}
	private static void matchByHawaii(MongoCollection<Document> zips) {
		Bson matchFilter = match(eq("state", "HI"));
		zips.aggregate(Arrays.asList(matchFilter));
		
		List<Document> zipsByMatch = zips.aggregate(Arrays.asList(matchFilter)).into(new ArrayList<>());
		
		zipsByMatch.forEach(printDocuments());
	}
	private static Consumer<Document> printDocuments(){
		return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
	}
}
