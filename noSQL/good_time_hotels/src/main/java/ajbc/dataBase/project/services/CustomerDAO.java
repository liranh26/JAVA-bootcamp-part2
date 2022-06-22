package ajbc.dataBase.project.services;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import ajbc.dataBase.project.models.Customer;
import ajbc.dataBase.project.models.Order;

public class CustomerDAO {

	public Customer getCustomerById(MongoCollection<Customer> collection, ObjectId id) {
		return collection.find(Filters.eq("_id", id)).first();
	}

	public Customer getCustomerByName(MongoCollection<Customer> collection, String name) {
		return collection.find(Filters.eq("first_name", name)).first();
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
