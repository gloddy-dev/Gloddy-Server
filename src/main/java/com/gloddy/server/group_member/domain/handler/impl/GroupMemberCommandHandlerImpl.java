package com.gloddy.server.group_member.domain.handler.impl;

import com.gloddy.server.group_member.domain.handler.GroupMemberCommandHandler;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.infra.repository.GroupMemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupMemberMemberCommandHandlerImpl implements GroupMemberCommandHandler {
    private final GroupMemberJpaRepository userGroupJpaRepository;


    @Override
    public GroupMember save(GroupMember groupMember) {
        return userGroupJpaRepository.save(groupMember);
    }
}
