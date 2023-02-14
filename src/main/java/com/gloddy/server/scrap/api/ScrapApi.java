package com.gloddy.server.scrap.api;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.dto.GroupResponse.GetGroup;
import com.gloddy.server.scrap.dto.ScrapResponse.CreateScrap;
import com.gloddy.server.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScrapApi {
    private final ScrapService scrapService;

    @PostMapping("/groups/{groupId}/scrap")
    public ResponseEntity<CreateScrap> create(
            @PathVariable Long groupId,
            @AuthenticationPrincipal Long userId
    ) {
        CreateScrap response = scrapService.create(groupId, userId);
        return ApiResponse.ok(response);
    }

    @GetMapping("/scraps")
    public ResponseEntity<PageResponse<GetGroup>> findScrapsByUserId(
            @AuthenticationPrincipal Long userId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        PageResponse<GetGroup> response = scrapService.findScrapByUserId(userId, pageRequest);
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/groups/{groupId}/scrap")
    public ResponseEntity<Void> delete(
            @PathVariable Long groupId,
            @AuthenticationPrincipal Long userId
    ) {
        scrapService.delete(groupId, userId);
        return ApiResponse.noContent();
    }
}
