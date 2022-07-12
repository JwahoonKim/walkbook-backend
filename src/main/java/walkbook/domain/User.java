package walkbook.domain;

import lombok.*;
import walkbook.domain.support.DateEntity;
import walkbook.domain.support.PostLike;

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
    private final List<PostLike> postLikes = new ArrayList<>(); // 좋아요를 누른 글

    @OneToMany(mappedBy = "user")
    private final List<Post> myPosts = new ArrayList<>(); // 내가 작성한 글


    public User(String username, String password, String nickname, String description) {
        this.username =  username;
        this.password = password;
        this.nickname = nickname;
        this.description = description;
    }
}
