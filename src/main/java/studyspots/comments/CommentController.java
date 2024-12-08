package studyspots.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentDAO commentDAO;

	@PostMapping("/add")
	public ResponseEntity<?> addComment(@RequestBody CommentRequest request) {
		try {
			Comment savedComment = this.commentDAO.addComment(request);
			return ResponseEntity.ok(savedComment);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest()
					.body(new ErrorResponse(e.getMessage()));
		}
	}

	@PutMapping("/edit")
	public ResponseEntity<?> editComment(@RequestBody CommentRequest request) {
		try {
			Comment updatedComment = this.commentDAO.updateComment(request);
			return ResponseEntity.ok(updatedComment);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest()
					.body(new ErrorResponse(e.getMessage()));
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteComment(
			@RequestParam Long userId,
			@RequestParam Long postId,
			@RequestParam Long commentId) {
		try {
			boolean deleted = this.commentDAO.deleteComment(userId, postId, commentId);
			if (deleted) {
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.notFound().build();
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest()
					.body(new ErrorResponse(e.getMessage()));
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
