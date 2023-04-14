package com.gloddy.server.reliability;

import com.gloddy.server.common.BaseApiTest;
import com.gloddy.server.reliability.entity.vo.ScoreMinusType;
import com.gloddy.server.reliability.entity.vo.ScorePlusType;
import com.gloddy.server.reliability.entity.vo.ScoreType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClassifyScoreTypeUnitTest extends BaseApiTest {

    @Test
    @DisplayName("plus 점수 타입 분류 테스트")
    public void successClassifyScorePlusTypeTest() {
        // given
        String scoreType = ScoreType.Estimated.name();

        // then
        boolean isPlusType = ScorePlusType.isPlusType(scoreType);

        // when
        Assertions.assertThat(isPlusType).isTrue();
    }

    @Test
    @DisplayName("minus 점수 타입 분류 테스트")
    public void successClassifyScoreMinusTypeTest() {
        // given
        String scoreType = ScoreType.Absence_Group.name();

        // then
        boolean isMinusType = ScoreMinusType.isMinusType(scoreType);

        // when
        Assertions.assertThat(isMinusType).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 점수 타입 테스트")
    public void successUpdateScoreTest() {
        // given
        String scoreType = "NONE";

        // then
        boolean isPlusType = ScorePlusType.isPlusType(scoreType);
        boolean isMinusType = ScoreMinusType.isMinusType(scoreType);

        // when
        Assertions.assertThat(isPlusType).isFalse();
        Assertions.assertThat(isMinusType).isFalse();
    }
}
