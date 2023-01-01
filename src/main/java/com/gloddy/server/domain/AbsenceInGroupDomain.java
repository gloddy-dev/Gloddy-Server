package com.gloddy.server.domain;

import com.gloddy.server.estimate.entity.AbsenceInGroup;

public class AbsenceInGroupDomain {
    private AbsenceInGroup absenceInGroup;
    private Long totalGroupCount;

    public AbsenceInGroupDomain(AbsenceInGroup absenceInGroup, Long totalGroupCount) {
        this.absenceInGroup = absenceInGroup;
        this.totalGroupCount = totalGroupCount;
    }

    public void plusAbsenceCount() {
        this.absenceInGroup.plusAbsenceCount();
    }

    public boolean checkAlreadyAbsence() {
        return this.absenceInGroup.getAbsence();
    }

    public boolean checkAbsenceCountOver() {
        return this.absenceInGroup.getAbsenceCount() > (this.totalGroupCount / 2);
    }

    public void absence() {
        this.absenceInGroup.absence();
    }
}
