package cotato.backend.domains.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domains.post.dto.PostDto;
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

	@PostMapping("/single")
	public ResponseEntity<DataResponse<Void>> saveSinglePosts(@RequestBody SaveSinglePostRequest request) {
		postService.saveSinglePost(request);
		return ResponseEntity.ok(DataResponse.ok());
	}

	@GetMapping("/read")
	public ResponseEntity<DataResponse<PostDto>> readPostById(@RequestParam Long id) {
		return ResponseEntity.ok(DataResponse.from(postService.findPostById(id)));
	}
}
