package com.gloddy.server.user.event;

import com.gloddy.server.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCreateEvent {
    private User user;
}
