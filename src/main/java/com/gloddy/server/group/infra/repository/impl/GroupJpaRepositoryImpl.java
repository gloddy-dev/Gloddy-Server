package com.gloddy.server.group.infra.repository.impl;

import com.gloddy.server.auth.domain.QUser;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.QGroup;
import com.gloddy.server.group.infra.repository.custom.GroupJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gloddy.server.auth.domain.QUser.*;
import static com.gloddy.server.group.domain.QGroup.*;

@Repository
@RequiredArgsConstructor
public class GroupJpaRepositoryImpl implements GroupJpaRepositoryCustom {

    private final JPAQueryFactory query;


    @Override
    public List<Group> findAllByCaptainId(Long captainId) {
        return query.selectFrom(group)
                .join(group.captain, user).fetchJoin()
                .where(captainIdEq(captainId))
                .fetch();

    }

    private BooleanExpression captainIdEq(Long captainId) {
        return group.captain.id.eq(captainId);
    }
}
