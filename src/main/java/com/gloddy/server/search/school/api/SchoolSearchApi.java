package com.gloddy.server.search.school.api;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.search.school.api.dto.SchoolSearchResponse;
import com.gloddy.server.search.school.manager.SchoolSearchManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.gloddy.server.search.school.api.dto.SchoolSearchResponse.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SchoolSearchApi {

    private final SchoolSearchManager schoolSearchManager;

    @GetMapping("/schools")
    public ResponseEntity<All> searchSchool(
            @RequestParam(value = "keyword", required = false, defaultValue = "empty") String keyword
    ) {
        return ApiResponse.ok(schoolSearchManager.searchByNameKeyword(keyword));
    }
}
