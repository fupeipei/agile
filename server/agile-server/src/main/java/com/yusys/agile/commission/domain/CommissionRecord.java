package com.yusys.agile.commission.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 代办记录类
 * @date 2021/2/1
 */
public class CommissionRecord implements Serializable {

    private static final long serialVersionUID = -2076546806454422444L;

    /**
     * 代办记录主键
     */
    private Long id;

    /**
     * 代办主键
     */
    private Long commissonId;

    /**
     * 代办标题
     */
    private String title;

    /**
     * 代办类型
     */
    private Byte type;

    /**
     * 代办人
     */
    private Long handler;

    /**
     * 工作项类型
     */
    private Long issueId;

    /**
     * 项目编号
     */
    private Long projectId;

    /**
     * 阶段编号
     */
    private Long stageId;

    /**
     * 泳道编号
     */
    private Long laneId;

    /**
     * 数据状态
     */
    private String state;

    /**
     * 创建人
     */
    private Long createUid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateUid;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 租户编码
     */
    private String tenantCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommissonId() {
        return commissonId;
    }

    public void setCommissonId(Long commissonId) {
        this.commissonId = commissonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getHandler() {
        return handler;
    }

    public void setHandler(Long handler) {
        this.handler = handler;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public Long getLaneId() {
        return laneId;
    }

    public void setLaneId(Long laneId) {
        this.laneId = laneId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Long updateUid) {
        this.updateUid = updateUid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    @Override
    public String toString() {
        return "CommissionRecord{" +
                "id=" + id +
                ", commissonId=" + commissonId +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", handler=" + handler +
                ", issueId=" + issueId +
                ", projectId=" + projectId +
                ", stageId=" + stageId +
                ", laneId=" + laneId +
                ", state='" + state + '\'' +
                ", createUid=" + createUid +
                ", createTime=" + createTime +
                ", updateUid=" + updateUid +
                ", updateTime=" + updateTime +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}