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

	@Column(name = "address")
	private String address;

	@Column(name = "image")
	private String image;

	@Column(name = "latitude")
	private float latitude;

	@Column(name = "longitude")
	private float longitude;

	@Column(name = "rating")
	private float rating;

	public int getSpotId() {
		return this.spotId;
	}

	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public float getLongitude() {
		return this.longitude;
	}

	public float getLatitude() {
		return this.latitude;
	}

	public String getImage() {
		return this.image;
	}

	public float getRating() {
		return this.rating;
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

	public void setImage(String image) {
		this.image = image;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

}
