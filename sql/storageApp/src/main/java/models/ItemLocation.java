package models;

import java.util.Objects;

public class ItemLocation {

	private int itemId;
	private int locationId;
	
	public ItemLocation() {}
	
	public ItemLocation(int itemId, int locationId) {
		this.itemId = itemId;
		this.locationId = locationId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(itemId, locationId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemLocation other = (ItemLocation) obj;
		return itemId == other.itemId && locationId == other.locationId;
	}

	@Override
	public String toString() {
		return "ItemLocation [itemId=" + itemId + ", locationId=" + locationId + "]";
	}
	
}
