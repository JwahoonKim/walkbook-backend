package walkbook.dto.request.user;

import lombok.Data;
import walkbook.domain.User;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUserRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public User toUserEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .build();
    }
}
