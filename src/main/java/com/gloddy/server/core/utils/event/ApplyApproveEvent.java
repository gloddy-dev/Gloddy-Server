package com.gloddy.server.core.utils.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ApplyApproveEvent {
    private Long userId;
    private Long groupId;
}
