package walkbook.dto.response.commentReply;

import lombok.Data;
import walkbook.domain.Comment;
import walkbook.domain.CommentReply;
import walkbook.domain.User;

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
    public static class CommentDto {
        private Long id;
        private String content;

        public CommentDto(Comment comment) {
            id = comment.getId();
            content = comment.getContent();
        }
    }

    @Data
    public static class UserDto {
        private Long id;
        private String username;

        public UserDto(User user) {
            id = user.getId();
            username = user.getUsername();
        }
    }
}
