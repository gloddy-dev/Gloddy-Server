package com.gloddy.server.group.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupPlace {

    @Column(name = "place_name")
    private String name;

    @Column(name = "place_address")
    private String address;

    @Column(name = "place_latitude")
    private BigDecimal latitude;

    @Column(name = "place_longitude")
    private BigDecimal longitude;
}
