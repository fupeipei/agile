package com.yusys.agile.milestone.dto;

import com.yusys.agile.fault.dto.UserDTO;

import java.util.Date;
import java.util.List;

/**
 * 里程碑dto
 *

 * @create 2020-08-12 15:45
 */
public class MilestoneDTO {

    private Long milestoneId;

    private String milestoneName;

    private Integer milestoneStatus;

    private String description;

    private String state;

    private Long projectId;

    private Date planFinishTime;

    private Date realFinishTime;

    private Date createTime;

    private Long createUid;

    private Date updateTime;

    private Long updateUid;

    private String tenantCode;

    /**
     * 用户id集合
     */
    private List<Long> userIds;

    /**
     * 负责人
     */
    private List<UserDTO> users;

    /**
     * 是否超时 0未超时 1 超时
     */
    private Integer isTimeout;

    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Long milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public Integer getMilestoneStatus() {
        return milestoneStatus;
    }

    public void setMilestoneStatus(Integer milestoneStatus) {
        this.milestoneStatus = milestoneStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getPlanFinishTime() {
        return planFinishTime;
    }

    public void setPlanFinishTime(Date planFinishTime) {
        this.planFinishTime = planFinishTime;
    }

    public Date getRealFinishTime() {
        return realFinishTime;
    }

    public void setRealFinishTime(Date realFinishTime) {
        this.realFinishTime = realFinishTime;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Long updateUid) {
        this.updateUid = updateUid;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public Integer getIsTimeout() {
        return isTimeout;
    }

    public void setIsTimeout(Integer isTimeout) {
        this.isTimeout = isTimeout;
    }
}