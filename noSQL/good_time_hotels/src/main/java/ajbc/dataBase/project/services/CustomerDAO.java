package ajbc.dataBase.project.services;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
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
	
	
	public Customer insertOrder(MongoCollection<Customer> collection, Order order) {
		Customer tmp = getCustomerById(collection, order.getCustomerId());
		tmp.addOrder(order);
		return collection.findOneAndReplace(Filters.eq("_id", order.getCustomerId()), tmp);
	}
	
	public Customer deleteOrder(MongoCollection<Customer> collection, Order order) {
		Bson filter = Filters.eq("orders._id", order.getId());
		Customer customer = collection.find(filter).first();
		
		customer.getOrders().remove(order);
	
		Bson insertFilter = Filters.eq("_id", customer.getId());
		return collection.findOneAndReplace(insertFilter, customer);
	}
	
}
