package com.gloddy.server.unit.userGroup;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.common.userGroup.UserGroupDomainTest;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.user_group.domain.UserGroup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.*;

public class IsAbsenceVoteCountOverTest extends UserGroupDomainTest {


    /**
     * 그룹 인원 7명
     * 투표 수 4명
     */
    @Test
    void isAbsenceVoteCountOverTest_true() {
        User user = getMockUser();
        Group group = getMockGroup();

        UserGroup userGroup = getInitUserGroup(user, group);
        userGroup.plusAbsenceVoteCount();
        userGroup.plusAbsenceVoteCount();
        userGroup.plusAbsenceVoteCount();
        userGroup.plusAbsenceVoteCount();

        willReturn(7).given(group).getMemberCount();

        Assertions.assertThat(userGroup.isAbsenceVoteCountOver()).isTrue();
    }

    /**
     * 그룹 인원 7명
     * 투표 수 3명
     */
    @Test
    void isAbsenceVoteCountOverTest_false() {
        User user = getMockUser();
        Group group = getMockGroup();

        UserGroup userGroup = getInitUserGroup(user, group);
        userGroup.plusAbsenceVoteCount();
        userGroup.plusAbsenceVoteCount();
        userGroup.plusAbsenceVoteCount();

        willReturn(7).given(group).getMemberCount();

        Assertions.assertThat(userGroup.isAbsenceVoteCountOver()).isFalse();
    }
}
