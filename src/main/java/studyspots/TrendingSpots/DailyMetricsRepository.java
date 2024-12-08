package studyspots.TrendingSpots;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DailyMetricsRepository extends CrudRepository<DailyMetrics, Integer> {
	List<DailyMetrics> findBySpotId(int spotId);
	DailyMetrics findFirstBySpotIdOrderByDateAsc(int spotId);
	
	@Query("SELECT DISTINCT spotId FROM DailyMetrics")
	List<Integer> findDistinctSpotId();
}	
