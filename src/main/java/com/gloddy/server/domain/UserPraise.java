package com.gloddy.server.domain;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.estimate.entity.embedded.PraiseValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserPraise {

    private User user;
    private AbsenceInGroupDomain absenceInGroupDomain;
    private PraiseValue praiseValue;

    protected UserPraise() {
    }

    public void applyPraisePoint() {

        if (praiseValue.isCalm()) {
            user.getPraise().plusCalmCount();
        } else if (praiseValue.isKind()) {
            user.getPraise().plusKindCount();
        } else if (praiseValue.isActive()) {
            user.getPraise().plusActiveCount();
        } else if (praiseValue.isHumor()) {
            user.getPraise().plusHumorCount();
        } else if (praiseValue.isAbsence()) {
            procAbsence();
        }

        throw new RuntimeException("fail applyPraisePoint");
    }

    private void procAbsence() {
        if (absenceInGroupDomain.checkAlreadyAbsence()) {
            return;
        }

        absenceInGroupDomain.plusAbsenceCount();

        if (absenceInGroupDomain.checkAbsenceCountOver()) {
            user.getPraise().plusAbsenceCount();
        }
    }
}
