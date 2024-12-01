package studyspots.AllStudySpots;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

@RestController
@RequestMapping("/AllStudySpots")
public class AllStudySpotsController {
	@Autowired
	private AllStudySpotsRepository allStudySpotsRepository;
	
	@PostMapping("/add")
	public @ResponseBody String addStudySpot(@RequestParam int spot_id, @RequestParam String name, @RequestParam String address, @RequestParam float latitude, @RequestParam float longitude, @RequestParam String category) {
		StudySpot studySpot = new StudySpot();
		studySpot.setSpotId(spot_id);
		studySpot.setAddress(address);
		studySpot.setLatitude(latitude);
		studySpot.setLongitude(longitude);
		studySpot.setName(name);
		studySpot.setCategory(category);
		
		if(!this.allStudySpotsRepository.existsByName(name)) {
			return "Study spot successfully added";
		}
		return "Study spot already exists";
	}
	
//	@PutMapping("/edit")
//	public String 
	
	
	

}
