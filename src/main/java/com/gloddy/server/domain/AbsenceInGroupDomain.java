package com.gloddy.server.domain;

import com.gloddy.server.estimate.entity.UserGroupAbsence;

public class AbsenceInGroupDomain {
    private UserGroupAbsence userGroupAbsence;
    private Long totalGroupCount;

    public AbsenceInGroupDomain(UserGroupAbsence userGroupAbsence, Long totalGroupCount) {
        this.userGroupAbsence = userGroupAbsence;
        this.totalGroupCount = totalGroupCount;
    }

    public void plusAbsenceCount() {
        this.userGroupAbsence.plusAbsenceCount();
    }

    public boolean checkAlreadyAbsence() {
        return this.userGroupAbsence.getAbsence();
    }

    public boolean checkAbsenceCountOver() {
        return this.userGroupAbsence.getAbsenceCount() > (this.totalGroupCount / 2);
    }

    public void absence() {
        this.userGroupAbsence.absence();
    }
}
