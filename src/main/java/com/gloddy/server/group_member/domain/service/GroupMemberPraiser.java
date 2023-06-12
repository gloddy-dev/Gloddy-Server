package com.gloddy.server.group_member.domain.service;

import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.group_member.domain.service.strategy.praise.PraiseStrategy;
import com.gloddy.server.group_member.domain.service.strategy.praise.PraiseStrategyFactory;
import com.gloddy.server.group_member.event.producer.GroupMemberEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.Estimate.*;

@Component
@RequiredArgsConstructor
public class GroupMemberPraiser {

    private final GroupMemberQueryHandler groupMemberQueryHandler;
    private final GroupMemberEventProducer groupMemberEventProducer;

    public void praise(Long groupId, List<PraiseInfo> praiseInfos) {
        praiseInfos.forEach(praiseInfo -> {
            GroupMember groupMember = groupMemberQueryHandler.findByUserIdAndGroupId(praiseInfo.getUserId(), groupId);
            PraiseStrategy praiseStrategy = PraiseStrategyFactory.getStrategy(praiseInfo.getPraiseValue());
            praiseStrategy.praise(groupMember, groupMemberEventProducer);
        });
    }
}
