package walkbook.api.repository;

import org.springframework.stereotype.Repository;
import walkbook.api.domain.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    public Optional<User> findByUsername(String username) {
        return em.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    public Optional<User> findByNickname(String nickname) {
        return em.createQuery("select u from User u where u.nickname = :nickname", User.class)
                .setParameter("nickname", nickname)
                .getResultStream()
                .findFirst();
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return em.createQuery("select u from User u where u.username = :username and u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultStream()
                .findFirst();
    }

    public void remove(User user) {
        em.remove(user);
    }

    public User findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id))
                .orElseThrow(() -> new RuntimeException("유저 정보를 찾을 수 없습니다. id = " + id));
    }
}
