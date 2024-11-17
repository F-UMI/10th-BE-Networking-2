package cotato.backend.common.dto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse<T> extends BaseResponse {
	private final List<T> items;
	private final int currentPage;
	private final int totalPages;
	private final long totalItems;

	private PageResponse(HttpStatus status, List<T> content, int number, int totalPages, long totalElements) {
		super(status);
		this.items = content;
		this.currentPage = number;
		this.totalPages = totalPages;
		this.totalItems = totalElements;
	}

	public static <T> PageResponse<T> from(Page<T> page) {
		return new PageResponse<>(
			HttpStatus.OK,
			page.getContent(),
			page.getNumber(),
			page.getTotalPages(),
			page.getTotalElements()
		);
	}
}
