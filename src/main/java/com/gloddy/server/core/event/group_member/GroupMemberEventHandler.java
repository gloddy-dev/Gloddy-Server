package com.gloddy.server.core.event.group_member;

import com.gloddy.server.mate.application.MateSaveService;
import com.gloddy.server.praise.application.PraiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupMemberEventHandler {
    private final PraiseService praiseService;
    private final MateSaveService mateSaveService;

    @EventListener
    public void groupMemberReceivePraiseEventHandler(GroupMemberReceivePraiseEvent event) {
        praiseService.updatePraisePoint(event.getUserId(), event.getPraiseValue());
    }

    @EventListener
    public void groupMemberSelectBestMateEventHandler(GroupMemberSelectBestMateEvent event) {
        mateSaveService.save(event.getMateInfo(), event.getSelectUserId());
    }
}
