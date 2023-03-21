package com.gloddy.server.core.event.reliability;

import com.gloddy.server.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReliabilityLevelUpdateEvent {
    private User user;
    private Long score;
}
