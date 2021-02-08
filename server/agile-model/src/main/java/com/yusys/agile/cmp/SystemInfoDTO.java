package com.yusys.agile.cmp;

import java.util.Date;
import java.util.List;

/**
 *    沈峰
 */
public class SystemInfoDTO {

    private Long id;
    private String bizCode;
    private Long configId;
    private String systemName;
    private Byte bizType;
    private String centerNameStr;
    private Date createTime;
    private Date updateTime;
    private List<String> centerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }

    public String getCenterNameStr() {
        return centerNameStr;
    }

    public void setCenterNameStr(String centerNameStr) {
        this.centerNameStr = centerNameStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<String> getCenterName() {
        return centerName;
    }

    public void setCenterName(List<String> centerName) {
        this.centerName = centerName;
    }

    @Override
    public String toString() {
        return "SystemInfoDTO{" +
                "id=" + id +
                ", configId=" + configId +
                ", systemName='" + systemName + '\'' +
                ", bizType=" + bizType +
                ", centerNameStr='" + centerNameStr + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", centerName=" + centerName +
                '}';
    }
}
