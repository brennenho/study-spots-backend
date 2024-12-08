package studyspots.AllStudySpots;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AllStudySpots")
public class StudySpot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spot_id")
	private int spotId;

	@Column(name = "name")
	private String name;

	@Column(name = "location")
	private String location;

	@Column(name = "image")
	private String image;

	@Column(name = "latitude")
	private float latitude;

	@Column(name = "longitude")
	private float longitude;
	
	@Column(name = "hours")
	private String hours;

	@Column(name = "tags")
	private String tags;

	public int getSpotId() {
		return spotId;
	}

	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
