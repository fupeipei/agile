package com.yusys.agile.versionmanager.dto;

import java.io.Serializable;
import java.util.List;

public class BjVersionPlanBindingReqsDTO implements Serializable {
    private static final long serialVersionUID = 9062929032766798213L;

    private Long versionPlanId;

    List<BusinessReqBindingDTO> reqBindingDTOList;

    public Long getVersionPlanId() {
        return versionPlanId;
    }

    public void setVersionPlanId(Long versionPlanId) {
        this.versionPlanId = versionPlanId;
    }

    public List<BusinessReqBindingDTO> getReqBindingDTOList() {
        return reqBindingDTOList;
    }

    public void setReqBindingDTOList(List<BusinessReqBindingDTO> reqBindingDTOList) {
        this.reqBindingDTOList = reqBindingDTOList;
    }
}
