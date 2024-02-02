package com.gloddy.server.outbox.adapter.user.mapper;

import com.gloddy.server.outbox.adapter.user.event.UserAdapterEvent;
import com.gloddy.server.outbox.adapter.user.event.UserEventType;
import com.gloddy.server.user.event.UserCreateEvent;
import com.gloddy.server.user.event.UserProfileUpdateEvent;
import com.gloddy.server.user.event.UserReliabilityUpgradeEvent;

import java.time.LocalDateTime;

public class UserEventMapper {

    public static UserAdapterEvent toAdapterEvent(UserCreateEvent userCreateEvent) {
        return new UserAdapterEvent(
                userCreateEvent.getUser().getId(),
                UserEventType.JOIN,
                LocalDateTime.now()
        );
    }

    public static UserAdapterEvent toAdapterEvent(UserProfileUpdateEvent userProfileUpdateEvent) {
        return new UserAdapterEvent(
                userProfileUpdateEvent.getUserId(),
                UserEventType.UPDATE_PROFILE,
                LocalDateTime.now()
        );
    }

    public static UserAdapterEvent toAdapterEvent(UserReliabilityUpgradeEvent userReliabilityUpgradeEvent) {
        return new UserAdapterEvent(
                userReliabilityUpgradeEvent.getUserId(),
                UserEventType.UPGRADE_RELIABILITY,
                LocalDateTime.now()
        );
    }
}
