// CommentRequest.java
package studyspots.comments;

public class CommentRequest {
	private Long userId;
	private Long postId;
	private Long commentId;
	private String title;
	private String description;
	private Integer rating;

	public Long getUserId() {
		return this.userId;
	}

	public Long getPostId() {
		return this.postId;
	}

	public Long getCommentId() {
		return this.commentId;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getRating() {
		return this.rating;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
}