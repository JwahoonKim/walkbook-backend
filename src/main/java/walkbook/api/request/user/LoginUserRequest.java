package walkbook.api.request.user;

import lombok.Data;
import walkbook.api.domain.model.User;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginUserRequest {

    @NotEmpty
    private String id;

    @NotEmpty
    private String password;

    public User toUserEntity() {
        return User.builder()
                .username(id)
                .password(password)
                .build();
    }
}
