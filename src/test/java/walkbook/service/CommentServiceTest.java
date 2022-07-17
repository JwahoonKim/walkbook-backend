//package walkbook.service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import walkbook.domain.Comment;
//import walkbook.domain.Post;
//import walkbook.domain.User;
//import walkbook.dto.request.comment.CreateCommentRequest;
//import walkbook.dto.request.comment.UpdateCommentRequest;
//import walkbook.dto.response.comment.CommentResponseDto;
//import walkbook.repository.CommentRepository;
//import walkbook.repository.PostRepository;
//import walkbook.repository.UserRepository;
//
//import javax.transaction.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//@Transactional
//class CommentServiceTest {
//
//    @Autowired
//    PostService postService;
//    @Autowired PostRepository postRepository;
//    @Autowired
//    UserService userService;
//    @Autowired UserRepository userRepository;
//    @Autowired CommentRepository commentRepository;
//    @Autowired
//    CommentService commentService;
//
//    @Test
//    public void 댓글_달기_성공() {
//        //given
//        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
//        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
//        CreateCommentRequest request = new CreateCommentRequest(post.getId(), "comment1");
//
//        //when
//        CommentResponseDto comment = commentService.save(post.getId(), request);
//
//        //then
//        assertThat(comment.getContent()).isEqualTo("comment1");
//        assertThat(comment.getUser().getUsername()).isEqualTo(user.getUsername());
//        assertThat(comment.getPost().getTitle()).isEqualTo(post.getTitle());
//    }
//
//    @Test
//    public void 댓글_조회_성공() {
//        //given
//        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
//        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
//        CreateCommentRequest request = new CreateCommentRequest(post.getId(), "comment1");
//        CommentResponseDto comment = commentService.save(post.getId(), request);
//
//        //when
//        Comment findComment = commentService.findById(comment.getId());
//
//        //then
//        assertThat(findComment.getContent()).isEqualTo(comment.getContent());
//    }
//
//    @Test
//    public void 댓글_수정_성공() {
//        //given
//        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
//        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
//        CreateCommentRequest request = new CreateCommentRequest(post.getId(), "comment1");
//        CommentResponseDto comment = commentService.save(post.getId(), request);
//
//        //when
//        commentService.update(comment.getId(), new UpdateCommentRequest("변경된comment"));
//        Comment updateComment = commentService.findById(comment.getId());
//
//        //then
//        assertThat(updateComment.getContent()).isEqualTo("변경된comment");
//    }
//
//    @Test
//    public void 댓글_삭제_성공() {
//        //given
//        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
//        Post post = postRepository.save(new Post(user, "title1", "desc1", "start1", "end1", "tmi1"));
//        CreateCommentRequest request = new CreateCommentRequest(post.getId(), "comment1");
//        CommentResponseDto comment = commentService.save(post.getId(), request);
//
//        //when
//        commentService.delete(comment.getId());
//
//        //then
//        assertThrows(RuntimeException.class, () -> commentService.delete(comment.getId()));
//    }
//
//}