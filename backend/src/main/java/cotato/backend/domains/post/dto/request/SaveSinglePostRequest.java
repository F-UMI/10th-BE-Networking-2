package cotato.backend.domains.post.dto.request;

import cotato.backend.domains.post.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record SaveSinglePostRequest(@NotBlank(message = "Title은 공백일 수 없습니다.") String title,
									@NotBlank(message = "Content는 공백일 수 없습니다.") String content,
									@NotBlank(message = "Name은 공백일 수 없습니다.") String name) {

	public Post toEntity() {
		return Post.builder().
			title(title).
			content(content).
			name(name).
			build();
	}
}