package com.gloddy.server.user.api;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.user.application.UserCommandService;
import com.gloddy.server.user.domain.dto.UserUpdateRequest;
import com.gloddy.server.user.domain.dto.UserUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserUpdateApi {

    private final UserCommandService userCommandService;

    @Operation(summary = "프로필 수정")
    @PatchMapping("/me/info")
    public ResponseEntity<UserUpdateResponse> updateInfo(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid UserUpdateRequest.Info request
    ) {
        UserUpdateResponse response = userCommandService.updateProfile(userId, request);
        return ApiResponse.ok(response);
    }
}
