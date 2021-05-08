package com.yusys.agile.team.dto;

import com.yusys.agile.sprint.dto.UserSprintHourDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 团队DTO
 *
 * @create 2020-04-10 17:25
 */
@Data
public class TeamDTO {
    private Long teamId;

    private String teamName;

    private String teamDesc;

    private String state;

    private Long projectId;

    private Date createTime;

    private Long createUid;

    private List<UserSprintHourDTO> users;

    private String tenantCode;

}
