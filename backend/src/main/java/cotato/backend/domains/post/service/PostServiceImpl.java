package cotato.backend.domains.post.service;

import static cotato.backend.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.excel.ExcelUtils;
import cotato.backend.common.exception.ApiException;
import cotato.backend.domains.post.dto.PostDto;
import cotato.backend.domains.post.dto.request.SavePostsByExcelRequest;
import cotato.backend.domains.post.dto.request.SaveSinglePostRequest;
import cotato.backend.domains.post.entity.Post;
import cotato.backend.domains.post.repository.PostJdbcRepository;
import cotato.backend.domains.post.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final PostJdbcRepository postJdbcRepository;

	@Override
	// 로컬 파일 경로로부터 엑셀 파일을 읽어 Post 엔터티로 변환하고 저장
	public void saveEstatesByExcel(SavePostsByExcelRequest request) {
		try {
			// 엑셀 파일을 읽어 데이터 프레임 형태로 변환
			List<Post> posts = ExcelUtils.parseExcelFile(request.path()).stream()
				.map(row -> {
					String title = row.get("title");
					String content = row.get("content");
					String name = row.get("name");
					return new Post(title, content, name, 0, 0);
				})
				.toList();
			postJdbcRepository.saveAllPost(posts);
		} catch (Exception e) {
			log.error("Failed to save estates by excel", e);
			throw ApiException.from(INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void saveSinglePost(SaveSinglePostRequest request) {
		try {
			postRepository.save(request.toEntity());
		} catch (Exception e) {
			log.error("Failed to save single post", e);
			throw ApiException.from(INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public PostDto findPostById(Long id) {
		try {
			Post findPost = postRepository.findById(id).orElseThrow();
			findPost.increaseViews();
			return PostDto.builder()
				.title(findPost.getTitle())
				.content(findPost.getContent())
				.name(findPost.getName())
				.build();
		} catch (Exception e) {
			log.error("Failed to find post by id", e);
			throw ApiException.from(INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Page<PostDto> findPostsSortedByLikes(Pageable pageable) {
		Page<Post> posts = postRepository.findAll(pageable);
		return posts.map(PostDto::from);
	}

	@Override
	public void deletePostById(Long id) {
		try {
			postRepository.deleteById(id);
		}catch (Exception e) {
			log.error("Failed to delete post by id", e);
			throw ApiException.from(INTERNAL_SERVER_ERROR);
		}
	}
}
