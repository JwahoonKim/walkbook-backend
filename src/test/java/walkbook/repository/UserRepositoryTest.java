package walkbook.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import walkbook.api.domain.model.User;
import walkbook.api.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Test
    public void 유저_조회_실패() {
        Long invalidId = 1L;
        String err = assertThrows(RuntimeException.class, () -> userRepository.findById(invalidId))
                        .getMessage();
        assertThat(err).isEqualTo("유저 정보를 찾을 수 없습니다. id = " + invalidId);
    }

    @Test
    public void 회원가입_성공() {
        //given
        User user = User.builder()
                .username("user1")
                .password("1234")
                .nickname("jahni")
                .description("안녕하세요.")
                .build();
        //when
        Long saveId = userRepository.save(user);

        //then
        User findUser = em.find(User.class, saveId);
        assertThat(findUser.getId()).isEqualTo(saveId);
        assertThat(findUser.getUsername()).isEqualTo("user1");
        assertThat(findUser.getPassword()).isEqualTo("1234");
        assertThat(findUser.getNickname()).isEqualTo("jahni");
        assertThat(findUser.getDescription()).isEqualTo("안녕하세요.");
    }

    @Test
    public void 회원가입_실패_아이디_혹은_닉네임_중복() {
        //given
        User user1 = User.builder()
                .username("user1")
                .password("1234")
                .nickname("jahni")
                .description("안녕하세요.")
                .build();

        User usernameDuplicatedUser = User.builder()
                .username("user1")
                .password("12345")
                .nickname("kahni")
                .description("안녕하세요.")
                .build();

        User nicknameDuplicatedUser = User.builder()
                .username("user2")
                .password("12345")
                .nickname("jahni")
                .description("안녕하세요.")
                .build();

        // expected
        userRepository.save(user1);
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(usernameDuplicatedUser));
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(nicknameDuplicatedUser));
    }

    @Test
    public void 유저아이디로_조회_성공() {
        //given
        User user1 = User.builder()
                .username("user1")
                .password("1234")
                .nickname("jahni")
                .description("안녕하세요.")
                .build();
        User user2 = User.builder()
                .username("user2")
                .password("1234")
                .nickname("jahni2")
                .description("안녕하세요.")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        //when
        Optional<User> findUser = userRepository.findByUsername("user2");

        //then
        assertThat(findUser.get().getUsername()).isEqualTo("user2");
        assertThat(findUser.get().getNickname()).isEqualTo("jahni2");
    }

    @Test
    public void 닉네임으로_조회_성공() {
        //given
        User user1 = User.builder()
                .username("user1")
                .password("1234")
                .nickname("jahni")
                .description("안녕하세요.")
                .build();
        User user2 = User.builder()
                .username("user2")
                .password("1234")
                .nickname("jahni2")
                .description("안녕하세요.")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        //when
        Optional<User> findUser = userRepository.findByNickname("jahni2");

        //then
        assertThat(findUser.get().getUsername()).isEqualTo("user2");
        assertThat(findUser.get().getNickname()).isEqualTo("jahni2");
    }

    @Test
    public void 아이디와_비밀번호로_조회_성공() {
        //given
        User user1 = User.builder()
                .username("user1")
                .password("1234")
                .nickname("jahni")
                .description("안녕하세요.")
                .build();

        User user2 = User.builder()
                .username("user2")
                .password("1234")
                .nickname("jahni2")
                .description("안녕하세요.")
                .build();

        userRepository.save(user1);

        //when
        Optional<User> user = userRepository.findByUsernameAndPassword("user1", "1234");

        //then
        assertThat(user.get().getUsername()).isEqualTo("user1");
        assertThat(user.get().getPassword()).isEqualTo("1234");
        assertThat(user.get().getNickname()).isEqualTo("jahni");
    }

    public void 아이디와_비밀번호로_조회_실패() {
        //given
        User user1 = User.builder()
                .username("user1")
                .password("1234")
                .nickname("jahni")
                .description("안녕하세요.")
                .build();

        User user2 = User.builder()
                .username("user2")
                .password("1234")
                .nickname("jahni2")
                .description("안녕하세요.")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        //when
        Optional<User> user = userRepository.findByUsernameAndPassword("user1", "123");

        //then
        assertThat(user.isEmpty()).isEqualTo(true);
    }

    public void 유저_삭제_성공() {
        //given
        User user1 = User.builder()
                .username("user1")
                .password("1234")
                .nickname("jahni")
                .description("안녕하세요.")
                .build();

        User user2 = User.builder()
                .username("user2")
                .password("1234")
                .nickname("jahni2")
                .description("안녕하세요.")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        //when
        userRepository.remove(user2);

        //then
        String err = assertThrows(RuntimeException.class, () -> userRepository.findById(2L))
                .getMessage();
        assertThat(err).isEqualTo("유저 정보를 찾을 수 없습니다. id = " + 2L);
    }

}