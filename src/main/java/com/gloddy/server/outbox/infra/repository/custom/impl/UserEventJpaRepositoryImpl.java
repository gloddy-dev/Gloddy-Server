package com.gloddy.server.outbox.infra.repository.custom.impl;

import com.gloddy.server.outbox.domain.dto.QUserOutboxEventPayload;
import com.gloddy.server.outbox.domain.dto.UserOutboxEventPayload;
import com.gloddy.server.outbox.infra.repository.custom.UserEventJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.gloddy.server.outbox.domain.QUserEvent.userEvent;

@Repository
@RequiredArgsConstructor
public class UserEventJpaRepositoryImpl implements UserEventJpaRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<UserOutboxEventPayload> findAllByNotPublished() {
        return query.select(new QUserOutboxEventPayload(userEvent.id))
                .from(userEvent)
                .where(isNotPublished())
                .fetch();
    }

    private BooleanExpression isNotPublished() {
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(2);
        return userEvent.published.eq(false)
                .and(userEvent.createdAt.loe(localDateTime));
    }
}
