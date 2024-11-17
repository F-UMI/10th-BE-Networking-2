package cotato.backend.domains.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cotato.backend.domains.post.dto.PostDto;
import cotato.backend.domains.post.dto.request.SavePostsByExcelRequest;
import cotato.backend.domains.post.dto.request.SaveSinglePostRequest;

public interface PostService {
	void saveEstatesByExcel(SavePostsByExcelRequest request);

	void saveSinglePost(SaveSinglePostRequest request);

	PostDto findPostById(Long id);

	Page<PostDto> findPostsSortedByLikes(Pageable pageable);
}
