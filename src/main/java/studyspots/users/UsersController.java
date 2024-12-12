package studyspots.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		User existingUser = this.userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
		if (existingUser == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("Invalid credentials"));
		}
		return ResponseEntity.ok(existingUser);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		User existingUser = this.userRepository.findByEmail(request.getEmail());
		if (existingUser != null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("Email already registered"));
		}

		User newUser = new User();
		newUser.setEmail(request.getEmail());
		newUser.setPassword(request.getPassword());
		newUser.setName(request.getName());
		User savedUser = this.userRepository.save(newUser);

		return ResponseEntity.ok(savedUser);
	}

	@GetMapping("/getbyemail")
	public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
		try {
			User user = this.userRepository.findByEmail(email);
			if (user == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
		}
	}
}

class ErrorResponse {
	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}