package com.gloddy.server.estimate.domain.vo.strategy;

import com.gloddy.server.user_group.domain.UserGroup;

public class ActivePraiseStrategy implements PraiseStrategy {
    @Override
    public void praise(UserGroup userGroup) {
        userGroup.getUser().getPraise().plusActiveCount();
    }
}
