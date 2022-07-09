package walkbook.api.domain.model;

import lombok.*;
import walkbook.api.domain.model.support.DateEntity;
import walkbook.api.domain.model.support.PostLike;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class User extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // 로그인 아이디

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

//    @Lob
    private String description;

    @OneToMany(mappedBy = "user")
    private List<PostLike> postLikes = new ArrayList<>(); // 좋아요를 누른 글

    @OneToMany(mappedBy = "user")
    private List<Post> myPosts = new ArrayList<>(); // 내가 작성한 글



}
