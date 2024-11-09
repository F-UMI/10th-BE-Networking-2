package cotato.backend.domains.post.dto;

public record PostDto(Long id, String title, String content, String name, int views) {
}
