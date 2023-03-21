package com.gloddy.server.group.repository.impl;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.repository.custom.UserGroupJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.gloddy.server.group.entity.QGroup.group;
import static com.gloddy.server.group.entity.QUserGroup.userGroup;

@Repository
@RequiredArgsConstructor
public class UserGroupJpaRepositoryImpl implements UserGroupJpaRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Group> findExpectedGroupsByUser(User user) {
        return query.select(group)
            .from(userGroup)
            .join(userGroup.group, group)
            .where(userEq(user), startTimeAfter(LocalDateTime.now()))
            .orderBy(group.id.desc())
            .fetch();
    }

    private BooleanExpression userEq(User user) {
        return userGroup.user.eq(user);
    }

    private BooleanExpression startTimeAfter(LocalDateTime limit) {
        return userGroup.group.startTime.after(limit);
    }
}
