package com.gloddy.server.apply.event;

import com.gloddy.server.core.event.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ApplyCreateEvent implements Event {
    private Long captainId;
    private Long groupId;
    private Long applyUserId;
}
