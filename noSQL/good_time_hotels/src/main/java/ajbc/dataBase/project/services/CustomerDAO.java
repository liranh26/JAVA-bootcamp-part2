package ajbc.dataBase.project.services;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

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

import ajbc.dataBase.project.models.Customer;
import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Order;
import ajbc.dataBase.project.utils.MyConnString;

public class CustomerDAO {
	
	public Customer getCustomerById(MongoCollection<Customer> collection,ObjectId id) {
		Customer customer = collection.find(Filters.eq("_id", id)).first();
		return customer;
	}
	
	public Customer getCustomerByName(MongoCollection<Customer> collection, String name) {
		Customer customer = collection.find(Filters.eq("first_name", name)).first();
		return customer;
	}
	
	public void updateHotelOrder(MongoCollection<Customer> collection, List<Order> orders) {
		for (Order order : orders) {
			FindOneAndReplaceOptions returnAfterOption = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
			Customer customer = collection.findOneAndReplace(Filters.eq("_id", order.getCustomerId()), getCustomerById(collection, order.getCustomerId()));
			System.out.println("The customer has updated: " + customer);
		}
	}
	
	public Customer insertOrder(MongoCollection<Customer> collection, Order order) {
		Customer tmp = getCustomerById(collection, order.getCustomerId());
		tmp.addOrder(order);
		return collection.findOneAndReplace(Filters.eq("_id", order.getCustomerId()), tmp);
	}
	
	public void deleteOrder(MongoCollection<Customer> collection, Order order) {
		collection.deleteOne(Filters.eq("orders._id", order.getId()));
	}
	
}
