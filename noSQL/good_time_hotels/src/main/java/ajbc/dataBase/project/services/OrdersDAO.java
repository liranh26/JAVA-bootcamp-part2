package ajbc.dataBase.project.services;

import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
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
import com.mongodb.client.model.Sorts;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;

import ajbc.dataBase.project.models.Customer;
import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Order;
import ajbc.dataBase.project.utils.MyConnString;


public class OrdersDAO {
	
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

	public void totalIncomeFromOrders(MongoCollection<Document> doc) {
		
		Bson group = group(null, sum("total_income", "$total_income"));
		Bson projectFields = project(fields(excludeId(), include("total_income")));
		
		List<Document> res = doc.aggregate(Arrays.asList(group, projectFields)).into(new ArrayList<>());
		res.forEach(d -> System.out.println(d.toJson(JsonWriterSettings.builder().indent(true).build())));
	
	}
	
}

