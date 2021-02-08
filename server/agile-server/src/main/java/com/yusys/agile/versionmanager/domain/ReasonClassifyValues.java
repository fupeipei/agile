package com.yusys.agile.versionmanager.domain;

public class ReasonClassifyValues {
    private Integer id;

    private Integer classifyId;

    private Integer changeReasonId;

    private String changeReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public Integer getChangeReasonId() {
        return changeReasonId;
    }

    public void setChangeReasonId(Integer changeReasonId) {
        this.changeReasonId = changeReasonId;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason == null ? null : changeReason.trim();
    }
}