package com.gloddy.server.common.apply;

import com.gloddy.server.apply.entity.Apply;
import com.gloddy.server.apply.repository.ApplyJpaRepository;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.common.BaseApiTest;
import com.gloddy.server.estimate.repository.AbsenceInGroupJpaRepository;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.repository.GroupJpaRepository;
import com.gloddy.server.group.repository.UserGroupJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ApplyApiTest extends BaseApiTest {

    @Autowired
    protected GroupJpaRepository groupJpaRepository;

    @Autowired
    protected ApplyJpaRepository applyJpaRepository;

    @Autowired
    protected UserGroupJpaRepository userGroupJpaRepository;

    @Autowired
    protected AbsenceInGroupJpaRepository absenceInGroupJpaRepository;

    protected Group createMyGroup() {
        Group group = Group.builder().user(user).build();
        return groupJpaRepository.save(group);
    }

    protected Group createGroup(User user) {
        Group group = Group.builder().user(user).build();
        return groupJpaRepository.save(group);
    }

    protected Apply createApply(User applyUser, Group applyGroup) {
        Apply apply = Apply.builder().user(applyUser).group(applyGroup).build();
        return applyJpaRepository.save(apply);
    }
}
