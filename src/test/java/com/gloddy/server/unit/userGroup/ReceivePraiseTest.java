package com.gloddy.server.unit.userGroup;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.common.userGroup.UserGroupDomainTest;
import com.gloddy.server.estimate.domain.Praise;
import com.gloddy.server.estimate.domain.vo.PraiseValue;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.UserGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("UserGroupUnitTest - receivePraise")
public class ReceivePraiseTest extends UserGroupDomainTest {

    @Test
    @DisplayName("Active 칭찬 받음")
    void receivePraise_Active_success() {
        User user = getMockUser();
        Group group = getMockGroup();
        Praise praise = getInitPraise(user);
        UserGroup userGroup = getInitUserGroup(user, group);

        willReturn(praise).given(user).getPraise();

        userGroup.receivePraise(PraiseValue.ACTIVE);

        assertThat(praise.getTotalActiveCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Humor 칭찬 받음")
    void receivePraise_Humor_success() {
        User user = getMockUser();
        Group group = getMockGroup();
        Praise praise = getInitPraise(user);
        UserGroup userGroup = getInitUserGroup(user, group);

        willReturn(praise).given(user).getPraise();

        userGroup.receivePraise(PraiseValue.HUMOR);

        assertThat(praise.getTotalHumorCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Kind 칭찬 받음")
    void receivePraise_Kind_success() {
        User user = getMockUser();
        Group group = getMockGroup();
        Praise praise = getInitPraise(user);
        UserGroup userGroup = getInitUserGroup(user, group);

        willReturn(praise).given(user).getPraise();

        userGroup.receivePraise(PraiseValue.KIND);

        assertThat(praise.getTotalKindCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Calm 칭찬 받음")
    void receivePraise_Calm_success() {
        User user = getMockUser();
        Group group = getMockGroup();
        Praise praise = getInitPraise(user);
        UserGroup userGroup = getInitUserGroup(user, group);

        willReturn(praise).given(user).getPraise();

        userGroup.receivePraise(PraiseValue.CALM);

        assertThat(praise.getTotalCalmCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Absence 칭찬 받음 - 칭찬 받고도 아직 과반수 넘지 못함")
    void receivePraise_Absence_success_case1() {
        User user = getMockUser();
        Group group = getMockGroup();
        Praise praise = getInitPraise(user);
        UserGroup userGroup = getInitUserGroup(user, group);

        willReturn(7).given(group).getMemberCount();

        userGroup.receivePraise(PraiseValue.ABSENCE);

        assertThat(userGroup.getAbsenceVoteCount()).isEqualTo(1);
        assertThat(userGroup.isAbsence()).isFalse();
        assertThat(praise.getTotalAbsenceCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("Absence 칭찬 받음 - 칭찬 받고 과반수 넘음")
    void receivePraise_Absence_success_case2() {
        User user = getMockUser();
        Group group = getMockGroup();
        Praise praise = getInitPraise(user);
        UserGroup userGroup = getInitUserGroup(user, group);

        userGroup.plusAbsenceVoteCount();
        userGroup.plusAbsenceVoteCount();
        userGroup.plusAbsenceVoteCount();
        willReturn(praise).given(user).getPraise();
        willReturn(7).given(group).getMemberCount();

        userGroup.receivePraise(PraiseValue.ABSENCE);
        assertThat(userGroup.getAbsenceVoteCount()).isEqualTo(4);
        assertThat(userGroup.isAbsence()).isTrue();
        assertThat(praise.getTotalAbsenceCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Absence 칭찬 받음 - 칭찬 받기 전 이미 결근 처리")
    void receivePraise_Absence_success_case3() {
        User user = getMockUser();
        Group group = getMockGroup();
        Praise praise = getInitPraise(user);
        UserGroup userGroup = getInitUserGroup(user, group);

        praise.plusAbsenceCount();
        userGroup.absence();

        userGroup.receivePraise(PraiseValue.ABSENCE);
        assertThat(userGroup.getAbsenceVoteCount()).isEqualTo(1);
        assertThat(userGroup.isAbsence()).isTrue();
        assertThat(praise.getTotalAbsenceCount()).isEqualTo(1);
    }
}
