package com.yusys.agile.teamV3.dto;

import com.yusys.agile.sprintV3.dto.SprintV3UserHourDTO;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TeamV3DTO
 * @Description: TODO
 * @Author: libinbin
 * @Date 2021/6/1
 **/
@Data
public class TeamV3DTO {
    private Long teamId;

    private String teamName;

    private String teamDesc;

    private String teamType;

    private String state;

    private Long projectId;

    private Date createTime;

    private Long createUid;

    private List<SprintV3UserHourDTO> users;

    private List<SsoSystemRestDTO> teamSystems;

    private String tenantCode;
}
