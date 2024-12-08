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
		Comment updatedComment = this.commentDAO.updateComment(request);
		if (updatedComment == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(updatedComment);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteComment(
			@RequestParam Long userId,
			@RequestParam Long postId,
			@RequestParam Long commentId) {
		boolean deleted = this.commentDAO.deleteComment(userId, postId, commentId);
		if (!deleted) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
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
