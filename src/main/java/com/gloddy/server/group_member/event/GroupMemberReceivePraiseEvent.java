package com.gloddy.server.core.event.group_member;

import com.gloddy.server.praise.domain.vo.PraiseValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMemberReceivePraiseEvent {
    private Long userId;
    private PraiseValue praiseValue;
}
