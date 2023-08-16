package com.gloddy.server.search.school.manager.impl;

import com.gloddy.server.search.school.infra.respository.SchoolJpaRepository;
import com.gloddy.server.search.school.manager.SchoolSearchManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.gloddy.server.search.school.api.dto.SchoolSearchResponse.*;

@Component
@RequiredArgsConstructor
public class SchoolSearchMysqlJpaManager implements SchoolSearchManager {

    private final SchoolJpaRepository schoolJpaRepository;

    @Override
    public All searchByNameKeyword(String keyword) {
        return new All(schoolJpaRepository.findByNameKeywordContain(keyword));
    }
}
