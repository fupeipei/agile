package com.yusys.agile.changemanagement.dto;

/**
 * 变更请求DTO
 *
 * @create 2020-11-30 10:01
 */
public class ChangeManagementRequestDTO {

    /**
     * 请求系统code
     */
    private String systemCode;

    /**
     * 请求开始时间
     */
    private String createStartDate;

    /**
     * 请求结束时间
     */
    private String createEndDate;

    /**
     * 修改开始时间
     */
    private String updateStartTime;

    /**
     * 修改结束时间
     */
    private String updateEndTime;

    /**
     * 版本id
     */
    private Long versionId;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getCreateStartDate() {
        return createStartDate;
    }

    public void setCreateStartDate(String createStartDate) {
        this.createStartDate = createStartDate;
    }

    public String getCreateEndDate() {
        return createEndDate;
    }

    public void setCreateEndDate(String createEndDate) {
        this.createEndDate = createEndDate;
    }

    public String getUpdateStartTime() {
        return updateStartTime;
    }

    public void setUpdateStartTime(String updateStartTime) {
        this.updateStartTime = updateStartTime;
    }

    public String getUpdateEndTime() {
        return updateEndTime;
    }

    public void setUpdateEndTime(String updateEndTime) {
        this.updateEndTime = updateEndTime;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    @Override
    public String toString() {
        return "ChangeManagementRequestDTO{" +
                "systemCode='" + systemCode + '\'' +
                ", createStartDate='" + createStartDate + '\'' +
                ", createEndDate='" + createEndDate + '\'' +
                ", updateStartTime='" + updateStartTime + '\'' +
                ", updateEndTime='" + updateEndTime + '\'' +
                ", versionId=" + versionId +
                '}';
    }
}