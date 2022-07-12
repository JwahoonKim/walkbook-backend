package walkbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walkbook.domain.Comment;
import walkbook.domain.CommentReply;
import walkbook.domain.User;
import walkbook.dto.request.commentReply.CreateCommentReplyRequest;
import walkbook.dto.request.commentReply.UpdateCommentReplyRequest;
import walkbook.dto.response.commentReply.CommentReplyResponseDto;
import walkbook.repository.CommentReplyRepository;
import walkbook.repository.CommentRepository;
import walkbook.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentReplyService {

    private final CommentReplyRepository commentReplyRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentReplyResponseDto save(Long commentId, CreateCommentReplyRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("해당 id의 댓글이 존재하지 않습니다.."));
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("해당 id의 회원이 존재하지 않습니다."));
        CommentReply reply = CommentReply.creatCommentReply(user, comment, request.getContent());
        CommentReply saved = commentReplyRepository.save(reply);
        return CommentReplyResponseDto.of(saved);
    }

    public CommentReplyResponseDto findById(Long replyId) {
        CommentReply reply = commentReplyRepository.findById(replyId).orElseThrow(() -> new RuntimeException("해당 id의 대댓글이 존재하지 않습니다."));
        return CommentReplyResponseDto.of(reply);
    }

    public void delete(Long replyId) {
        CommentReply findReply = commentReplyRepository.findById(replyId).orElseThrow(() -> new RuntimeException("해당 id의 대댓글이 존재하지 않습니다."));
        commentReplyRepository.delete(findReply);
    }

    public void update(Long replyId, UpdateCommentReplyRequest request) {
        CommentReply findReply = commentReplyRepository.findById(replyId).orElseThrow(() -> new RuntimeException("해당 id의 대댓글이 존재하지 않습니다."));
        findReply.setContent(request.getContent());
    }
}
