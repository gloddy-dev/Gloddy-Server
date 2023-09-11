package com.gloddy.server.authEmail.api;

import com.gloddy.server.authEmail.domain.dto.request.AuthEmailRequest;
import com.gloddy.server.authEmail.domain.dto.response.AuthEmailResponse;
import com.gloddy.server.authEmail.application.AuthEmailService;
import com.gloddy.server.core.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthEmailApi {
    private final AuthEmailService authEmailService;

    @PostMapping("/auth/email")
    public ResponseEntity<Void> authEmail(
            @RequestBody @Valid AuthEmailRequest.AuthEmail request) {
        authEmailService.authEmail(request);
        return ApiResponse.noContent();
    }

    @PostMapping("/auth/verify-code")
    public ResponseEntity<AuthEmailResponse.VerifyCode> verifyCode(
            @RequestBody @Valid AuthEmailRequest.AuthCode request) {
        AuthEmailResponse.VerifyCode response = authEmailService.verifyCode(request);
        return ApiResponse.ok(response);
    }

    @PostMapping("/users/email/verify-code")
    @Operation(summary = "회원 가입 이후 학교 이메일 코드 인증 API")
    public ResponseEntity<Void> verifyEmailCodeAfterSignUp(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid AuthEmailRequest.AuthCode request) {
        authEmailService.verifyEmailCodeAfterSignUp(userId, request);
        return ApiResponse.noContent();
    }
}
