package walkbook.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import walkbook.api.domain.model.User;
import walkbook.api.dto.request.user.CreateUserRequest;
import walkbook.api.dto.request.user.UpdateUserRequest;
import walkbook.api.dto.response.user.CreateUserResponse;
import walkbook.api.dto.response.user.UpdateUserResponse;
import walkbook.api.dto.response.user.UserDto;
import walkbook.api.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {

    private final UserService userService;

    //회원 등록
    // 중복 체크 해야함
    @PostMapping("/v1/user")
    public CreateUserResponse signup(@Valid CreateUserRequest request) {
        Long id = userService.join(request.toUserEntity());
        return new CreateUserResponse(id, request.getUsername());
    }

    //회원 정보 조회, 인증 필요
    @GetMapping("/v1/user/{id}")
    public UserDto get(@PathVariable Long id) {
        User findUser = userService.findUser(id);
        return UserDto.of(findUser);
    }

    //회원정보 수정, 인증 필요
    @PatchMapping("/v1/user/{id}")
    public UpdateUserResponse update(@PathVariable Long id, @Valid UpdateUserRequest updateContents) {
        User updateUser = userService.update(id, updateContents);
        return UpdateUserResponse.of(updateUser);
    }

    //회원 삭제, 인증 필요
    @DeleteMapping("/v1/user/{id}")
    public String delete(@PathVariable Long id) {
        userService.remove(id);
        return "삭제되었습니다.";
    }

}
