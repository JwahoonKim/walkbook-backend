package walkbook.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walkbook.api.domain.model.Post;
import walkbook.api.domain.model.User;
import walkbook.api.domain.model.support.Line;
import walkbook.api.domain.model.support.Path;
import walkbook.api.dto.request.post.CreatePostRequest;
import walkbook.api.dto.request.post.UpdatePostRequest;
import walkbook.api.dto.response.post.PostResponseDto;
import walkbook.api.repository.PathRepository;
import walkbook.api.repository.PostRepository;
import walkbook.api.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PathRepository pathRepository;

    // 유저 아이디와 일치하는 유저가 없는 경우 체크하자
    public Long save(CreatePostRequest request) {
        User user = userRepository.findById(request.getUserId());
        Post post = request.toEntity();
        post.setUser(user);
        postRepository.save(post);
        savePath(request.getPaths(), post);
        return post.getId();
    }

    // path 등록
    private void savePath(List<Line> lines, Post post) {
        lines.stream()
                .map(line -> new Path(post, line))
                .forEach(pathRepository::save);
    }

    public PostResponseDto findById(Long id) {
        Post findPost = postRepository.findById(id);
        return PostResponseDto.of(findPost);
    }

    public void update(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id);
        postRepository.update(id, request);
        updatePath(request, post);
    }

    private void updatePath(UpdatePostRequest request, Post post) {
        // 메모리에 있는 기존 path 삭제
        post.getPaths().clear();
        // 디비에 path 갱신
        savePath(request.getPaths(), post);
    }

    // 연관관계 메소드 추가해줘야할듯?
    public Long delete(Long id) {
        postRepository.remove(id);
        return id;
    }

    // data init용 메서드 -> 추후 삭제
    public Long save(Post post) {
        return postRepository.save(post);
    }


}
