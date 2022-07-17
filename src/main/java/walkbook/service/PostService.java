package walkbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walkbook.domain.Post;
import walkbook.domain.User;
import walkbook.domain.support.PostLike;
import walkbook.dto.request.post.CreatePostRequest;
import walkbook.dto.request.post.UpdatePostRequest;
import walkbook.dto.response.post.PostDetailResponseDto;
import walkbook.dto.response.post.PostListResponse;
import walkbook.dto.response.post.PostResponseDto;
import walkbook.repository.PostLikeRepository;
import walkbook.repository.PostRepository;
import walkbook.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    public PostDetailResponseDto save(User authUser, CreatePostRequest request) {
        if (authUser == null) {
            throw new RuntimeException("권한이 없습니다.");
        }
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new RuntimeException("해당 id의 회원이 존재하지 않습니다."));
        Post post = request.toEntity(user);
        Post savedPost = postRepository.save(post);
        return PostDetailResponseDto.of(user, savedPost);
    }

    public PostListResponse findAll(User authUser) {
        List<Post> posts = postRepository.findAll();
        PostListResponse response = new PostListResponse();

        posts.stream()
                .map(post -> PostResponseDto.of(authUser, post))
                .forEach(response.getPosts()::add);

        return response;
    }

    public PostDetailResponseDto findById(User authUser, Long id) {
        Post findPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id의 글이 존재하지 않습니다. id = " + id));
        return PostDetailResponseDto.of(authUser, findPost);
    }

    public PostDetailResponseDto update(User authUser, Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 id의 글이 존재하지 않습니다. id = " + id));
        authCheck(authUser, post);
        post.update(request);
        return PostDetailResponseDto.of(authUser, post);
    }

    public Long delete(User authUser, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 id의 글이 존재하지 않습니다. id = " + id));
        authCheck(authUser, post);
        postRepository.delete(post);
        return id;
    }

    public void like(User authUser, Long postId) {
        if (authUser == null) {
            throw new RuntimeException("권한이 없습니다.");
        }

        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("해당 id의 글이 존재하지 않습니다. id = " + postId));
        User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new RuntimeException("해당 id의 회원이 존재하지 않습니다."));

        postLikeRepository.findPostLikeByUserAndPost(user, post)
                .ifPresentOrElse(
                        // 이미 좋아요를 누른 경우
                        like -> deleteLike(post, user, like),
                        // 좋아요를 누르지 않은 경우
                        () -> createLike(post, user)
                );
    }

    private void createLike(Post post, User user) {
        PostLike like = PostLike.createLike(user, post);
        postLikeRepository.save(like);
    }

    private void deleteLike(Post post, User user, PostLike like) {
        post.getLike().removeIf(l -> l.getId().equals(like.getId()));
        user.getLikePosts().removeIf(l -> l.getId().equals(like.getId()));
        postLikeRepository.delete(like);
    }

    private void authCheck(User authUser, Post post) {
        if (authUser == null || !authUser.getId().equals(post.getUser().getId())) {
            throw new RuntimeException("권한이 없습니다.");
        }
    }

}
