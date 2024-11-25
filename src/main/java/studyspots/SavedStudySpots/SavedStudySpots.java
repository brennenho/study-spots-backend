package studyspots.SavedStudySpots;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SavedSpots")
public class SavedStudySpots {

	@Id

	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "save_id")
	private int saveId;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "spot_id")
	private int spotId;

	@Column(name = "date")
	private LocalDate date;

	public int getId() {
		return this.saveId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSpotId() {
		return this.spotId;
	}

	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}