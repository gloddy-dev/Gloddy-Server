package com.gloddy.server.estimate.domain.vo.strategy;

import com.gloddy.server.user_group.domain.UserGroup;

public class AbsencePraiseStrategy implements PraiseStrategy {
    @Override
    public void praise(UserGroup userGroup) {
        userGroup.plusAbsenceVoteCount();
        if (userGroup.isAbsence()) {
            return;
        }

        if (userGroup.isAbsenceVoteCountOver()) {
            userGroup.absence();
            userGroup.getUser().getPraise().plusAbsenceCount();
        }
    }
}
