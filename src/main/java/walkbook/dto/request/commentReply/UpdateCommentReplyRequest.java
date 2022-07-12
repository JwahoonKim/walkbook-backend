package walkbook.dto.request.commentReply;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCommentReplyRequest {
    private String content;
}
