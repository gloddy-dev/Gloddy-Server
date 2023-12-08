package com.gloddy.server.group.domain.dto;

import com.gloddy.server.group.domain.Group;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupPayload {
    private Long groupId;
    private Long captainId;
    private String groupImage;
    private List<Long> groupMemberIds;

    public static GroupPayload toDto(Group group) {
        return new GroupPayload(
                group.getId(),
                group.getCaptainId(),
                group.getImageUrl(),
                group.getGroupMemberUserIds()
        );
    }
}
