package walkbook.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import walkbook.api.domain.model.Comment;
import walkbook.api.dto.request.comment.CreateCommentRequest;
import walkbook.api.dto.request.comment.UpdateCommentRequest;
import walkbook.api.dto.response.comment.CommentResponseDto;
import walkbook.api.service.CommentService;
import walkbook.api.service.PostService;
import walkbook.api.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentApiController {

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    // 댓글 등록
    @PostMapping("/v1/post/{postId}/comment")
    public Long create(@PathVariable Long postId, @Valid @RequestBody CreateCommentRequest request) {
        return commentService.save(postId, request);
    }

    // 댓글 조회
    @GetMapping("/v1/comment/{commentId}")
    public CommentResponseDto findById(@PathVariable Long commentId) {
        Comment comment = commentService.findById(commentId);
        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    @PatchMapping("/v1/comment/{commentId}")
    public Long update(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest request) {
        return commentService.update(commentId, request);
    }

    // 댓글 삭제
    @DeleteMapping("v1/comment/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.remove(commentId);
    }

}
