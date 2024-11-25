package studyspots.SavedStudySpots;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/SavedStudySpots")
public class SavedStudySpotController {
	@Autowired
	private SavedStudySpotsRepository savedStudySpotsRepository;

	@PostMapping(path="/add")
	public @ResponseBody String addNewSavedSpot (@RequestParam int user_id, @RequestParam int spot_id) {
		if (this.savedStudySpotsRepository.existsByUserIdAndSpotId(user_id, spot_id)) {
			return "Already Saved";
		}

		SavedStudySpots spot = new SavedStudySpots();
		spot.setUserId(user_id);
		spot.setSpotId(spot_id);
		spot.setDate(LocalDate.now());
		this.savedStudySpotsRepository.save(spot);
		return "Saved";
	}

	@PostMapping(path = "/remove")
	@Transactional
	public @ResponseBody String removeSavedSpot(@RequestParam int spot_id, @RequestParam int user_id) {
		if (this.savedStudySpotsRepository.existsByUserIdAndSpotId(user_id, spot_id)) {
			this.savedStudySpotsRepository.deleteByUserIdAndSpotId(user_id, spot_id);
			return "Deleted";
		}
		return "Not Found";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<SavedStudySpots> getAllSpots() {
		return this.savedStudySpotsRepository.findAll();
	}

	@GetMapping(path="/userspecific")
	public @ResponseBody Iterable<SavedStudySpots> getStudySpotsByUserId(@RequestParam int user_id) {
		return this.savedStudySpotsRepository.findByUserId(user_id);
	}

}