package cotato.backend.domains.post.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post",
	indexes = {
		@Index(name = "idx_post_views", columnList = "views")
	}
)
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String title;

	@NonNull
	private String content;

	@NonNull
	private String name;

	@ColumnDefault("0")
	private int views;

	@ColumnDefault("0")
	private int likes;

	@Version
	@Column(name = "version", columnDefinition = "bigint default 0")
	private Long version = 0L;

	@Builder
	public Post(String title, String content, String name, int views, int likes) {
		this.title = title;
		this.content = content;
		this.name = name;
		this.views = views;
		this.likes = likes;
	}

	public void increaseViews() {
		this.views++;
	}
}
