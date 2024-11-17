package cotato.backend.domains.post.dto;

import cotato.backend.domains.post.entity.Post;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record PostDto(@NonNull String title, @NonNull String content, @NonNull String name, int views, int likes) {

	public static PostDto from(Post post) {
		return new PostDto(post.getTitle(), post.getContent(), post.getName(), post.getViews(), post.getLikes());
	}
}
