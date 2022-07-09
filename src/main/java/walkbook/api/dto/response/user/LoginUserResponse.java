package walkbook.api.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginUserResponse {

    private String username;
    private String password;

}
