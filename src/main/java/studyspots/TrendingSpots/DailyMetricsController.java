package studyspots.TrendingSpots;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import studyspots.AllStudySpots.AllStudySpotsRepository;
import studyspots.AllStudySpots.StudySpot;
import studyspots.comments.Comment;
import studyspots.comments.CommentDAO;

@Controller
@RequestMapping(path="/DailyMetrics")
public class DailyMetricsController {

	@Autowired
	private DailyMetricsRepository dailyMetricsRepository;
	
	@Autowired
	private AllStudySpotsRepository allStudySpotsRepository;
	
	@Autowired
    private TrendingMetricsController trendingMetricsController;
	
	@Autowired
	private CommentDAO commentDao;
	
	@PostMapping(path="/processData")
	public @ResponseBody void processAll() {
		List<StudySpot> allSpots = allStudySpotsRepository.findAll();
		for (int i = 0; i < allSpots.size(); i++) {
			List<Comment> allComments = commentDao.getCommentsBySpot((long) allSpots.get(i).getSpotId());
			
			float totalRating = 0;
			for (int j = 0; j < allComments.size(); j++) {
				totalRating += allComments.get(j).getRating();
			}
			
			int spotId = allSpots.get(i).getSpotId();
			float rating = totalRating;
			int visitCount = commentDao.getCommentsBySpot((long) spotId).size();

			addNewDailyMetric(spotId, visitCount, rating);
		}
	}

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

