package com.gloddy.server.estimate.dto;

import com.gloddy.server.estimate.entity.embedded.PraiseValue;
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
