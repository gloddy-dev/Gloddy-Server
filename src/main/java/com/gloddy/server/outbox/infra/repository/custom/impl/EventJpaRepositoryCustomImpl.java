package com.gloddy.server.outbox.infra.repository.custom.impl;

import static com.gloddy.server.outbox.domain.QEvent.event1;

import com.gloddy.server.outbox.domain.dto.OutboxEventPayload;
import com.gloddy.server.outbox.domain.dto.QOutboxEventPayload;
import com.gloddy.server.outbox.infra.repository.custom.EventJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventJpaRepositoryCustomImpl implements EventJpaRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<OutboxEventPayload> findAllByNotPublished() {
        return query.select(new QOutboxEventPayload(event1.id))
                .from(event1)
                .where(isNotPublished())
                .fetch();
    }

    private BooleanExpression isNotPublished() {
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(2);
        return event1.published.eq(false);
//                .and(event1.createdAt.loe(localDateTime));
    }
}
