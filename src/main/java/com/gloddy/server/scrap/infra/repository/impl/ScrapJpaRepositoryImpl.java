package com.gloddy.server.scrap.infra.repository.impl;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.scrap.infra.repository.custom.ScrapJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.gloddy.server.scrap.domain.QScrap.*;

@Repository
@RequiredArgsConstructor
public class ScrapJpaRepositoryImpl implements ScrapJpaRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public List<Group> findGroupsByUserIdAndStartTimeAfterOrderByGroupCreatedAt(Long userId, LocalDateTime time) {
        return query.select(scrap.group)
                .from(scrap)
                .where(userIdEq(userId), groupStartTimeEnd(time))
                .orderBy(scrap.group.createdAt.desc())
                .fetch();
    }

    private BooleanExpression userIdEq(Long userId) {
        return scrap.user.id.eq(userId);
    }

    private BooleanExpression groupStartTimeEnd(LocalDateTime time) {
        return scrap.group.dateTime.startDateTime.after(time);
    }
}
