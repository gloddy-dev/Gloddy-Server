package com.gloddy.server.auth.service;

import com.gloddy.server.auth.dto.UserRequest;
import com.gloddy.server.auth.dto.UserResponse;
import com.gloddy.server.auth.entity.Mate;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.entity.score.Score;
import com.gloddy.server.auth.handler.UserHandler;
import com.gloddy.server.auth.handler.UserHandlerImpl;
import com.gloddy.server.auth.repository.MateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MateJpaRepository mateJpaRepository;
    private final UserHandlerImpl userHandlerImpl;

    public UserResponse.CrateMate create(UserRequest.CreateMate request) {
        User user = userHandlerImpl.findById(request.getUserId());
        user.updateScore(Score.BEST_MATE);
        Mate mate = mateJpaRepository.save(Mate.builder()
                 .user(user)
                 .content(request.getContent())
                 .build()
        );
        return new UserResponse.CrateMate(mate.getId());
    }
}
