package walkbook.domain.model;

import lombok.NoArgsConstructor;
import walkbook.domain.model.support.DateEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class CommentReply extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Comment comment;

    private String content;

}
