package walkbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import walkbook.domain.Comment;
import walkbook.dto.request.comment.CreateCommentRequest;
import walkbook.dto.request.comment.UpdateCommentRequest;
import walkbook.dto.response.comment.CommentResponseDto;
import walkbook.service.CommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/v1/post/{postId}/comment")
    public CommentResponseDto create(@PathVariable Long postId, @Valid @RequestBody CreateCommentRequest request) {
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
        commentService.delete(commentId);
    }

}
