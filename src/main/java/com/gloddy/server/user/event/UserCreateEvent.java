package com.gloddy.server.user.event;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCreateEvent implements Event {
    private User user;
}
