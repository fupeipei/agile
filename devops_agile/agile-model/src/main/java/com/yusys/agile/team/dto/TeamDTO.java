package com.yusys.agile.team.dto;

import com.yusys.agile.sprint.dto.UserSprintHourDTO;

import java.util.Date;
import java.util.List;

/**
 * 团队DTO
 *

 * @create 2020-04-10 17:25
 */
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

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamDesc() {
        return teamDesc;
    }

    public void setTeamDesc(String teamDesc) {
        this.teamDesc = teamDesc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public List<UserSprintHourDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserSprintHourDTO> users) {
        this.users = users;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}
