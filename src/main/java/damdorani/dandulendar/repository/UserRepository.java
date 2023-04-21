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

    public Optional<User> findByEmail(String email){
        return em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList()
                .stream().findAny();
    }

    public User saveUser(User user) {
        if(user.getUser_id() == null){
            em.persist(user);
        }else{
            em.merge(user);
        }
        return user;
    }


}
