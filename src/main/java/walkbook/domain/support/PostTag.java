package walkbook.domain.support;

import walkbook.domain.Post;
import walkbook.domain.Tag;

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
