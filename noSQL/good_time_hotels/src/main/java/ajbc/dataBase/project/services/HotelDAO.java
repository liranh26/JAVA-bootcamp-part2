package ajbc.dataBase.project.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import ajbc.dataBase.project.models.Hotel;

public class HotelDAO {

	public Hotel getHotel(MongoCollection<Hotel> collection, String hotelName) {
		Hotel hotel = collection.find(Filters.eq("name", hotelName)).first();
		return hotel;
	}
	
}
