package ajbc.dataBase.project.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Order;
import ajbc.dataBase.project.models.Room;


public class HotelDAO {
	MongoCollection<Hotel> collection;
	
	public List<Hotel> getHotles(MongoCollection<Hotel> collection){
		return collection.find().into(new ArrayList<>());
	}
	
	public Hotel getHotelById(MongoCollection<Hotel> collection, ObjectId id) {
		Hotel hotel = collection.find(Filters.eq("_id", id)).first();
		return hotel;
	}
	
	public Hotel getHotelByName(MongoCollection<Hotel> collection, String hotelName) {
		Hotel hotel = collection.find(Filters.eq("name", hotelName)).first();
		return hotel;
	}
	
	public Hotel insertOrder(MongoCollection<Hotel> collection, Order order) {
		Hotel tmp = getHotelById(collection, order.getHotelId());
		
		if(tmp.checkAvailbleRoom(order.getStartDate(), order.getNights())) {
			tmp.addOrderId(order);
		}
		return collection.findOneAndReplace(Filters.eq("_id", order.getHotelId()), tmp);
	}
	
	public List<Hotel> getHotelsByCity(MongoCollection<Hotel> collection, String city){
		
		List<Hotel> hotels = getHotles(collection);
		return hotels.stream().filter(h -> h.getAddress().getCity().equalsIgnoreCase(city)).collect(Collectors.toList());
	}
	
	public Room checkRoomAtDate(MongoCollection<Hotel> collection, ObjectId id, LocalDate date) {
		
		
		return null;
	}
}
