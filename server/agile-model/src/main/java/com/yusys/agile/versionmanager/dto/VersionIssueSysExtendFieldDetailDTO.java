package com.yusys.agile.versionmanager.dto;

import java.io.Serializable;

public class VersionIssueSysExtendFieldDetailDTO implements Serializable {

    /**
     * 工作项ID
     */
    private Long issueId;
    /**
     * 父工作项ID
     */
    private Long parentId;
    /**
     * Feature需求业务状态
     */
    private String bizStatus;
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * ba需求负责人
     */
    private String handlerName;
    /**
     * 系统测试负责人
     */
    private String baUserName;

    /**
     * 部署说明
     */
    private String deployIllustration;

    /**
     * 实际完成时间
     */
    private String actualFinishTime;

    /**
     * 联调测试实际完成时间
     */
    private String debugActualFinishTime;

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(String bizStatus) {
        this.bizStatus = bizStatus;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getBaUserName() {
        return baUserName;
    }

    public void setBaUserName(String baUserName) {
        this.baUserName = baUserName;
    }

    public String getDeployIllustration() {
        return deployIllustration;
    }

    public void setDeployIllustration(String deployIllustration) {
        this.deployIllustration = deployIllustration;
    }

    public String getActualFinishTime() {
        return actualFinishTime;
    }

    public void setActualFinishTime(String actualFinishTime) {
        this.actualFinishTime = actualFinishTime;
    }

    public String getDebugActualFinishTime() {
        return debugActualFinishTime;
    }

    public void setDebugActualFinishTime(String debugActualFinishTime) {
        this.debugActualFinishTime = debugActualFinishTime;
    }
}