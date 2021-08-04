package com.yusys.agile.projectmanager.dto;

import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class ProjectManagerDto implements Serializable {


    private Long projectId;

    private String projectName;

    private String projectCode;

    private Long principal;

    private Double projectProgress;

    private String principalUserName;

    private String principalUserAccount;

    private Date startTime;

    private Date endTime;

    private Long projectStatusId;

    private Long projectTypeId;

    private Long researchModelId;

    private String projectContent;

    private String name;

    private List<Long> userIds;

    private List<Long> productIds;

    private List<Long> systemIds;

    private List<SsoUserDTO> userList;


}
