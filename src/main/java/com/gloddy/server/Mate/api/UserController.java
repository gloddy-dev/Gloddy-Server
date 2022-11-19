package com.gloddy.server.Mate.api;

import com.gloddy.server.Mate.dto.UserRequest;
import com.gloddy.server.Mate.dto.UserResponse;
import com.gloddy.server.Mate.service.MateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class UserController {
    private final MateService userService;

    @PostMapping("/{groupId}/mate")
    public UserResponse.CrateMate create(
            @PathVariable Long groupId,
            @RequestBody UserRequest.CreateMate request,
            @AuthenticationPrincipal Long userId
    ) {
        return userService.create(request);
    }
}
