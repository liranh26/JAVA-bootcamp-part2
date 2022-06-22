package ajbc.dataBase.project.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Order;
import ajbc.dataBase.project.models.Room;

public class HotelDAO {

	public Room getRoomById(MongoCollection<Hotel> collection, ObjectId id) {
		Hotel tmpHotel = collection.find(Filters.eq("rooms._id", id)).first();
		List<Room> room = tmpHotel.getRooms().stream().filter(r -> r.getId() == id).collect(Collectors.toList());
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
		
		if (room == null)
			return null;

		addOrderDetails(tmpHotel, room, order);

		return collection.findOneAndReplace(Filters.eq("_id", order.getHotelId()), tmpHotel);
	}

	private void addOrderDetails(Hotel tmpHotel, Room room, Order order) {
		tmpHotel.addOrderId(order);
		room.addOrder(order);

		for (int i = 0; i < order.getNights(); i++)
			room.addDate(order.getStartDate().plusDays(i));
	}

	
	private Room getAvailbleRoom(MongoCollection<Hotel> collection, Hotel hotel, Order order) {
		boolean empty;

		for (Room room : hotel.getRooms()) {
			empty = true;
			for (int i = 0; i < order.getNights(); i++) {
				if(room.getDatesReserved().contains(order.getStartDate().plusDays(i))) {
					empty = false;
					break;
				}
			}
			if (empty) {
				return room;
			}
		}

		return null;
	}

	
	// TODO fix double dates
	public boolean hasAvailbeRoomAtDate(MongoCollection<Hotel> collection, ObjectId hotelId, LocalDate date) {

		Hotel hotel = getHotelById(collection, hotelId);
		boolean ans = false;
		int numRooms = 0;

		for (Room room : hotel.getRooms()) {
			if(room.getDatesReserved().contains(date)) {
				System.out.println("Occupied!");
				numRooms++;
			}
		}
		if(numRooms < hotel.getRooms().size())
			ans = true;
	
		return ans;
//		Bson filter = eq("_id", hotelId);
//		Bson filter2 = nin("rooms.dates_reserved", date);
//		Bson filters = combine(filter, filter2);
//		System.out.println(collection.find(filters).into(new ArrayList<>()));
	}

	public List<Hotel> getHotelsByCity(MongoCollection<Hotel> collection, String city) {
		Bson filter = eq("address.city", city);
		return collection.find(filter).into(new ArrayList<>());
	}

	public void deleteOrder(MongoCollection<Hotel> collection, Order order) {

		Bson hotelFilter = eq("_id", order.getHotelId());

		Bson roomFilter = eq("rooms.*.room_orders", order.getId());
		Bson filters = combine(hotelFilter, roomFilter);

		collection.findOneAndDelete(filters);

//		if(hotel.getRooms() != null)
//			for (Room room : hotel.getRooms()) {
//				if(room.getRoomOrders().contains(order)) 
//					for (int i = 0; i < order.getNights(); i++) {
//						room.getDatesReserved().remove(order.getStartDate().plusDays(i));	
//					
//					room.getRoomOrders().remove(order);
//				}
//			}
//		hotel.getOrders().remove(order.getId());
//		
//		updateHotel(collection, hotel);
	}

	public void updateHotel(MongoCollection<Hotel> collection, Hotel hotel) {
		Bson filter = eq("_id", hotel.getId());
		collection.updateOne(filter, (Bson) hotel);
	}

}
