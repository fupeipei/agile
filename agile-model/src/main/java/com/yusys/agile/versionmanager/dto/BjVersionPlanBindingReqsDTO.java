package com.yusys.agile.versionmanager.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BjVersionPlanBindingReqsDTO implements Serializable {
    private static final long serialVersionUID = 9062929032766798213L;

    private Long versionPlanId;

    private List<BusinessReqBindingDTO> reqBindingDTOList;
}
