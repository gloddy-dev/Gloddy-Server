package com.gloddy.server.user.application;

import com.gloddy.server.group_member.event.GroupMemberReceivePraiseEvent;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.dto.UserUpdateRequest;
import com.gloddy.server.user.domain.dto.UserUpdateResponse;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.user.domain.vo.PraiseValue;
import com.gloddy.server.user.domain.vo.ScoreTypes;
import com.gloddy.server.user.event.producer.UserEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService {
    private final UserQueryHandler userQueryHandler;
    private final UserEventProducer userEventProducer;


    public UserUpdateResponse updateProfile(Long userId, UserUpdateRequest.Info request) {
        User user = userQueryHandler.findByIdFetch(userId);
        user.updateProfile(
                request.getImageUrl(),
                request.getName(),
                request.getBirth(),
                request.getGender(),
                request.getIntroduce(),
                request.getPersonalities(),
                request.getCountryName(),
                request.getCountryImage()
        );
        publishEvent(user);
        return UserUpdateResponse.of(user.getProfile());
    }

    public void praise(Long userId, PraiseValue praiseValue) {
        User user = userQueryHandler.findById(userId);
        user.receivePraise(praiseValue);
        publishEvent(user);
    }

    public void upgradeReliability(Long userId, ScoreTypes scoreType) {
        User user = userQueryHandler.findById(userId);
        user.reflectReliability(scoreType);
        publishEvent(user);
    }

    private void publishEvent(User user) {
        user.getEvents().forEach(userEventProducer::produceEvent);
    }
}
