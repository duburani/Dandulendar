package damdorani.dandulendar.repository;

import damdorani.dandulendar.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public Optional<User> findByUserId(String userId){
        return em.createQuery("select u from User u where u.user_id = :user_id", User.class)
                .setParameter("user_id", userId)
                .getResultList()
                .stream().findAny();
    }

    public User findUserById(String id){
        return em.find(User.class, id);
    }
    public void saveUser(User user) {
        em.persist(user);
    }
}
