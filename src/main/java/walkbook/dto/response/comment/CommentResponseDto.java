package walkbook.dto.response.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import walkbook.domain.Comment;
import walkbook.domain.User;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private Boolean isAuthor;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private UserDto user;
    private PostDto post;
    // TODO: 대댓글 정보 추가

    public static CommentResponseDto of(User authUser, Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .isAuthor(isAuthor(authUser, comment.getUser()))
                .createdAt(comment.getCreatedAt())
                .lastModifiedAt(comment.getLastModifiedAt())
                .user(new UserDto(comment.getUser().getId(), comment.getUser().getUsername()))
                .post(new PostDto(comment.getPost().getId(), comment.getPost().getTitle()))
                .build();
    }

    private static Boolean isAuthor(User authUser, User user) {
        return authUser.getId().equals(user.getId());
    }

    @Data
    @AllArgsConstructor
    public static class UserDto {
        private Long id;
        private String username;
    }

    @Data
    @AllArgsConstructor
    public static class PostDto {
        private Long postId;
        private String title;
    }
}
