package walkbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import walkbook.auth.AuthUser;
import walkbook.domain.User;
import walkbook.dto.request.post.CreatePostRequest;
import walkbook.dto.request.post.UpdatePostRequest;
import walkbook.dto.response.post.PostDetailResponseDto;
import walkbook.dto.response.post.PostListResponse;
import walkbook.service.PostService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    // 글 등록
    @PostMapping("/v1/post")
    public PostDetailResponseDto create(@AuthUser User authUser, @Valid @RequestBody CreatePostRequest request) {
        return postService.save(authUser, request);
    }

    // 글 전부 조회
    @GetMapping("/v1/post/all")
    public PostListResponse findAll(@AuthUser User authUser) {
        return postService.findAll(authUser);
    }

    // 글 조회
    @GetMapping("/v1/post/{id}")
    public PostDetailResponseDto findById(@AuthUser User authUser, @PathVariable Long id) {
        return postService.findById(authUser, id);
    }

    // 글 수정
    @PatchMapping("/v1/post/{id}")
    public PostDetailResponseDto update(@AuthUser User authUser, @PathVariable Long id, @Valid @RequestBody UpdatePostRequest request) {
        return postService.update(authUser, id, request);
    }

    // 글 삭제
    @DeleteMapping("/v1/post/{id}")
    public Map<String, Long> delete(@AuthUser User authUser, @PathVariable Long id) {
        return Map.of("id" ,postService.delete(authUser, id));
    }

    // 좋아요 기능
    @GetMapping("/v1/post/{postId}/like")
    public String likePost(@AuthUser User authUser, @PathVariable Long postId) {
        postService.like(authUser, postId);
        return "ok";
    }
}
