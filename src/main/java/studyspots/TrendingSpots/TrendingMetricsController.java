package studyspots.TrendingSpots;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import studyspots.TrendingSpots.DailyMetrics;
import studyspots.TrendingSpots.DailyMetricsRepository;
import studyspots.SavedStudySpots.SavedStudySpots;

@Controller
@RequestMapping(path="/TrendingMetrics")
public class TrendingMetricsController {

	@Autowired
	private DailyMetricsRepository dailyMetricsRepository;

	@Autowired
	private TrendingMetricsRepository trendingMetricsRepository;

	@GetMapping(path="/getNewTrending")
	public @ResponseBody Iterable<TrendingMetrics> getNewTrending() {
        trendingMetricsRepository.deleteAll();

		List<TrendingMetrics> trendingMetricsList = new ArrayList<TrendingMetrics>();
		List<Integer> spotIdsList = dailyMetricsRepository.findDistinctSpotId();
		
		for (int i = 0; i < spotIdsList.size(); i++) {
			TrendingMetrics metrics = new TrendingMetrics();
			
			double totalRating = 0;
			int totalVisits = 0;
			
			List<DailyMetrics> spotIdList = dailyMetricsRepository.findBySpotId(spotIdsList.get(i));
			
			for (int j = 0; j < dailyMetricsRepository.findBySpotId(spotIdsList.get(i)).size(); j++) {
				totalRating += (spotIdList.get(j).getRating());
				totalVisits += (spotIdList.get(j).getVisitCount());
			}
			
			metrics.setSpotId(spotIdsList.get(i));
			metrics.setTotalVisits(totalVisits);
			metrics.setAverageRating(totalRating/spotIdList.size());
			metrics.setTrendingMetrics(totalVisits, totalRating);

			trendingMetricsList.add(metrics);
		}

		//Yes I used bubble sort
		for (int i = 0; i < trendingMetricsList.size()-1; i++) {
			boolean swap;
			for (int j = 0; j < trendingMetricsList.size() - i - 1; j++) {
				swap = false;
				
				if (trendingMetricsList.get(j).getTrendingMetrics() < trendingMetricsList.get(j + 1).getTrendingMetrics()) {
					
					TrendingMetrics temp = trendingMetricsList.get(j);
					trendingMetricsList.set(j, trendingMetricsList.get(j + 1));
					trendingMetricsList.set(j + 1, temp);
					
					swap = true;
				}
				if (!swap) {
					break;
				}
			}
		}

		List<TrendingMetrics> organizedList = new ArrayList<TrendingMetrics>();

		for (int i = 0; i < Math.min(10, trendingMetricsList.size()); i++) {
			organizedList.add(trendingMetricsList.get(i));
			trendingMetricsRepository.save(trendingMetricsList.get(i));
		}
		return organizedList;
	}
	
	@GetMapping(path="/returnCurrentTrending")
	public @ResponseBody Iterable<TrendingMetrics> returnTrending() {
		List<TrendingMetrics> currentTrendings = (List<TrendingMetrics>) trendingMetricsRepository.findAll();
		return currentTrendings;
	}

}


