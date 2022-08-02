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
import walkbook.dto.request.post.CreatePostRequest;
import walkbook.dto.request.post.UpdatePostRequest;
import walkbook.dto.request.user.CreateUserRequest;
import walkbook.dto.response.post.PostDetailResponseDto;
import walkbook.dto.response.user.UserResponseDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "walkbook-api.herokuapp.com", uriPort = 443)
@SpringBootTest
class PostControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String URL = "/api/v1/post";
    private UserResponseDto defaultUser;
    private PostDetailResponseDto defaultPost1;
    private PostDetailResponseDto defaultPost2;
    private String token;

    @BeforeEach
    public void setUp() {
        CreateUserRequest userRequest = new CreateUserRequest("defaultUsername", "1234", "defaultNick", "defaultDesc");
        defaultUser = userService.join(userRequest);
        HttpServletResponse mockResponse = new MockHttpServletResponse();
        token = userService.login(userRequest.getUsername(), userRequest.getPassword(), mockResponse);

        Line line1 = new Line(1.0, 2.0, 3.0, 4.0);
        Line line2 = new Line(5.0, 6.0, 7.0, 8.0);
        CreatePostRequest postRequest1 = new CreatePostRequest("title1", "desc1", "start1", "end1", "tmi1", List.of(line1, line2));
        defaultPost1 = postService.save(new User(defaultUser.getId(), defaultUser.getUsername(), "1234", "defaultNick", "defaultDesc"), postRequest1);

        CreatePostRequest postRequest2 = new CreatePostRequest("title2", "desc2", "start2", "end2", "tmi2", List.of(line1, line2));
        defaultPost2 = postService.save(new User(defaultUser.getId(), defaultUser.getUsername(), "1234", "defaultNick", "defaultDesc"), postRequest2);

    }

    @Test
    public void 글_등록_성공() throws Exception {
        Line line1 = new Line(1.0, 2.0, 3.0, 4.0);
        Line line2 = new Line(5.0, 6.0, 7.0, 8.0);
        CreatePostRequest request = new CreatePostRequest("title1", "desc1", "start1", "end1", "tmi1", List.of(line1, line2));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value(request.getTitle()))
                .andExpect(jsonPath("description").value(request.getDescription()))
                .andExpect(jsonPath("tmi").value(request.getTmi()))
                .andExpect(jsonPath("title").value(request.getTitle()))
                .andExpect(jsonPath("$.paths").isArray())
                .andExpect(jsonPath("$.paths[0].startX").value(line1.getStartX()))
                .andDo(document("글 등록",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void 글_전부_조회_성공() throws Exception {
        mockMvc.perform(get(URL + "/all")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts[0].title").value(defaultPost1.getTitle()))
                .andExpect(jsonPath("$.posts[0].author.username").value(defaultUser.getUsername()))
                .andExpect(jsonPath("$.posts[0].title").value(defaultPost1.getTitle()))
                .andExpect(jsonPath("$.posts[0].isAuthor").value(true))
                .andDo(document("글 전부 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer {jwtToken} [Optional]"))));
    }

    @Test
    public void 글_단건_조회_성공() throws Exception {
        mockMvc.perform(get(URL + "/{id}", defaultPost1.getPostId())
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value(defaultPost1.getTitle()))
                .andExpect(jsonPath("description").value(defaultPost1.getDescription()))
                .andExpect(jsonPath("tmi").value(defaultPost1.getTmi()))
                .andExpect(jsonPath("title").value(defaultPost1.getTitle()))
                .andExpect(jsonPath("$.paths").isArray())
                .andExpect(jsonPath("$.paths[0].startX").value(defaultPost1.getPaths().get(0).getStartX()))
                .andDo(document("글 단건 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                            parameterWithName("id").description("글ID")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer {jwtToken} [Optional]"))));
    }

    @Test
    public void 글_수정_성공() throws Exception {
        Line line1 = new Line(4.0, 5.0, 6.0, 7.0);
        Line line2 = new Line(8.0, 9.0, 10.0, 11.0);
        UpdatePostRequest request = new UpdatePostRequest("변경된title", "변경된desc1", "변경된start", "변경된end", "변경된tmi", List.of(line1, line2));

        mockMvc.perform(patch(URL + "/{id}", defaultPost1.getPostId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value(request.getTitle()))
                .andExpect(jsonPath("description").value(request.getDescription()))
                .andExpect(jsonPath("tmi").value(request.getTmi()))
                .andExpect(jsonPath("title").value(request.getTitle()))
                .andExpect(jsonPath("$.paths").isArray())
                .andExpect(jsonPath("$.paths[0].startX").value(line1.getStartX()))
                .andDo(document("글 수정",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("글ID")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer {jwtToken}"))));
    }

    @Test
    public void 글_삭제_성공() throws Exception {
        mockMvc.perform(delete(URL + "/{id}", defaultPost1.getPostId())
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").value(defaultPost1.getPostId()))
                    .andDo(document("글 삭제",
                            preprocessResponse(prettyPrint()),
                            pathParameters(
                                parameterWithName("id").description("글ID")
                            ),
                            requestHeaders(
                                headerWithName("Authorization").description("Bearer {jwtToken}"))));
    }

    @Test
    public void 글_좋아요_성공() throws Exception {
        mockMvc.perform(get(URL + "/{id}/like", defaultPost1.getPostId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"))
                .andDo(document("글 좋아요",
                        pathParameters(
                                parameterWithName("id").description("글ID")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer {jwtToken}"))));
    }
}