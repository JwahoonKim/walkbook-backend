package walkbook.api.dto.response.commentReply;

import lombok.Data;
import walkbook.api.domain.model.Comment;
import walkbook.api.domain.model.CommentReply;
import walkbook.api.domain.model.User;

@Data
public class CommentReplyResponseDto {

    private Long id;
    private String content;
    private UserDto user;
    private CommentDto comment;

    public static CommentReplyResponseDto of(CommentReply reply) {
        CommentReplyResponseDto dto = new CommentReplyResponseDto();
        dto.setId(reply.getId());
        dto.setContent(reply.getContent());
        dto.setUser(new UserDto(reply.getUser()));
        dto.setComment(new CommentDto(reply.getComment()));
        return dto;
    }

    @Data
    private static class CommentDto {

        private Long id;
        private String content;

        public CommentDto(Comment comment) {
            id = comment.getId();
            content = comment.getContent();
        }
    }

    @Data
    private static class UserDto {

        private Long id;
        private String username;

        public UserDto(User user) {
            id = user.getId();
            username = user.getUsername();
        }
    }
}
