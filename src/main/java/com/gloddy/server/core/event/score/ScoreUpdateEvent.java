package com.gloddy.server.core.event.score;

import com.gloddy.server.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScoreUpdateEvent {
    private User user;
    private String ScoreType;
}
