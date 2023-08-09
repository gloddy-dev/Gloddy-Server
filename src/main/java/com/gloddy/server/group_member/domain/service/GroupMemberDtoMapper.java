package com.gloddy.server.group_member.domain.service;

import com.gloddy.server.group_member.domain.GroupMember;

import java.util.List;
import java.util.stream.Collectors;

import static com.gloddy.server.group_member.api.dto.GroupMemberResponse.*;

public class GroupMemberDtoMapper {

    public static GetAll mapToGetAllFrom(List<GroupMember> groupMembers) {
        return GetAll.of(groupMembers.stream()
                .map(GroupMemberDtoMapper::matToGetOneFrom)
                .toList());
    }

    private static GetOne matToGetOneFrom(GroupMember groupMember) {
        return GetOne.from(groupMember);
    }
}
