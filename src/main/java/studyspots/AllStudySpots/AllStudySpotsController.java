package studyspots.AllStudySpots;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studyspots")
public class AllStudySpotsController {

	@Autowired
	private AllStudySpotsRepository allStudySpotsRepository;

	@PostMapping("/add")
	public ResponseEntity<?> addStudySpot(@RequestBody StudySpotRequest request) {
		try {
			if(this.allStudySpotsRepository.existsByName(request.getName())) {
				return ResponseEntity.badRequest().body(new ErrorResponse("Study spot already exists."));
			}

			StudySpot studySpot = new StudySpot();
			studySpot.setName(request.getName());
			studySpot.setAddress(request.getAddress());
			studySpot.setImage(request.getImage());
			studySpot.setLatitude(0.0f);  // Default value
			studySpot.setLongitude(0.0f); // Default value
			studySpot.setRating(1.0f);    // Initial rating

			StudySpot savedSpot = this.allStudySpotsRepository.save(studySpot);
			return ResponseEntity.ok(savedSpot);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
		}
	}
	@GetMapping("/get")
	public @ResponseBody List<StudySpot> getStudySpotByName(@RequestParam String name) {
		return this.allStudySpotsRepository.findByName(name);
	}

	@GetMapping("/getall")
	public @ResponseBody List<StudySpot> getAll() {
		return this.allStudySpotsRepository.findAll();
	}

	// @PutMapping("/update/")

	// @PutMapping("/edit")
	// public String
}

class StudySpotRequest {
	private String name;
	private String address;
	private String location;
	private String hours;
	private String image;

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public String getAddress() { return this.address; }
	public void setAddress(String address) { this.address = address; }

	public String getLocation() { return this.location; }
	public void setLocation(String location) { this.location = location; }

	public String getHours() { return this.hours; }
	public void setHours(String hours) { this.hours = hours; }

	public String getImage() { return this.image; }
	public void setImage(String image) { this.image = image; }
}

// Error response class
class ErrorResponse {
	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}

	public String getMessage() { return this.message; }
	public void setMessage(String message) { this.message = message; }
}
