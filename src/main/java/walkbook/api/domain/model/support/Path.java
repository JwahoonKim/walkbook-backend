package walkbook.api.domain.model.support;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import walkbook.api.domain.model.Post;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Path {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    // Double이라 부동소수점 오차가 생기지 않는지 확인하자.
    @Embedded
    private Line line;

    @Override
    public String toString() {
        return "Path{" +
                "id=" + id +
                ", post=" + post +
                ", line=" + line +
                '}';
    }

    public Path(Post post, Line line) {
        setPost(post);
        setLine(line);
    }
}
