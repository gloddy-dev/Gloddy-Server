package com.gloddy.server.group_member.event;

import com.gloddy.server.core.event.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMemberEstimateCompleteEvent implements Event {
    private Long userId;
}
