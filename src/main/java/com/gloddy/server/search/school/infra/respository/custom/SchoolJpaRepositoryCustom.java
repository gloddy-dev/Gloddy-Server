package com.gloddy.server.search.school.infra.respository.custom;

import com.gloddy.server.search.school.api.dto.SchoolSearchResponse;

import java.util.List;

public interface SchoolJpaRepositoryCustom {
    List<SchoolSearchResponse.One> findByNameKeywordContain(String keyword);
}
