package com.gloddy.server.auth.api;

import com.gloddy.server.auth.service.UserService;
import com.gloddy.server.core.dto.auth.AuthRequest;
import com.gloddy.server.core.dto.auth.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse.SignUp> signUp(@RequestBody AuthRequest.SignUp req) {
        AuthResponse.SignUp response = userService.signUp(req);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse.Login> login(@RequestBody AuthRequest.Login req) {
        AuthResponse.Login response = userService.login(req.getEmail());

        return ResponseEntity.ok(response);
    }


}
