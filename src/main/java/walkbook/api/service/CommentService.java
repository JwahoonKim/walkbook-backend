package walkbook.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walkbook.api.domain.model.Comment;
import walkbook.api.domain.model.Post;
import walkbook.api.domain.model.User;
import walkbook.api.repository.CommentRepository;
import walkbook.api.repository.PostRepository;
import walkbook.api.repository.UserRepository;
import walkbook.api.dto.request.comment.CreateCommentRequest;
import walkbook.api.dto.request.comment.UpdateCommentRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Long save(Long postId, CreateCommentRequest request) {
        Post post = postRepository.findById(postId);
        User user = userRepository.findById(request.getUserId());

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(request.getContent());

        commentRepository.save(comment);
        return comment.getId();
    }


    public Comment findById(Long id) {
        return commentRepository.findById(id);
    }

    public Long update(Long id, UpdateCommentRequest request) {
        Comment updateComment = new Comment();
        updateComment.setContent(request.getContent());
        return commentRepository.update(id, updateComment);
    }

    // 연관관계 있는것들도 없애줘야함
    public void remove(Long id) {
        commentRepository.remove(id);
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}
