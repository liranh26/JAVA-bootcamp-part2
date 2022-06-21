package ajbc.dataBase.project.services;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import ajbc.dataBase.project.models.Customer;

public class CustomerDAO {

	public Customer getCustomer(MongoCollection<Customer> collection, ObjectId id) {
		Customer customer = collection.find(Filters.eq("_id", id)).first();
		return customer;
	}
	
	public Customer getCustomerByName(MongoCollection<Customer> collection, String name) {
		Customer customer = collection.find(Filters.eq("first_name", name)).first();
		return customer;
	}
	
//	public List<Customer> getCustomersList(MongoCollection<Customer> collection, ObjectId id) {
//		
//		Customer customer = collection.find(Filters.eq("_id", id)).first();
//		return customer;
//	}
	
	
}
