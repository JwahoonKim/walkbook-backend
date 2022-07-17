package walkbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public CommentReplyResponseDto create(@PathVariable Long commentId, @RequestBody CreateCommentReplyRequest request) {
        return commentReplyService.save(commentId, request);
    }

    // 대댓글 조회
    // TODO: 자기가 쓴 대댓글인지 알려주기
    @GetMapping("/v1/comment/comment-reply/{replyId}")
    public CommentReplyResponseDto findById(@PathVariable Long replyId) {
        return commentReplyService.findById(replyId);
    }

    // 대댓글 삭제
    @DeleteMapping("/v1/comment/comment-reply/{replyId}")
    public void delete(@PathVariable Long replyId) {
        commentReplyService.delete(replyId);
    }

    // 대댓글 수정
    @PatchMapping("/v1/comment/comment-reply/{replyId}")
    public String update(@PathVariable Long replyId,
                        @RequestBody UpdateCommentReplyRequest request) {
        commentReplyService.update(replyId, request);
        return request.getContent();
    }

}
