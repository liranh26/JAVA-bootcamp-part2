package ajbc.dataBase.project.main;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


import ajbc.dataBase.project.utils.MyConnString;
import ajbc.dataBase.project.utils.Utils;



public class SeedHotelsDB {

	public static void main(String[] args) {

		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(MyConnString.uri())
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).codecRegistry(codecRegistry)
				.build();

		try (MongoClient mongoClient = MongoClients.create(settings)) {
			
			Utils.seedCustomers(mongoClient);
			Utils.seedHotels(mongoClient);
			Utils.seedOrders(mongoClient);
	
		}

	}
	
}
