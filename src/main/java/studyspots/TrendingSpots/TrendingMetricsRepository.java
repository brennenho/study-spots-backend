package studyspots.TrendingSpots;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TrendingMetricsRepository extends CrudRepository<TrendingMetrics, Integer> {
	List<TrendingMetrics> findBySpotId(int spotId);
}
