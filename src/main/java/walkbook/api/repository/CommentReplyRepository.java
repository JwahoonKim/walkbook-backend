package walkbook.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import walkbook.api.domain.model.CommentReply;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class CommentReplyRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(CommentReply reply) {
        em.persist(reply);
        return reply.getId();
    }


    public CommentReply findById(Long id) {
        return em.find(CommentReply.class, id);
    }

    public void remove(Long id) {
        CommentReply reply = em.find(CommentReply.class, id);
        em.remove(reply);
    }

    public void update(Long id, String content) {
        CommentReply reply = em.find(CommentReply.class, id);
        reply.setContent(content);
    }
}
