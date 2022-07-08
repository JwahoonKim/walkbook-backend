package walkbook.api.domain.model;

import lombok.NoArgsConstructor;
import walkbook.api.domain.model.support.PostLike;
import walkbook.api.domain.model.support.DateEntity;
import walkbook.api.domain.model.support.PostTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Post extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String title;

    private String description;
    private String startPlace;
    private String endPlace;
    private String tmi;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Path> paths = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostTag> postTags = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostLike> like = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    // 대댓글을 Post에서 관리해야할지 고민해보자
//    private List<ReplyComment> replyComments = new ArrayList<>();

}
