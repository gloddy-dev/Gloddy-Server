package com.gloddy.server.auth.api;

import com.gloddy.server.auth.dto.UserRequest;
import com.gloddy.server.auth.dto.UserResponse;
import com.gloddy.server.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class UserController {
    private final UserService userService;

    @PostMapping("/{groupId}/mate")
    public UserResponse.CrateMate create(
            @PathVariable Long groupId,
            @RequestBody UserRequest.CreateMate request,
            @AuthenticationPrincipal Long userId
    ) {
        return userService.create(request);
    }
}
