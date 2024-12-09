package studyspots.AllStudySpots;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
			studySpot.setLocation(request.getLocation());
			studySpot.setImage(request.getImage());
			studySpot.setLatitude(request.getLatitude());
			studySpot.setLongitude(request.getLongitude());
			studySpot.setHours(request.getHours());
			studySpot.setTags(request.getTags());

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
	
	@GetMapping("/getbyid/{id}")
	public @ResponseBody StudySpot getStudySpotById(@PathVariable int id) {
		return this.allStudySpotsRepository.findById(id);
	}

	@GetMapping("/getall")
	public @ResponseBody List<StudySpot> getAll() {
		return this.allStudySpotsRepository.findAll();
	}
	
	@GetMapping("/getnamebyid/{id}")
	public @ResponseBody String getNameById(@PathVariable int id) {
		List<StudySpot> getAllList = getAll();
		for (int i = 0; i < getAllList.size(); i++) {
			if (getAllList.get(i).getSpotId() == id) {
				return getAllList.get(i).getName();
			}
		}
		return "Error getting Name";
	}
}

class StudySpotRequest {
	private String name;
	private String location;
	private float longitude;
	private float latitude;
	private String hours;
	private String image;
	private List<String> tags;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTags() {
		return String.join(",", tags);
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
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
