package studyspots.TrendingSpots;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/DailyMetrics")
public class DailyMetricsController {

	@Autowired
	private DailyMetricsRepository dailyMetricsRepository;

	@PostMapping(path="/add")
	public @ResponseBody String addNewDailyMetric (@RequestParam int spot_id, @RequestParam int visit_count, @RequestParam double rating) {
		List<DailyMetrics> metricsChecker = dailyMetricsRepository.findBySpotId(spot_id);
		if (metricsChecker.size() < 7) {
			DailyMetrics metrics = new DailyMetrics();
			metrics.setDate(LocalDate.now());
			metrics.setRating(rating);
			metrics.setSpotId(spot_id);
			metrics.setVisitCount(visit_count);
			dailyMetricsRepository.save(metrics);
			return "Added";
		}	
		else {
			dailyMetricsRepository.delete(dailyMetricsRepository.findFirstBySpotIdOrderByTimestampAsc(spot_id));
			DailyMetrics metrics = new DailyMetrics();
			metrics.setDate(LocalDate.now());
			metrics.setRating(rating);
			metrics.setSpotId(spot_id);
			metrics.setVisitCount(visit_count);
			dailyMetricsRepository.save(metrics);
			return "Removed old Data, Added new Data";
		}	
	}
}

