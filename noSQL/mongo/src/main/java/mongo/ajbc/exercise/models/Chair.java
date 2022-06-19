package mongo.ajbc.exercise.models;

public class Chair {

	private String id, manufacturer, model;
	private boolean isStool;
	private float price;
	private Measurement measurement;
	
	public Chair(String id, String manufacturer, String model, boolean isStool, float price, Measurement measurement) {
		this.id = id;
		this.manufacturer = manufacturer;
		this.model = model;
		this.isStool = isStool;
		this.price = price;
		this.measurement = measurement;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public boolean isStool() {
		return isStool;
	}

	public void setStool(boolean isStool) {
		this.isStool = isStool;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	@Override
	public String toString() {
		return "Chair [id=" + id + ", manufacturer=" + manufacturer + ", model=" + model + ", isStool=" + isStool
				+ ", price=" + price + ", measurement=" + measurement + "]";
	}

}
