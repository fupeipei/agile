package com.yusys.agile.team.dto;

import com.yusys.agile.sprintV3.dto.SprintV3UserHourDTO;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 团队DTO
 *
 * @create 2021-06-01 17:25
 */
@Data
public class TeamDTO {
    private Long teamId;

    private String teamName;

    private String teamDesc;

    private String state;

    private String teamType;

    private Date createTime;

    private Long createUid;

    private List<SprintV3UserHourDTO> users;

    private List<SsoSystemRestDTO> teamSystems;

    private String tenantCode;

}
