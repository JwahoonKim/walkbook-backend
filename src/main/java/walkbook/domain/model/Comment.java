package walkbook.domain.model;

import lombok.NoArgsConstructor;
import walkbook.domain.model.support.DateEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Comment extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<CommentReply> replies = new ArrayList<>();

}
