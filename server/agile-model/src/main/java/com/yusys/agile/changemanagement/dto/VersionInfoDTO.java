package com.yusys.agile.changemanagement.dto;

import java.util.Date;
import java.util.List;

/**
 * 版本信息DTO
 *
 *     
 * @create 2020-12-01 14:26
 */
public class VersionInfoDTO {

    // 主键
    private Long id;
    // 版本名
    private String versionName;
    // 计划部署日期
    private Date planDeployDate;
    // 版本描述
    private String versionDescribe;
    // 部署类型 1:常规部署, 2:热部署,3:其他
    private Byte deployType;

    private String state;


    // 关联的epic信息
    private List<EpicInfoDTO> epicList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Date getPlanDeployDate() {
        return planDeployDate;
    }

    public void setPlanDeployDate(Date planDeployDate) {
        this.planDeployDate = planDeployDate;
    }

    public String getVersionDescribe() {
        return versionDescribe;
    }

    public void setVersionDescribe(String versionDescribe) {
        this.versionDescribe = versionDescribe;
    }

    public Byte getDeployType() {
        return deployType;
    }

    public void setDeployType(Byte deployType) {
        this.deployType = deployType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<EpicInfoDTO> getEpicList() {
        return epicList;
    }

    public void setEpicList(List<EpicInfoDTO> epicList) {
        this.epicList = epicList;
    }

    @Override
    public String toString() {
        return "VersionInfoDTO{" +
                "id=" + id +
                ", versionName='" + versionName + '\'' +
                ", planDeployDate=" + planDeployDate +
                ", versionDescribe='" + versionDescribe + '\'' +
                ", deployType=" + deployType +
                ", state='" + state + '\'' +
                ", epicList=" + epicList +
                '}';
    }
}