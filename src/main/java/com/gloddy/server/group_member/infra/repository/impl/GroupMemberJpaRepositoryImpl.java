package com.gloddy.server.group_member.infra.repository.impl;

import com.gloddy.server.user.domain.QUser;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.infra.repository.custom.GroupMemberJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.gloddy.server.user.domain.QUser.*;
import static com.gloddy.server.group.domain.QGroup.group;
import static com.gloddy.server.group_member.domain.QGroupMember.*;
import static com.gloddy.server.user.domain.QPraise.praise;
import static com.gloddy.server.user.domain.QReliability.*;

@Repository
@RequiredArgsConstructor
public class GroupMemberJpaRepositoryImpl implements GroupMemberJpaRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Group> findExpectedGroupsByUser(User user) {
        return query.select(group)
                .from(groupMember)
                .join(groupMember.group, group)
                .where(userEq(user), startTimeAfter(LocalDateTime.now()))
                .orderBy(group.dateTime.startDateTime.desc())
                .fetch();
    }

    @Override
    public Page<GroupMember> findParticipatedGroupsByUser(User user, Pageable pageable) {
        List<GroupMember> groups = query.select(groupMember)
                .from(groupMember)
                .join(groupMember.group, group)
                .where(userEq(user), startTimeBefore(LocalDateTime.now()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(group.dateTime.startDateTime.desc())
                .fetch();

        Long total = query.select(group.count())
                .from(groupMember)
                .join(groupMember.group, group)
                .where(userEq(user), startTimeBefore(LocalDateTime.now()))
                .fetchOne();

        return new PageImpl<>(groups, pageable, total);
    }

    @Override
    public List<GroupMember> findUserGroupsToPraiseByUserIdInAndGroupId(List<Long> userIds, Long groupId) {
        return query.selectFrom(groupMember)
                .join(groupMember.user, user).fetchJoin()
                .join(groupMember.group, group).fetchJoin()
                .join(user.praise, praise).fetchJoin()
                .join(user.reliability, reliability).fetchJoin()
                .where(userIdIn(userIds), groupIdEq(groupId))
                .fetch();
    }

    @Override
    public List<GroupMember> findAllByGroupIdFetchUserAndGroup(Long groupId) {
        return query.selectFrom(groupMember)
                .join(groupMember.user, user).fetchJoin()
                .join(groupMember.group, group).fetchJoin()
                .join(user.reliability, reliability).fetchJoin()
                .where(groupIdEq(groupId))
                .fetch();
    }

    @Override
    public List<GroupMember> findByUserIdFetchGroupAndUser(Long userId) {
        return query.select(groupMember)
                .from(groupMember)
                .join(groupMember.group, group).fetchJoin()
                .join(groupMember.user, user).fetchJoin()
                .where(userIdEq(userId))
                .fetch();
    }

    @Override
    public Long countByUserIdAndIsAbsenceAndEndDateTimeBeforeJoinGroup(Long userId, boolean isAbsence, LocalDateTime time) {
        return query.select(groupMember.count())
                .from(groupMember)
                .join(groupMember.group, group)
                .where(userIdEq(userId), isAbsenceEq(isAbsence), endTimeBefore(time))
                .fetchOne();
    }

    @Override
    public boolean existsByUserAndGroupEndTimeBefore(User user) {
        Long fetchFirst = query.select(group.id)
                .from(groupMember)
                .innerJoin(groupMember.group, group)
                .innerJoin(groupMember.user, QUser.user)
                .where(
                        userEq(user),
                        endTimeAfter(LocalDateTime.now())
                ).fetchFirst();

        return fetchFirst != null;
    }

    @Override
    public Optional<GroupMember> findByIdFetchGroupAndUser(Long groupMemberId) {
        return Optional.ofNullable(query.selectFrom(groupMember)
                .innerJoin(groupMember.group, group).fetchJoin()
                .innerJoin(groupMember.user, user).fetchJoin()
                .where(groupMemberIdEq(groupMemberId))
                .fetchOne());
    }

    private BooleanExpression userEq(User user) {
        return groupMember.user.eq(user);
    }

    private BooleanExpression startTimeAfter(LocalDateTime limit) {
        return groupMember.group.dateTime.startDateTime.after(limit);
    }

    private BooleanExpression startTimeBefore(LocalDateTime limit) {
        return groupMember.group.dateTime.startDateTime.before(limit);
    }

    private BooleanExpression userIdIn(List<Long> userIds) {
        return groupMember.user.id.in(userIds);
    }

    private BooleanExpression groupIdEq(Long groupId) {
        return groupMember.group.id.eq(groupId);
    }

    private BooleanExpression userIdEq(Long userId) {
        return groupMember.user.id.eq(userId);
    }

    private BooleanExpression groupMemberIdEq(Long groupMemberId) {
        return groupMember.id.eq(groupMemberId);
    }

    private BooleanExpression endTimeBefore(LocalDateTime time) {
        return groupMember.group.dateTime.endDateTime.before(time);
    }

    private BooleanExpression endTimeAfter(LocalDateTime time) {
        return groupMember.group.dateTime.endDateTime.after(time);
    }

    private BooleanExpression isAbsenceEq(boolean isAbsence) {
        return groupMember.isAbsence.eq(isAbsence);
    }
}
