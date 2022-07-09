package walkbook.api.dto.request.user;

import lombok.Data;
import walkbook.api.domain.model.User;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUserRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String password;

    public User toUserEntity() {
        return User.builder()
                .username(id)
                .password(password)
                .build();
    }
}
