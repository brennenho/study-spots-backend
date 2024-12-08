package studyspots.TrendingSpots;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
	@Autowired 
	private DailyMetricsController dailyMetricsController;

	@Scheduled(cron = "0 0 0 * * *")
	public void retrieveDailyInfo() {
		dailyMetricsController.processAll();
		
	}
	

}
