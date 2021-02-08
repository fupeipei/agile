package com.yusys.agile.commission.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 代办类
 * @date 2020/2/1
 */
public class Commission implements Serializable {

    private static final long serialVersionUID = 1017018754926524571L;

    /**
     * 代办主键
     */
    private Long id;

    /**
     * 工作项编号
     */
    private Long issueId;

    /**
     * 项目编号
     */
    private Long projectId;

    /**
     * 代办标题
     */
    private String title;

    /**
     * 代办类型
     */
    private Byte type;

    /**
     * 当前代办人
     */
    private Long currentHandler;

    /**
     * 阶段编号
     */
    private Long stageId;

    /**
     * 泳道编号
     */
    private Long laneId;

    /**
     * 代办创建人
     */
    private Long createUid;

    /**
     * 代办创建时间
     */
    private Date createTime;

    /**
     * 代办更新人
     */
    private Long updateUid;

    /**
     * 代办更新时间
     */
    private Date updateTime;

    /**
     * 数据状态
     */
    private String state;

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

    public Long getCurrentHandler() {
        return currentHandler;
    }

    public void setCurrentHandler(Long currentHandler) {
        this.currentHandler = currentHandler;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    @Override
    public String toString() {
        return "Commission{" +
                "id=" + id +
                ", issueId=" + issueId +
                ", projectId=" + projectId +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", currentHandler=" + currentHandler +
                ", stageId=" + stageId +
                ", laneId=" + laneId +
                ", createUid=" + createUid +
                ", createTime=" + createTime +
                ", updateUid=" + updateUid +
                ", updateTime=" + updateTime +
                ", state='" + state + '\'' +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}