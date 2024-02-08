package com.gloddy.server.outbox.infra.repository.custom.impl;

import static com.gloddy.server.outbox.domain.QGroupEvent.groupEvent;

import com.gloddy.server.outbox.domain.dto.GroupOutboxEventPayload;
import com.gloddy.server.outbox.domain.dto.QGroupOutboxEventPayload;
import com.gloddy.server.outbox.infra.repository.custom.GroupEventJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupEventJpaRepositoryCustomImpl implements GroupEventJpaRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<GroupOutboxEventPayload> findAllByNotPublished() {
        return query.select(new QGroupOutboxEventPayload(groupEvent.id))
                .from(groupEvent)
                .where(isNotPublished())
                .fetch();
    }

    private BooleanExpression isNotPublished() {
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(2);
        return groupEvent.published.eq(false)
                .and(groupEvent.createdAt.loe(localDateTime));
    }
}
