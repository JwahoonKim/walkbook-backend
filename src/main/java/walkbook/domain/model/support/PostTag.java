package walkbook.domain.model.support;

import walkbook.domain.model.Post;
import walkbook.domain.model.Tag;

import javax.persistence.*;

@Entity
public class PostTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Tag tag;

}
