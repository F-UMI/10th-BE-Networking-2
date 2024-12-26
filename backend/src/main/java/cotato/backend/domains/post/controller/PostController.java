package cotato.backend.domains.post.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.common.dto.PageResponse;
import cotato.backend.domains.post.dto.response.PostResponse;
import cotato.backend.domains.post.dto.request.SavePostsByExcelRequest;
import cotato.backend.domains.post.dto.request.SaveSinglePostRequest;
import cotato.backend.domains.post.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostServiceImpl postService;

	@PostMapping("/excel")
	public ResponseEntity<DataResponse<Void>> savePostsByExcel(@RequestBody SavePostsByExcelRequest request) {
		postService.saveEstatesByExcel(request);
		return ResponseEntity.ok(DataResponse.ok());
	}

	@PostMapping
	public ResponseEntity<DataResponse<Void>> savePost(@RequestBody SaveSinglePostRequest request) {
		postService.saveSinglePost(request);
		return ResponseEntity.ok(DataResponse.ok());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DataResponse<PostResponse>> getPost(@PathVariable Long id) {
		return ResponseEntity.ok(DataResponse.from(postService.findPostById(id)));
	}

	@GetMapping
	public ResponseEntity<PageResponse<PostResponse>> getPopularPosts(
		@PageableDefault(sort = "likes", direction = Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok(PageResponse.from(postService.findPostsSortedByLikes(pageable)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<DataResponse<Void>> deletePost(@PathVariable Long id) {
		postService.deletePostById(id);
		return ResponseEntity.ok(DataResponse.ok());
	}
}
