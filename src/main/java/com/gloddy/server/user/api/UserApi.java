package com.gloddy.server.user.api;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.estimate.service.mate.MateService;
import com.gloddy.server.estimate.service.praise.PraiseService;
import com.gloddy.server.user.dto.UserResponse;
import com.gloddy.server.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.gloddy.server.estimate.dto.MateResponse.*;
import static com.gloddy.server.estimate.dto.PraiseResponse.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class UserApi {
    private final UserService userService;
    private final PraiseService praiseService;
    private final MateService mateService;

    @GetMapping("/me/page")
    public UserResponse.MyPage getMyPage(@AuthenticationPrincipal Long userId) {
        return userService.getMyPage(userId);
    }

    @ApiOperation("마이페이지 칭찬 조회")
    @GetMapping("/me/praises")
    public ResponseEntity<getPraiseForUser> getPraiseForUser(
            @AuthenticationPrincipal Long userId
    ) {
        getPraiseForUser response = praiseService.getPraiseForUser(userId);
        return ApiResponse.ok(response);
    }

    @ApiOperation("마이페이지 모임후기 조회")
    @GetMapping("/me/mates")
    public ResponseEntity<getMatesForUser> getMatesForUser(
            @AuthenticationPrincipal Long userId
    ) {
        getMatesForUser response = mateService.getMatesForUser(userId);
        return ApiResponse.ok(response);
    }

    @ApiOperation("마이페이지 모임후기 삭제")
    @DeleteMapping("/me/mate/{mateId}")
    public ResponseEntity<Void> deleteMateForUser(
            @PathVariable Long mateId,
            @AuthenticationPrincipal Long userId
    ) {
        mateService.deleteMateForUser(mateId, userId);
        return ApiResponse.noContent();
    }
}
