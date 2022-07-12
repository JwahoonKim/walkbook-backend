package walkbook.repository;

import org.springframework.stereotype.Repository;
import walkbook.domain.Post;
import walkbook.domain.support.Path;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PathRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Path path) {
        em.persist(path);
    }

    public void removeByPostId(Long postId) {
        Post post = em.find(Post.class, postId);
        List<Path> paths = em.createQuery("select p from Path p where p.post = :post", Path.class)
                .setParameter("post", post)
                .getResultList();
        paths.forEach(path -> em.remove(path));
    }

}
