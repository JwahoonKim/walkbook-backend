package walkbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import walkbook.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//    @PersistenceContext
//    EntityManager em;
//
//    public Long save(Comment comment) {
//        em.persist(comment);
//        return comment.getId();
//    }
//
//    public Comment findById(Long id) {
//        return em.find(Comment.class, id);
//    }
//
//    public Long update(Long id, Comment updateComment) {
//        Comment comment = em.find(Comment.class, id);
//        comment.setContent(updateComment.getContent());
//        return comment.getId();
//    }
//
//    public void remove(Long id) {
//        Comment comment = em.find(Comment.class, id);
//        em.remove(comment);
//    }
}