package walkbook.dto.request.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentRequest {

    @NotEmpty(message = "수정할 댓글 내용을 입력해주세요.")
    private String content;

}
