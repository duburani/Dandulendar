package damdorani.dandulendar.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import damdorani.dandulendar.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

//import static damdorani.dandulendar.domain.QGroup.group;


import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class GroupRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Group findGroup(int id) {
        return em.find(Group.class, id);
    }
}
