package com.yusys.agile.fault.dto;

import java.util.List;

/**
 * 同步itc模块需求对象
 *
 *
 * @create 2020-10-12 15:27
 */
public class SyncModuleDTO {

    private Long externalStoryId;

    /**
     * 模块的id
     */
    private Long externalModuleId;

    /**
     * 模块的id集合
     */
    private List<Long> externalModuleIds;

    /**
     * 模块名称
     */
    private String externalModuleName;

    /**
     * 处理人id
     */
    private Long externalHandlerId;

    /**
     * 局方需求编号
     */
    private String requirementOriginalCode;
    /**
     * 项目id
     */
    private Long externalProjectId;
    /**
     * feature id
     */
    private Long externalRequirementId;
    /**
     * feature名
     */
    private String externalRequirementName;
    /**
     * epic id
     */
    private Long parentExternalRequirementId;
    /**
     * epic 名
     */
    private String parentExternalRequirementName;
    /**
     * YuDO2
     */
    private String systemCode;
    /**
     * 操作类型0:新增，1：修改，2:删除
     */
    private Integer operateType;

    private  String bossRequirementCode;

    public Long getExternalModuleId() {
        return externalModuleId;
    }

    public void setExternalModuleId(Long externalModuleId) {
        this.externalModuleId = externalModuleId;
    }

    public String getExternalModuleName() {
        return externalModuleName;
    }

    public void setExternalModuleName(String externalModuleName) {
        this.externalModuleName = externalModuleName;
    }

    public Long getExternalHandlerId() {
        return externalHandlerId;
    }

    public void setExternalHandlerId(Long externalHandlerId) {
        this.externalHandlerId = externalHandlerId;
    }

    public String getRequirementOriginalCode() {
        return requirementOriginalCode;
    }

    public void setRequirementOriginalCode(String requirementOriginalCode) {
        this.requirementOriginalCode = requirementOriginalCode;
    }

    public Long getExternalProjectId() {
        return externalProjectId;
    }

    public void setExternalProjectId(Long externalProjectId) {
        this.externalProjectId = externalProjectId;
    }

    public Long getExternalRequirementId() {
        return externalRequirementId;
    }

    public void setExternalRequirementId(Long externalRequirementId) {
        this.externalRequirementId = externalRequirementId;
    }

    public String getExternalRequirementName() {
        return externalRequirementName;
    }

    public void setExternalRequirementName(String externalRequirementName) {
        this.externalRequirementName = externalRequirementName;
    }

    public Long getParentExternalRequirementId() {
        return parentExternalRequirementId;
    }

    public void setParentExternalRequirementId(Long parentExternalRequirementId) {
        this.parentExternalRequirementId = parentExternalRequirementId;
    }

    public String getParentExternalRequirementName() {
        return parentExternalRequirementName;
    }

    public void setParentExternalRequirementName(String parentExternalRequirementName) {
        this.parentExternalRequirementName = parentExternalRequirementName;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Long getExternalStoryId() {
        return externalStoryId;
    }

    public void setExternalStoryId(Long externalStoryId) {
        this.externalStoryId = externalStoryId;
    }

    public List<Long> getExternalModuleIds() {
        return externalModuleIds;
    }

    public void setExternalModuleIds(List<Long> externalModuleIds) {
        this.externalModuleIds = externalModuleIds;
    }

    public String getBossRequirementCode() {
        return bossRequirementCode;
    }

    public void setBossRequirementCode(String bossRequirementCode) {
        this.bossRequirementCode = bossRequirementCode;
    }

    @Override
    public String toString() {
        return "SyncModuleDTO{" +
                "externalStoryId=" + externalStoryId +
                ", externalModuleId=" + externalModuleId +
                ", externalModuleIds=" + externalModuleIds +
                ", externalModuleName='" + externalModuleName + '\'' +
                ", externalHandlerId=" + externalHandlerId +
                ", requirementOriginalCode='" + requirementOriginalCode + '\'' +
                ", externalProjectId=" + externalProjectId +
                ", externalRequirementId=" + externalRequirementId +
                ", externalRequirementName='" + externalRequirementName + '\'' +
                ", parentExternalRequirementId=" + parentExternalRequirementId +
                ", parentExternalRequirementName='" + parentExternalRequirementName + '\'' +
                ", systemCode='" + systemCode + '\'' +
                ", operateType=" + operateType +
                ", bossRequirementCode='" + bossRequirementCode + '\'' +
                '}';
    }
}