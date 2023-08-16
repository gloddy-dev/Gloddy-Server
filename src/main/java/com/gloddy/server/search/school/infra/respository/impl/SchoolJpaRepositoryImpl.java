package com.gloddy.server.search.school.infra.respository.impl;


import com.gloddy.server.search.school.api.dto.QSchoolSearchResponse_One;
import com.gloddy.server.search.school.api.dto.SchoolSearchResponse;
import com.gloddy.server.search.school.infra.respository.custom.SchoolJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gloddy.server.search.school.QSchool.*;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Repository
@RequiredArgsConstructor
public class SchoolJpaRepositoryImpl implements SchoolJpaRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public List<SchoolSearchResponse.One> findByNameKeywordContain(String keyword) {
        return query.select(new QSchoolSearchResponse_One(
                        school.name,
                        school.address))
                .from(school)
                .where(nameContains(keyword))
                .fetch();

    }

    private BooleanExpression nameContains(String keyword) {
        return isNotEmpty(keyword) ? school.name.containsIgnoreCase(keyword) : null;
    }
}
