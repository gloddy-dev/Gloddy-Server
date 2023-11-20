package com.gloddy.server.group_member.event;

import com.gloddy.server.core.event.Event;
import com.gloddy.server.user.domain.vo.PraiseValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMemberReceivePraiseEvent implements Event {
    private Long userId;
    private PraiseValue praiseValue;
}
