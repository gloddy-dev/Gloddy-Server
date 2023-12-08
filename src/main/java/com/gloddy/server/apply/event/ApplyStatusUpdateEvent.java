package com.gloddy.server.apply.event;

import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.core.event.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ApplyStatusUpdateEvent implements Event {
    private Long applyId;
    private Status status;
}
