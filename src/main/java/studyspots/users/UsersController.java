package studyspots.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UserRepository usersRepository;

	@PostMapping("/login")
	public ResponseEntity<Boolean> login(@RequestBody LoginRequest request) {
		User existingUser = this.usersRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
		

		if (existingUser == null) {
			return ResponseEntity.ok(false);
		}
		
		return ResponseEntity.ok(true);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Boolean> register(@RequestBody RegisterRequest request) {
		User existingUser = this.usersRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
		

		if (existingUser != null) {
			return ResponseEntity.ok(false);
		}
		
		User newUser = new User();
		newUser.setEmail(request.getEmail());
		newUser.setPassword(request.getPassword());
		newUser.setName(request.getName());

		this.usersRepository.save(newUser);
		
		return ResponseEntity.ok(true);
	}
}