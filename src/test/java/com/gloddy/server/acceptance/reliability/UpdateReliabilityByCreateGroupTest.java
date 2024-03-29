package com.gloddy.server.acceptance.reliability;

import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.core.event.GroupParticipateEvent;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.user.domain.vo.ReliabilityLevel;
import com.gloddy.server.user.domain.vo.ScorePlusType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RecordApplicationEvents
public class UpdateReliabilityByCreateGroupTest extends ReliabilityApiTest {

    @Autowired
    private ApplicationEvents events;

    @Test
    @Transactional
    @Commit
    @DisplayName("그룹 생성 시 신뢰도 점수 업데이트 테스트")
    void successUpdateReliabilityByCreateGroup() throws Exception {
        // given
        GroupRequest.Create request = createGroupCreateRequest();

        // when
        String url = "/api/v1/group-create";
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                .header("X-AUTH-TOKEN", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        assertThat(user.getReliability()).isNotNull();
        assertThat(user.getReliability().getScore()).isEqualTo(0);
        assertThat(user.getReliability().getLevel()).isEqualTo(ReliabilityLevel.HOOD);

        long eventCount = events.stream(GroupParticipateEvent.class).count();
        assertThat(eventCount).isEqualTo(1);
    }

    @AfterTransaction
    @Transactional
    @Commit
    void afterEvent() {

        assertThat(user.getReliability().getScore()).isEqualTo(ScorePlusType.Created_Group.getScore());
        assertThat(user.getReliability().getLevel()).isEqualTo(ReliabilityLevel.HOOD);

        groupMemberJpaRepository.deleteAll();
        groupJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }
}
