package com.gloddy.server.apply.api;

import com.gloddy.server.apply.dto.ApplyRequest;
import com.gloddy.server.apply.dto.ApplyResponse;
import com.gloddy.server.apply.service.ApplyService;
import com.gloddy.server.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ApplyApi {

    private final ApplyService applyService;

    @PostMapping("/groups/{groupId}/apply")
    public ResponseEntity<ApplyResponse.create> create(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long groupId,
            @RequestBody ApplyRequest.create request
    ) {
        ApplyResponse.create response = applyService.createApply(userId, groupId, request);
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/groups/{groupId}/apply")
    public ResponseEntity<Void> delete(
         @AuthenticationPrincipal Long userId,
         @PathVariable Long groupId
    ) {
        applyService.deleteApply(userId, groupId);
        return ApiResponse.noContent();
    }
}
