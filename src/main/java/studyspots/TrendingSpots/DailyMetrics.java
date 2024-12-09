package studyspots.TrendingSpots;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Daily_Metrics")
public class DailyMetrics {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "metric_id")
	private int dailyMetricId;

	@Column(name = "spot_id")
	private int spotId;

	@Column(name = "visit_count")
	private int visitCount;

	@Column(name = "rating")
	private double rating;

	@Column(name = "date")
	private LocalDate date;

	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getRating() {
		return rating;
	}

	public int getSpotId() {
		return spotId;
	}

	public void setSpotId(int spot_id) {
		this.spotId = spot_id;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visit_Count) {
		this.visitCount = visit_Count;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
