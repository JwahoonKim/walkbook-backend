package walkbook.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import walkbook.domain.User;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    private String description;

    public User toUserEntity() {
        return User.builder()
                .username(username)
//                .password(passwordEncoder.encode(password))
                .password(password)
                .nickname(nickname)
                .description(description)
                .build();
    }
}
