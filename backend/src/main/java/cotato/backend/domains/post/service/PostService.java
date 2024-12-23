package cotato.backend.domains.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cotato.backend.domains.post.dto.PostResponse;
import cotato.backend.domains.post.dto.request.SavePostsByExcelRequest;
import cotato.backend.domains.post.dto.request.SaveSinglePostRequest;

public interface PostService {
	void saveEstatesByExcel(SavePostsByExcelRequest request);

	void saveSinglePost(SaveSinglePostRequest request);

	PostResponse findPostById(Long id) throws Exception;

	Page<PostResponse> findPostsSortedByLikes(Pageable pageable);

	void deletePostById(Long id);
}
