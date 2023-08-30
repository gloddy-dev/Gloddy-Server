package com.gloddy.server.apply.api;

import com.gloddy.server.apply.domain.dto.ApplyRequest;
import com.gloddy.server.apply.domain.dto.ApplyResponse;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.application.ApplyService;
import com.gloddy.server.core.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<ApplyResponse.Create> create(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long groupId,
            @RequestBody ApplyRequest.Create request
    ) {
        ApplyResponse.Create response = applyService.createApply(userId, groupId, request);
        return ApiResponse.ok(response);
    }

    @Operation(description = "지원서 승인/거절")
    @PatchMapping("/groups/{groupId}/applies/{applyId}")
    public ResponseEntity<Void> updateStatus(
         @AuthenticationPrincipal Long userId,
         @PathVariable Long groupId,
         @PathVariable Long applyId,
         @RequestParam Status status
    ) {
        applyService.updateStatusApply(userId, groupId, applyId, status);
        return ApiResponse.noContent();
    }

    @Operation(description = "지원서 전체 조회 - 방장 지원서 확인")
    @GetMapping("/groups/{groupId}/applies")
    public ResponseEntity<ApplyResponse.GetAll> getAll(
            @AuthenticationPrincipal Long userId,
            @PathVariable("groupId") Long groupId
    ) {
        ApplyResponse.GetAll response = applyService.getAll(userId, groupId);
        return ApiResponse.ok(response);
    }

    @Operation(description = "거절된 지원서 확인")
    @PostMapping("/applies/{applyId}")
    public ResponseEntity<Void> checkRejectedApply(
            @AuthenticationPrincipal Long userId,
            @PathVariable("applyId") Long applyId
    ) {
        applyService.checkRejectedApply(userId, applyId);
        return ApiResponse.noContent();
    }
}
