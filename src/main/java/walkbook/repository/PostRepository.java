package walkbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import walkbook.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}