package com.yusys.agile.fault.dto;

/**
 * 同步故事DTO
 *
 *
 * @create 2020-04-28 14:51
 */
public class StorySyncDTO {
    /**
     * 故事id
     */
    private Long externalRequirementId;
    /**
     * 故事名称
     */
    private String requirementName;

    /**
     * 项目id
     */
    private Long externalProjectId;

    /**
     * 上级id
     */
    private Long parentExternalRequirementId;

    /**
     * 迭代id
     */
    private Long externalVersionId;

    /**
     * 描述
     */
    private String requirementDesc;
    /**
     * 计划工作时长
     */
    private Integer expectWorkload;

    /**
     * 实际工作时长
     */
    private Integer actualWorkload;


    /**
     * 开始时间
     */
    private String beginDate;
    /**
     * 结束时间
     */
    private String expectFinishTime;

    /**
     * 创建id
     */
    private Long externalCreateUserId;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改人
     */
    private Long externalUpdateUserId;
    /**
     * 修改时间
     */
    private String updateTime;

    private String systemCode;

    private String sourceType;

    /**
     * 操作
     */
    private Integer operateType;

    /**
     * 外部需求的feature要带上的epicId
     */
    private Long parentExternalId;

    /**
     * 测试负责人
     */
    private Long externalHandlerId;

    /**
     * feature时要带上的epic名
     */
    private String parentExternalRequirementName;


    public Long getExternalRequirementId() {
        return externalRequirementId;
    }

    public void setExternalRequirementId(Long externalRequirementId) {
        this.externalRequirementId = externalRequirementId;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public Long getExternalProjectId() {
        return externalProjectId;
    }

    public void setExternalProjectId(Long externalProjectId) {
        this.externalProjectId = externalProjectId;
    }

    public Long getParentExternalRequirementId() {
        return parentExternalRequirementId;
    }

    public void setParentExternalRequirementId(Long parentExternalRequirementId) {
        this.parentExternalRequirementId = parentExternalRequirementId;
    }

    public Long getExternalVersionId() {
        return externalVersionId;
    }

    public void setExternalVersionId(Long externalVersionId) {
        this.externalVersionId = externalVersionId;
    }

    public String getRequirementDesc() {
        return requirementDesc;
    }

    public void setRequirementDesc(String requirementDesc) {
        this.requirementDesc = requirementDesc;
    }

    public Integer getExpectWorkload() {
        return expectWorkload;
    }

    public void setExpectWorkload(Integer expectWorkload) {
        this.expectWorkload = expectWorkload;
    }

    public Integer getActualWorkload() {
        return actualWorkload;
    }

    public void setActualWorkload(Integer actualWorkload) {
        this.actualWorkload = actualWorkload;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getExpectFinishTime() {
        return expectFinishTime;
    }

    public void setExpectFinishTime(String expectFinishTime) {
        this.expectFinishTime = expectFinishTime;
    }

    public Long getExternalCreateUserId() {
        return externalCreateUserId;
    }

    public void setExternalCreateUserId(Long externalCreateUserId) {
        this.externalCreateUserId = externalCreateUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getExternalUpdateUserId() {
        return externalUpdateUserId;
    }

    public void setExternalUpdateUserId(Long externalUpdateUserId) {
        this.externalUpdateUserId = externalUpdateUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Long getParentExternalId() {
        return parentExternalId;
    }

    public void setParentExternalId(Long parentExternalId) {
        this.parentExternalId = parentExternalId;
    }

    public Long getExternalHandlerId() {
        return externalHandlerId;
    }

    public void setExternalHandlerId(Long externalHandlerId) {
        this.externalHandlerId = externalHandlerId;
    }

    public String getParentExternalRequirementName() {
        return parentExternalRequirementName;
    }

    public void setParentExternalRequirementName(String parentExternalRequirementName) {
        this.parentExternalRequirementName = parentExternalRequirementName;
    }
}