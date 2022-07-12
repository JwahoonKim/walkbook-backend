package walkbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import walkbook.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}