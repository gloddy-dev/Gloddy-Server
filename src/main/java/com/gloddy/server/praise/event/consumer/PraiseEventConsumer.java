package com.gloddy.server.praise.event.consumer;

import com.gloddy.server.group_member.event.GroupMemberReceivePraiseEvent;
import com.gloddy.server.praise.application.PraiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PraiseEventConsumer {
    private final PraiseService praiseService;

    @EventListener
    public void consume(GroupMemberReceivePraiseEvent event) {
        praiseService.updatePraisePoint(event.getUserId(), event.getPraiseValue());
    }
}
