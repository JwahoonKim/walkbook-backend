package walkbook.api.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import walkbook.api.domain.model.Post;
import walkbook.api.domain.model.User;
import walkbook.api.domain.model.support.Line;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private UserResponseDto user;
    private String title;
    private String description;
    private String startPlace;
    private String endPlace;
    private String tmi;

    private final List<Line> paths = new ArrayList<>();
//    private List<PostTag> postTags = new ArrayList<>();
//    private List<PostLike> like = new ArrayList<>();
//    private List<Comment> comments = new ArrayList<>();

    public static PostResponseDto of(Post post) {
        PostResponseDto dto = PostResponseDto.builder()
                .user(new UserResponseDto(post.getUser()))
                .title(post.getTitle())
                .description(post.getDescription())
                .startPlace(post.getStartPlace())
                .endPlace(post.getEndPlace())
                .tmi(post.getTmi())
                .build();

        // Path 정보 담아주기
        post.getPaths().stream()
                .map(Line::new)
                .forEach(line -> dto.getPaths().add(line));

        return dto;
    }

    @Data
    private static class UserResponseDto {

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
