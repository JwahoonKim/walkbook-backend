package walkbook.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import walkbook.domain.Post;
import walkbook.domain.User;
import walkbook.domain.support.Line;
import walkbook.domain.support.Path;
import walkbook.dto.response.user.UserResponseDto;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PostDetailResponseDto {

    private Long postId;
    private String title;
    private String description;
    private String startPlace;
    private String endPlace;
    private String tmi;
    private Boolean isAuthor;
    private Boolean isLiked;
    private Integer likeCount;
    private UserResponseDto author;

    private final List<Line> paths = new ArrayList<>();
    // TODO: 아래 내용 채워주기
//    private List<PostTag> postTags = new ArrayList<>();
//    private List<PostLike> like = new ArrayList<>();
//    private List<Comment> comments = new ArrayList<>();

    public static PostDetailResponseDto of(User user, Post post) {
        PostDetailResponseDto dto = PostDetailResponseDto.builder()
                .postId(post.getId())
                .author(UserResponseDto.of(post.getUser()))
                .title(post.getTitle())
                .description(post.getDescription())
                .startPlace(post.getStartPlace())
                .endPlace(post.getEndPlace())
                .tmi(post.getTmi())
                .isAuthor(isAuthor(user, post))
                .isLiked(isLiked(user, post))
                .likeCount(post.getLike().size())
                .build();

        // Path 정보 담아주기
        pathSetting(dto, post.getPaths());

        return dto;
    }


    private static Boolean isLiked(User user, Post post) {
        if (user == null)
            return false;

        return user.getLikePosts()
                .stream()
                .anyMatch(likePost -> likePost.getPost().getId().equals(post.getId()));
    }

    private static Boolean isAuthor(User user, Post post) {
        if (user == null)
            return false;

        User author = post.getUser();
        return author.getId().equals(user.getId());
    }

    private static void pathSetting(PostDetailResponseDto dto, List<Path> paths) {
        paths.stream()
                .map(Line::new)
                .forEach(line -> dto.getPaths().add(line));
    }

}
