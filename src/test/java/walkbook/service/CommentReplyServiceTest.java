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

    @Autowired PostService postService;
    @Autowired PostRepository postRepository;
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired CommentService commentService;
    @Autowired CommentReplyService commentReplyService;

    @Test
    public void 대댓글_등록_성공() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
        Comment comment = commentRepository.save(Comment.createComment(user, post, "comment1"));

        //when
        CreateCommentReplyRequest request = new CreateCommentReplyRequest("대댓글1");
        CommentReplyResponseDto reply = commentReplyService.save(user, comment.getId(), request);

        //then
        assertThat(reply.getIsAuthor()).isTrue();
        assertThat(reply.getContent()).isEqualTo("대댓글1");
        assertThat(reply.getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(reply.getComment().getContent()).isEqualTo(comment.getContent());
    }

    @Test
    public void 대댓글_등록_실패_권한없음() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
        Comment comment = commentRepository.save(Comment.createComment(user, post, "comment1"));

        //when + then
        CreateCommentReplyRequest request = new CreateCommentReplyRequest("대댓글1");
        assertThrows(RuntimeException.class, () -> commentReplyService.save(null, comment.getId(), request));
    }

    @Test
    public void 대댓글_조회_성공() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
        Comment savedComment = commentRepository.save(Comment.createComment(user, post, "comment1"));

        CreateCommentReplyRequest request = new CreateCommentReplyRequest("대댓글1");
        CommentReplyResponseDto response = commentReplyService.save(user, savedComment.getId(), request);

        //when
        CommentReplyResponseDto findReply = commentReplyService.findById(null, response.getId());

        //then
        assertThat(findReply.getContent()).isEqualTo("대댓글1");
        assertThat(findReply.getIsAuthor()).isFalse();
        assertThat(findReply.getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(findReply.getComment().getContent()).isEqualTo(savedComment.getContent());
    }

    @Test
    public void 대댓글_삭제_성공() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
        Comment comment = commentRepository.save(Comment.createComment(user, post, "comment1"));

        CreateCommentReplyRequest request = new CreateCommentReplyRequest("대댓글1");
        CommentReplyResponseDto reply = commentReplyService.save(user, comment.getId(), request);

        //when
        commentReplyService.delete(user, reply.getId());

        //then
        assertThrows(RuntimeException.class, () -> commentReplyService.findById(null, reply.getId()));
    }

    @Test
    public void 대댓글_삭제_실패_권한없음() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        User user2 = userRepository.save(new User("kim2", "1234", "nick2", "desc1"));
        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
        Comment comment = commentRepository.save(Comment.createComment(user, post, "comment1"));

        CreateCommentReplyRequest request = new CreateCommentReplyRequest("대댓글1");
        CommentReplyResponseDto reply = commentReplyService.save(user, comment.getId(), request);

        //when + then
        assertThrows(RuntimeException.class, () -> commentReplyService.delete(null, reply.getId()));
        assertThrows(RuntimeException.class, () -> commentReplyService.delete(user2, reply.getId()));
    }

    @Test
    public void 대댓글_수정_성공() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
        Comment comment = commentRepository.save(Comment.createComment(user, post, "comment1"));

        CreateCommentReplyRequest request = new CreateCommentReplyRequest("대댓글1");
        CommentReplyResponseDto response = commentReplyService.save(user, comment.getId(), request);

        //when
        commentReplyService.update(user, response.getId(), new UpdateCommentReplyRequest("변경된대댓글"));
        CommentReplyResponseDto findReply = commentReplyService.findById(user, response.getId());

        //then
        assertThat(findReply.getContent()).isEqualTo("변경된대댓글");
    }

    @Test
    public void 대댓글_수정_실패_권한없음() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        User user2 = userRepository.save(new User("kim2", "1234", "nick2", "desc1"));
        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
        Comment comment = commentRepository.save(Comment.createComment(user, post, "comment1"));

        CreateCommentReplyRequest request = new CreateCommentReplyRequest("대댓글1");
        CommentReplyResponseDto response = commentReplyService.save(user, comment.getId(), request);

        //when + then
        assertThrows(RuntimeException.class, () -> commentReplyService.update(null, response.getId(), new UpdateCommentReplyRequest("변경된대댓글")));
        assertThrows(RuntimeException.class, () -> commentReplyService.update(user2, response.getId(), new UpdateCommentReplyRequest("변경된대댓글")));
    }
}