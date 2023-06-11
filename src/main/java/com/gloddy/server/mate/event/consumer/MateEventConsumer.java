package com.gloddy.server.mate.event.consumer;

import com.gloddy.server.group_member.event.GroupMemberSelectBestMateEvent;
import com.gloddy.server.mate.application.MateSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MateEventConsumer {

    private final MateSaveService mateSaveService;

    @EventListener
    public void consume(GroupMemberSelectBestMateEvent event) {
        mateSaveService.save(event.getMateInfo(), event.getSelectUserId());
    }
}
