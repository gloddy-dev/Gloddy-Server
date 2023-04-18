package com.gloddy.server.domain;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.core.event.reliability.ReliabilityEventPublisher;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.estimate.entity.embedded.PraiseValue;
import com.gloddy.server.reliability.entity.vo.ScoreType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserPraise {

    private User user;
    private AbsenceInGroupDomain absenceInGroupDomain;
    private PraiseValue praiseValue;

    protected UserPraise() {
    }

    public void applyPraisePoint(ReliabilityEventPublisher reliabilityEventPublisher) {
        if (praiseValue.isAbsence()) {
            procAbsence(reliabilityEventPublisher);
            return;
        }
        updatePraisePoint(reliabilityEventPublisher);
    }

    private void updatePraisePoint(ReliabilityEventPublisher reliabilityEventPublisher) {

        if (praiseValue.isCalm()) {
            user.getPraise().plusCalmCount();
        } else if (praiseValue.isKind()) {
            user.getPraise().plusKindCount();
        } else if (praiseValue.isActive()) {
            user.getPraise().plusActiveCount();
        } else if (praiseValue.isHumor()) {
            user.getPraise().plusHumorCount();
        } else {
            throw new RuntimeException("존재하지 않는 칭찬 타입입니다.");
        }

        reliabilityEventPublisher.publish(new ReliabilityScoreUpdateEvent(user, ScoreType.Praised));
    }

    private void procAbsence(ReliabilityEventPublisher reliabilityEventPublisher) {
        if (absenceInGroupDomain.checkAlreadyAbsence()) {
            return;
        }

        absenceInGroupDomain.plusAbsenceCount();

        if (absenceInGroupDomain.checkAbsenceCountOver()) {
            absenceInGroupDomain.absence();
            user.getPraise().plusAbsenceCount();
            reliabilityEventPublisher.publish(new ReliabilityScoreUpdateEvent(user, ScoreType.Absence_Group));
        }
    }
}
