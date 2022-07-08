package walkbook.api.request.user;

import lombok.Data;
import walkbook.api.domain.model.User;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateUserRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
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
