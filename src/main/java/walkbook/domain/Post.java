package walkbook.domain;

import lombok.*;
import walkbook.domain.support.*;
import walkbook.dto.request.post.UpdatePostRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Path> paths = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private final List<PostTag> postTags = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final List<PostLike> like = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private final List<Comment> comments = new ArrayList<>();


    // 대댓글을 Post에서 관리해야할지 고민해보자
//    private List<ReplyComment> replyComments = new ArrayList<>();


    public Post(User user, String title, String description, String startPlace, String endPlace, String tmi) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.tmi = tmi;
    }

    // 연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
        user.getMyPosts().add(this);
    }

    public void addPath(Path path) {
        paths.add(path);
        path.setPost(this);
    }

    public void update(UpdatePostRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.startPlace = request.getStartPlace();
        this.endPlace = request.getEndPlace();
        this.tmi = request.getTmi();
        updatePaths(request.getPaths());
    }

    private void updatePaths(List<Line> lines) {
        this.paths.clear();
        lines.forEach(line -> addPath(new Path(this, line)));
    }
}
