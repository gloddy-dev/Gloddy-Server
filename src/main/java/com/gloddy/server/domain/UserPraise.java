package com.gloddy.server.domain;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.core.event.score.ScoreEventPublisher;
import com.gloddy.server.core.event.score.ScoreUpdateEvent;
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

    public void applyPraisePoint(ScoreEventPublisher scoreEventPublisher) {

        if (praiseValue.isAbsence()) {
            procAbsence(scoreEventPublisher);
            return;
        }
        updatePraisePoint(scoreEventPublisher);
    }

    private void updatePraisePoint(ScoreEventPublisher scoreEventPublisher) {

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

        scoreEventPublisher.publish(new ScoreUpdateEvent(user, ScoreType.Praised));
    }

    private void procAbsence(ScoreEventPublisher scoreEventPublisher) {
        if (absenceInGroupDomain.checkAlreadyAbsence()) {
            return;
        }

        absenceInGroupDomain.plusAbsenceCount();

        // TODO: 왜 checkAbsenceCountOver()가 됐을 때 user.getPraise().plusAbsenceCount(); 하지?
        if (absenceInGroupDomain.checkAbsenceCountOver()) {
            absenceInGroupDomain.absence();
            user.getPraise().plusAbsenceCount();
            scoreEventPublisher.publish(new ScoreUpdateEvent(user, ScoreType.Absence_Group));
        }
    }
}
