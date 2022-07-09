package walkbook.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import walkbook.api.dto.request.post.CreatePostRequest;
import walkbook.api.dto.request.post.UpdatePostRequest;
import walkbook.api.dto.response.post.PostResponseDto;
import walkbook.api.service.PostService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;

    // 글 등록
    // 글 쓸때 유저 아이디를 변경해서 보내면 딴 사람이 쓴게 되는데 어떻게 처리하지?
    @PostMapping("/v1/post")
    public Long create(@Valid @RequestBody CreatePostRequest request) {
        return postService.save(request);
    }

    // 글 조회
    @GetMapping("/v1/post/{id}")
    public PostResponseDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    // 글 수정
    @PatchMapping("/v1/post/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody UpdatePostRequest request) {
        postService.update(id, request);
    }

    // 글 삭제
    @DeleteMapping("/v1/post/{id}")
    public Long delete(@PathVariable Long id) {
        return postService.delete(id);
    }

}