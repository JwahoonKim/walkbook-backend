package walkbook.api.repository;

import org.springframework.stereotype.Repository;
import walkbook.api.domain.model.User;
import walkbook.api.dto.request.user.UpdateUserRequest;

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

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return em.createQuery("select u from User u where u.username = :username and u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultStream()
                .findFirst();
    }

    public void remove(Long id) {
        User findUser = em.find(User.class, id);
        em.remove(findUser);
    }

    public void update(Long id, UpdateUserRequest updateContents) {
        User findUser = em.find(User.class, id);
        // 중복 체크 + null 체크해야함
        findUser.setNickname(updateContents.getNickname());
        findUser.setDescription(updateContents.getDescription());
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }
}
