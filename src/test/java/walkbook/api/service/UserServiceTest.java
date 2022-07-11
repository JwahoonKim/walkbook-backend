package walkbook.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import walkbook.api.domain.model.User;
import walkbook.api.dto.request.user.CreateUserRequest;
import walkbook.api.dto.request.user.UpdateUserRequest;
import walkbook.api.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 회원가입_성공() {
        //given
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("user1");
        request.setPassword("1234");
        request.setNickname("jahni");
        request.setDescription("안녕하세요");

        //when
        Long savedId = userService.join(request);
        User findUser = userRepository.findById(savedId)
                .orElse(new User());

        //then
        assertThat(findUser.getId()).isEqualTo(savedId);
        assertThat(findUser.getUsername()).isEqualTo("user1");
        assertThat(findUser.getPassword()).isEqualTo("1234");
        assertThat(findUser.getNickname()).isEqualTo("jahni");
        assertThat(findUser.getDescription()).isEqualTo("안녕하세요");
    }

    @Test
    public void 회원가입_실패_아이디_닉네임_중복() {
        //given
        CreateUserRequest request1 = new CreateUserRequest();
        request1.setUsername("user1");
        request1.setPassword("1234");
        request1.setNickname("jahni");
        request1.setDescription("안녕하세요");

        CreateUserRequest request2 = new CreateUserRequest();
        request2.setUsername("user1");
        request2.setPassword("1234");
        request2.setNickname("jahni2");
        request2.setDescription("안녕하세요");

        CreateUserRequest request3 = new CreateUserRequest();
        request3.setUsername("user2");
        request3.setPassword("1234");
        request3.setNickname("jahni");
        request3.setDescription("안녕하세요");

        userService.join(request1);

        // expected
        RuntimeException e1 = assertThrows(RuntimeException.class, () -> userService.join(request2));
        assertThat(e1.getMessage()).isEqualTo("아이디가 중복됩니다.");

        RuntimeException e2 = assertThrows(RuntimeException.class, () -> userService.join(request3));
        assertThat(e2.getMessage()).isEqualTo("닉네임이 중복됩니다.");
    }

    @Test
    public void 회원삭제_성공() {
        //given
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("user1");
        request.setPassword("1234");
        request.setNickname("jahni");
        request.setDescription("안녕하세요");
        Long savedId = userService.join(request);

        //when
        userService.remove(savedId);

        //then
        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.findById(savedId));
        assertThat(e.getMessage()).isEqualTo("해당 아이디의 회원이 존재하지 않습니다. id = " + savedId);

    }

    @Test
    public void 회원삭제_실패_없는회원() {
        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.findById(1L));
        assertThat(e.getMessage()).isEqualTo("해당 아이디의 회원이 존재하지 않습니다. id = " + 1);
    }

    @Test
    public void 회원수정_성공() {
        //given
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("user1");
        request.setPassword("1234");
        request.setNickname("jahni");
        request.setDescription("안녕하세요");
        Long savedId = userService.join(request);

        UpdateUserRequest update = new UpdateUserRequest("변경된nick", "변경된desc");

        //when
        User user = userService.update(savedId, update);

        //then
        assertThat(user.getNickname()).isEqualTo("변경된nick");
        assertThat(user.getDescription()).isEqualTo("변경된desc");
    }

    @Test
    public void 회원수정_실패() {
        //given
        CreateUserRequest request1 = new CreateUserRequest();
        request1.setUsername("user1");
        request1.setPassword("1234");
        request1.setNickname("jahni");
        request1.setDescription("안녕하세요");
        Long id1 = userService.join(request1);

        CreateUserRequest request2 = new CreateUserRequest();
        request2.setUsername("user2");
        request2.setPassword("1234");
        request2.setNickname("jahni2");
        request2.setDescription("안녕하세요");
        Long id2 = userService.join(request2);

        UpdateUserRequest update = new UpdateUserRequest("jahni", "변경된desc");

        //when
        User user = userService.update(id1, update);

        //then
        assertThrows(RuntimeException.class, () -> userService.update(id2, update));
    }
}