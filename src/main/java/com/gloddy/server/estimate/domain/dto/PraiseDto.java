package com.gloddy.server.estimate.domain.dto;

import com.gloddy.server.praise.domain.vo.PraiseValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PraiseDto {

    private Long userId;
    private PraiseValue praiseValue;
}
