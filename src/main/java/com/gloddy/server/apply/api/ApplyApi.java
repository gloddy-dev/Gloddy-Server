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
