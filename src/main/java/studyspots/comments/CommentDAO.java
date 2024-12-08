package studyspots.comments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

	public Comment addComment(CommentRequest request) {
		try (Connection conn = this.dataSource.getConnection()) {
			// First check if both spot_id and user_id exist
			if (!this.spotExists(conn, request.getPostId())) {
				throw new RuntimeException("Study spot with ID " + request.getPostId() + " does not exist");
			}
			if (!this.userExists(conn, request.getUserId())) {
				throw new RuntimeException("User with ID " + request.getUserId() + " does not exist");
			}

			String sql = "INSERT INTO Comments (user_id, post_id, title, description, timestamp) VALUES (?, ?, ?, ?, ?)";

			try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setLong(1, request.getUserId());
				pstmt.setLong(2, request.getPostId());
				pstmt.setString(3, request.getTitle());
				pstmt.setString(4, request.getDescription());
				pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

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
						return comment;
					}
					throw new SQLException("Creating comment failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error adding comment: " + e.getMessage(), e);
		}
	}

	public Comment findComment(Long userId, Long postId, Long commentId) {
		String sql = "SELECT * FROM Comments WHERE user_id = ? AND post_id = ? AND id = ?";

		try (Connection conn = this.dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
		} catch (SQLException e) {
			throw new RuntimeException("Error finding comment", e);
		}
	}

	public Comment updateComment(CommentRequest request) {
		String sql = "UPDATE Comments SET title = ?, description = ?, timestamp = ? WHERE user_id = ? AND post_id = ? AND id = ?";

		try (Connection conn = this.dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, request.getTitle());
			pstmt.setString(2, request.getDescription());
			pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.setLong(4, request.getUserId());
			pstmt.setLong(5, request.getPostId());
			pstmt.setLong(6, request.getCommentId());

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) {
				return this.findComment(request.getUserId(), request.getPostId(), request.getCommentId());
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException("Error updating comment", e);
		}
	}

	public boolean deleteComment(Long userId, Long postId, Long commentId) {
		String sql = "DELETE FROM Comments WHERE user_id = ? AND post_id = ? AND id = ?";

		try (Connection conn = this.dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setLong(1, userId);
			pstmt.setLong(2, postId);
			pstmt.setLong(3, commentId);

			int affectedRows = pstmt.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			throw new RuntimeException("Error deleting comment", e);
		}
	}
}