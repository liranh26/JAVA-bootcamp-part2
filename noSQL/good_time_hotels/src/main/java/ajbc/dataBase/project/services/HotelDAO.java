package ajbc.dataBase.project.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Order;
import ajbc.dataBase.project.models.Room;

public class HotelDAO {
	
	public Room getRoomById(MongoCollection<Hotel> collection, ObjectId id) {
		Hotel tmpHotel = collection.find(Filters.eq("rooms._id", id)).first();
		List<Room> room = tmpHotel.getRooms().stream().filter(r -> r.getId()==id).collect(Collectors.toList());
		return room.get(0); 
	}
	
	public List<Hotel> getHotles(MongoCollection<Hotel> collection) {
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
		Hotel tmpHotel = getHotelById(collection, order.getHotelId());
		
		Room room = getAvailbleRoom(collection, tmpHotel, order);
		
		addOrderDetails(tmpHotel, room, order);
		
		return collection.findOneAndReplace(Filters.eq("_id", order.getHotelId()), tmpHotel);
	}

	private void addOrderDetails(Hotel tmpHotel, Room room, Order order) {
		tmpHotel.addOrderId(order);
		
		for (int i = 0; i < order.getNights(); i++) 
			room.addDate(order.getStartDate().plusDays(i));			
	}
	
	
	private Room getAvailbleRoom(MongoCollection<Hotel> collection, Hotel hotel, Order order) {
		boolean empty;
		
		for (Room room : hotel.getRooms()) {
			empty = true;
			for (int i = 0; i < order.getNights(); i++) {
				if(!roomAtDate(collection, hotel.getId(), room.getId(), order.getStartDate().plusDays(i))) {
					empty = false;
					break;
				}
			}
			if(empty) {
				return room;
			}
		}

		return null;
	}

	public List<Hotel> getHotelsByCity(MongoCollection<Hotel> collection, String city) {
		Bson filter = eq("address.city", city);
		return collection.find(filter).into(new ArrayList<>());
	}

	
	
	
	//start date end date
	public List<Room> checkRoomAtDate(MongoCollection<Hotel> collection, ObjectId id, LocalDate date) {
		
		Bson match = match( and( eq("_id", id), ne("rooms.dates_reserved", date)));
		
		Hotel results = collection.aggregate(Arrays.asList(match)).first();
		
		return results.getRooms();
	}
	
	
	public boolean roomAtDate(MongoCollection<Hotel> collection, ObjectId hotelId, ObjectId roomId, LocalDate date) {
		
		Bson match = match( and( eq("_id", hotelId), eq("rooms._id", roomId), ne("rooms.dates_reserved", date)));
		Bson project = project(fields(excludeId(), include("rooms")));
		
		List<Hotel> results = collection.aggregate(Arrays.asList(match, project)).into(new ArrayList<>());
		System.out.println(results);
		
		return results != null ;
	}
	
	
}
