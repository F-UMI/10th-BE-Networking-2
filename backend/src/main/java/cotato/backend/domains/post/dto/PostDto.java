package cotato.backend.domains.post.dto;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record PostDto(@NonNull String title, @NonNull String content, @NonNull String name, int views) {
}
