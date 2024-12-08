package studyspots.comments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDAO {
	private final DataSource dataSource;

	@Autowired
	public CommentDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private boolean spotExists(Connection conn, Long spotId) throws SQLException {
		String sql = "SELECT 1 FROM AllStudySpots WHERE spot_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, spotId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	private boolean userExists(Connection conn, Long userId) throws SQLException {
		String sql = "SELECT 1 FROM Users WHERE user_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	private boolean commentExists(Connection conn, Long commentId) throws SQLException {
		String sql = "SELECT 1 FROM Comments WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, commentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	public Comment addComment(CommentRequest request) {
		try (Connection conn = this.dataSource.getConnection()) {
			// Validate post_id and user_id
			if (!this.spotExists(conn, request.getPostId())) {
				throw new RuntimeException("Study spot with ID " + request.getPostId() + " does not exist");
			}
			if (!this.userExists(conn, request.getUserId())) {
				throw new RuntimeException("User with ID " + request.getUserId() + " does not exist");
			}
			// Validate rating
			if ((request.getRating() == null) || (request.getRating() < 1) || (request.getRating() > 10)) {
				throw new RuntimeException("Rating must be between 1 and 10");
			}

			String sql = "INSERT INTO Comments (user_id, post_id, title, description, timestamp, rating) VALUES (?, ?, ?, ?, ?, ?)";

			try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setLong(1, request.getUserId());
				pstmt.setLong(2, request.getPostId());
				pstmt.setString(3, request.getTitle());
				pstmt.setString(4, request.getDescription());
				pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
				pstmt.setInt(6, request.getRating());

				int affectedRows = pstmt.executeUpdate();

				if (affectedRows == 0) {
					throw new SQLException("Creating comment failed, no rows affected.");
				}

				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						Comment comment = new Comment();
						comment.setId(generatedKeys.getInt(1));
						comment.setUserId(request.getUserId());
						comment.setPostId(request.getPostId());
						comment.setTitle(request.getTitle());
						comment.setDescription(request.getDescription());
						comment.setTimestamp(LocalDateTime.now());
						comment.setRating(request.getRating());
						return comment;
					}
					throw new SQLException("Creating comment failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error adding comment: " + e.getMessage(), e);
		}
	}

	public Comment updateComment(CommentRequest request) {
		try (Connection conn = this.dataSource.getConnection()) {
			// First validate all IDs
			if (!this.commentExists(conn, request.getCommentId())) {
				throw new RuntimeException("Comment with ID " + request.getCommentId() + " does not exist");
			}
			if (!this.spotExists(conn, request.getPostId())) {
				throw new RuntimeException("Study spot with ID " + request.getPostId() + " does not exist");
			}
			if (!this.userExists(conn, request.getUserId())) {
				throw new RuntimeException("User with ID " + request.getUserId() + " does not exist");
			}
			// Validate rating
			if ((request.getRating() == null) || (request.getRating() < 1) || (request.getRating() > 10)) {
				throw new RuntimeException("Rating must be between 1 and 10");
			}

			// Verify the comment belongs to this user and post
			String verifySql = "SELECT 1 FROM Comments WHERE id = ? AND user_id = ? AND post_id = ?";
			try (PreparedStatement verifyStmt = conn.prepareStatement(verifySql)) {
				verifyStmt.setLong(1, request.getCommentId());
				verifyStmt.setLong(2, request.getUserId());
				verifyStmt.setLong(3, request.getPostId());
				try (ResultSet rs = verifyStmt.executeQuery()) {
					if (!rs.next()) {
						throw new RuntimeException("Comment does not belong to this user and post combination");
					}
				}
			}

			String sql = "UPDATE Comments SET title = ?, description = ?, timestamp = ?, rating = ? WHERE id = ? AND user_id = ? AND post_id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, request.getTitle());
				pstmt.setString(2, request.getDescription());
				pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
				pstmt.setInt(4, request.getRating());
				pstmt.setLong(5, request.getCommentId());
				pstmt.setLong(6, request.getUserId());
				pstmt.setLong(7, request.getPostId());

				int affectedRows = pstmt.executeUpdate();
				if (affectedRows > 0) {
					return this.findComment(request.getUserId(), request.getPostId(), request.getCommentId());
				}
				throw new RuntimeException("Update failed, no rows affected");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error updating comment: " + e.getMessage(), e);
		}
	}

	public boolean deleteComment(Long userId, Long postId, Long commentId) {
		try (Connection conn = this.dataSource.getConnection()) {
			// First validate all IDs
			if (!this.commentExists(conn, commentId)) {
				throw new RuntimeException("Comment with ID " + commentId + " does not exist");
			}
			if (!this.spotExists(conn, postId)) {
				throw new RuntimeException("Study spot with ID " + postId + " does not exist");
			}
			if (!this.userExists(conn, userId)) {
				throw new RuntimeException("User with ID " + userId + " does not exist");
			}

			// Verify the comment belongs to this user and post
			String verifySql = "SELECT 1 FROM Comments WHERE id = ? AND user_id = ? AND post_id = ?";
			try (PreparedStatement verifyStmt = conn.prepareStatement(verifySql)) {
				verifyStmt.setLong(1, commentId);
				verifyStmt.setLong(2, userId);
				verifyStmt.setLong(3, postId);
				try (ResultSet rs = verifyStmt.executeQuery()) {
					if (!rs.next()) {
						throw new RuntimeException("Comment does not belong to this user and post combination");
					}
				}
			}

			String sql = "DELETE FROM Comments WHERE id = ? AND user_id = ? AND post_id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setLong(1, commentId);
				pstmt.setLong(2, userId);
				pstmt.setLong(3, postId);

				int affectedRows = pstmt.executeUpdate();
				return affectedRows > 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error deleting comment: " + e.getMessage(), e);
		}
	}

	public Comment findComment(Long userId, Long postId, Long commentId) {
		try (Connection conn = this.dataSource.getConnection()) {
			String sql = "SELECT * FROM Comments WHERE user_id = ? AND post_id = ? AND id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setLong(1, userId);
				pstmt.setLong(2, postId);
				pstmt.setLong(3, commentId);

				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						Comment comment = new Comment();
						comment.setId(rs.getInt("id"));
						comment.setUserId(rs.getLong("user_id"));
						comment.setPostId(rs.getLong("post_id"));
						comment.setTitle(rs.getString("title"));
						comment.setDescription(rs.getString("description"));
						comment.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
						return comment;
					}
					return null;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error finding comment: " + e.getMessage(), e);
		}
	}



	public List<Comment> getCommentsBySpot(Long spotId) {
		try (Connection conn = this.dataSource.getConnection()) {
			String sql = "SELECT * FROM Comments WHERE post_id = ? ORDER BY timestamp DESC";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setLong(1, spotId);

				List<Comment> comments = new ArrayList<>();
				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs.next()) {
						Comment comment = new Comment();
						comment.setId(rs.getInt("id"));
						comment.setUserId(rs.getLong("user_id"));
						comment.setPostId(rs.getLong("post_id"));
						comment.setTitle(rs.getString("title"));
						comment.setDescription(rs.getString("description"));
						comment.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
						comments.add(comment);
					}
				}
				return comments;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error fetching comments: " + e.getMessage(), e);
		}
	}
}