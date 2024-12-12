package studyspots.SavedStudySpots;

import java.time.LocalDate;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import studyspots.users.User;
import studyspots.users.UserRepository;

@Controller
@RequestMapping(path="/SavedStudySpots")
public class SavedStudySpotController {
	@Autowired
	private SavedStudySpotsRepository savedStudySpotsRepository;

	@Autowired
	private UserRepository userRepository;

	@PostMapping(path="/add")
	public @ResponseBody String addNewSavedSpot (
			@RequestParam String email,
			@RequestParam int spot_id) {

		User user = this.userRepository.findByEmail(email);
		if (user == null) {
			return "User not found";
		}

		if (this.savedStudySpotsRepository.existsByUserIdAndSpotId(user.getUserId(), spot_id)) {
			return "Already Saved";
		}

		SavedStudySpots spot = new SavedStudySpots();
		spot.setUserId(user.getUserId());
		spot.setSpotId(spot_id);
		spot.setDate(LocalDate.now());
		this.savedStudySpotsRepository.save(spot);
		return "Saved";
	}

	@GetMapping(path="/userspecific")
	public @ResponseBody Iterable<SavedStudySpots> getStudySpotsByUser(
			@RequestParam String email) {  // Change to accept email
		User user = this.userRepository.findByEmail(email);
		if (user == null) {
			return Collections.emptyList();
		}
		return this.savedStudySpotsRepository.findByUserId(user.getUserId());
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
}
