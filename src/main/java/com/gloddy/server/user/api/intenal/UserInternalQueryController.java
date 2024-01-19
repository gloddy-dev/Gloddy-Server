package com.gloddy.server.user.api.intenal;


import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.user.application.internal.UserInternalQueryService;
import com.gloddy.server.user.application.internal.dto.UserPreviewResponse;
import com.gloddy.server.user.application.internal.dto.UserPreviewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/internal/payload")
@RequiredArgsConstructor
public class UserInternalQueryController {

    private final UserInternalQueryService userInternalQueryService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserPreviewResponse> getUserPreview(@PathVariable("userId") Long userId) {
        UserPreviewResponse response = userInternalQueryService.getUserPreview(userId);
        return ApiResponse.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<UserPreviewsResponse> getUserPreviews(
            @RequestParam("ids") Collection<Long> ids
    ) {
        UserPreviewsResponse response = userInternalQueryService.getUserPreviews(ids);
        return ApiResponse.ok(response);
    }
}
