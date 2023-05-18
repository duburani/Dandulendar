package damdorani.dandulendar.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.dto.UserGroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.core.types.Projections.list;
import static damdorani.dandulendar.domain.QGroup.group;
import static damdorani.dandulendar.domain.QUserGroup.userGroup;
import static damdorani.dandulendar.domain.QUser.user;

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

    public List<UserGroupResponse> findGroupByUserId(String userId) {
        List<UserGroupResponse> groupFormList = queryFactory
                .select(Projections.constructor(UserGroupResponse.class, group, user))
                .from(group)
                .leftJoin(group.userGroups, userGroup)
                .leftJoin(userGroup.user, user)
                .where(group.group_id.eq(
                        queryFactory.select(userGroup.group.group_id)
                                .from(userGroup)
                                .where(userGroup.user.user_id.eq(userId))
                ))
                .fetch();
        return groupFormList;
    }
}
