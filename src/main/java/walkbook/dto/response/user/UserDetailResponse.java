package walkbook.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import walkbook.domain.User;
import walkbook.dto.response.post.PostResponseDto;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserDetailResponse {

    private Long id;
    private String username;
    private String nickname;
    private String description;
    private final List<PostResponseDto> myPosts = new ArrayList<>();
    // TODO: 좋아하는 글 추가

    public static UserDetailResponse of(User user) {
        UserDetailResponse response = UserDetailResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .description(user.getDescription())
                .build();

        settingPosts(user, response);

        return response;
    }

    private static void settingPosts(User user, UserDetailResponse response) {
        user.getMyPosts()
                .stream()
                .map(post -> PostResponseDto.of(user, post))
                .forEach(response.myPosts::add);
    }

}
