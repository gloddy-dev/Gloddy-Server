package com.gloddy.server.apply.api;

import com.gloddy.server.apply.dto.ApplyRequest;
import com.gloddy.server.apply.dto.ApplyResponse;
import com.gloddy.server.apply.entity.vo.Status;
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

    // 모임 나가기 api
    @DeleteMapping("/groups/{groupId}/apply")
    public ResponseEntity<Void> delete(
         @AuthenticationPrincipal Long userId,
         @PathVariable Long groupId
    ) {
        applyService.deleteApply(userId, groupId);
        return ApiResponse.noContent();
    }

    // 지원서 승인/거절 api
    @PatchMapping("/groups/{groupId}/applys/{applyId}")
    public ResponseEntity<Void> updateStatus(
         @AuthenticationPrincipal Long userId,
         @PathVariable Long groupId,
         @PathVariable Long applyId,
         @RequestParam Status status
    ) {
        applyService.updateStatusApply(userId, groupId, applyId, status);
        return ApiResponse.noContent();
    }
}
