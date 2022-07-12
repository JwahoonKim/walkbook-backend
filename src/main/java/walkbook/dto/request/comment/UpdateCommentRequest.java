package walkbook.dto.request.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class UpdateCommentRequest {

    @NotEmpty(message = "수정할 댓글 내용을 입력해주세요.")
    private final String content;

}
