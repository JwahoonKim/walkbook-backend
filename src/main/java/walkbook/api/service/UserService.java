package walkbook.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walkbook.api.domain.model.User;
import walkbook.api.dto.request.user.CreateUserRequest;
import walkbook.api.dto.request.user.UpdateUserRequest;
import walkbook.api.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

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

    public void remove(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디의 회원이 존재하지 않습니다. id = " + id));
        userRepository.delete(user);
    }

    public User update(Long id, UpdateUserRequest updateContents) {
        validateNickname(updateContents.getNickname(), id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디의 회원이 존재하지 않습니다. id = " + id));
        user.setNickname(updateContents.getNickname());
        user.setDescription(updateContents.getDescription());
        return user;
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
