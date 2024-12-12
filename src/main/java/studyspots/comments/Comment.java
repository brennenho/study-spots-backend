package studyspots.comments;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "post_id")
	private Long postId;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	@Column(name = "rating")
	private int rating;

	public Integer getId() {
		return this.id;
	}

	public Long getUserId() {
		return this.userId;
	}

	public Long getPostId() {
		return this.postId;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}

	public int getRating() {
		return this.rating;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setRating(int rating) {
		if ((rating < 1) || (rating > 10)) {
			throw new IllegalArgumentException("Rating must be between 1 and 10");
		}
		this.rating = rating;
	}
}
