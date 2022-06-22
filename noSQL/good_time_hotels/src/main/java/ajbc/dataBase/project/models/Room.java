package ajbc.dataBase.project.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Room {

	private ObjectId id;
	private int number;
	@BsonProperty(value = "has_tub")
	private boolean hasTub;
	@BsonProperty(value = "dates_reserved")
<<<<<<< HEAD
	private List<LocalDate> datesReserved;
	@BsonProperty(value = "room_orders")
	private List<ObjectId> roomOrders;
=======
	private List<LocalDate> datesReserved = new ArrayList<LocalDate>();;
>>>>>>> parent of 71e405b (backup)
	
	public Room() {}
	
	public Room(ObjectId id, int number, boolean hasTub) {
		this.id = id;
		this.number = number;
		this.hasTub = hasTub;
<<<<<<< HEAD
		datesReserved = new ArrayList<LocalDate>();
		roomOrders = new ArrayList<ObjectId>(); 
	}
	
	public void addOrder(Order order) {
		roomOrders.add(order.getId());
	}
	
	public List<ObjectId> getRoomOrders() {
		return roomOrders;
	}

	public void setRoomOrders(List<ObjectId> roomOrders) {
		this.roomOrders = roomOrders;
	}

=======
//		datesReserved = new ArrayList<LocalDate>();
	}
	
>>>>>>> parent of 71e405b (backup)
	public List<LocalDate> getDatesReserved() {
		return datesReserved;
	}

	public void addDate(LocalDate date) {
		datesReserved.add(date);
		Collections.sort(datesReserved);
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isHasTub() {
		return hasTub;
	}

	public void setHasTub(boolean hasTub) {
		this.hasTub = hasTub;
	}
	
	

	@Override
	public String toString() {
<<<<<<< HEAD
		return "Room [id=" + id + ", number=" + number + ", hasTub=" + hasTub + ", datesReserved=" + datesReserved
				+ ", roomOrders=" + roomOrders + "]";
	}



=======
		return "Room [id=" + id + ", number=" + number + ", hasTub=" + hasTub + "]";
	}
	
	
>>>>>>> parent of 71e405b (backup)
	
}
