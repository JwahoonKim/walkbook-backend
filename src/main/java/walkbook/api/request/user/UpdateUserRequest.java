package walkbook.api.request.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateUserRequest {

    @NotEmpty
    private String nickname;
    private String description;

}
