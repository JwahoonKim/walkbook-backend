package walkbook.dto.response.commentReply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import walkbook.domain.Comment;
import walkbook.domain.CommentReply;
import walkbook.domain.User;

@Data
@AllArgsConstructor
@Builder
public class CommentReplyResponseDto {

    private Long id;
    private String content;
    private Boolean isAuthor;
    private UserDto user;
    private CommentDto comment;

    public static CommentReplyResponseDto of(User authUser, CommentReply reply) {
        return CommentReplyResponseDto.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .isAuthor(isAuthor(authUser, reply.getUser()))
                .user(new UserDto(reply.getUser()))
                .comment(new CommentDto(reply.getComment()))
                .build();
    }

    private static Boolean isAuthor(User authUser, User user) {
        if (authUser == null)
            return false;
        return authUser.getId().equals(user.getId());
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
