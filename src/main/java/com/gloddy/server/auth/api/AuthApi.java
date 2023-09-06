package com.gloddy.server.auth.api;

import com.gloddy.server.auth.application.AuthService;
import com.gloddy.server.auth.domain.dto.AuthRequest;
import com.gloddy.server.auth.domain.dto.AuthResponse;
import com.gloddy.server.core.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthApi {

    private final AuthService authService;

    @Operation(security = {})
    @PostMapping("/email/check")
    public ResponseEntity<AuthResponse.Whether> emailCheck(@RequestBody AuthRequest.EmailCheck req) {
        AuthResponse.Whether response = authService.emailCheck(req.getEmail());

        return ApiResponse.ok(response);
    }

    @Operation(security = {})
    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse.SignUp> signUp(@RequestBody AuthRequest.SignUp req) {
        AuthResponse.SignUp response = authService.signUp(req);

        return ApiResponse.ok(response);
    }

    @Operation(security = {})
    @PostMapping("/login")
    public ResponseEntity<AuthResponse.Login> login(@RequestBody AuthRequest.Login req) {
        AuthResponse.Login response = authService.login(req);

        return ApiResponse.ok(response);
    }

    @Operation(security = {})
    @PostMapping("/token-reissue")
    public ResponseEntity<AuthResponse.Token> tokenReissue(@RequestBody AuthRequest.ReIssueToken req) {
        AuthResponse.Token response = authService.reissueToken(req.getAccessToken(), req.getRefreshToken());
        return ApiResponse.ok(response);
    }

    @PatchMapping("/sign-out")
    public ResponseEntity<Void> signOut(
            @AuthenticationPrincipal Long userId
    ) {
        authService.signOut(userId);
        return ApiResponse.noContent();
    }
}
