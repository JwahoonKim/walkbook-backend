package walkbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import walkbook.domain.Post;
import walkbook.domain.User;
import walkbook.domain.support.PostLike;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findPostLikeByUserAndPost(User user, Post post);
}
