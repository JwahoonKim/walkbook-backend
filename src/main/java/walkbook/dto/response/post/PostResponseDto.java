package walkbook.dto.response.post;

import lombok.*;
import walkbook.domain.Post;
import walkbook.domain.User;
import walkbook.domain.support.Line;
import walkbook.domain.support.Path;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long postId;
    private UserResponseDto user;
    private String title;
    private String description;
    private String startPlace;
    private String endPlace;
    private String tmi;

    private final List<Line> paths = new ArrayList<>();
    // TODO: 아래 내용 채워주기
//    private List<PostTag> postTags = new ArrayList<>();
//    private List<PostLike> like = new ArrayList<>();
//    private List<Comment> comments = new ArrayList<>();

    public static PostResponseDto of(Post post) {
        PostResponseDto dto = PostResponseDto.builder()
                .postId(post.getId())
                .user(new UserResponseDto(post.getUser()))
                .title(post.getTitle())
                .description(post.getDescription())
                .startPlace(post.getStartPlace())
                .endPlace(post.getEndPlace())
                .tmi(post.getTmi())
                .build();

        // Path 정보 담아주기
        pathSetting(dto, post.getPaths());

        return dto;
    }

    private static void pathSetting(PostResponseDto dto, List<Path> paths) {
        paths.stream()
                .map(Line::new)
                .forEach(line -> dto.getPaths().add(line));
    }

    @Data
    public static class UserResponseDto {

        private Long id;
        private String username;
        private String nickname;
        private String description;

        public UserResponseDto(User entity) {
            id = entity.getId();
            username = entity.getUsername();
            nickname = entity.getNickname();
            description = entity.getDescription();
        }
    }
}
