package com.gloddy.server.estimate.entity.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PraiseInGroup {

    @Column(name = "calm_count")
    private Integer calmCount;

    @Column(name = "kind_count")
    private Integer kindCount;

    @Column(name = "active_count")
    private Integer activeCount;

    @Column(name = "humor_count")
    private Integer humorCount;

    public Integer calculateTotalPraiseCountInGroup() {
        return this.calmCount + this.kindCount + this.activeCount + this.humorCount;
    }
}
