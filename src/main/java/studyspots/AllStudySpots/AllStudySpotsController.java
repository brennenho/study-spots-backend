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
			System.out.println("Received request: " + request.getName());
			System.out.println("Tags: " + request.getTags());

			request.validate();
			if(this.allStudySpotsRepository.existsByName(request.getName())) {
				System.out.println("Study spot already exists: " + request.getName());
				return ResponseEntity.badRequest().body(new ErrorResponse("Study spot already exists."));
			}

			StudySpot studySpot = new StudySpot();
			studySpot.setName(request.getName());
			studySpot.setLocation(request.getLocation());
			studySpot.setImage(request.getImage());
			studySpot.setLatitude(request.getLatitude());
			studySpot.setLongitude(request.getLongitude());
			studySpot.setHours(request.getHours());
			studySpot.setTags(String.join(",", request.getTags()));

			StudySpot savedSpot = this.allStudySpotsRepository.save(studySpot);
			return ResponseEntity.ok(savedSpot);
		} catch (IllegalArgumentException e) {
			System.out.println("Validation error: " + e.getMessage());
			return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
		} catch (Exception e) {
			System.out.println("General error: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.badRequest()
					.body(new ErrorResponse("Error adding study spot: " + e.getMessage()));
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
		List<StudySpot> getAllList = this.getAll();
		for (StudySpot element : getAllList) {
			if (element.getSpotId() == id) {
				return element.getName();
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
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getLongitude() {
		return this.longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return this.latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public String getHours() {
		return this.hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<String> getTags() {
		return this.tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void validate() throws IllegalArgumentException {
		if ((this.name == null) || this.name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be empty");
		}
		if ((this.location == null) || this.location.trim().isEmpty()) {
			throw new IllegalArgumentException("Location cannot be empty");
		}
		if ((this.hours == null) || this.hours.trim().isEmpty()) {
			throw new IllegalArgumentException("Hours cannot be empty");
		}
		if ((this.tags == null) || this.tags.isEmpty()) {
			throw new IllegalArgumentException("At least one tag must be selected");
		}
	}
}

class ErrorResponse {
	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}

	public String getMessage() { return this.message; }
	public void setMessage(String message) { this.message = message; }
}
