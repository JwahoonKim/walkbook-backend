package walkbook.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import walkbook.api.dto.request.commentReply.CreateCommentReplyRequest;
import walkbook.api.dto.request.commentReply.UpdateCommentReplyRequest;
import walkbook.api.dto.response.commentReply.CommentReplyResponseDto;
import walkbook.api.service.CommentReplyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentReplyApiController {

    private final CommentReplyService commentReplyService;

    // 대댓글 등록
    @PostMapping("/v1/comment/{commentId}/comment-reply")
    public Long create(@PathVariable Long commentId, @RequestBody CreateCommentReplyRequest request) {
        return commentReplyService.save(commentId, request);
    }

    // 대댓글 조회
    @GetMapping("/v1/comment/{commentId}/comment-reply/{replyId}")
    public CommentReplyResponseDto findById(
            @PathVariable Long commentId, @PathVariable Long replyId) {
        return commentReplyService.findById(commentId, replyId);
    }

    // 대댓글 삭제
    @DeleteMapping("/v1/comment/{commentId}/comment-reply/{replyId}")
    public void delete(@PathVariable Long commentId, @PathVariable Long replyId) {
        commentReplyService.delete(commentId, replyId);
    }


    // 대댓글 수정
    @PatchMapping("/v1/comment/{commentId}/comment-reply/{replyId}")
    public void update(@PathVariable Long commentId,
                        @PathVariable Long replyId,
                        @RequestBody UpdateCommentReplyRequest request) {
        commentReplyService.update(commentId, replyId, request);
    }

}
