package com.yusys.agile.set.stage.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 看板模板类
 * @date 2020/08/24
 */
public class KanbanTemplate implements Serializable {

    private static final long serialVersionUID = -4672610040004328022L;

    private Long templateId;

    private String templateName;

    private String templateDesc;

    private Byte templateType;

    private Byte defaultTemplate;

    private String state;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String tenantCode;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc == null ? null : templateDesc.trim();
    }

    public Byte getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Byte templateType) {
        this.templateType = templateType;
    }

    public Byte getDefaultTemplate() {
        return defaultTemplate;
    }

    public void setDefaultTemplate(Byte defaultTemplate) {
        this.defaultTemplate = defaultTemplate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Long updateUid) {
        this.updateUid = updateUid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    @Override
    public String toString() {
        return "KanbanTemplate{" +
                "templateId=" + templateId +
                ", templateName='" + templateName + '\'' +
                ", templateDesc='" + templateDesc + '\'' +
                ", templateType=" + templateType +
                ", defaultTemplate=" + defaultTemplate +
                ", state='" + state + '\'' +
                ", createUid=" + createUid +
                ", createTime=" + createTime +
                ", updateUid=" + updateUid +
                ", updateTime=" + updateTime +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}