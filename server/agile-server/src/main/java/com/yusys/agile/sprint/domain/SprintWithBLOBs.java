package com.yusys.agile.sprint.domain;

import java.io.Serializable;

public class SprintWithBLOBs extends Sprint implements Serializable {
    private String sprintDesc;

    private String sprintDays;

    private static final long serialVersionUID = 1L;

    public String getSprintDesc() {
        return sprintDesc;
    }

    public void setSprintDesc(String sprintDesc) {
        this.sprintDesc = sprintDesc == null ? null : sprintDesc.trim();
    }

    public String getSprintDays() {
        return sprintDays;
    }

    public void setSprintDays(String sprintDays) {
        this.sprintDays = sprintDays == null ? null : sprintDays.trim();
    }
}