package com.yusys.agile.versionmanager.dto;

import com.yusys.agile.issue.dto.IssueDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
@Data
public class BusinessReqBindingDTO implements Serializable {
    private static final long serialVersionUID = -4091465047459960274L;

    private Long requirementId; // 版本计划关联的需求ID

    private ArrayList<IssueDTO> systemBranchBindingDTOList;


}
