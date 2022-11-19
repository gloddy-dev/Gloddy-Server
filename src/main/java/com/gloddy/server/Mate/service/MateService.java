package com.gloddy.server.Mate.service;

import com.gloddy.server.Mate.dto.UserRequest;
import com.gloddy.server.Mate.dto.UserResponse;
import com.gloddy.server.Mate.entity.Mate;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.entity.score.Score;
import com.gloddy.server.auth.handler.UserHandlerImpl;
import com.gloddy.server.Mate.repository.MateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MateService {
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
