package mongo.ajbc.pojo.exercise;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import mongo.ajbc.exercise.utils.ConnString;

public class PojoExe {

	public static void main(String[] args) {
		// set logger to Errors only
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// prepare codec registry
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(ConnString.uri())
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
				.codecRegistry(codecRegistry).build();
		
		try(MongoClient mongoClient = MongoClients.create(settings)){
			
			MongoDatabase myDB = mongoClient.getDatabase("sample_mflix");
			MongoCollection<Document> movies = myDB.getCollection("movies");
			MongoCollection<Document> comments = myDB.getCollection("comments");
			
//			numMoviesInYear(movies);
			
			Bson match = match(gte("date",LocalDateTime.of(2017, 1,1,0,0)));
			Bson pipeline = lookup("movies", "_id", "_id", "movie");
			Bson projectName = project(fields(include("name", "email", "text", "date"), computed("movie_title", "$movie_title")));
			List<Document> titlesJoin = comments.aggregate(Arrays.asList(match)).into(new ArrayList<>());
			
			titlesJoin.forEach(printDocuments());
			
		}
	}
	private static void numMoviesInYear(MongoCollection<Document> movies) {
		Bson match = match(eq("type", "movie"));
		Bson groupYear = group("$year", sum("total_movies",1));
		Bson project = project(fields(excludeId(), include("total_movies"), computed("year", "$_id")));
		Bson sort = sort(Sorts.descending("total_movies"));
		Bson limit = limit(10);
		
		List<Document> results = movies.aggregate(Arrays.asList(match, groupYear, project, sort, limit))
				.into(new ArrayList<>());
		
		results.forEach(printDocuments());
	}
	private static Consumer<Document> printDocuments() {
		return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
	}
}
