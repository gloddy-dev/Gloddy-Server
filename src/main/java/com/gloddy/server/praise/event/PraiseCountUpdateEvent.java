package com.gloddy.server.praise.event;

import com.gloddy.server.core.event.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PraiseCountUpdateEvent implements Event {
    private Long userId;
    private boolean isAbsenceCountUpdate;
}
