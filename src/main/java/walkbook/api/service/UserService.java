package walkbook.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walkbook.api.domain.model.User;
import walkbook.api.repository.UserRepository;
import walkbook.api.dto.request.user.UpdateUserRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public Long join(User user) {
        return userRepository.save(user);
    }

    public void remove(Long id) {
        userRepository.remove(id);
    }

    public User update(Long id, UpdateUserRequest updateContents) {
        userRepository.update(id, updateContents);
        return findUser(id);
    }

    public User findUser(Long id) {
        return userRepository.findById(id);
    }
}
