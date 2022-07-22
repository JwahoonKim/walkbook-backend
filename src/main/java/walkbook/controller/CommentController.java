package walkbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import walkbook.auth.AuthUser;
import walkbook.domain.User;
import walkbook.dto.request.comment.CreateCommentRequest;
import walkbook.dto.request.comment.UpdateCommentRequest;
import walkbook.dto.response.comment.CommentResponseDto;
import walkbook.service.CommentService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/v1/post/{postId}/comment")
    public CommentResponseDto create(@AuthUser User authUser, @PathVariable Long postId, @Valid @RequestBody CreateCommentRequest request) {
        return commentService.save(authUser, postId, request);
    }

    // 댓글 조회
    @GetMapping("/v1/comment/{commentId}")
    public CommentResponseDto findById(@AuthUser User authUser, @PathVariable Long commentId) {
        return commentService.findById(authUser, commentId);
    }

    // 댓글 수정
    @PatchMapping("/v1/comment/{commentId}")
    public Map<String, String> update(@AuthUser User authUser, @PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest request) {
        commentService.update(authUser, commentId, request);
        return Map.of("updateContent", request.getContent());
    }

    // 댓글 삭제
    @DeleteMapping("v1/comment/{commentId}")
    public String delete(@AuthUser User user, @PathVariable Long commentId) {
        commentService.delete(user, commentId);
        return "deleted";
    }

}
