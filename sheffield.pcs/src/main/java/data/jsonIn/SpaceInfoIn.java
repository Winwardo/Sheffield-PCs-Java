package data.jsonIn;

public class SpaceInfoIn {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SpaceInfoIn [current=" + current + ", total_text=" + total_text + ", location=" + location
				+ ", building=" + building + ", photo=" + photo + "]";
	}

	String current;
	String total_text;
	String location;
	String building;
	String photo;

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getTotal_text() {
		return total_text;
	}

	public void setTotal_text(String total_text) {
		this.total_text = total_text;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
