package com.gloddy.server.user.infra.repository.impl;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Phone;
import com.gloddy.server.auth.domain.vo.kind.Status;
import com.gloddy.server.user.infra.repository.custom.UserJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.gloddy.server.auth.domain.QUser.*;

@Repository
@RequiredArgsConstructor
public class UserJpaRepositoryImpl implements UserJpaRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public Optional<User> findByPhone(Phone phone) {
        return Optional.ofNullable(query.selectFrom(user)
                .where(eqPhone(phone), isActive())
                .fetchOne());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(query.selectFrom(user)
                .where(eqEmail(email), isActive())
                .fetchOne());
    }

    private BooleanExpression eqPhone(Phone phone) {
        return user.phone.eq(phone);
    }

    private BooleanExpression eqEmail(String email) {
        return user.school.email.eq(email);
    }

    private BooleanExpression isActive() {
        return user.status.eq(Status.ACTIVE);
    }
}
