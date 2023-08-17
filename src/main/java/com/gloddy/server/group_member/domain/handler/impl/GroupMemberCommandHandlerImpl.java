package com.gloddy.server.group_member.domain.handler.impl;

import com.gloddy.server.group_member.domain.handler.GroupMemberCommandHandler;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.infra.repository.GroupMemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupMemberCommandHandlerImpl implements GroupMemberCommandHandler {
    private final GroupMemberJpaRepository GroupMemberJpaRepository;


    @Override
    public GroupMember save(GroupMember groupMember) {
        return GroupMemberJpaRepository.save(groupMember);
    }

    @Override
    public void delete(GroupMember groupMember) {
        GroupMemberJpaRepository.delete(groupMember);
    }


}
