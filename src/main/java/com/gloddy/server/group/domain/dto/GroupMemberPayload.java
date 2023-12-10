package com.gloddy.server.group.domain.dto;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.user.domain.User;
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

    public static GroupMemberPayload toDto(Group group, User groupMemberUser) {
        return new GroupMemberPayload(
                group.getId(),
                group.getCaptainId(),
                groupMemberUser.getNickName(),
                group.getImageUrl()
        );
    }
}
