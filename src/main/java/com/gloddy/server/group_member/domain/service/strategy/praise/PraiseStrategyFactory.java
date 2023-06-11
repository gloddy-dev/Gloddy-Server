package com.gloddy.server.group_member.domain.service.strategy.praise;

import com.gloddy.server.praise.domain.vo.PraiseValue;

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
