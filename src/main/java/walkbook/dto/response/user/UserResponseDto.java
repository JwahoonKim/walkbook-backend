package walkbook.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import walkbook.domain.User;

@Data
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Long id;
    private String username;
    private String nickname;
    private String description;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .description(user.getDescription())
                .build();
    }
}
