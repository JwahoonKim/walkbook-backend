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
import java.util.List;
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
    public void 회원가입_성공() {
        //given
        User user = User.builder()
                .username("user1")
                .password("1234")
                .nickname("jahni")
                .description("안녕하세요.")
                .build();
        //when
        User savedUser = userRepository.save(user);

        //then
        User findUser = em.find(User.class, savedUser.getId());
        assertThat(findUser.getId()).isEqualTo(savedUser.getId());
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

        //expected
        List<User> all = userRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
        userRepository.delete(user2);
        List<User> all2 = userRepository.findAll();
        assertThat(all2.size()).isEqualTo(1);
    }

}