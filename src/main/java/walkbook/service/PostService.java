package walkbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walkbook.domain.Post;
import walkbook.domain.User;
import walkbook.domain.support.PostLike;
import walkbook.dto.request.post.CreatePostRequest;
import walkbook.dto.request.post.UpdatePostRequest;
import walkbook.dto.response.post.PostResponseDto;
import walkbook.repository.PostLikeRepository;
import walkbook.repository.PostRepository;
import walkbook.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    // 유저 아이디와 일치하는 유저가 없는 경우 체크하자
    public PostResponseDto save(CreatePostRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 id의 회원이 존재하지 않습니다."));
        Post post = request.toEntity(user);
        Post savedPost = postRepository.save(post);
        return PostResponseDto.of(savedPost);
    }

    public PostResponseDto findById(Long id) {
        Post findPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id의 글이 존재하지 않습니다. id = " + id));
        return PostResponseDto.of(findPost);
    }

    public PostResponseDto update(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id의 글이 존재하지 않습니다. id = " + id));
        post.update(request);
        return PostResponseDto.of(post);
    }

    public Long delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id의 글이 존재하지 않습니다. id = " + id));
        postRepository.delete(post);
        return id;
    }

    public void like(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("해당 id의 글이 존재하지 않습니다. id = " + postId));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 id의 회원이 존재하지 않습니다."));

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

}
