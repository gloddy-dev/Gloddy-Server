package com.gloddy.server.group.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor
public class GroupPlace {

    @Column(name = "place_name")
    public String name;

    @Column(name = "place_latitude")
    public BigDecimal latitude;

    @Column(name = "place_longitude")
    public BigDecimal longitude;

    public GroupPlace(String name, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
