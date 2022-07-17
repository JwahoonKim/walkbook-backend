package walkbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import walkbook.domain.User;
import walkbook.dto.request.user.CreateUserRequest;
import walkbook.dto.request.user.UpdateUserRequest;
import walkbook.dto.response.user.UserResponseDto;
import walkbook.repository.UserRepository;

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
        UserResponseDto savedUser = userService.join(request);
        User findUser = userRepository.findById(savedUser.getId())
                .orElse(new User());

        //then
        assertThat(findUser.getId()).isEqualTo(savedUser.getId());
        assertThat(findUser.getUsername()).isEqualTo("user1");
        assertThat(findUser.getPassword()).isEqualTo(request.getPassword());
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
        UserResponseDto savedUser = userService.join(request);

        User authUser = new User();
        authUser.setId(savedUser.getId());

        //when
        userService.remove(authUser, savedUser.getId());

        //then
        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.findById(authUser, savedUser.getId()));
        assertThat(e.getMessage()).isEqualTo("해당 아이디의 회원이 존재하지 않습니다. id = " + savedUser.getId());
    }

    @Test
    public void 회원삭제_실패_권한없음() {
        //given
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("user1");
        request.setPassword("1234");
        request.setNickname("jahni");
        request.setDescription("안녕하세요");
        UserResponseDto user = userService.join(request);

        User authUser = new User();
        authUser.setId(user.getId() + 2L);

        //expected
        assertThrows(RuntimeException.class, () -> userService.remove(authUser, user.getId()));
    }

    @Test
    public void 회원삭제_실패_없는회원() {
        User authUser = new User();
        authUser.setId(1L);
        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.findById(authUser,1L));
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
        UserResponseDto updatedUser = userService.join(request);

        User authUser = new User();
        authUser.setId(updatedUser.getId());
        UpdateUserRequest update = new UpdateUserRequest("변경된nick", "변경된desc");

        //when
        UserResponseDto user = userService.update(authUser, updatedUser.getId(), update);

        //then
        assertThat(user.getNickname()).isEqualTo("변경된nick");
        assertThat(user.getDescription()).isEqualTo("변경된desc");
    }

    @Test
    public void 회원수정_실패_권한없음() {
        //given
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("user1");
        request.setPassword("1234");
        request.setNickname("jahni");
        request.setDescription("안녕하세요");
        UserResponseDto updatedUser = userService.join(request);

        User authUser = new User();
        authUser.setId(updatedUser.getId() + 2L);
        UpdateUserRequest update = new UpdateUserRequest("변경된nick", "변경된desc");

        //expected
        assertThrows(RuntimeException.class, () -> userService.update(authUser, updatedUser.getId(), update));
    }

    @Test
    public void 회원수정_실패_닉네임중복() {
        //given
        CreateUserRequest request1 = new CreateUserRequest();
        request1.setUsername("user1");
        request1.setPassword("1234");
        request1.setNickname("jahni");
        request1.setDescription("안녕하세요");
        UserResponseDto user1 = userService.join(request1);

        User authUser = new User();
        authUser.setId(user1.getId());

        CreateUserRequest request2 = new CreateUserRequest();
        request2.setUsername("user2");
        request2.setPassword("1234");
        request2.setNickname("jahni2");
        request2.setDescription("안녕하세요");
        UserResponseDto user2 = userService.join(request2);

        UpdateUserRequest update = new UpdateUserRequest("jahni", "변경된desc");

        //when
        UserResponseDto user = userService.update(authUser, user1.getId(), update);

        //then
        assertThrows(RuntimeException.class, () -> userService.update(new User(), user2.getId(), update));
    }
}