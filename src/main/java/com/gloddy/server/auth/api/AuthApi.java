package com.gloddy.server.auth.api;

import com.gloddy.server.auth.application.AuthService;
import com.gloddy.server.auth.domain.dto.AuthRequest;
import com.gloddy.server.auth.domain.dto.AuthResponse;
import com.gloddy.server.core.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthApi {

    private final AuthService authService;

    @Operation(security = {})
    @PostMapping("/auth/email/check")
    public ResponseEntity<AuthResponse.Whether> emailCheck(@RequestBody AuthRequest.EmailCheck req) {
        AuthResponse.Whether response = authService.emailCheck(req.getEmail());

        return ApiResponse.ok(response);
    }

    @Operation(security = {})
    @PostMapping("/auth/sign-up")
    public ResponseEntity<AuthResponse.SignUp> signUp(@RequestBody AuthRequest.SignUp req) {
        AuthResponse.SignUp response = authService.signUp(req);

        return ApiResponse.ok(response);
    }

    @Operation(security = {})
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse.Login> login(@RequestBody AuthRequest.Login req) {
        AuthResponse.Login response = authService.login(req);

        return ApiResponse.ok(response);
    }

    @Operation(security = {})
    @PostMapping("/auth/token-reissue")
    public ResponseEntity<AuthResponse.Token> tokenReissue(@RequestBody AuthRequest.ReIssueToken req) {
        AuthResponse.Token response = authService.reissueToken(req.getAccessToken(), req.getRefreshToken());
        return ApiResponse.ok(response);
    }

}
