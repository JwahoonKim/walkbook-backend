package walkbook.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walkbook.api.domain.model.Post;
import walkbook.api.domain.model.User;
import walkbook.api.dto.request.post.CreatePostRequest;
import walkbook.api.dto.request.post.UpdatePostRequest;
import walkbook.api.dto.response.post.PostResponseDto;
import walkbook.api.repository.PostRepository;
import walkbook.api.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
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

    // 연관관계 메소드 추가해줘야할듯?
    public Long delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id의 글이 존재하지 않습니다. id = " + id));
        postRepository.delete(post);
        return id;
    }

}
