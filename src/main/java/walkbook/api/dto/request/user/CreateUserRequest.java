package walkbook.api.dto.request.user;

import lombok.Data;
import walkbook.api.domain.model.User;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUserRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    private String description;


    public User toUserEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .description(description)
                .build();
    }
}
