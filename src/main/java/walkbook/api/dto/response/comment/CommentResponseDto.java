package walkbook.api.dto.response.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import walkbook.api.domain.model.Comment;

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

    // 대댓글 정보 추가할 예정

    @Data
    @AllArgsConstructor
    private static class UserDto {
        private Long id;
        private String username;
    }

    @Data
    @AllArgsConstructor
    private static class PostDto {
        private Long postId;
        private String title;
    }
}
