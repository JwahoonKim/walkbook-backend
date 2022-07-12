package walkbook.dto.request.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {

    @NotNull(message = "유저 아이디를 입력해주세요.")
    private Long userId;
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String content;

}
