package com.gloddy.server.search.school.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SchoolSearchResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class All {
        private List<One> schools;
    }

    @NoArgsConstructor
    @Getter
    public static class One {
        private String name;
        private String address;

        @QueryProjection
        public One(String name, String address) {
            this.name = name;
            this.address = address;
        }
    }
}
