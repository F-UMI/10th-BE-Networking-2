package cotato.backend.domains.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cotato.backend.domains.post.entity.Post;
import jakarta.persistence.LockModeType;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from Post p where p.id = :id")
	Optional<Post> findByIdWithPessimisticLock(Long id);

	@Lock(LockModeType.OPTIMISTIC)
	@Query("select p from Post p where p.id = :id")
	Optional<Post>  findByIdWithOptimisticLock(Long id);
}