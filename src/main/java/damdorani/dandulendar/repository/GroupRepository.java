package damdorani.dandulendar.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

//import static damdorani.dandulendar.domain.QGroup.group;


import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Group findGroup(int id) {
        return em.find(Group.class, id);
    }

    public int saveGroup(Group group) {
        em.persist(group);
        return group.getGroup_id();
    }

/*    public User findCoupleCodeMaster(String coupleCode){
        return em.find(User.class, coupleCode);
    }*/


}
