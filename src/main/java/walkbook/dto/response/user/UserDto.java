package walkbook.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import walkbook.domain.User;

@Data
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String nickname;
    private String description;

    public static UserDto of(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .description(user.getDescription())
                .build();
    }

    // 좋아하는 글
    // 내가 작성한 글 내용 추가
}
