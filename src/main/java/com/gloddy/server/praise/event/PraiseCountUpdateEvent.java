package com.gloddy.server.core.event.praise;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PraiseCountUpdateEvent {
    private Long userId;
    private boolean isAbsenceCountUpdate;
}
