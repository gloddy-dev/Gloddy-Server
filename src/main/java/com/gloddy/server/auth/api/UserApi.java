package com.gloddy.server.auth.api;

import com.gloddy.server.auth.dto.UserResponse;
import com.gloddy.server.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class UserApi {
    private final UserService userService;

    @GetMapping("/myPage")
    public UserResponse.MyPage getMyPage(
            @AuthenticationPrincipal Long userId
    ) {
        return userService.getMyPage(userId);
    }
}
