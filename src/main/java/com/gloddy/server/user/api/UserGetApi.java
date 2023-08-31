package com.gloddy.server.user.api;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.mate.application.MateService;
import com.gloddy.server.praise.application.PraiseService;
import com.gloddy.server.user.domain.dto.UserGetResponse;
import com.gloddy.server.user.application.UserGetService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.gloddy.server.mate.domain.dto.MateResponse.*;
import static com.gloddy.server.praise.domain.dto.PraiseResponse.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class UserGetApi {
    private final UserGetService userService;
    private final PraiseService praiseService;
    private final MateService mateService;

    @Operation(summary = "마이페이지 조회")
    @GetMapping("/me/page")
    public ResponseEntity<UserGetResponse> getMyPage(@AuthenticationPrincipal Long userId) {
        UserGetResponse response = userService.getUserPage(userId);
        return ApiResponse.ok(response);
    }

    @Operation(summary = "프로필 조회")
    @GetMapping("/users/{userId}/me/page")
    public ResponseEntity<UserGetResponse> getProfile(
            @PathVariable Long userId
    ) {
        UserGetResponse response = userService.getUserPage(userId);
        return ApiResponse.ok(response);
    }

    @Operation(summary = "마이페이지 칭찬 조회")
    @GetMapping("/me/praises")
    public ResponseEntity<getPraiseForUser> getPraiseForUser(
            @AuthenticationPrincipal Long userId
    ) {
        getPraiseForUser response = praiseService.getPraiseForUser(userId);
        return ApiResponse.ok(response);
    }

    @Operation(summary = "마이페이지 모임후기 조회")
    @GetMapping("/me/mates")
    public ResponseEntity<getMatesForUser> getMatesForUser(
            @AuthenticationPrincipal Long userId
    ) {
        getMatesForUser response = mateService.getMatesForUser(userId);
        return ApiResponse.ok(response);
    }

    @Operation(summary = "마이페이지 모임후기 삭제")
    @DeleteMapping("/me/mate/{mateId}")
    public ResponseEntity<Void> deleteMateForUser(
            @PathVariable Long mateId,
            @AuthenticationPrincipal Long userId
    ) {
        mateService.deleteMateForUser(mateId, userId);
        return ApiResponse.noContent();
    }
}
