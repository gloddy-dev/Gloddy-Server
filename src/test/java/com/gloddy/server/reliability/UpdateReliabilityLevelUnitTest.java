package com.gloddy.server.reliability;


import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateReliabilityLevelUnitTest {

    @Mock
    private Reliability reliability;

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
