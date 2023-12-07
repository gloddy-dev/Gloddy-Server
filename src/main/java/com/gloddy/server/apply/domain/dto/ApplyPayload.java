package com.gloddy.server.apply.domain.dto;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.group.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplyPayload {
    private Long groupId;
    private Long applyUserId;
    private Long captainId;
    private String groupImage;

    public static ApplyPayload toDto(Apply apply) {
        return new ApplyPayload(
                apply.getGroup().getId(),
                apply.getUserId(),
                apply.getGroup().getCaptainId(),
                apply.getGroup().getImageUrl()
        );
    }
}
