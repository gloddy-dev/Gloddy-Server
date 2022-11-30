package com.gloddy.server.estimate.entity.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Mate {

    @Column(name = "mate_id")
    private Long mateId;

    @Column(name = "selection_reason", columnDefinition = "longtext")
    private String selectionReason;
}
