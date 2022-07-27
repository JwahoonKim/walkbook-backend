package walkbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import walkbook.auth.AuthUser;
import walkbook.domain.User;
import walkbook.dto.request.commentReply.CreateCommentReplyRequest;
import walkbook.dto.request.commentReply.UpdateCommentReplyRequest;
import walkbook.dto.response.commentReply.CommentReplyResponseDto;
import walkbook.service.CommentReplyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentReplyController {

    private final CommentReplyService commentReplyService;

    // 대댓글 등록
    @PostMapping("/v1/comment/{commentId}/comment-reply")
    public CommentReplyResponseDto create(@AuthUser User user, @PathVariable Long commentId, @RequestBody CreateCommentReplyRequest request) {
        return commentReplyService.save(user, commentId, request);
    }

    // 대댓글 조회
    @GetMapping("/v1/comment/comment-reply/{replyId}")
    public CommentReplyResponseDto findById(@AuthUser User authUser, @PathVariable Long replyId) {
        return commentReplyService.findById(authUser, replyId);
    }

    // 대댓글 삭제
    @DeleteMapping("/v1/comment/comment-reply/{replyId}")
    public void delete(@AuthUser User authUser, @PathVariable Long replyId) {
        commentReplyService.delete(authUser, replyId);
    }

    // 대댓글 수정
    @PatchMapping("/v1/comment/comment-reply/{replyId}")
    public String update(
            @AuthUser User authUser,
            @PathVariable Long replyId,
            @RequestBody UpdateCommentReplyRequest request)
    {
        commentReplyService.update(authUser, replyId, request);
        return request.getContent();
    }

}
