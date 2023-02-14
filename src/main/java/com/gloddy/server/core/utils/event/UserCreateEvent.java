package com.gloddy.server.core.utils.event;

import com.gloddy.server.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCreateEvent {
    private User user;
}
