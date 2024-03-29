package com.gloddy.server.apply.infra.repository.impl;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.QApply;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.infra.repository.custom.ApplyJpaRepositoryCustom;
import com.gloddy.server.user.domain.QReliability;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.gloddy.server.apply.domain.QApply.*;
import static com.gloddy.server.user.domain.QUser.*;
import static com.gloddy.server.group.domain.QGroup.*;
import static com.gloddy.server.user.domain.QReliability.*;

@Repository
@RequiredArgsConstructor
public class ApplyJpaRepositoryImpl implements ApplyJpaRepositoryCustom {
    private final JPAQueryFactory query;


    @Override
    public Optional<Apply> findByIdFetchGroupAndCaptain(Long id) {
        Apply apply = query.selectFrom(QApply.apply)
                .join(QApply.apply.group, group).fetchJoin()
                .join(group.captain, user).fetchJoin()
                .where(idEq(id))
                .fetchOne();

        return Optional.ofNullable(apply);
    }

    @Override
    public List<Apply> findAllByGroupIdAndStatus(Long groupId, Status status) {
        return query.selectFrom(apply)
                .join(apply.user, user).fetchJoin()
                .join(user.reliability, reliability).fetchJoin()
                .where(groupIdEq(groupId), statusEq(status))
                .orderBy(apply.createdAt.asc())
                .fetch();
    }

    @Override
    public List<Apply> findAllByUserIdAndStatus(Long userId, Status status) {
        return query.selectFrom(apply)
                .join(apply.user, user).fetchJoin()
                .join(apply.group, group).fetchJoin()
                .where(userIdEq(userId), statusEq(status))
                .fetch();
    }

    @Override
    public Optional<Apply> findByIdFetchUserAndGroup(Long id) {
        Apply apply = query.selectFrom(QApply.apply)
                .join(QApply.apply.group, group).fetchJoin()
                .join(QApply.apply.user, user).fetchJoin()
                .where(idEq(id))
                .fetchOne();

        return Optional.ofNullable(apply);
    }

    private BooleanExpression idEq(Long id) {
        return apply.id.eq(id);
    }

    private BooleanExpression groupIdEq(Long groupId) {
        return apply.group.id.eq(groupId);
    }

    private BooleanExpression statusEq(Status status) {
        return apply.status.eq(status);
    }

    private BooleanExpression userIdEq(Long userId) {
        return apply.user.id.eq(userId);
    }
}
