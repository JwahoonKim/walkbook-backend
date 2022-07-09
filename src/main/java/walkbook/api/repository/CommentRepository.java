package walkbook.api.repository;

import org.springframework.stereotype.Repository;
import walkbook.api.domain.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentRepository {

    @PersistenceContext
    EntityManager em;

    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    public Long update(Long id, Comment updateComment) {
        Comment comment = em.find(Comment.class, id);
        comment.setContent(updateComment.getContent());
        return comment.getId();
    }

    public void remove(Long id) {
        Comment comment = em.find(Comment.class, id);
        em.remove(comment);
    }
}