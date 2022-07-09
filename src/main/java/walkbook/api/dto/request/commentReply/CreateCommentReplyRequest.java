package walkbook.api.dto.request.commentReply;

import lombok.*;

@Data
@NoArgsConstructor
public class CreateCommentReplyRequest {

    private Long userId;
    private String content;

}
