package studyspots.TrendingSpots;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trending_scores")
public class TrendingMetrics{

	@Id

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "metric_id")
	private int dailyMetricId;

	@Column(name = "spot_id")
	private int spotId;

	@Column(name = "total_visits")
	private int totalVisits;

	@Column(name = "trending_metrics")
	private double trendingMetrics;

	@Column(name = "average_rating")
	private double averageRating;

	public double getAverageRatings() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public int getSpotId() {
		return spotId;
	}

	public void setSpotId(int spot_id) {
		this.spotId = spot_id;
	}

	public int getTotalVisits() {
		return totalVisits;
	}

	public void setTotalVisits(int totalVisits) {
		this.totalVisits = totalVisits;
	}

	public void setTrendingMetrics(int visitCount, double totalRating) {
		trendingMetrics = (0.7 * visitCount + 0.3 * totalRating);
	}

	public double getTrendingMetrics() {
		return trendingMetrics;
	}

}