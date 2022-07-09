package walkbook.api.dto.request.comment;

import lombok.Data;
import walkbook.api.domain.model.Comment;
import walkbook.api.domain.model.Post;
import walkbook.api.domain.model.User;

@Data
public class CreateCommentRequest {

    private final Long userId;
    private final String content;

    public Comment toEntity(User user, Post post, String content) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .build();
    }
}
