package com.gloddy.server.group_member.application;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import com.gloddy.server.group.domain.vo.GroupMemberVO;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.domain.handler.GroupMemberCommandHandler;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.group_member.event.GroupMemberLeaveEvent;
import com.gloddy.server.group_member.event.producer.GroupMemberEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupMemberDeleteService {

    private final GroupQueryHandler groupQueryHandler;
    private final GroupMemberQueryHandler groupMemberQueryHandler;
    private final GroupMemberCommandHandler groupMemberCommandHandler;
    private final GroupMemberEventProducer groupMemberEventProducer;

    @Transactional
    public void delete(Long userId, Long groupId) {
        GroupMember groupMember = groupMemberQueryHandler.findByUserIdAndGroupId(userId, groupId);
        deleteGroupMemberVo(userId, groupId);
        deleteGroupMember(userId, groupId);

        groupMemberEventProducer.produceEvent(new GroupMemberLeaveEvent(groupMember.getId(), userId));
    }

    private void deleteGroupMemberVo(Long userId, Long groupId) {
        Group group = groupQueryHandler.findById(groupId);
        group.deleteGroupMemberVoByUserId(userId);
    }

    private void deleteGroupMember(Long userId, Long groupId) {
        GroupMember groupMember = groupMemberQueryHandler.findByUserIdAndGroupId(userId, groupId);
        groupMemberCommandHandler.delete(groupMember);
    }
}
