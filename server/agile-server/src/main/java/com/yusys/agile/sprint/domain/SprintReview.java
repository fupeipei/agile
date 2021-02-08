package com.yusys.agile.sprint.domain;

import java.io.Serializable;
import java.util.Date;

public class SprintReview implements Serializable {
    private Long reviewId;

    private String reviewDesc;

    private Long reviewType;

    private Long sprintId;

    private Long proposeUid;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String tenantCode;

    private static final long serialVersionUID = 1L;

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc == null ? null : reviewDesc.trim();
    }

    public Long getReviewType() {
        return reviewType;
    }

    public void setReviewType(Long reviewType) {
        this.reviewType = reviewType;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Long getProposeUid() {
        return proposeUid;
    }

    public void setProposeUid(Long proposeUid) {
        this.proposeUid = proposeUid;
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
}