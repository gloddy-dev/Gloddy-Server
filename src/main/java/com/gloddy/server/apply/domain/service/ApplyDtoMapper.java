package com.gloddy.server.apply.domain.service;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.dto.ApplyResponse;
import com.gloddy.server.auth.domain.User;

import java.util.List;

public class ApplyDtoMapper {

    public static ApplyResponse.GetAll mapToGetAll(List<Apply> applies) {

        List<ApplyResponse.GetOne> getOnes = applies.stream()
                .map(ApplyDtoMapper::mapToGetOne)
                .toList();

        return new ApplyResponse.GetAll(getOnes.size(), getOnes);
    }

    private static ApplyResponse.GetOne mapToGetOne(Apply apply) {
        User applyUser = apply.getUser();
        return new ApplyResponse.GetOne(
                applyUser.getId(),
                applyUser.getNickName(),
                applyUser.getImageUrl(),
                applyUser.getReliabilityLevel(),
                apply.getContent(),
                apply.getReason()
        );
    }

}
