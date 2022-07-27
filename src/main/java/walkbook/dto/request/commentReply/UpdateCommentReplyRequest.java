package walkbook.dto.request.commentReply;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UpdateCommentReplyRequest {
    @NotBlank(message = "대댓글 내용을 입력해주세요.")
    private String content;
}
