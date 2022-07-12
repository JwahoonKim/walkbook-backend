package walkbook.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import walkbook.domain.support.DateEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
public class CommentReply extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    private String content;

    // 연관관계 편의 메서드
    public void setComment(Comment comment) {
        this.comment = comment;
        comment.getReplies().add(this);
    }

    public static CommentReply creatCommentReply(User user, Comment comment, String content) {
        CommentReply commentReply = new CommentReply();
        commentReply.setUser(user);
        commentReply.setComment(comment);
        commentReply.setContent(content);
        return commentReply;
    }
}
