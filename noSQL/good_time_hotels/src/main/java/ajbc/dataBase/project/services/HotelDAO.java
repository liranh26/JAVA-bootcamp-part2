package ajbc.dataBase.project.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import ajbc.dataBase.project.models.Hotel;
import ajbc.dataBase.project.models.Order;
import ajbc.dataBase.project.models.Room;

public class HotelDAO {
	private final int SUBTRACT = -1;

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
		tmpHotel.addIncome(tmpHotel.getPricePerNight() * order.getNights());
		for (int i = 0; i < order.getNights(); i++)
			room.addDate(order.getStartDate().plusDays(i));
	}

	private Room getAvailbleRoom(MongoCollection<Hotel> collection, Hotel hotel, Order order) {
		boolean isEmpty;
		for (Room room : hotel.getRooms()) {
			if (room == null)
				continue;

			isEmpty = true;
			// check if the room id occupied in the required dates
			for (int i = 0; i < order.getNights(); i++)
				if (room.getDatesReserved().contains(order.getStartDate().plusDays(i))) {
					isEmpty = false;
					break;
				}

			if (isEmpty)
				return room;
		} 
		return null;
	}

	public boolean hasAvailbeRoomAtDate(MongoCollection<Hotel> collection, ObjectId hotelId, LocalDate date) {

		Hotel hotel = getHotelById(collection, hotelId);
		boolean ans = false;
		int numRooms = 0;

		for (Room room : hotel.getRooms()) 
			if (room.getDatesReserved().contains(date)) 
				numRooms++;
			
		if (numRooms < hotel.getRooms().size())
			ans = true;

		return ans;
	}
	
	
	public void hasAvailbeRoom(MongoCollection<Document> collection, ObjectId hotelId, LocalDate date) {

		Bson unwind = unwind("$rooms");
		Bson unwind2 = unwind("$dates_reserved");
		Bson match = match(eq("_id", hotelId));
		Bson match2 = match(eq("dates_reserved.date", date));
		
		AggregateIterable<Document> doc = collection.aggregate(Arrays.asList(unwind, match));
		
		doc.forEach(d -> System.out.println(d.toJson(JsonWriterSettings.builder().indent(true).build())));
	
	}
	
	
	

	public List<Hotel> getHotelsByCity(MongoCollection<Hotel> collection, String city) {
		Bson filter = eq("address.city", city);
		return collection.find(filter).into(new ArrayList<>());
	}

	public void deleteOrder(MongoCollection<Hotel> collection, Order order) {
		Hotel hotel = getHotelById(collection, order.getHotelId());
		
		for (Room room : hotel.getRooms()) 
			if (room.getRoomOrders().remove(order.getId())) 
				// remove dates of room reserved
				for (int i = 0; i < order.getNights(); i++)
					room.getDatesReserved().remove(order.getStartDate().plusDays(i));

		hotel.getOrders().remove(order.getId());
		hotel.addIncome(hotel.getPricePerNight() * order.getNights() * SUBTRACT);

		Bson filter = eq("_id", hotel.getId());
		UpdateResult updateResult = collection.replaceOne(filter, hotel);
		System.out.println(updateResult);
	}

	public void sortHotelByIncome(MongoCollection<Document> collection) {
		Bson sort = sort(Sorts.descending("total_income"));
		Bson projectFields = project(fields(excludeId(), include("name", "total_income")));

		List<Document> res = collection.aggregate(Arrays.asList(sort, projectFields)).into(new ArrayList<>());
		res.forEach(d -> System.out.println(d.toJson(JsonWriterSettings.builder().indent(true).build())));
	}

}
