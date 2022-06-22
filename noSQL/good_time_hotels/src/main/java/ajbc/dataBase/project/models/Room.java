package ajbc.dataBase.project.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Room {

	private ObjectId id;
	private int number;
	@BsonProperty(value = "has_tub")
	private boolean hasTub;
	@BsonProperty(value = "is_occupied")
	private boolean isOccupied;
	
	public Room() {}
	
	public Room(ObjectId id, int number, boolean hasTub) {
		this.id = id;
		this.number = number;
		this.hasTub = hasTub;
		isOccupied = false;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
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
		return "Room [id=" + id + ", number=" + number + ", hasTub=" + hasTub + "]";
	}
	
	
	
}
