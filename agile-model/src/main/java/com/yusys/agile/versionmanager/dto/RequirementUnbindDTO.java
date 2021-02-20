package com.yusys.agile.versionmanager.dto;

import java.util.List;

public class RequirementUnbindDTO {
    private Long versionPlanId; // 版本计划主键

    private List<Long> requirementIdList; // 业务需求主键列表, 个数为1

    private String unbindingReason; // 解绑原因

    private String unbindingComments; // 解绑原因说明

    private Long newVersionId; // 需求解绑后，要绑定的新的版本Id

    public Long getVersionPlanId() {
        return versionPlanId;
    }

    public void setVersionPlanId(Long versionPlanId) {
        this.versionPlanId = versionPlanId;
    }

    public List<Long> getRequirementIdList() {
        return requirementIdList;
    }

    public void setRequirementIdList(List<Long> requirementIdList) {
        this.requirementIdList = requirementIdList;
    }

    public String getUnbindingReason() {
        return unbindingReason;
    }

    public void setUnbindingReason(String unbindingReason) {
        this.unbindingReason = unbindingReason;
    }

    public Long getNewVersionId() {
        return newVersionId;
    }

    public void setNewVersionId(Long newVersionId) {
        this.newVersionId = newVersionId;
    }

    public String getUnbindingComments() {
        return unbindingComments;
    }

    public void setUnbindingComments(String unbindingComments) {
        this.unbindingComments = unbindingComments;
    }
}
