package com.gloddy.server;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.entity.UserGroup;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class UnitTest {

    protected Group getMockGroup() {
        return Mockito.mock(Group.class);
    }

    protected User getMockUser() {
        return Mockito.mock(User.class);
    }

}
