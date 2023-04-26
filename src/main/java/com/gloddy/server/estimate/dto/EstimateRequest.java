package com.gloddy.server.estimate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstimateRequest {

    private List<PraiseDto> praiseDtos;
    private MateDto mateDto;

    public List<Long> getAllBePraisedUserIds() {
        return praiseDtos.stream()
            .map(PraiseDto::getUserId)
            .collect(Collectors.toUnmodifiableList());
    }
}
