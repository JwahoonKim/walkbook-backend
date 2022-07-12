package walkbook.dto.response.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import walkbook.domain.Comment;

@Data
public class CommentResponseDto {

    private Long id;
    private String content;
    private UserDto user;
    private PostDto post;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        user = new UserDto(comment.getUser().getId(), comment.getUser().getUsername());
        post = new PostDto(comment.getPost().getId(), comment.getPost().getTitle());
    }

    // TODO: 대댓글 정보 추가

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
