package walkbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import walkbook.auth.AuthUser;
import walkbook.domain.User;
import walkbook.dto.request.user.CreateUserRequest;
import walkbook.dto.request.user.LoginUserRequest;
import walkbook.dto.request.user.UpdateUserRequest;
import walkbook.dto.response.user.UserDetailResponse;
import walkbook.dto.response.user.UserResponseDto;
import walkbook.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    //회원 등록
    // TODO: 회원의 비밀번호는 암호화해서 저장하기
    @PostMapping("/v1/user/signup")
    public UserResponseDto signup(@Valid @RequestBody CreateUserRequest request) {
        return userService.join(request);
    }

    // 로그인
    @PostMapping("/v1/user/login")
    public String login(@Valid @RequestBody LoginUserRequest request, HttpServletResponse response) {
        userService.login(request.getUsername(), request.getPassword(), response);
        return "ok";
    }

    // 회원 정보 조회
    // TODO: 쓴 글, 좋아하는 글 정보 dto에 담아주기
    @GetMapping("/v1/user/{id}")
    public UserDetailResponse findById(@AuthUser User authUser, @PathVariable Long id) {
        return userService.findById(authUser, id);
    }

    //회원정보 수정
    @PatchMapping("/v1/user/{id}")
    public UserResponseDto update(@AuthUser User authUser, @PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateContents) {
        return userService.update(authUser, id, updateContents);
    }

    //회원 삭제
    @DeleteMapping("/v1/user/{id}")
    public String delete(@AuthUser User authUser, @PathVariable Long id) {
        userService.remove(authUser, id);
        return "삭제되었습니다.";
    }

}
