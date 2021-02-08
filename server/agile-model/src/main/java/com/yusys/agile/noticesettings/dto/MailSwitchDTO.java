package com.yusys.agile.noticesettings.dto;

import java.io.Serializable;

public class MailSwitchDTO implements Serializable {
    private Long mailId;

    private Long userId;

    private Long projectId;

    private Byte type;

    private Byte mailType;

    private Byte mainSwitch;

    private String mainSwitchName;

    private Byte subSwitch;

    private String subSwitchName;
    private String tenantCode;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }



    private static final long serialVersionUID = 1L;

    public Long getMailId() {
        return mailId;
    }

    public void setMailId(Long mailId) {
        this.mailId = mailId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getMailType() {
        return mailType;
    }

    public void setMailType(Byte mailType) {
        this.mailType = mailType;
    }

    public Byte getMainSwitch() {
        return mainSwitch;
    }

    public void setMainSwitch(Byte mainSwitch) {
        this.mainSwitch = mainSwitch;
    }

    public String getMainSwitchName() {
        return mainSwitchName;
    }

    public void setMainSwitchName(String mainSwitchName) {
        this.mainSwitchName = mainSwitchName == null ? null : mainSwitchName.trim();
    }

    public Byte getSubSwitch() {
        return subSwitch;
    }

    public void setSubSwitch(Byte subSwitch) {
        this.subSwitch = subSwitch;
    }

    public String getSubSwitchName() {
        return subSwitchName;
    }

    public void setSubSwitchName(String subSwitchName) {
        this.subSwitchName = subSwitchName == null ? null : subSwitchName.trim();
    }
}