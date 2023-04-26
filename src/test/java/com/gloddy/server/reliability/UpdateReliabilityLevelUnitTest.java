package com.gloddy.server.reliability;

import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UpdateReliabilityLevelUnitTest {

    @Test
    public void successUpdateReliabilityLevelTest() {
        // given
        ReliabilityLevel reliabilityLevel = ReliabilityLevel.MATE;

        // when
        ReliabilityLevel level = reliabilityLevel.upgradeLevel(190L);

        // then
        Assertions.assertThat(level).isEqualTo(ReliabilityLevel.SOUL_MATE);
    }
}