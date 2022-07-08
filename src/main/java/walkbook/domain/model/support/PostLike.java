package walkbook.domain.model.support;

import lombok.NoArgsConstructor;
import walkbook.domain.model.Post;
import walkbook.domain.model.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class PostLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

}
