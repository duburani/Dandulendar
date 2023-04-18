package damdorani.dandulendar.repository;

import damdorani.dandulendar.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;
    //private final EntityManager em;

    public void saveUser(User user) {
        em.persist(user);
    }


}
