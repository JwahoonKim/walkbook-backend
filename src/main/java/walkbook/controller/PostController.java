package walkbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import walkbook.dto.request.post.CreatePostRequest;
import walkbook.dto.request.post.LikePostRequest;
import walkbook.dto.request.post.UpdatePostRequest;
import walkbook.dto.response.post.PostResponseDto;
import walkbook.service.PostService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    // 글 등록
    @PostMapping("/v1/post")
    public PostResponseDto create(@Valid @RequestBody CreatePostRequest request) {
        return postService.save(request);
    }

    // 글 조회
    @GetMapping("/v1/post/{id}")
    public PostResponseDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    // 글 수정
    @PatchMapping("/v1/post/{id}")
    public PostResponseDto update(@PathVariable Long id, @Valid @RequestBody UpdatePostRequest request) {
        return postService.update(id, request);
    }

    // 글 삭제
    @DeleteMapping("/v1/post/{id}")
    public Long delete(@PathVariable Long id) {
        return postService.delete(id);
    }

    // 좋아요 기능
    @GetMapping("/v1/post/{postId}/like")
    public void likePost(@PathVariable Long postId, @RequestBody LikePostRequest request) {
        postService.like(postId, request.getUserId());
    }
}
