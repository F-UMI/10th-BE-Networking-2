package cotato.backend.domains.post.dto.request;

import cotato.backend.domains.post.entity.Post;
import lombok.NonNull;

public record SaveSinglePostRequest(@NonNull String title, @NonNull String content, @NonNull String name) {

	public Post toEntity() {
		return Post.builder().
			title(title).
			content(content).
			name(name).
			build();
	}
}