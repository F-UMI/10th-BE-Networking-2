package cotato.backend.domains.post.dto.response;

import cotato.backend.domains.post.entity.Post;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record PostResponse(@NonNull String title, @NonNull String content, @NonNull String name, int views, int likes) {

	public static PostResponse from(Post post) {
		return new PostResponse(post.getTitle(), post.getContent(), post.getName(), post.getViews(), post.getLikes());
	}
}
