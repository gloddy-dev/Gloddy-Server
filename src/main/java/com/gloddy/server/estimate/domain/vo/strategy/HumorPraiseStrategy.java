package com.gloddy.server.estimate.domain.vo.strategy;

import com.gloddy.server.group.domain.UserGroup;

public class HumorPraiseStrategy implements PraiseStrategy {
    @Override
    public void praise(UserGroup userGroup) {
        userGroup.getUser().getPraise().plusHumorCount();
    }
}
