package com.gloddy.server.core.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupParticipateEvent implements Event {
    private Long userId;
    private Long groupId;
}
