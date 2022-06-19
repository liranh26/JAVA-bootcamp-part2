package mongo.ajbc.exercise.models;
import org.bson.types.ObjectId;

import com.google.gson.annotations.SerializedName;

public class Measurement{

	@SerializedName("_id")
	private ObjectId id;
	@SerializedName("height")
	private double hieght;
	@SerializedName("width")
	private double width;
	@SerializedName("depth")
	private double depth;
	
	public Measurement(ObjectId id, double hieght, double width, double depth) {
		this.id = id;
		this.hieght = hieght;
		this.width = width;
		this.depth = depth;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public double getHieght() {
		return hieght;
	}

	public void setHieght(double hieght) {
		this.hieght = hieght;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "Measurement [id=" + id + ", hieght=" + hieght + ", width=" + width + ", depth=" + depth + "]";
	}
	
}
