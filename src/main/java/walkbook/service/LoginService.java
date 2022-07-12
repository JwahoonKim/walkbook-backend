package walkbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walkbook.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final UserRepository userRepository;

//    public User login(User user) {
//        return userRepository
//                .findByUsernameAndPassword(user.getUsername(), user.getPassword())
//                .orElseThrow(() -> new RuntimeException("아이디 혹은 비밀번호를 확인해주세요."));
//    }

}
