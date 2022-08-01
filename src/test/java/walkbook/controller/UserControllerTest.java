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
import walkbook.dto.request.user.CreateUserRequest;
import walkbook.dto.request.user.LoginUserRequest;
import walkbook.dto.request.user.UpdateUserRequest;
import walkbook.dto.response.user.UserResponseDto;
import walkbook.service.UserService;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "walkbook-api.herokuapp.com", uriPort = 443)
@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String URL = "/api/v1/user";
    private UserResponseDto defaultUser;
    private String token;

    @BeforeEach
    public void setUser() {
        CreateUserRequest request = new CreateUserRequest("defaultUsername", "1234", "defaultNick", "defaultDesc");
        defaultUser = userService.join(request);
        HttpServletResponse mockResponse = new MockHttpServletResponse();
        token = userService.login(request.getUsername(), request.getPassword(), mockResponse);
    }

    @Test
    public void 회원가입() throws Exception {
        CreateUserRequest request = new CreateUserRequest("username1", "password1", "nickname1", "desc1");

        mockMvc.perform(post(URL + "/signup")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value(request.getUsername()))
                .andExpect(jsonPath("nickname").value(request.getNickname()))
                .andExpect(jsonPath("description").value(request.getDescription()))
                .andDo(document("회원가입",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                            fieldWithPath("username").description("username1"),
                            fieldWithPath("password").description("password1"),
                            fieldWithPath("nickname").description("password1"),
                            fieldWithPath("description").description("desc1").optional()
                        )));
    }

    @Test
    public void 로그인() throws Exception {
        CreateUserRequest createRequest = new CreateUserRequest("username1", "password1", "nickname1", "desc1");
        userService.join(createRequest);
        LoginUserRequest loginRequest = new LoginUserRequest(createRequest.getUsername(), createRequest.getPassword());

        mockMvc.perform(post(URL + "/login")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"))
                .andExpect(header().exists("Authorization"))
                .andDo(document("로그인",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseHeaders(
                                headerWithName("Authorization").description("jwtToken")
                        ),
                        requestFields(
                                fieldWithPath("username").description("username1"),
                                fieldWithPath("password").description("password1")
                        )));
    }

    @Test
    public void 회원정보_조회() throws Exception {
        mockMvc.perform(get(URL + "/{id}", defaultUser.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value(defaultUser.getUsername()))
                .andExpect(jsonPath("nickname").value(defaultUser.getNickname()))
                .andExpect(jsonPath("description").value(defaultUser.getDescription()))
                .andDo(print())
                .andDo(document("회원정보 조회",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("회원ID")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer {jwtToken}"))
                        ));
    }

    @Test
    public void 회원정보_수정() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest("변경된nickname", "변경된desc");

        mockMvc.perform(patch(URL + "/{id}", defaultUser.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value(request.getNickname()))
                .andExpect(jsonPath("description").value(request.getDescription()))
                .andDo(print())
                .andDo(document("회원정보 수정",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("회원ID")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer {jwtToken}"))
                ));
    }

    @Test
    public void 회원_삭제() throws Exception {
        mockMvc.perform(delete(URL + "/{id}", defaultUser.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("삭제되었습니다."))
                .andDo(document("회원정보 삭제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("회원ID")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer {jwtToken}"))
                ));
    }

}