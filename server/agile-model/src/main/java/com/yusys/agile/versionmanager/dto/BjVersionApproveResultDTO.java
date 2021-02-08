package com.yusys.agile.versionmanager.dto;

public class BjVersionApproveResultDTO {
    //主键
    private Long id;
    // 版本计划主键
    private Long versionPlanId;
    // 局方需求编号
    private String partaReqnum;
    // 客户需求编号
    private String bizNum;
    // 计划部署时间
    private String planDeployDate;
    // 交维类型
    private String intersectionType;
    // 审批状态
    private Integer approveStatus;
    // 审批意见
    private String suggestions;
    // 客户需求名称
    private String bizName;
    // 局方审批时间
    private String reviewEndTime;
    // 有效状态：1表示有效，0表示无效
    private Byte validStatus;
    // 第几页
    private Integer page;
    // 每页显示几条
    private Integer pageSize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersionPlanId() {
        return versionPlanId;
    }

    public void setVersionPlanId(Long versionPlanId) {
        this.versionPlanId = versionPlanId;
    }

    public String getPartaReqnum() {
        return partaReqnum;
    }

    public void setPartaReqnum(String partaReqnum) {
        this.partaReqnum = partaReqnum == null ? null : partaReqnum.trim();
    }

    public String getBizNum() {
        return bizNum;
    }

    public void setBizNum(String bizNum) {
        this.bizNum = bizNum == null ? null : bizNum.trim();
    }

    public String getPlanDeployDate() {
        return planDeployDate;
    }

    public void setPlanDeployDate(String planDeployDate) {
        this.planDeployDate = planDeployDate == null ? null : planDeployDate.trim();
    }

    public String getIntersectionType() {
        return intersectionType;
    }

    public void setIntersectionType(String intersectionType) {
        this.intersectionType = intersectionType == null ? null : intersectionType.trim();
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions == null ? null : suggestions.trim();
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getReviewEndTime() {
        return reviewEndTime;
    }

    public void setReviewEndTime(String reviewEndTime) {
        this.reviewEndTime = reviewEndTime;
    }

    public Byte getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Byte validStatus) {
        this.validStatus = validStatus;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}