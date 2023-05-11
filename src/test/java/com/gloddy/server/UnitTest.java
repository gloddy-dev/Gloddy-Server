package com.gloddy.server;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
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
