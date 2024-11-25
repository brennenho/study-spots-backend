package studyspots.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	Comment findByUserIdAndPostId(Long userId, Long postId);
	Comment findByUserIdAndPostIdAndId(Long userId, Long postId, Long id);
}
