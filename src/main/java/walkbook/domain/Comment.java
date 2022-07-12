package walkbook.domain;

import lombok.*;
import walkbook.domain.support.DateEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Comment extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CommentReply> replies = new ArrayList<>();

    // 연관관계 편의 메서드
    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public static Comment createComment(User user, Post post, String content) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(content);
        return comment;
    }

}
