package models;

import java.time.LocalDate;
import java.util.Objects;

public class Item {

	private int itemId;
	private String name;
	private double uniTPrice;
	private LocalDate date;
	private int quantity;
	
	public Item() {}
	
	public Item(String name, double uniTPrice, LocalDate date, int quantity) {
		this.name = name;
		this.uniTPrice = uniTPrice;
		this.date = date;
		this.quantity = quantity;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemId() {
		return itemId;
	}


	public String getName() {
		return name;
	}


	public double getUniTPrice() {
		return uniTPrice;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getQuantity() {
		return quantity;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setUniTPrice(double uniTPrice) {
		this.uniTPrice = uniTPrice;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(date, itemId, name, quantity, uniTPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(date, other.date) && itemId == other.itemId && Objects.equals(name, other.name)
				&& quantity == other.quantity
				&& Double.doubleToLongBits(uniTPrice) == Double.doubleToLongBits(other.uniTPrice);
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", name=" + name + ", uniTPrice=" + uniTPrice + ", date=" + date
				+ ", quantity=" + quantity + "]";
	}
	
	
	
}
