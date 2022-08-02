package walkbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import walkbook.domain.User;
import walkbook.domain.support.Line;
import walkbook.dto.request.comment.CreateCommentRequest;
import walkbook.dto.request.comment.UpdateCommentRequest;
import walkbook.dto.request.post.CreatePostRequest;
import walkbook.dto.request.user.CreateUserRequest;
import walkbook.dto.response.comment.CommentResponseDto;
import walkbook.dto.response.post.PostDetailResponseDto;
import walkbook.dto.response.user.UserResponseDto;
import walkbook.service.CommentService;
import walkbook.service.PostService;
import walkbook.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "walkbook-api.herokuapp.com", uriPort = 443)
@SpringBootTest
class CommentControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String URL = "/api/v1";
    private UserResponseDto defaultUser;
    private PostDetailResponseDto defaultPost;
    private CommentResponseDto defaultComment;
    private String token;

    @BeforeEach
    public void setUserAndPost() {
        CreateUserRequest userRequest = new CreateUserRequest("defaultUsername", "1234", "defaultNick", "defaultDesc");
        defaultUser = userService.join(userRequest);
        HttpServletResponse mockResponse = new MockHttpServletResponse();
        token = userService.login(userRequest.getUsername(), userRequest.getPassword(), mockResponse);

        Line line1 = new Line(1.0, 2.0, 3.0, 4.0);
        Line line2 = new Line(5.0, 6.0, 7.0, 8.0);
        CreatePostRequest postRequest1 = new CreatePostRequest("title1", "desc1", "start1", "end1", "tmi1", List.of(line1, line2));
        defaultPost = postService.save(new User(defaultUser.getId(), defaultUser.getUsername(), "1234", "defaultNick", "defaultDesc"), postRequest1);
        User user = new User(defaultUser.getId(), defaultUser.getUsername(), "1234", defaultUser.getNickname(), defaultUser.getDescription());
        defaultComment = commentService.save(user, defaultPost.getPostId(), new CreateCommentRequest("defaultComment"));
    }


    @Test
    public void 댓글_등록_성공() throws Exception {
        CreateCommentRequest request = new CreateCommentRequest("댓글1");

        mockMvc.perform(post(URL + "/post/{postId}/comment", defaultPost.getPostId())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("content").value(request.getContent()))
                .andExpect(jsonPath("isAuthor").value(true))
                .andExpect(jsonPath("user.username").value(defaultUser.getUsername()))
                .andExpect(jsonPath("post.title").value(defaultPost.getTitle()))
                .andDo(document("댓글 등록",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("postId").description("글ID")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer {jwtToken}"))));
    }

    @Test
    public void 댓글_조회_성공() throws Exception {
        mockMvc.perform(get(URL + "/comment/{commentId}", defaultComment.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(jsonPath("content").value(defaultComment.getContent()))
                .andExpect(jsonPath("isAuthor").value(true))
                .andExpect(jsonPath("user.username").value(defaultUser.getUsername()))
                .andExpect(jsonPath("post.title").value(defaultPost.getTitle()))
                .andDo(document("댓글 조회",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("commentId").description("댓글ID")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer {jwtToken} [Optional]"))));
    }

    @Test
    public void 댓글_수정_성공() throws Exception {
        UpdateCommentRequest request = new UpdateCommentRequest("변경된comment");

        mockMvc.perform(patch(URL + "/comment/{commentId}", defaultComment.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("updateContent").value(request.getContent()))
                .andDo(document("댓글 수정",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("commentId").description("댓글ID")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer {jwtToken}"))));
    }

    @Test
    public void 댓글_삭제_성공() throws Exception {
        mockMvc.perform(delete(URL + "/comment/{commentId}", defaultComment.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(content().string("deleted"))
                .andDo(document("댓글 삭제",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("commentId").description("댓글ID")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer {jwtToken}"))));
    }
}