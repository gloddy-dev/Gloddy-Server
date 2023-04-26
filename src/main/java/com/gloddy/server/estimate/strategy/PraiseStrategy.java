package com.gloddy.server.estimate.strategy;

import com.gloddy.server.estimate.entity.embedded.PraiseValue;
import com.gloddy.server.group.entity.UserGroup;

public interface PraiseStrategy {

    void praise(UserGroup userGroup);
}
