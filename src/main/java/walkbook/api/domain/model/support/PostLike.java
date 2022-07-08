package walkbook.api.domain.model.support;

import lombok.NoArgsConstructor;
import walkbook.api.domain.model.Post;
import walkbook.api.domain.model.User;

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
