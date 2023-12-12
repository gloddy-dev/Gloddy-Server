package com.gloddy.server.batch.group.event;

import com.gloddy.server.core.event.Event;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupEndEvent implements Event {
    private Long groupId;
}
