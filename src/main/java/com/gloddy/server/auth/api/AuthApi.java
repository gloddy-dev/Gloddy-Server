package com.gloddy.server.auth.api;

import com.gloddy.server.auth.service.AuthService;
import com.gloddy.server.auth.dto.AuthRequest;
import com.gloddy.server.auth.dto.AuthResponse;
import com.gloddy.server.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthApi {

    private final AuthService userService;

    @PostMapping("/auth/sign-up")
    public ResponseEntity<AuthResponse.SignUp> signUp(@RequestBody AuthRequest.SignUp req) {
        AuthResponse.SignUp response = userService.signUp(req);

        return ApiResponse.ok(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse.Login> login(@RequestBody AuthRequest.Login req) {
        AuthResponse.Login response = userService.login(req.getEmail());

        return ApiResponse.ok(response);
    }


}
