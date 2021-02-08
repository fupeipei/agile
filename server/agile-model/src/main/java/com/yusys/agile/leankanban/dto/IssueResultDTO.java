package com.yusys.agile.leankanban.dto;

import com.yusys.agile.issue.dto.IssueDTO;

import java.util.List;

/**
 *   : zhaoyd6
 * @Date: 2020/5/19
 * @Description: 组装精益看板
 *
 */
public class IssueResultDTO {
    private Long stageId;
    private Byte stageType;
    private List<IssueDTO> issueDTOList;
    private Boolean hasNext;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public Byte getStageType() {
        return stageType;
    }

    public void setStageType(Byte stageType) {
        this.stageType = stageType;
    }

    public List<IssueDTO> getIssueDTOList() {
        return issueDTOList;
    }

    public void setIssueDTOList(List<IssueDTO> issueDTOList) {
        this.issueDTOList = issueDTOList;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }
}
