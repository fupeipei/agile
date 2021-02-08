package com.yusys.agile.versionmanager.dto;

import com.yusys.agile.issue.dto.IssueDTO;

import java.io.Serializable;
import java.util.List;

public class BusinessReqBindingDTO implements Serializable {
    private static final long serialVersionUID = -4091465047459960274L;

    private Long requirementId; // 版本计划关联的需求ID

    private List<IssueDTO> systemBranchBindingDTOList;

    public Long getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

}
