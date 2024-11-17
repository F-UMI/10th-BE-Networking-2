package cotato.backend.domains.post.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cotato.backend.domains.post.entity.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostJdbcRepository {

	private final JdbcTemplate jdbcTemplate;

	@Transactional
	public void saveAllPost(List<Post> posts) {
		String sql = "insert into post (title, content, name) values (?, ?, ?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Post post = posts.get(i);
				ps.setString(1, post.getTitle());
				ps.setString(2, post.getContent());
				ps.setString(3, post.getName());
			}

			@Override
			public int getBatchSize() {
				return posts.size();
			}
		});

	}
}
