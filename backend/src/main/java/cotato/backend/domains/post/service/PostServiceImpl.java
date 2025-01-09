package cotato.backend.domains.post.service;

import static cotato.backend.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.excel.ExcelUtils;
import cotato.backend.common.exception.ApiException;
import cotato.backend.domains.post.dto.response.PostResponse;
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
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final PostJdbcRepository postJdbcRepository;
	private final PostNamedService postNamedService;

	private final static int VIEWS_INIT_VALUE = 0;
	private final static int LIKES_INIT_VALUE = 0;

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
					return new Post(title, content, name, VIEWS_INIT_VALUE, LIKES_INIT_VALUE);
				})
				.toList();
			postJdbcRepository.saveAllPost(posts);
			log.info("엑셀 파일로부터 {}개의 게시글 저장 완료", posts.size());
		} catch (Exception e) {
			log.error("엑셀 파일 처리 중 오류 발생: {}", e.getMessage());
			throw ApiException.from(POST_SAVE_FAILED);
		}
	}

	@Override
	public void saveSinglePost(SaveSinglePostRequest request) {
		try {
			postRepository.save(request.toEntity());
			log.info("게시글 저장 완료: {}", request.title());
		} catch (Exception e) {
			log.error("게시글 저장 중 오류 발생: {}", e.getMessage());
			throw ApiException.from(POST_SAVE_FAILED);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public PostResponse findPostById(Long id) {
		try {
			Post post = postRepository.findById(id)
				.orElseThrow(() -> ApiException.from(POST_NOT_FOUND));
			postNamedService.increase(post, String.valueOf(post.getId()));
			postRepository.saveAndFlush(post);
			return PostResponse.from(post);
		} catch (OptimisticLockingFailureException e) {
			log.warn("게시글 동시성 문제 발생 - ID: {}", id);
			throw ApiException.from(CONCURRENCY_PROBLEM);
		}
	}

	@Override
	@Cacheable(value = "posts", key = "#pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort")
	public Page<PostResponse> findPostsSortedByLikes(Pageable pageable) {
		try {
			Page<Post> posts = postRepository.findAll(pageable);
			log.info("게시글 목록 조회 완료 - 총 게시글 수: {}", posts.getTotalElements());
			return posts.map(PostResponse::from);
		} catch (Exception e) {
			log.error("게시글 목록 조회 중 오류 발생: {}", e.getMessage());
			throw ApiException.from(POST_NOT_FOUND);
		}
	}

	@Override
	public void deletePostById(Long id) {
		validatePostById(id);
		clearPostCache();
		postRepository.deleteById(id);
		log.info("게시글 삭제 완료 - ID: {}", id);
	}

	private void validatePostById(Long id) {
		if (!postRepository.existsById(id)) {
			log.warn("존재하지 않는 게시글 - ID: {}", id);
			throw ApiException.from(POST_NOT_FOUND);
		}
	}


	@CacheEvict(value = "posts", allEntries = true)
	public void clearPostCache() {
		log.info("게시글 캐시 초기화");
	}
}
