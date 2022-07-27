package com.gloddy.server.authEmail.controller;

import com.gloddy.server.authEmail.dto.request.AuthCodeRequest;
import com.gloddy.server.authEmail.dto.request.AuthEmailRequest;
import com.gloddy.server.authEmail.service.AuthEmailService;
import com.gloddy.server.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthEmailController {
    private final AuthEmailService authEmailService;

    @PostMapping("/auth/email")
    public ResponseEntity<Void> authEmail(
            @Valid @RequestBody AuthEmailRequest request) {
        authEmailService.authEmail(request);
        return ApiResponse.noContent();
    }

    @PostMapping("/auth/verify-code")
    public ResponseEntity<Boolean> verifyCode(
            @RequestBody AuthCodeRequest request) {
        Boolean response = authEmailService.verifyCode(request);
        return ApiResponse.ok(response);
    }
}
