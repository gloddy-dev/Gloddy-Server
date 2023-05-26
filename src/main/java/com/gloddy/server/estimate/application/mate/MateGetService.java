package com.gloddy.server.estimate.application.mate;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.estimate.domain.Mate;
import com.gloddy.server.estimate.infra.repository.MateJpaRepository;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.gloddy.server.estimate.domain.dto.MateResponse.*;

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
        return new getMateForUser(
                mateUser.getImageUrl(),
                mateUser.getName(),
                mateUser.getSchool(),
                mate.getCreatedAt(),
                mate.getSelectionReason()
        );
    }
}
