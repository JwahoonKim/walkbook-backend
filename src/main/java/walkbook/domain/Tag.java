package walkbook.domain;

import lombok.NoArgsConstructor;
import walkbook.domain.support.PostTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    @OneToMany(mappedBy = "tag")
    private List<PostTag> postTags = new ArrayList<>();

}
