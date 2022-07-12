package walkbook.domain.support;

import lombok.NoArgsConstructor;
import walkbook.domain.Post;
import walkbook.domain.User;

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
