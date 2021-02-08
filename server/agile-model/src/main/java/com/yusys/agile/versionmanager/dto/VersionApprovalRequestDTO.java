package com.yusys.agile.versionmanager.dto;

import java.util.List;

/**
 * @description:
 *   : HaoXU
 * @date:Create: 2020/8/1
 */
public class VersionApprovalRequestDTO {

    public List<Long> getRequirementIdList() {
        return requirementIdList;
    }

    public void setRequirementIdList(List<Long> requirementIdList) {
        this.requirementIdList = requirementIdList;
    }

    private List<Long> requirementIdList;

}
