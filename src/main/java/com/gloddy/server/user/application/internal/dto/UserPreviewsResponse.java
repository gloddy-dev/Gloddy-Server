package com.gloddy.server.user.application.internal.dto;

import java.util.List;

public record UserPreviewsResponse(
        List<UserPreviewResponse> users
) {
}
