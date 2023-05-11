package com.gloddy.server.group.infra.repository.impl;

import com.gloddy.server.group.infra.repository.custom.GroupJpaRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupJpaRepositoryImpl implements GroupJpaRepositoryCustom {

    private final JPAQueryFactory query;
}
