package walkbook.repository.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walkbook.auth.JwtUtils;
import walkbook.domain.User;
import walkbook.dto.request.user.CreateUserRequest;
import walkbook.dto.request.user.UpdateUserRequest;
import walkbook.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디의 회원이 존재하지 않습니다. id = " + id));
    }

    public Long join(CreateUserRequest request) {
        User user = request.toUserEntity();
        validateUsername(user.getUsername());
        validateNickname(user.getNickname());
        userRepository.save(user);
        return user.getId();
    }

    public void login(String username, String password, HttpServletResponse response) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("해당 아이디의 회원이 존재하지 않습니다."));
        if (password.equals(user.getPassword())) {
            String jwt = jwtUtils.createJwt(username);
            response.setHeader("Authorization", jwt);
        } else {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }
    }

    public void remove(User authUser, Long id) {
        authCheck(authUser, id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디의 회원이 존재하지 않습니다. id = " + id));
        userRepository.delete(user);
    }

    public User update(User authUser, Long id, UpdateUserRequest updateContents) {
        authCheck(authUser, id);
        validateNickname(updateContents.getNickname(), id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디의 회원이 존재하지 않습니다. id = " + id));
        user.setNickname(updateContents.getNickname());
        user.setDescription(updateContents.getDescription());
        return user;
    }

    private void authCheck(User authUser, Long id) {
        if (authUser == null || !id.equals(authUser.getId())) {
            throw new RuntimeException("권한이 없습니다.");
        }
    }

    private void validateUsername(String username) {
        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent())
            throw new RuntimeException("아이디가 중복됩니다.");
    }

    private void validateNickname(String nickname) {
        Optional<User> findUser = userRepository.findByNickname(nickname);
        if (findUser.isPresent()) {
            throw new RuntimeException("닉네임이 중복됩니다.");
        }
    }

    private void validateNickname(String nickname, Long id) {
        Optional<User> findUser = userRepository.findByNickname(nickname);
        if (findUser.isPresent() && !findUser.get().getId().equals(id)) {
            throw new RuntimeException("닉네임이 중복됩니다.");
        }
    }


}
