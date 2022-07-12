package walkbook.domain.support;

import lombok.Getter;
import lombok.NoArgsConstructor;
import walkbook.domain.Post;
import walkbook.domain.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public static PostLike createLike(User user, Post post) {
        PostLike postLike = new PostLike();
        postLike.setUser(user);
        postLike.setPost(post);
        return postLike;
    }

    private void setUser(User user) {
        this.user = user;
        user.getLikePosts().add(this);
    }

    private void setPost(Post post) {
        this.post = post;
        post.getLike().add(this);
    }

}
