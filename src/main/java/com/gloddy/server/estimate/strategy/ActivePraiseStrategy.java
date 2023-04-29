package com.gloddy.server.estimate.strategy;

import com.gloddy.server.group.entity.UserGroup;

public class ActivePraiseStrategy implements PraiseStrategy {
    @Override
    public void praise(UserGroup userGroup) {
        userGroup.getUser().getPraise().plusActiveCount();
    }
}
