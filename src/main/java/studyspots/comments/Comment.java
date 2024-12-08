// Comment.java
package studyspots.comments;

import java.time.LocalDateTime;

public class Comment {
	private Integer id;
	private Long userId;
	private Long postId;
	private String title;
	private String description;
	private LocalDateTime timestamp;

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
}