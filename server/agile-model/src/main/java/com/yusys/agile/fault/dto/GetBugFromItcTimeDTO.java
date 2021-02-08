package com.yusys.agile.fault.dto;

/**
 * 从itc获取创建或重新打开bug的参数DTO
 *
 *     
 * @create 2020-10-20 09:50
 */
public class GetBugFromItcTimeDTO {

    private String createStartDate;

    private String createEndDate;

    private String updateStartDate;

    private String updateEndDate;


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

    public String getUpdateStartDate() {
        return updateStartDate;
    }

    public void setUpdateStartDate(String updateStartDate) {
        this.updateStartDate = updateStartDate;
    }

    public String getUpdateEndDate() {
        return updateEndDate;
    }

    public void setUpdateEndDate(String updateEndDate) {
        this.updateEndDate = updateEndDate;
    }

    @Override
    public String toString() {
        return "GetBugFromItcTimeDTO{" +
                "createStartDate='" + createStartDate + '\'' +
                ", createEndDate='" + createEndDate + '\'' +
                ", updateStartDate='" + updateStartDate + '\'' +
                ", updateEndDate='" + updateEndDate + '\'' +
                '}';
    }
}