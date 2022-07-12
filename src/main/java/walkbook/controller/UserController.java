package walkbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import walkbook.domain.User;
import walkbook.dto.request.user.CreateUserRequest;
import walkbook.dto.request.user.UpdateUserRequest;
import walkbook.dto.response.user.CreateUserResponse;
import walkbook.dto.response.user.UpdateUserResponse;
import walkbook.dto.response.user.UserDto;
import walkbook.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    //회원 등록
    @PostMapping("/v1/user")
    public CreateUserResponse signup(@Valid @RequestBody CreateUserRequest request) {
        Long id = userService.join(request);
        return new CreateUserResponse(id, request.getUsername());
    }

    // 회원 정보 조회
    // TODO: 쓴 글, 좋아하는 글 정보 dto에 담아주기
    @GetMapping("/v1/user/{id}")
    public UserDto findById(@PathVariable Long id) {
        User findUser = userService.findById(id);
        return UserDto.of(findUser);
    }

    //회원정보 수정
    @PatchMapping("/v1/user/{id}")
    public UpdateUserResponse update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateContents) {
        User updateUser = userService.update(id, updateContents);
        return UpdateUserResponse.of(updateUser);
    }

    //회원 삭제
    @DeleteMapping("/v1/user/{id}")
    public String delete(@PathVariable Long id) {
        userService.remove(id);
        return "삭제되었습니다.";
    }

}
