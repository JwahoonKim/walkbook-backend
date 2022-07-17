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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PostLike> likePosts = new ArrayList<>(); // 좋아요를 누른 글

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Post> myPosts = new ArrayList<>(); // 내가 작성한 글

    public User(String username, String password, String nickname, String description) {
        this.username =  username;
        this.password = password;
        this.nickname = nickname;
        this.description = description;
    }

    //== 패스워드 암호화 ==//
//    public void encodePassword(PasswordEncoder passwordEncoder){
//        this.password = passwordEncoder.encode(password);
//    }

}
