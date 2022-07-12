package walkbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import walkbook.domain.Comment;
import walkbook.domain.Post;
import walkbook.domain.User;
import walkbook.dto.request.commentReply.CreateCommentReplyRequest;
import walkbook.dto.request.commentReply.UpdateCommentReplyRequest;
import walkbook.dto.response.commentReply.CommentReplyResponseDto;
import walkbook.repository.CommentRepository;
import walkbook.repository.PostRepository;
import walkbook.repository.UserRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class CommentReplyServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentReplyService commentReplyService;

    @Test
    public void 대댓글_등록_성공() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
        Comment savedComment = commentRepository.save(Comment.createComment(user, post, "comment1"));

        //when
        CreateCommentReplyRequest request = new CreateCommentReplyRequest(user.getId(), "대댓글1");
        CommentReplyResponseDto response = commentReplyService.save(savedComment.getId(), request);

        //then
        assertThat(response.getContent()).isEqualTo("대댓글1");
        assertThat(response.getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getComment().getContent()).isEqualTo(savedComment.getContent());
    }

    @Test
    public void 대댓글_조회_성공() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
        Comment savedComment = commentRepository.save(Comment.createComment(user, post, "comment1"));

        CreateCommentReplyRequest request = new CreateCommentReplyRequest(user.getId(), "대댓글1");
        CommentReplyResponseDto response = commentReplyService.save(savedComment.getId(), request);

        //when
        CommentReplyResponseDto findReply = commentReplyService.findById(response.getId());

        //then
        assertThat(findReply.getContent()).isEqualTo("대댓글1");
        assertThat(findReply.getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(findReply.getComment().getContent()).isEqualTo(savedComment.getContent());
    }

    @Test
    public void 대댓글_삭제_성공() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
        Comment savedComment = commentRepository.save(Comment.createComment(user, post, "comment1"));

        CreateCommentReplyRequest request = new CreateCommentReplyRequest(user.getId(), "대댓글1");
        CommentReplyResponseDto response = commentReplyService.save(savedComment.getId(), request);

        //when
        commentService.delete(response.getId());

        //then
        assertThrows(RuntimeException.class, () -> commentService.findById(response.getId()));
    }

    @Test
    public void 대댓글_수정_성공() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
        Comment savedComment = commentRepository.save(Comment.createComment(user, post, "comment1"));

        CreateCommentReplyRequest request = new CreateCommentReplyRequest(user.getId(), "대댓글1");
        CommentReplyResponseDto response = commentReplyService.save(savedComment.getId(), request);

        //when
        commentReplyService.update(response.getId(), new UpdateCommentReplyRequest("변경된대댓글"));
        CommentReplyResponseDto findReply = commentReplyService.findById(response.getId());

        //then
        assertThat(findReply.getContent()).isEqualTo("변경된대댓글");
    }
}