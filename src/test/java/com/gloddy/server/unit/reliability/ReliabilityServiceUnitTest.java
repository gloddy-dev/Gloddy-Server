package com.gloddy.server.unit.reliability;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.reliability.domain.Reliability;
import com.gloddy.server.reliability.domain.vo.ReliabilityLevel;
import com.gloddy.server.reliability.domain.vo.ScoreMinusType;
import com.gloddy.server.reliability.domain.vo.ScorePlusType;
import com.gloddy.server.reliability.domain.vo.ScoreType;
import com.gloddy.server.reliability.domain.handler.ReliabilityQueryHandler;
import com.gloddy.server.reliability.application.ReliabilityService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

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
    @DisplayName("신뢰도 등급 업데이트 테스트 - ScorePlusType")
    void successUpdateReliabilityByScorePlusTypeTest() {
        // given
        Reliability reliability = new Reliability(
                999999L, user, 89L, ReliabilityLevel.HOOD
        );

        willReturn(9999L)
                .given(user).getId();
        willReturn(reliability)
                .given(reliabilityQueryHandler).findByUserId(9999L);

        // when
        reliabilityService.update(user.getId(), ScoreType.Created_Group);

        // then
        Assertions.assertThat(reliability.getLevel()).isEqualTo(ReliabilityLevel.MATE);
        Assertions.assertThat(reliability.getScore()).isEqualTo(89L + ScorePlusType.Created_Group.getScore());
    }

    @Test
    @DisplayName("신뢰도 등급 업데이트 테스트 - ScoreMinusType")
    void successUpdateReliabilityByScoreMinusTypeTest() {
        // given
        Reliability reliability = new Reliability(
                999999L, user, 95L, ReliabilityLevel.MATE
        );

        willReturn(9999L)
                .given(user).getId();
        willReturn(reliability)
                .given(reliabilityQueryHandler).findByUserId(9999L);

        // when
        reliabilityService.update(user.getId(), ScoreType.Absence_Group);

        // then
        Assertions.assertThat(reliability.getLevel()).isEqualTo(ReliabilityLevel.HOOD);
        Assertions.assertThat(reliability.getScore()).isEqualTo(95L - ScoreMinusType.Absence_Group.getScore());
    }
}
