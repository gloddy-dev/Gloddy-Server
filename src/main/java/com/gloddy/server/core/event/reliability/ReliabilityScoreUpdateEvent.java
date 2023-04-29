package com.gloddy.server.core.event.reliability;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.reliability.entity.vo.ScoreType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReliabilityScoreUpdateEvent {
    private Long useId;
    private ScoreType type;
}
