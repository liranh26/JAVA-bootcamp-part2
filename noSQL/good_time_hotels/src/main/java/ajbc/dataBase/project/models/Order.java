package ajbc.dataBase.project.models;

import java.time.LocalDate;
import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Order {

	private ObjectId id;
	@BsonProperty(value = "hotel_id")
	private ObjectId hotelId;
	@BsonProperty(value = "customer_id")
	private ObjectId customerId;
	@BsonProperty(value = "order_date")
	private LocalDate orderDate;
	@BsonProperty(value = "start_date")
	private LocalDate startDate;
	private int nights;
	@BsonProperty(value = "total_price")
	private double totalPrice;
	
	public Order() {}
	
	public Order(ObjectId id, ObjectId hotelId, ObjectId customerId, LocalDate orderDate, LocalDate startDate,
			int nights, double totalPrice) {
		this.id = id;
		this.hotelId = hotelId;
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.startDate = startDate;
		this.nights = nights;
		this.totalPrice = totalPrice;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getHotelId() {
		return hotelId;
	}

	public void setHotelId(ObjectId hotelId) {
		this.hotelId = hotelId;
	}

	public ObjectId getCustomerId() {
		return customerId;
	}

	public void setCustomerId(ObjectId customerId) {
		this.customerId = customerId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", hotelId=" + hotelId + ", customerId=" + customerId + ", orderDate=" + orderDate
				+ ", startDate=" + startDate + ", nights=" + nights + ", totalPrice=" + totalPrice + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, hotelId, id, nights, orderDate, startDate, totalPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(customerId, other.customerId) && Objects.equals(hotelId, other.hotelId)
				&& Objects.equals(id, other.id) && nights == other.nights && Objects.equals(orderDate, other.orderDate)
				&& Objects.equals(startDate, other.startDate)
				&& Double.doubleToLongBits(totalPrice) == Double.doubleToLongBits(other.totalPrice);
	}

	
}
