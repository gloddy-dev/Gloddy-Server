package com.gloddy.server.query.group;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import com.gloddy.server.praise.domain.Praise;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.user_group.domain.UserGroup;
import com.gloddy.server.query.QueryTest;
import com.gloddy.server.reliability.domain.Reliability;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class FindUserGroupsToPraiseByUserIdInAndGroupIdTest extends QueryTest {

    private Group getGroup() {
        return groupJpaRepository.save(Group.builder().build());
    }

    private User getUser() {
        return userJpaRepository.save(User.builder().personalities(List.of(Personality.KIND)).build());
    }

    private Praise getPraise(User user) {
        Praise praise = Praise.empty();
        praise.init(user);
        return praiseJpaRepository.save(praise);
    }

    private Reliability getReliability(User user) {
        Reliability reliability = new Reliability(user);
        return reliabilityRepository.save(reliability);
    }

    private UserGroup getUserGroup(User user, Group group) {
        UserGroup userGroup = UserGroup.empty();
        userGroup.init(user, group);
        return userGroupJpaRepository.save(userGroup);
    }


    @Test
    void find_test_success() {

        Group group = getGroup();

        User user_1 = getUser();
        Praise praise_1 = getPraise(user_1);
        getReliability(user_1);
        User user_2 = getUser();
        Praise praise_2 = getPraise(user_2);
        getReliability(user_2);

        UserGroup userGroup_1 = getUserGroup(user_1, group);
        UserGroup userGroup_2 = getUserGroup(user_2, group);

        em.flush();
        em.clear();

        List<UserGroup> findUserGroups = userGroupJpaRepository.findUserGroupsToPraiseByUserIdInAndGroupId(
                List.of(user_1.getId(), user_2.getId()), group.getId()
        );

        assertThat(findUserGroups.get(0).getId()).isNotNull();
        assertThat(findUserGroups.get(0).getGroup().getId()).isEqualTo(group.getId());
        assertThat(findUserGroups.size()).isEqualTo(2);
    }
}
