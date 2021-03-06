package walkbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import walkbook.domain.Post;
import walkbook.domain.User;
import walkbook.domain.support.Line;
import walkbook.domain.support.PostLike;
import walkbook.dto.request.post.CreatePostRequest;
import walkbook.dto.request.post.UpdatePostRequest;
import walkbook.dto.request.user.CreateUserRequest;
import walkbook.dto.response.post.PostDetailResponseDto;
import walkbook.dto.response.user.UserResponseDto;
import walkbook.repository.PostLikeRepository;
import walkbook.repository.PostRepository;
import walkbook.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostLikeRepository postLikeRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 글_등록_성공() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));

        //when
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(1.0, 2.0, 3.0, 4.0));
        lines.add(new Line(5.0, 6.0, 7.0, 8.0));
        CreatePostRequest request = new CreatePostRequest("title1", "desc1", "start", "end", "tmi1", lines);
        PostDetailResponseDto savedPost = postService.save(user, request);

        //then
        assertThat(savedPost.getAuthor().getUsername()).isEqualTo("kim");
        assertThat(savedPost.getTitle()).isEqualTo("title1");
        assertThat(savedPost.getDescription()).isEqualTo("desc1");
        assertThat(savedPost.getStartPlace()).isEqualTo("start");
        assertThat(savedPost.getEndPlace()).isEqualTo("end");
        assertThat(savedPost.getTmi()).isEqualTo("tmi1");
        assertThat(savedPost.getPaths().size()).isEqualTo(2);
        assertThat(savedPost.getPaths().get(0).getStartX()).isEqualTo(1.0);
        assertThat(savedPost.getPaths().get(0).getStartY()).isEqualTo(2.0);
        assertThat(savedPost.getPaths().get(0).getEndX()).isEqualTo(3.0);
        assertThat(savedPost.getPaths().get(0).getEndY()).isEqualTo(4.0);
        assertThat(savedPost.getPaths().get(1).getStartX()).isEqualTo(5.0);
        assertThat(savedPost.getPaths().get(1).getEndX()).isEqualTo(7.0);
    }

    @Test
    public void 글_등록_실패_해당_회원없음() {
        //given
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(1.0, 2.0, 3.0, 4.0));
        lines.add(new Line(5.0, 6.0, 7.0, 8.0));
        CreatePostRequest request = new CreatePostRequest("title1", "desc1", "start", "end", "tmi1", lines);

        User authUser = new User();
        authUser.setId(1L);

        //expected
        RuntimeException e = assertThrows(RuntimeException.class, () -> postService.save(authUser, request));
        assertThat(e.getMessage()).isEqualTo("해당 id의 회원이 존재하지 않습니다.");
    }

    @Test
    public void 글_등록_실패_권한없음() {
        //given
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(1.0, 2.0, 3.0, 4.0));
        lines.add(new Line(5.0, 6.0, 7.0, 8.0));
        CreatePostRequest request = new CreatePostRequest("title1", "desc1", "start", "end", "tmi1", lines);

        //expected
        RuntimeException e = assertThrows(RuntimeException.class, () -> postService.save(null, request));
        assertThat(e.getMessage()).isEqualTo("권한이 없습니다.");
    }

    @Test
    public void 글_조회_성공() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        User user2 = userRepository.save(new User("kim2", "1234", "nick2", "desc2"));

        List<Line> lines = new ArrayList<>();
        lines.add(new Line(1.0, 2.0, 3.0, 4.0));
        lines.add(new Line(5.0, 6.0, 7.0, 8.0));
        CreatePostRequest request = new CreatePostRequest("title1", "desc1", "start", "end", "tmi1", lines);
        PostDetailResponseDto savedPost = postService.save(user, request);

        //when
        PostDetailResponseDto result = postService.findById(user, savedPost.getPostId());
        PostDetailResponseDto nonAuth1 = postService.findById(null, savedPost.getPostId());
        PostDetailResponseDto nonAuth2 = postService.findById(user2, savedPost.getPostId());

        //then
        assertThat(result.getAuthor().getUsername()).isEqualTo("kim");
        assertThat(result.getTitle()).isEqualTo("title1");
        assertThat(result.getDescription()).isEqualTo("desc1");
        assertThat(result.getStartPlace()).isEqualTo("start");
        assertThat(result.getEndPlace()).isEqualTo("end");
        assertThat(result.getTmi()).isEqualTo("tmi1");
        assertThat(result.getPaths().size()).isEqualTo(2);
        assertThat(result.getIsAuthor()).isTrue();
        assertThat(result.getLikeCount()).isEqualTo(0);
        assertThat(result.getIsLiked()).isFalse();
        assertThat(result.getPaths().get(0).getStartX()).isEqualTo(1.0);
        assertThat(result.getPaths().get(0).getStartY()).isEqualTo(2.0);
        assertThat(result.getPaths().get(0).getEndX()).isEqualTo(3.0);
        assertThat(result.getPaths().get(0).getEndY()).isEqualTo(4.0);
        assertThat(result.getPaths().get(1).getStartX()).isEqualTo(5.0);
        assertThat(result.getPaths().get(1).getEndX()).isEqualTo(7.0);

        assertThat(nonAuth1.getIsAuthor()).isFalse();
        assertThat(nonAuth2.getIsAuthor()).isFalse();
    }

    @Test
    public void 글_조회_실패_없는글() {
        RuntimeException e = assertThrows(RuntimeException.class, () -> postService.findById(null, 1L));
        assertThat(e.getMessage()).isEqualTo("해당 id의 글이 존재하지 않습니다. id = " + 1);
    }


    @Test
    public void 글_수정_성공() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));

        List<Line> lines = new ArrayList<>();
        lines.add(new Line(1.0, 2.0, 3.0, 4.0));
        lines.add(new Line(5.0, 6.0, 7.0, 8.0));
        CreatePostRequest request = new CreatePostRequest("title1", "desc1", "start", "end", "tmi1", lines);
        PostDetailResponseDto savedPost = postService.save(user, request);

        //when
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle("변경된title");
        updatePostRequest.setDescription("변경된desc");
        updatePostRequest.setStartPlace("변경된start");
        updatePostRequest.setEndPlace("변경된end");
        updatePostRequest.setTmi("변경된tmi");

        List<Line> lines2 = new ArrayList<>();
        lines2.add(new Line(10.0, 11.0, 12.0, 13.0));
        lines2.add(new Line(14.0, 15.0, 16.0, 17.0));
        lines2.add(new Line(18.0, 19.0, 20.0, 21.0));
        updatePostRequest.setPaths(lines2);

        PostDetailResponseDto update = postService.update(user, savedPost.getPostId(), updatePostRequest);

        //then
        assertThat(update.getTitle()).isEqualTo("변경된title");
        assertThat(update.getDescription()).isEqualTo("변경된desc");
        assertThat(update.getStartPlace()).isEqualTo("변경된start");
        assertThat(update.getEndPlace()).isEqualTo("변경된end");
        assertThat(update.getTmi()).isEqualTo("변경된tmi");
        assertThat(update.getIsAuthor()).isTrue();
        assertThat(update.getPaths().size()).isEqualTo(3);
        assertThat(update.getPaths().get(0).getStartX()).isEqualTo(10.0);
        assertThat(update.getPaths().get(0).getStartY()).isEqualTo(11.0);
        assertThat(update.getPaths().get(0).getEndX()).isEqualTo(12.0);
        assertThat(update.getPaths().get(0).getEndY()).isEqualTo(13.0);
        assertThat(update.getPaths().get(1).getStartX()).isEqualTo(14.0);
        assertThat(update.getPaths().get(1).getEndX()).isEqualTo(16.0);
        assertThat(update.getPaths().get(2).getEndX()).isEqualTo(20.0);
    }

    @Test
    public void 글_수정_실패_권한없음() {
        //given
        User user = userRepository.save(new User("kim", "1234", "nick1", "desc1"));
        User user2 = userRepository.save(new User("kim2", "1234", "nick2", "desc2"));

        List<Line> lines = new ArrayList<>();
        lines.add(new Line(1.0, 2.0, 3.0, 4.0));
        lines.add(new Line(5.0, 6.0, 7.0, 8.0));
        CreatePostRequest request = new CreatePostRequest("title1", "desc1", "start", "end", "tmi1", lines);
        PostDetailResponseDto savedPost = postService.save(user, request);

        //when
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle("변경된title");
        updatePostRequest.setDescription("변경된desc");
        updatePostRequest.setStartPlace("변경된start");
        updatePostRequest.setEndPlace("변경된end");
        updatePostRequest.setTmi("변경된tmi");

        List<Line> lines2 = new ArrayList<>();
        lines2.add(new Line(10.0, 11.0, 12.0, 13.0));
        lines2.add(new Line(14.0, 15.0, 16.0, 17.0));
        lines2.add(new Line(18.0, 19.0, 20.0, 21.0));
        updatePostRequest.setPaths(lines2);


        RuntimeException e = assertThrows(RuntimeException.class, () -> postService.update(user2, savedPost.getPostId(), updatePostRequest));
        assertThat(e.getMessage()).isEqualTo("권한이 없습니다.");
    }

    @Test
    public void 글_수정_실패_없는글() {
        //given
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle("변경된title");
        updatePostRequest.setDescription("변경된desc");
        updatePostRequest.setStartPlace("변경된start");
        updatePostRequest.setEndPlace("변경된end");
        updatePostRequest.setTmi("변경된tmi");

        List<Line> lines2 = new ArrayList<>();
        lines2.add(new Line(10.0, 11.0, 12.0, 13.0));
        lines2.add(new Line(14.0, 15.0, 16.0, 17.0));
        lines2.add(new Line(18.0, 19.0, 20.0, 21.0));
        updatePostRequest.setPaths(lines2);

        User authUser = new User();
        authUser.setId(1L);

        //then
        RuntimeException e = assertThrows(RuntimeException.class, () -> postService.update(authUser, 1L, updatePostRequest));
        assertThat(e.getMessage()).isEqualTo("해당 id의 글이 존재하지 않습니다. id = 1");
    }

    @Test
    public void 글_삭제_성공() {
        //given
        CreateUserRequest userRequest = new CreateUserRequest("kim", "1234", "jahni", "안녕하세요");
        UserResponseDto user = userService.join(userRequest);

        User authUser = new User();
        authUser.setId(user.getId());

        List<Line> lines = new ArrayList<>();
        lines.add(new Line(1.0, 2.0, 3.0, 4.0));
        lines.add(new Line(5.0, 6.0, 7.0, 8.0));
        CreatePostRequest request = new CreatePostRequest("title1", "desc1", "start", "end", "tmi1", lines);
        PostDetailResponseDto savedPost = postService.save(authUser, request);

        //when
        postService.delete(authUser, savedPost.getPostId());

        //then
        RuntimeException e = assertThrows(RuntimeException.class, () -> postService.findById(authUser, savedPost.getPostId()));
        assertThat(e.getMessage()).isEqualTo("해당 id의 글이 존재하지 않습니다. id = " + savedPost.getPostId());
    }

    @Test
    public void 글_좋아요_성공() {
        //given
        User user = new User("kim", "1234", "nick1", "desc1");
        User savedUser = userRepository.save(user);

        Post post = new Post(user, "title1", "desc1", "start1", "end1", "tmi1");
        Post savedPost = postRepository.save(post);

        // excepted1 - 좋아요를 누르지 않은 상태
        postService.like(savedUser, post.getId());
        PostLike like = postLikeRepository.findPostLikeByUserAndPost(savedUser, savedPost).get();

        assertThat(like.getPost().getTitle()).isEqualTo(post.getTitle());
        assertThat(like.getUser().getUsername()).isEqualTo("kim");
        assertThat(user.getLikePosts().size()).isEqualTo(1);
        assertThat(post.getLike().size()).isEqualTo(1);

        // expected2 - 좋아요를 누른 상태
        postService.like(savedUser, post.getId());
        Optional<PostLike> like2 = postLikeRepository.findPostLikeByUserAndPost(savedUser, savedPost);
        assertThat(like2.isEmpty()).isTrue();
        assertThat(user.getLikePosts().size()).isEqualTo(0);
        assertThat(post.getLike().size()).isEqualTo(0);
    }

    @Test
    public void 글_좋아요_실패_권한없음() {
        //given
        User user = new User("kim", "1234", "nick1", "desc1");
        User savedUser = userRepository.save(user);

        Post post = new Post(user, "title1", "desc1", "start1", "end1", "tmi1");
        Post savedPost = postRepository.save(post);

        // excepted1 - 좋아요를 누르지 않은 상태
        RuntimeException e = assertThrows(RuntimeException.class, () -> postService.like(null, post.getId()));
        assertThat(e.getMessage()).isEqualTo("권한이 없습니다.");
    }

}