package walkbook.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import walkbook.api.domain.model.Post;
import walkbook.api.dto.request.post.UpdatePostRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    private final PathRepository pathRepository;

    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    public void remove(Long id) {
        Post post = em.find(Post.class, id);
        em.remove(post);
    }

    public void update(Long id, UpdatePostRequest request) {
        Post post = em.find(Post.class, id);
        updatePost(id, request, post);
    }

    private void updatePost(Long id, UpdatePostRequest request, Post post) {
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setStartPlace(request.getStartPlace());
        post.setEndPlace(request.getEndPlace());
        post.setTmi(request.getTmi());
        // path 업데이트를 위해 path 테이블 삭제
        pathRepository.removeByPostId(id);
    }
}