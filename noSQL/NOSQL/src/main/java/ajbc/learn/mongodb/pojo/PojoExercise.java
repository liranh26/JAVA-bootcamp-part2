package ajbc.learn.mongodb.pojo;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import ajbc.learn.mongodb.models.Comments;
import ajbc.learn.mongodb.models.Movies;
import ajbc.learn.util.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class PojoExercise {

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
			MongoDatabase myDB = mongoClient.getDatabase("sample_mflix");
			MongoCollection<Movies> movies = myDB.getCollection("movies", Movies.class);
			MongoCollection<Comments> comments = myDB.getCollection("movies", Comments.class);
			
			Movies movie = movies.find(Filters.eq("_id", new ObjectId("573a1391f29313caabcd8979"))).first();
			System.out.println(movie);
			
//			List<Comments> commentsList = comments.find(Filters.eq("movies",));

		}
		
	}
}
