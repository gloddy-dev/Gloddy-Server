package com.gloddy.server.apply.infra.repository.impl;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.QApply;
import com.gloddy.server.apply.infra.repository.custom.ApplyJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.gloddy.server.apply.domain.QApply.*;
import static com.gloddy.server.auth.domain.QUser.*;
import static com.gloddy.server.group.domain.QGroup.*;

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

    private BooleanExpression idEq(Long id) {
        return apply.id.eq(id);
    }
}
