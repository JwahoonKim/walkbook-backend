//package walkbook.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.transaction.annotation.Transactional;
//import walkbook.dto.request.user.CreateUserRequest;
//import walkbook.dto.request.user.UpdateUserRequest;
//
//import java.io.UnsupportedEncodingException;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void 회원등록_성공() throws Exception {
//        CreateUserRequest request = new CreateUserRequest("user1", "1234", "nick1", "desc1");
//
//        mvc.perform(post("/api/v1/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value("user1"));
//    }
//
//    @Test
//    public void 회원등록_중복아이디_실패() throws Exception {
//        //given
//        CreateUserRequest request = new CreateUserRequest("user", "1234", "nick", "desc1");
//        CreateUserRequest usernameDupl = new CreateUserRequest("user", "1234", "nick2", "desc1");
//        CreateUserRequest nicknameDupl = new CreateUserRequest("user2", "1234", "nick", "desc1");
//
//        mvc.perform(post("/api/v1/user")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)));
//
//        // 아이디 중복 요청
//        mvc.perform(post("/api/v1/user")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(usernameDupl)))
//                .andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("$.code").value(400))
//                .andExpect(jsonPath("$.errors.message").value("아이디가 중복됩니다."))
//                .andDo(print());
//
//        // 닉네임 중복 요청
//        mvc.perform(post("/api/v1/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(nicknameDupl)))
//                .andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("$.code").value(400))
//                .andExpect(jsonPath("$.errors.message").value("닉네임이 중복됩니다."))
//                .andDo(print());
//    }
//
//    @Test
//    public void 회원등록_필수값_입력_안해서_실패() throws Exception {
//        CreateUserRequest request = new CreateUserRequest("", "1234", "", "desc1");
//
//        mvc.perform(post("/api/v1/user")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
//                .andExpect(jsonPath("$.errors.username").value("아이디를 입력해주세요."))
//                .andExpect(jsonPath("$.errors.nickname").value("닉네임을 입력해주세요."))
//                .andDo(print());
//    }
//
//    @Test
//    public void 회원조회_성공() throws Exception {
//        CreateUserRequest request = new CreateUserRequest("user1", "1234", "nick1", "desc1");
//
//        MvcResult result = mvc.perform(post("/api/v1/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                        .andReturn();
//        Long id = getId(result);
//
//        mvc.perform(get("/api/v1/user/" + id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.username").value("user1"))
//                .andExpect(jsonPath("$.nickname").value("nick1"))
//                .andExpect(jsonPath("$.description").value("desc1"));
//    }
//
//    @Test
//    // TODO: 에러코드 바꾸기 404?
//    public void 회원조회_실패() throws Exception {
//        mvc.perform(get("/api/v1/user/1"))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void 회원수정_성공() throws Exception {
//        CreateUserRequest request = new CreateUserRequest("user1", "1234", "nick1", "desc1");
//
//        MvcResult result = mvc.perform(post("/api/v1/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andReturn();
//
//        Long id = getId(result);
//
//        UpdateUserRequest update = new UpdateUserRequest("변경된 nick", "변경된 desc");
//
//        mvc.perform(patch("/api/v1/user/" + id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(update)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.username").value("user1"))
//                .andExpect(jsonPath("$.nickname").value("변경된 nick"))
//                .andExpect(jsonPath("$.description").value("변경된 desc"))
//                .andDo(print())
//                .andReturn();
//
//    }
//
//    @Test
//    public void 회원수정_실패_중복닉네임() throws Exception {
//        CreateUserRequest request = new CreateUserRequest("user1", "1234", "nick1", "desc1");
//        CreateUserRequest request2 = new CreateUserRequest("user2", "1234", "nick2", "desc1");
//
//        MvcResult result1 = mvc.perform(post("/api/v1/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andReturn();
//
//        Long id1 = getId(result1);
//
//        MvcResult result2 = mvc.perform(post("/api/v1/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andReturn();
//
//        Long id2 = getId(result2);
//
//        UpdateUserRequest update = new UpdateUserRequest("nick1", "변경된 desc");
//        UpdateUserRequest update2 = new UpdateUserRequest("nick1", "변경된 desc2");
//
//        // 같은 유저가 desc만 변경해서 요청하는 경우
//        mvc.perform(patch("/api/v1/user/" + id1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(update)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id1))
//                .andExpect(jsonPath("$.username").value("user1"))
//                .andExpect(jsonPath("$.nickname").value("nick1"))
//                .andExpect(jsonPath("$.description").value("변경된 desc"))
//                .andDo(print());
//
//        // 다른 유저가 같은 닉네임 쓰는 경우
//        mvc.perform(patch("/api/v1/user/" + id2)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(update2)))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }
//
//    @Test
//    public void 회원삭제_성공() throws Exception {
//        CreateUserRequest request = new CreateUserRequest("user1", "1234", "nick1", "desc1");
//        MvcResult result = mvc.perform(post("/api/v1/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andReturn();
//
//        Long id = getId(result);
//
//        mvc.perform(delete("/api/v1/user/" + id));
//
//        mvc.perform(get("/api/v1/user/" + id))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.errors.message").value("해당 아이디의 회원이 존재하지 않습니다. id = " + id))
//                .andDo(print());
//    }
//
//    @Test
//    public void 회원삭제_실패_없는_회원() throws Exception {
//        mvc.perform(delete("/api/v1/user/1"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.errors.message").value("해당 아이디의 회원이 존재하지 않습니다. id = 1"));
//
//    }
//
//
//    private Long getId(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
//        String json = result.getResponse().getContentAsString();
//        IdDto idDto = objectMapper.readValue(json, IdDto.class);
//        return idDto.id;
//    }
//
//    static class IdDto {
//        public Long id;
//    }
//}