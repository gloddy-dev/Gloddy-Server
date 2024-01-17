package com.gloddy.server.user.application.internal.dto;

import com.gloddy.server.user.domain.vo.ReliabilityLevel;
import com.querydsl.core.annotations.QueryProjection;

public record UserPreviewResponse(Long id, Boolean isCertifiedStudent, String profileImage, String nickName,
                                  String countryName, String countryImage, ReliabilityLevel reliabilityLevel) {
    @QueryProjection
    public UserPreviewResponse {
    }
}
