package walkbook.dto.request.commentReply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentReplyRequest {

    @NotBlank(message = "대댓글 내용을 입력해주세요.")
    private String content;

}
