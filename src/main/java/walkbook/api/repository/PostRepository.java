package walkbook.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import walkbook.api.domain.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}