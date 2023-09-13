package com.gloddy.server.common.apply;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.infra.repository.ApplyJpaRepository;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.common.BaseApiTest;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.infra.repository.GroupJpaRepository;
import com.gloddy.server.group_member.infra.repository.GroupMemberJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ApplyApiTest extends BaseApiTest {

    @Autowired
    protected GroupJpaRepository groupJpaRepository;

    @Autowired
    protected ApplyJpaRepository applyJpaRepository;

    @Autowired
    protected GroupMemberJpaRepository groupMemberJpaRepository;

    protected Group createMyGroup() {
        Group group = Group.builder().captain(user).maxUser(10).build();
        return groupJpaRepository.save(group);
    }

    protected Group createGroup(User user) {
        Group group = Group.builder().captain(user).maxUser(10).build();
        return groupJpaRepository.save(group);
    }

    protected Apply createApply(User applyUser, Group applyGroup) {
        Apply apply = Apply.builder().user(applyUser).group(applyGroup).build();
        return applyJpaRepository.save(apply);
    }
}
