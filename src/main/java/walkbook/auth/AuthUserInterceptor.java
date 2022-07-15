package walkbook.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import walkbook.domain.User;
import walkbook.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUserInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User authUser = null;
        String authToken = request.getHeader("Authorization");

        if (jwtUtils.isInvalid(authToken)) {
            request.setAttribute("authUser", null);
            return true;
        }

        String jwt = authToken.replace("Bearer ", "");
        String username = jwtUtils.getUsernameFromJwt(jwt);

        authUser = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("해당 아이디의 유저가 없습니다. 토큰 에러!"));
        request.setAttribute("authUser", authUser);
        return true;
    }

}
