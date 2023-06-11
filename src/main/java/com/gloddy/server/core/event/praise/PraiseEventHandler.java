package com.gloddy.server.core.event.praise;

import com.gloddy.server.reliability.application.ReliabilityService;
import com.gloddy.server.reliability.domain.vo.ScoreType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PraiseEventHandler {
    private final ReliabilityService reliabilityService;

    @EventListener
    public void praiseCountUpdateEventHandler(PraiseCountUpdateEvent event) {
        if (event.isAbsenceCountUpdate()) {
            reliabilityService.update(event.getUserId(), ScoreType.Absence_Group);
        } else {
            reliabilityService.update(event.getUserId(), ScoreType.Praised);
        }
    }
}
