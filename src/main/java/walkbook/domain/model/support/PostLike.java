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

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

}
