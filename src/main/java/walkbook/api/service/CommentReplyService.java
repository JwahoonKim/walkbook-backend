package walkbook.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walkbook.api.domain.model.Comment;
import walkbook.api.domain.model.CommentReply;
import walkbook.api.domain.model.User;
import walkbook.api.repository.CommentReplyRepository;
import walkbook.api.repository.CommentRepository;
import walkbook.api.repository.UserRepository;
import walkbook.api.dto.request.commentReply.CreateCommentReplyRequest;
import walkbook.api.dto.request.commentReply.UpdateCommentReplyRequest;
import walkbook.api.dto.response.commentReply.CommentReplyResponseDto;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentReplyService {

    private final CommentReplyRepository commentReplyRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public Long save(Long commentId, CreateCommentReplyRequest request) {
        Comment comment = commentRepository.findById(commentId);
        User user = userRepository.findById(request.getUserId());
        CommentReply reply = new CommentReply();
        reply.setComment(comment);
        reply.setUser(user);
        reply.setContent(request.getContent());

        return commentReplyRepository.save(reply);
    }

    public CommentReplyResponseDto findById(Long commentId, Long replyId) {
        CommentReply reply = commentReplyRepository.findById(replyId);
        return CommentReplyResponseDto.of(reply);
    }

    public void delete(Long commentId, Long replyId) {
        commentReplyRepository.remove(replyId);
    }

    public void update(Long commentId, Long replyId, UpdateCommentReplyRequest request) {
        commentReplyRepository.update(replyId, request.getContent());
    }
}
