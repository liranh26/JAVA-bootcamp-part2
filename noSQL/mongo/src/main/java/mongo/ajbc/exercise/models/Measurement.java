package mongo.ajbc.exercise.models;

public class Measurement {

	private String id;
	private double hieght, width, depth;

	public Measurement(String id, double hieght, double width, double depth) {

		this.id = id;
		this.hieght = hieght;
		this.width = width;
		this.depth = depth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
