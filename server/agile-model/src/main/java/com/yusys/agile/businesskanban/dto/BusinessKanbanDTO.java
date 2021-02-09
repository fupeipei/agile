package com.yusys.agile.businesskanban.dto;

import java.util.Date;
import java.util.List;

/**
 * @Date: 2021/2/1
 * @Description:
 */
public class BusinessKanbanDTO {
    private Long kanbanId;

    private String kanbanName;

    private String kanbanDesc;

    private Long projectId;

    private Date createTime;

    private Long createUid;

    private Long status;

    private Date updateTime;

    private Long updateUid;

    private String state;

    private List<Long> userIds;

    private List<BusinessKanbanMembersDTO> membersDTOS;

    private String tenantCode;

    public Long getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(Long kanbanId) {
        this.kanbanId = kanbanId;
    }

    public String getKanbanName() {
        return kanbanName;
    }

    public void setKanbanName(String kanbanName) {
        this.kanbanName = kanbanName == null ? null : kanbanName.trim();
    }

    public String getKanbanDesc() {
        return kanbanDesc;
    }

    public void setKanbanDesc(String kanbanDesc) {
        this.kanbanDesc = kanbanDesc == null ? null : kanbanDesc.trim();
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
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

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public List<BusinessKanbanMembersDTO> getMembersDTOS() {
        return membersDTOS;
    }

    public void setMembersDTOS(List<BusinessKanbanMembersDTO> membersDTOS) {
        this.membersDTOS = membersDTOS;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return "BusinessKanbanDTO{" +
                "kanbanId=" + kanbanId +
                ", kanbanName='" + kanbanName + '\'' +
                ", kanbanDesc='" + kanbanDesc + '\'' +
                ", projectId=" + projectId +
                ", createTime=" + createTime +
                ", createUid=" + createUid +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", updateUid=" + updateUid +
                '}';
    }
}