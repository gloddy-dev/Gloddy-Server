package com.gloddy.server.group.infra.repository.impl;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.UserGroup;
import com.gloddy.server.group.infra.repository.custom.UserGroupJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.gloddy.server.auth.domain.QUser.*;
import static com.gloddy.server.estimate.domain.QPraise.*;
import static com.gloddy.server.group.domain.QGroup.group;
import static com.gloddy.server.group.domain.QUserGroup.userGroup;
import static com.gloddy.server.reliability.domain.QReliability.*;

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
                .orderBy(group.startTime.desc())
                .fetch();
    }

    @Override
    public Page<UserGroup> findParticipatedGroupsByUser(User user, Pageable pageable) {
        List<UserGroup> groups = query.select(userGroup)
                .from(userGroup)
                .join(userGroup.group, group)
                .where(userEq(user), startTimeBefore(LocalDateTime.now()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(group.startTime.desc())
                .fetch();

        Long total = query.select(group.count())
                .from(userGroup)
                .join(userGroup.group, group)
                .where(userEq(user), startTimeBefore(LocalDateTime.now()))
                .fetchOne();

        return new PageImpl<>(groups, pageable, total);
    }

    @Override
    public List<UserGroup> findUserGroupsToPraiseByUserIdInAndGroupId(List<Long> userIds, Long groupId) {
        return query.selectFrom(userGroup)
                .join(userGroup.user, user).fetchJoin()
                .join(userGroup.group, group).fetchJoin()
                .join(user.praise, praise).fetchJoin()
                .join(user.reliability, reliability).fetchJoin()
                .where(userIdIn(userIds), groupIdEq(groupId))
                .fetch();
    }

    private BooleanExpression userEq(User user) {
        return userGroup.user.eq(user);
    }

    private BooleanExpression startTimeAfter(LocalDateTime limit) {
        return userGroup.group.startTime.after(limit);
    }

    private BooleanExpression startTimeBefore(LocalDateTime limit) {
        return userGroup.group.startTime.before(limit);
    }

    private BooleanExpression userIdIn(List<Long> userIds) {
        return userGroup.user.id.in(userIds);
    }

    private BooleanExpression groupIdEq(Long groupId) {
        return userGroup.group.id.eq(groupId);
    }
}
