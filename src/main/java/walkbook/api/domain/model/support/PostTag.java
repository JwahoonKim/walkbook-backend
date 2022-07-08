package walkbook.api.domain.model.support;

import walkbook.api.domain.model.Post;
import walkbook.api.domain.model.Tag;

import javax.persistence.*;

@Entity
public class PostTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

}
