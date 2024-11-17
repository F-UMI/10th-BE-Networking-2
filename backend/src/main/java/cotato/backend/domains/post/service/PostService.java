package cotato.backend.domains.post.service;

import cotato.backend.domains.post.dto.PostDto;
import cotato.backend.domains.post.dto.request.SavePostsByExcelRequest;
import cotato.backend.domains.post.dto.request.SaveSinglePostRequest;

public interface PostService {
	void saveEstatesByExcel(SavePostsByExcelRequest request);
	void saveSinglePost(SaveSinglePostRequest request);
	PostDto findPostById(Long id);
}
