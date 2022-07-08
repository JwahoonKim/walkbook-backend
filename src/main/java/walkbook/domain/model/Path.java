package walkbook.domain.model;

import lombok.NoArgsConstructor;
import walkbook.domain.model.support.Line;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Path {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    // Double이라 부동소수점 오차가 생기지 않는지 확인하자.
    @Embedded
    private Line line;

}
