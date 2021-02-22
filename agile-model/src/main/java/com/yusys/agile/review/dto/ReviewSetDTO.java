package com.yusys.agile.review.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 评审设置类
 * @date 2020/09/08
 */
public class ReviewSetDTO implements Serializable {

    private static final long serialVersionUID = 872310568615456089L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 项目编号
     */
    private Long projectId;

    /**
     * 工作项类型
     */
    private Byte issueType;

    /**
     * 评审开关默认关闭 0:关闭 1:打开
     */
    private Byte reviewSwitch;

    /**
     * 评审通过率默认100
     */
    private Byte reviewPassRate;

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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Byte getIssueType() {
        return issueType;
    }

    public void setIssueType(Byte issueType) {
        this.issueType = issueType;
    }

    public Byte getReviewSwitch() {
        return reviewSwitch;
    }

    public void setReviewSwitch(Byte reviewSwitch) {
        this.reviewSwitch = reviewSwitch;
    }

    public Byte getReviewPassRate() {
        return reviewPassRate;
    }

    public void setReviewPassRate(Byte reviewPassRate) {
        this.reviewPassRate = reviewPassRate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return "ReviewSetDTO{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", issueType=" + issueType +
                ", reviewSwitch=" + reviewSwitch +
                ", reviewPassRate=" + reviewPassRate +
                ", state='" + state + '\'' +
                ", createUid=" + createUid +
                ", createTime=" + createTime +
                ", updateUid=" + updateUid +
                ", updateTime=" + updateTime +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}
