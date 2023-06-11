package com.gloddy.server.group_member.event;

import com.gloddy.server.group_member.domain.dto.GroupMemberRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMemberSelectBestMateEvent {
    private GroupMemberRequest.Estimate.MateInfo mateInfo;
    private Long selectUserId;
}
