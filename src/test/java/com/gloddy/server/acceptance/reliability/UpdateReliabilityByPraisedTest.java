package com.gloddy.server.acceptance.reliability;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.group_member.event.GroupMemberEstimateCompleteEvent;
import com.gloddy.server.group_member.event.GroupMemberReceivePraiseEvent;
import com.gloddy.server.mate.application.MateSaveService;
import com.gloddy.server.praise.domain.vo.PraiseValue;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.reliability.domain.vo.ReliabilityLevel;
import com.gloddy.server.reliability.domain.vo.ScorePlusType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.*;


@RecordApplicationEvents
public class UpdateReliabilityByPraisedTest extends ReliabilityApiTest {

    @MockBean
    private MateSaveService mateSaveService;

    @Autowired
    private ApplicationEvents events;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    @Transactional
    @Commit
    @DisplayName("칭찬 받은 유저 신뢰도 점수 업데이트 테스트")
    void successUpdateReliabilityByPraisedTest() throws Exception {
        // given
        User loginUser = user;
        User receivePraiseUser = createUser();
        Group group = createGroup();
        createGroupMember(loginUser, group);
        createGroupMember(receivePraiseUser, group);
        Estimate request = createEstimateRequest(receivePraiseUser, PraiseValue.KIND);


        // when
        String url = "/api/v1/groups/" + group.getId() + "/group_members" + "/estimate";
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-AUTH-TOKEN", accessToken)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        long groupMemberReceivePraiseEventCount = events.stream(GroupMemberReceivePraiseEvent.class).count();
        long groupMemberEstimateCompleteEventCount = events.stream(GroupMemberEstimateCompleteEvent.class).count();
        Assertions.assertThat(groupMemberReceivePraiseEventCount).isEqualTo(1);
        Assertions.assertThat(groupMemberEstimateCompleteEventCount).isEqualTo(1);
    }

    @AfterTransaction
    @Transactional
    @Commit
    void afterEvent() {

        verifyUserReliability();

        groupMemberJpaRepository.deleteAll();
        groupJpaRepository.deleteAll();
        mateJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }

    private void verifyUserReliability() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(status -> {
            User receivePraiseUser = userJpaRepository.findFirstByOrderByIdDesc();
            Assertions.assertThat(receivePraiseUser.getReliability().getScore())
                    .isEqualTo(ScorePlusType.Praised.getScore());
            Assertions.assertThat(receivePraiseUser.getReliability().getLevel()).isEqualTo(ReliabilityLevel.HOOD);
            return null;
        });
    }
}
