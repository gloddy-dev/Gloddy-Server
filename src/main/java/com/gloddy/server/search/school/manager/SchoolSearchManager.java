package com.gloddy.server.search.school.manager;

import com.gloddy.server.search.school.api.dto.SchoolSearchResponse;

public interface SchoolSearchManager {
    SchoolSearchResponse.All searchByNameKeyword(String keyword);
}
