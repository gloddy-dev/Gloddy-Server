package com.gloddy.server.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<T> {
    private long totalCount;
    private int currentCount;
    private int currentPage;
    private int totalPage;
    private List<T> contents;

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getTotalElements(),
                page.getNumberOfElements(),
                page.getNumber(),
                page.getTotalPages(),
                page.getContent()
        );
    }
}
