package ajbc.dataBase.project.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Hotel {

	private ObjectId id;
	private String name;
	private Address address;
	private int rank;
	private List<Room> rooms;
	@BsonProperty(value = "night_price")
	private float pricePerNight;
	private List<ObjectId> orders;
	private Map<LocalDate, List<Room>> dateTracker;
	
	public Hotel() {}
	
	public Hotel(ObjectId id, String name, Address address, int rank, List<Room> rooms, float pricePerNight,
			List<ObjectId> orders) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.rank = rank;
		this.rooms = rooms;
		this.pricePerNight = pricePerNight;
		this.orders = orders;
		dateTracker = new HashMap<LocalDate, List<Room>>();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public float getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(float pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public List<ObjectId> getOrders() {
		return orders;
	}

	public void setOrders(List<ObjectId> orders) {
		this.orders = orders;
	}
	
	public void addOrderId(Order order) {
		orders.add(order.getId());
		while(order.getNights() > 0) {
			if(dateTracker.get(order.getStartDate()) == null) {
				List<Room> addRoom = new ArrayList<Room>();
				addRoom.add(rooms.get(0));
				rooms.get(0).setOccupied(true);
				dateTracker.put(order.getStartDate(), addRoom);
			}else {
				for (int i = 0; i < rooms.size(); i++) {
					if(!rooms.get(i).isOccupied()) {
						dateTracker.get(order.getStartDate()).add(rooms.get(i));
						rooms.get(i).setOccupied(true);
					}					
				}
			}
			order.setNights(order.getNights()-1);
		}
	}

	public Map<LocalDate, List<Room>> getDateTracker() {
		return dateTracker;
	}

	public boolean checkAvailbleRoom(LocalDate date, int days) {
		boolean flag = true;
		if(dateTracker.get(date) == null)
			return flag;
		
		while(flag & days > 0) {
			flag = dateTracker.get(date).size() < rooms.size() ;
			date = date.plusDays(1);
			days--;
		}
		
		return flag;
	}
	
//	public void setDateTracker(Map<LocalDate, List<Room>> dateTracker) {
//		this.dateTracker = dateTracker;
//	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + ", address=" + address + ", rank=" + rank + ", rooms=" + rooms
				+ ", pricePerNight=" + pricePerNight + ", orders=" + orders + "]";
	}
	
	

}
