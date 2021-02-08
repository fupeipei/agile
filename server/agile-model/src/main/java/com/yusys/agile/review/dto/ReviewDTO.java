package com.yusys.agile.review.dto;

import com.yusys.agile.fault.dto.UserDTO;

import java.util.Date;
import java.util.List;

/**
 * 评审DTO
 *
 *
 * @create 2020-09-08 10:02
 */
public class ReviewDTO {

    private Long reviewId;

    private Long issueId;

    private Byte issueType;

    private String reviewTheme;

    private Date expectTime;
    /**
     * 当前状态：需要每次计算
     */
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

    /**
     * 创建人名
     */
    private String createUserName;
    /**
     * 评审人id
     */
    private List<Long> userIds;

    /**
     * 评审人
     */
    private List<UserDTO> users;

    /**
     * 评审记录
     */
    private List<ReviewRecordDTO> reviewRecordDTOList;

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
        this.reviewTheme = reviewTheme;
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
        this.reviewStatus = reviewStatus;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public List<ReviewRecordDTO> getReviewRecordDTOList() {
        return reviewRecordDTOList;
    }

    public void setReviewRecordDTOList(List<ReviewRecordDTO> reviewRecordDTOList) {
        this.reviewRecordDTOList = reviewRecordDTOList;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}