package studyspots.AllStudySpots;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "studyspots")
public class StudySpot {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@Column(name = "spot_id")
	private int spotId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "latitude")
	private float latitude;
	
	@Column(name = "longitude")
	private float longitude;
	
	@Column(name = "category")
	private String category;
	
	public int getSpotId() {
		return this.spotId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

}
