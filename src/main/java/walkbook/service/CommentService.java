package walkbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walkbook.domain.Comment;
import walkbook.domain.Post;
import walkbook.domain.User;
import walkbook.dto.request.comment.CreateCommentRequest;
import walkbook.dto.request.comment.UpdateCommentRequest;
import walkbook.dto.response.comment.CommentResponseDto;
import walkbook.repository.CommentRepository;
import walkbook.repository.PostRepository;
import walkbook.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponseDto save(User authUser, Long postId, CreateCommentRequest request) {
        if (authUser == null) {
            throw new RuntimeException("권한이 없습니다.");
        }
        User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new RuntimeException("해당 id의 회원이 존재하지 않습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("해당 id의 글이 존재하지 않습니다."));
        Comment comment = Comment.createComment(user, post, request.getContent());
        Comment saved = commentRepository.save(comment);
        return CommentResponseDto.of(authUser, saved);
    }


    public CommentResponseDto findById(User authUser, Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id의 댓글이 존재하지 않습니다."));
        return CommentResponseDto.of(authUser, comment);
    }

    public void update(User authUser, Long id, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 id의 댓글이 존재하지 않습니다."));
        authCheck(authUser, comment);
        comment.setContent(request.getContent());
    }

    public void delete(User authUser, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 id의 댓글이 존재하지 않습니다."));
        authCheck(authUser, comment);
        commentRepository.delete(comment);
    }

    private void authCheck(User authUser, Comment comment) {
        User author = comment.getUser();
        if (!authUser.getId().equals(author.getId())) {
            throw new RuntimeException("권한이 없습니다.");
        }
    }
}
