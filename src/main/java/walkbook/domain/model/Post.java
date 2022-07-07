package walkbook.domain.model;

import lombok.NoArgsConstructor;
import walkbook.domain.model.support.DateEntity;
import walkbook.domain.model.support.PostLike;
import walkbook.domain.model.support.PostTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Post extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String title;

    private String description;
    private String startPlace;
    private String endPlace;
    private String tmi;

    @OneToMany(mappedBy = "post")
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
