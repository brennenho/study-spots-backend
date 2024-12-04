package studyspots.AllStudySpots;

import java.time.LocalDateTime;

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
	public @ResponseBody void addStudySpot(@RequestParam int spot_id, @RequestParam String name, @RequestParam String address, @RequestParam String category) {
		StudySpot studySpot = new StudySpot();
		studySpot.setSpotId(spot_id);
		studySpot.setAddress(address);
		studySpot.setName(name);
	}
	
	@GetMapping("/get")
	public @ResponseBody void getStudySpotByName(@RequestParam String name) {
		
	}
	
	@DeleteMapping("/remove")
	public @ResponseBody void removeStudySpot() {
		
	}
	
	//@PutMapping("/update/")
	
	
//	@PutMapping("/edit")
//	public String 
	
	
	

}
