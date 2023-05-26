package com.gloddy.server.estimate.domain.vo.strategy;

import com.gloddy.server.estimate.domain.vo.PraiseValue;

public class PraiseStrategyFactory {

    public static PraiseStrategy getStrategy(PraiseValue praiseValue) {
        if (praiseValue.isActive()) {
            return new ActivePraiseStrategy();
        } else if (praiseValue.isCalm()) {
            return new CalmPraiseStrategy();
        } else if (praiseValue.isHumor()) {
            return new HumorPraiseStrategy();
        } else if (praiseValue.isKind()) {
            return new KindPraiseStrategy();
        }
        return new AbsencePraiseStrategy();
    }
}
