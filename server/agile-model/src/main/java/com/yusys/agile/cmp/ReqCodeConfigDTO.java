package com.yusys.agile.cmp;

import java.util.Date;
import java.util.List;

/**
 *    沈峰
 */
public class ReqCodeConfigDTO {
    private Long id;
    private String bizCode;
    private Byte bizType;
    private Date CreateTime;
    private Date updateTime;
    private List<SystemInfoDTO> systemInfo;

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

    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<SystemInfoDTO> getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(List<SystemInfoDTO> systemInfo) {
        this.systemInfo = systemInfo;
    }

    @Override
    public String toString() {
        return "ReqCodeConfigDTO{" +
                "id=" + id +
                ", bizCode='" + bizCode + '\'' +
                ", bizType=" + bizType +
                ", CreateTime=" + CreateTime +
                ", updateTime=" + updateTime +
                ", systemInfo=" + systemInfo +
                '}';
    }
}
