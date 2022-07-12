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

    public CommentResponseDto save(Long postId, CreateCommentRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("해당 id의 회원이 존재하지 않습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("해당 id의 글이 존재하지 않습니다."));
        Comment comment = Comment.createComment(user, post, request.getContent());
        Comment saved = commentRepository.save(comment);
        return new CommentResponseDto(saved);
    }


    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id의 댓글이 존재하지 않습니다."));
    }

    public Long update(Long id, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 id의 댓글이 존재하지 않습니다."));
        comment.setContent(request.getContent());
        return comment.getId();
    }

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 id의 댓글이 존재하지 않습니다."));
        commentRepository.delete(comment);
    }

}
