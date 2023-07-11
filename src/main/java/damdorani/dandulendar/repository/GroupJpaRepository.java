package damdorani.dandulendar.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import damdorani.dandulendar.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class GroupJpaRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Group findGroup(Long groupId) {
        return em.find(Group.class, groupId);
    }

    public Long saveGroup(Group group) {
        em.persist(group);
        return group.getGroup_id();
    }
}
