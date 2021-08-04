package com.yusys.agile.projectmanager.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class ProjectManagerDto implements Serializable {

    private String projectName;

    private String projectCode;

    private Long principal;

    private Date startTime;

    private Date endTime;

    private Long projectStatusId;

    private Long projectTypeId;

    private Long researchModelId;

    private String projectContent;

    private List<Long> userIds;

    private List<Long> productIds;

    private List<Long> systemIds;


}
