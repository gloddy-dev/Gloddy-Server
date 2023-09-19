package com.gloddy.server.group.infra.repository.impl;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.infra.repository.custom.GroupJpaRepositoryCustom;
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

    @Override
    public Page<Group> findAllByStartDateTimeAfterOrderByCreatedAtDesc(LocalDateTime time, Pageable pageable) {
        List<Group> groups = query.selectFrom(group)
                .where(startDateTimeAfter(time))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(group.createdAt.desc())
                .fetch();

        Long total = query.select(group.count())
                .from(group)
                .where(startDateTimeAfter(time))
                .fetchOne();

        return new PageImpl<>(groups, pageable, total);
    }

    private BooleanExpression captainIdEq(Long captainId) {
        return group.captain.id.eq(captainId);
    }

    private BooleanExpression startDateTimeAfter(LocalDateTime time) {
        return group.dateTime.startDateTime.after(time);
    }
}
