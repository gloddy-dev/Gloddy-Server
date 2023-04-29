package com.gloddy.server.estimate.strategy;

import com.gloddy.server.group.entity.UserGroup;

public class CalmPraiseStrategy implements PraiseStrategy {
    @Override
    public void praise(UserGroup userGroup) {
        userGroup.getUser().getPraise().plusCalmCount();
    }
}
