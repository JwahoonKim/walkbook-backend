package walkbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import walkbook.domain.CommentReply;

public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {
}
