package com.gloddy.server.user.infra.repository.impl;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Phone;
import com.gloddy.server.user.domain.vo.kind.Status;
import com.gloddy.server.user.domain.dto.PraiseResponse;
import com.gloddy.server.user.domain.dto.QPraiseResponse_GetPraiseForUser;
import com.gloddy.server.user.infra.repository.custom.UserJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.gloddy.server.user.domain.QUser.*;
import static com.gloddy.server.user.domain.QPraise.*;
import static com.gloddy.server.user.domain.QReliability.*;

@Repository
@RequiredArgsConstructor
public class UserJpaRepositoryImpl implements UserJpaRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public Optional<User> findByPhone(Phone phone) {
        return Optional.ofNullable(query.selectFrom(user)
                .where(eqPhone(phone))
                .fetchOne());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(query.selectFrom(user)
                .where(eqEmail(email))
                .fetchOne());
    }

    @Override
    public Optional<User> findByIdFetch(Long id) {
        return Optional.ofNullable(query.selectFrom(user)
                .where(eqId(id))
                .join(user.praise, praise).fetchJoin()
                .join(user.reliability, reliability).fetchJoin()
                .fetchOne());
    }

    @Override
    public PraiseResponse.GetPraiseForUser findPraiseByUserId(Long userId) {
        return query.select(new QPraiseResponse_GetPraiseForUser(
                        user.praise.totalCalmCount,
                        user.praise.totalKindCount,
                        user.praise.totalActiveCount,
                        user.praise.totalHumorCount,
                        user.praise.totalAbsenceCount))
                .from(user)
                .where(eqId(userId))
                .join(user.praise, praise)
                .fetchOne();
    }

    private BooleanExpression eqPhone(Phone phone) {
        return user.phone.eq(phone);
    }

    private BooleanExpression eqEmail(String email) {
        return user.school.email.eq(email);
    }

    private BooleanExpression eqId(Long id) {
        return user.id.eq(id);
    }
}
