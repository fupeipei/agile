package com.yusys.agile.businesskanban.dto;

import java.util.Date;

/**
 * @Date: 2020/4/30
 * @Description:
 */
public class BusinessKanbanMembersDTO {
    private Long id;

    private Long kanbanId;

    private Long userId;

    private String userName;

    private Date createTime;

    private String tenantCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(Long kanbanId) {
        this.kanbanId = kanbanId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return "BusinessKanbanMembersDTO{" +
                "id=" + id +
                ", kanbanId=" + kanbanId +
                ", userId=" + userId +
                ", createTime=" + createTime +
                '}';
    }
}