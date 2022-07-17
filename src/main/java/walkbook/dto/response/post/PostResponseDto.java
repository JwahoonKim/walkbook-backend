package walkbook.dto.response.post;

import lombok.Builder;
import lombok.Data;
import walkbook.domain.Post;
import walkbook.domain.User;
import walkbook.dto.response.user.UserResponseDto;

@Data
@Builder
public class PostResponseDto {

    private Long postId;
    private UserResponseDto author;
    private String title;
    private String description;
    private String startPlace;
    private String endPlace;
    private String tmi;
    private Boolean isAuthor;

    public static PostResponseDto of(User user, Post post) {
        return PostResponseDto.builder()
                .postId(post.getId())
                .author(UserResponseDto.of(post.getUser()))
                .title(post.getTitle())
                .description(post.getDescription())
                .startPlace(post.getStartPlace())
                .endPlace(post.getEndPlace())
                .tmi(post.getTmi())
                .isAuthor(isAuthor(user, post))
                .build();
    }

    private static Boolean isAuthor(User user, Post post) {
        if (user == null)
            return false;
        return user.getId().equals(post.getUser().getId());
    }
}