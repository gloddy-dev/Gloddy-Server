package com.gloddy.server.service.users;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.common.users.UserServiceTest;
import com.gloddy.server.user.domain.dto.UserResponse;
import com.gloddy.server.user.application.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class ExistNicknameTest extends UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void existNickname_returns_true() {
        //given
        Long userId = createUser();
        User user = userJpaRepository.findById(userId).orElseThrow();
        String nickName = user.getNickName();

        //when
        UserResponse.ExistNickname existNickname = userService.existNickname(nickName);

        //then
        assertTrue(existNickname.getIsExistNickname());
    }

    @Test
    void existNickname_returns_false() {
        //when
        UserResponse.ExistNickname existNickname = userService.existNickname("test");

        //then
        assertFalse(existNickname.getIsExistNickname());
    }
}
