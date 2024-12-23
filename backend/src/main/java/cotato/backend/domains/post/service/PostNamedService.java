package cotato.backend.domains.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.domains.post.entity.Post;
import cotato.backend.domains.post.repository.LockRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostNamedService {

	private final LockRepository lockRepository;

	public void increase(Post post, String id) {
		try {
			lockRepository.getLock(id.toString());
			post.increaseViews();
		} finally {
			lockRepository.releaseLock(id.toString());
		}
	}
}


