package studyspots.AllStudySpots;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	public @ResponseBody String addStudySpot(@RequestParam String name, @RequestParam String address, @RequestParam String image,
			@RequestParam float latitude, @RequestParam float longitude, @RequestParam float rating) {
		if(this.allStudySpotsRepository.existsByName(name)) {
			return "Study spot already exists.";
		}
		StudySpot studySpot = new StudySpot();
		studySpot.setAddress(address);
		studySpot.setName(name);
		studySpot.setImage(image);
		studySpot.setLatitude(latitude);
		studySpot.setLongitude(longitude);
		studySpot.setRating(rating);
		this.allStudySpotsRepository.save(studySpot);
		return "Study spot added";
		
		
	}
	
	@GetMapping("/get")
	public @ResponseBody List<StudySpot> getStudySpotByName(@RequestParam String name) {
		return this.allStudySpotsRepository.findByName(name);
	}
	
	@GetMapping("/getall")
	public @ResponseBody List<StudySpot> getAll() {
		return this.allStudySpotsRepository.findAll();
	}

	
	//@PutMapping("/update/")
	
	
//	@PutMapping("/edit")
//	public String 
	
	
	

}
