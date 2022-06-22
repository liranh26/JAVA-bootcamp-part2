package ajbc.dataBase.project.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import ajbc.dataBase.project.models.Customer;
import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Order;
import ajbc.dataBase.project.utils.MyConnString;


public class ordersDAO {
	
	HotelDAO hotelDAO = new HotelDAO();
	CustomerDAO customerDAO = new CustomerDAO();
	
	public List<Order> getAllOrderByCustomerId(MongoCollection<Order> collection, ObjectId id){

		return collection.find(Filters.eq("customer_id", id)).into(new ArrayList<>());
	}

	public void addOrder(MongoCollection<Order> orderColl, MongoCollection<Hotel> hotelColl,
			MongoCollection<Customer> customerColl, Order order) {

		if(hotelDAO.insertOrder(hotelColl, order) == null) {
			System.out.println("Dates not availble!");
			return;
		}
			
		customerDAO.insertOrder(customerColl, order);
		orderColl.insertOne(order);
	}


	public void deleteOrder(MongoCollection<Order> orderColl, MongoCollection<Hotel> hotelColl,
			MongoCollection<Customer> customerColl, Order order) {
		
		hotelDAO.deleteOrder(hotelColl, order);
		customerDAO.deleteOrder(customerColl, order);
		orderColl.findOneAndDelete(Filters.eq("_id", order.getId()));
	}
	
}

