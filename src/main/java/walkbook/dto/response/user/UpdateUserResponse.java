package walkbook.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import walkbook.domain.User;

@Data
@AllArgsConstructor
@Builder
public class UpdateUserResponse {

    private Long id;
    private String username;
    private String nickname;
    private String description;

    public static UpdateUserResponse of(User updateUser) {
        return UpdateUserResponse.builder()
                .id(updateUser.getId())
                .username(updateUser.getUsername())
                .nickname(updateUser.getNickname())
                .description(updateUser.getDescription())
                .build();
    }
}
