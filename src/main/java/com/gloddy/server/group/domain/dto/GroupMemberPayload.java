package com.gloddy.server.group.domain.dto;

import com.gloddy.server.group_member.domain.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberPayload {
    private Long groupId;
    private Long captainId;
    private String groupMemberName;
    private String groupImage;

    public static GroupMemberPayload toDto(GroupMember groupMember) {
        return new GroupMemberPayload(
                groupMember.getGroup().getId(),
                groupMember.getGroup().getCaptainId(),
                groupMember.getUser().getNickName(),
                groupMember.getGroup().getImageUrl()
        );
    }
}
