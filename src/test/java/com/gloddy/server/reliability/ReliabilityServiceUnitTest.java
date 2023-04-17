package com.gloddy.server.reliability;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import com.gloddy.server.reliability.entity.vo.ScorePlusType;
import com.gloddy.server.reliability.entity.vo.ScoreType;
import com.gloddy.server.reliability.handler.ReliabilityQueryHandler;
import com.gloddy.server.reliability.service.ReliabilityService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReliabilityServiceUnitTest {

    @Mock
    private ReliabilityQueryHandler reliabilityQueryHandler;

    @Mock
    private User user;

    @InjectMocks
    private ReliabilityService reliabilityService;

    /**
     * 신뢰도 89L, HOOD -> 그룹 참여 -> 신뢰도 91L, MATE
     */
    @Test
    void successUpdateReliabilityTest() {
        // given
        Reliability reliability = new Reliability(
                999999L, user, 89L, ReliabilityLevel.HOOD
        );

        BDDMockito.willReturn(reliability)
                .given(reliabilityQueryHandler).findByUser(user);

        // when
        reliabilityService.update(user, ScoreType.Created_Group);

        // then
        Assertions.assertThat(reliability.getLevel()).isEqualTo(ReliabilityLevel.MATE);
        Assertions.assertThat(reliability.getScore()).isEqualTo(89L + ScorePlusType.Created_Group.getScore());
    }

}
