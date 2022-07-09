package walkbook.api.dto.request.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserRequest {

    @NotBlank
    private String nickname;
    private String description;

}
