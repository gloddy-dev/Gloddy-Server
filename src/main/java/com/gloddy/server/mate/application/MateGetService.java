package com.gloddy.server.mate.application;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Profile;
import com.gloddy.server.mate.domain.Mate;
import com.gloddy.server.mate.infra.repository.MateJpaRepository;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.gloddy.server.mate.domain.dto.MateResponse.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MateGetService {
    private final MateJpaRepository mateJpaRepository;
    private final UserQueryHandler userQueryHandler;

    public getMatesForUser getMateForUser(Long userId) {
        List<getMateForUser> mates = mateJpaRepository.findAllByUserId(userId)
                .stream()
                .map(this::toGetMateForUserDto)
                .collect(Collectors.toList());

        return new getMatesForUser(mates);
    }

    private getMateForUser toGetMateForUserDto(Mate mate) {
        User mateUser = userQueryHandler.findById(mate.getMateId());
        Profile mateUserProfile = mateUser.getProfile();
        return new getMateForUser(
                mateUser.getId(),
                mateUserProfile.getImageUrl(),
                mateUserProfile.getNickname(),
                mateUser.getSchool(),
                mate.getCreatedAt(),
                mate.getSelectionReason()
        );
    }
}
