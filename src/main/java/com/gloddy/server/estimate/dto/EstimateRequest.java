package com.gloddy.server.estimate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstimateRequest {

    private List<PraiseDto> praiseDtos;
    private MateDto mateDto;
}
