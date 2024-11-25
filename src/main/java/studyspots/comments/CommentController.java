package studyspots.comments;

import java.time.LocalDateTime;

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
	private CommentRepository commentRepository;

	@PostMapping("/add")
	public ResponseEntity<Comment> addComment(@RequestBody CommentRequest request) {
		Comment comment = new Comment();
		comment.setUserId(request.getUserId());
		comment.setPostId(request.getPostId());
		comment.setTitle(request.getTitle());
		comment.setDescription(request.getDescription());
		comment.setTimestamp(LocalDateTime.now());

		Comment savedComment = this.commentRepository.save(comment);
		return ResponseEntity.ok(savedComment);
	}

	@PutMapping("/edit")
	public ResponseEntity<?> editComment(@RequestBody CommentRequest request) {
		Comment existingComment = this.commentRepository.findByUserIdAndPostIdAndId(
				request.getUserId(),
				request.getPostId(),
				request.getCommentId()
				);

		if (existingComment == null) {
			return ResponseEntity.notFound().build();
		}

		existingComment.setTitle(request.getTitle());
		existingComment.setDescription(request.getDescription());
		existingComment.setTimestamp(LocalDateTime.now());

		Comment updatedComment = this.commentRepository.save(existingComment);
		return ResponseEntity.ok(updatedComment);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteComment(
			@RequestParam Long userId,
			@RequestParam Long postId,
			@RequestParam Long commentId) {
		Comment comment = this.commentRepository.findByUserIdAndPostIdAndId(userId, postId, commentId);
		if (comment == null) {
			return ResponseEntity.notFound().build();
		}

		this.commentRepository.delete(comment);
		return ResponseEntity.ok().build();
	}
}