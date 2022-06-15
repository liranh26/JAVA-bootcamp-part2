package models;

import java.util.Objects;

public class Location {

	private int locationId;
	private String name;
	private String accessCode;
	
	public Location() {}
	
	public Location(String name, String accessCode) {
		this.name = name;
		this.accessCode = accessCode;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(accessCode, locationId, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return Objects.equals(accessCode, other.accessCode) && locationId == other.locationId
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", name=" + name + ", accessCode=" + accessCode + "]";
	}
	
}
