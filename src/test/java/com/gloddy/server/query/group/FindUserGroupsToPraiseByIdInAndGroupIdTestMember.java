package com.gloddy.server.query.group;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Profile;
import com.gloddy.server.user.domain.vo.kind.Personality;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.query.QueryTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class FindUserGroupsToPraiseByIdInAndGroupIdTestMember extends QueryTest {

    private Group getGroup() {
        return groupJpaRepository.save(Group.builder().build());
    }

    private User getUser() {
        Profile profile = Profile.builder()
                .personalities(List.of(Personality.KIND))
                .build();
        User user = User.builder().profile(profile).build();
        return userJpaRepository.save(user);
    }

    private GroupMember getUserGroup(User user, Group group) {
        GroupMember groupMember = GroupMember.empty();
        groupMember.init(user, group);
        return userGroupJpaRepository.save(groupMember);
    }


    @Test
    void find_test_success() {

        Group group = getGroup();

        User user_1 = getUser();
        User user_2 = getUser();

        GroupMember group_Member_1 = getUserGroup(user_1, group);
        GroupMember group_Member_2 = getUserGroup(user_2, group);

        em.flush();
        em.clear();

        List<GroupMember> findGroupMembers = userGroupJpaRepository.findUserGroupsToPraiseByUserIdInAndGroupId(
                List.of(user_1.getId(), user_2.getId()), group.getId()
        );

        assertThat(findGroupMembers.get(0).getId()).isNotNull();
        assertThat(findGroupMembers.get(0).getGroup().getId()).isEqualTo(group.getId());
        assertThat(findGroupMembers.size()).isEqualTo(2);
    }
}
