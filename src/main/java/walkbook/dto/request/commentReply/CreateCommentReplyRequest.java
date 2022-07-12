package walkbook.dto.request.commentReply;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentReplyRequest {

    @NotNull(message = "userId를 입력해주세요.")
    private Long userId;
    @NotBlank(message = "대댓글 내용을 입력해주세요.")
    private String content;

}
