package com.yusys.agile.fault.dto;

/**
 * 迭代同步itc数据DTO
 *
 * @create 2020-04-28 11:11
 */
public class SprintSyncDTO {
    /**
     * 迭代id
     */
    private Long externalId;
    /**
     * 项目id
     */
    private Long externalProjectId;
    /**
     * 迭代名称
     */
    private String versionName;

    /**
     * 开始时间
     */
    private String planStartDate;
    /**
     * 结束时间
     */
    private String planEndDate;

    /**
     * 完成时间
     */
    private String planReleaseDate;


    /**
     * 迭代版本号
     */
    private String versionCode;

    /**
     * 操作
     */
    private Integer operateType;

    /**
     * 需求类型 version、epic feature story
     */
    private String sourceType;

    private String systemCode;

    private String planDeployDate;


    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public Long getExternalProjectId() {
        return externalProjectId;
    }

    public void setExternalProjectId(Long externalProjectId) {
        this.externalProjectId = externalProjectId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getPlanReleaseDate() {
        return planReleaseDate;
    }

    public void setPlanReleaseDate(String planReleaseDate) {
        this.planReleaseDate = planReleaseDate;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getPlanDeployDate() {
        return planDeployDate;
    }

    public void setPlanDeployDate(String planDeployDate) {
        this.planDeployDate = planDeployDate;
    }
}