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

    public CommentReplyResponseDto save(User authUser, Long commentId, CreateCommentReplyRequest request) {
        if (authUser == null) {
            throw new RuntimeException("권한이 없습니다.");
        }
        User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new RuntimeException("해당 id의 회원이 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("해당 id의 댓글이 존재하지 않습니다.."));
        CommentReply reply = CommentReply.creatCommentReply(user, comment, request.getContent());
        CommentReply saved = commentReplyRepository.save(reply);
        return CommentReplyResponseDto.of(authUser, saved);
    }

    public CommentReplyResponseDto findById(User authUser, Long replyId) {
        CommentReply reply = commentReplyRepository.findById(replyId).orElseThrow(() -> new RuntimeException("해당 id의 대댓글이 존재하지 않습니다."));
        return CommentReplyResponseDto.of(authUser, reply);
    }

    public void delete(User authUser, Long replyId) {
        CommentReply findReply = commentReplyRepository.findById(replyId).orElseThrow(() -> new RuntimeException("해당 id의 대댓글이 존재하지 않습니다."));
        authCheck(authUser, findReply);
        commentReplyRepository.delete(findReply);
    }

    public void update(User authUser, Long replyId, UpdateCommentReplyRequest request) {
        CommentReply findReply = commentReplyRepository.findById(replyId).orElseThrow(() -> new RuntimeException("해당 id의 대댓글이 존재하지 않습니다."));
        authCheck(authUser, findReply);
        findReply.setContent(request.getContent());
    }

    private void authCheck(User authUser, CommentReply findReply) {
        if (authUser == null)
            throw new RuntimeException("권한이 없습니다.");

        User author = findReply.getUser();
        if (!authUser.getId().equals(author.getId()))
            throw new RuntimeException("권한이 없습니다.");
    }
}
