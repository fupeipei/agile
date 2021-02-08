package com.yusys.agile.review.domain;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {
    private Long reviewId;

    private Long issueId;

    private Byte issueType;

    private String reviewTheme;

    private Date expectTime;

    private String reviewStatus;

    private String version;

    private String state;

    private Long projectId;

    private Date createTime;

    private Long createUid;

    private Date updateTime;

    private Long updateUid;

    private String tenantCode;

    private String reviewDesc;

    private static final long serialVersionUID = 1L;

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Byte getIssueType() {
        return issueType;
    }

    public void setIssueType(Byte issueType) {
        this.issueType = issueType;
    }

    public String getReviewTheme() {
        return reviewTheme;
    }

    public void setReviewTheme(String reviewTheme) {
        this.reviewTheme = reviewTheme == null ? null : reviewTheme.trim();
    }

    public Date getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(Date expectTime) {
        this.expectTime = expectTime;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus == null ? null : reviewStatus.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc == null ? null : reviewDesc.trim();
    }
}