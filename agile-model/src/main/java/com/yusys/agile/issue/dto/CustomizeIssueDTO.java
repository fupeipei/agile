package com.yusys.agile.issue.dto;

import java.io.Serializable;

/**
 * @description 工作项定制对象
 * @date 2020/08/17
 */
public class CustomizeIssueDTO implements Serializable {

    private static final long serialVersionUID = 1829529326422332691L;

    /**
     * 缺陷id
     */
    private Long faultId;

    /**
     * 缺陷名称
     */
    private String faultName;

    /**
     * 缺陷数据状态
     */
    //private String faultState;

    /**
     * 缺陷领取人姓名
     */
    private String faultReceiverName;

    /**
     * 缺陷阶段编号
     */
    private Long faultStageId;

    /**
     * 缺陷阶段名称
     */
    private String faultStageName;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务领取人姓名
     */
    private String taskReceiverName;

    /**
     * 任务领取人账号
     */
    private String taskReceiverAccount;

    /**
     * 任务数据状态
     */
    //private String taskState;

    /**
     * 任务阶段编号
     */
    private Long taskStageId;

    /**
     * 任务阶段名称
     */
    private String taskStageName;

    /**
     * 故事id
     */
    private Long storyId;

    /**
     * 故事名称
     */
    private String storyName;

    /**
     * 故事数据状态
     */
    //private String storyState;

    /**
     * 故事阶段id
     */
    private Long storyStageId;

    /**
     * 故事阶段名称
     */
    private String storyStageName;

    /**
     * 研发需求id
     */
    private Long featureId;

    /**
     * 研发需求名称
     */
    private String featureName;

    /**
     * 研发需求数据状态
     */
    //private String featureState;

    /**
     * 研发需求阶段id
     */
    private Long featureStageId;

    /**
     * 研发需求阶段名称
     */
    private String featureStageName;

    /**
     * 业务需求id
     */
    private Long epicId;

    /**
     * 业务需求名称
     */
    private String epicName;

    /**
     * 业务需求数据状态
     */
    //private String epicState;

    /**
     * 业务状态编号
     */
    private Long epicStageId;

    /**
     * 业务状态名称
     */
    private String epicStageName;

    /**
     * 客户需求编号
     */
    private String bizNum;

    /**
     * 迭代id
     */
    private Long sprintId;

    /**
     * 迭代名称
     */
    private String sprintName;

    /**
     * 迭代状态
     */
    private String sprintState;

    public Long getFaultId() {
        return faultId;
    }

    public void setFaultId(Long faultId) {
        this.faultId = faultId;
    }

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public String getFaultReceiverName() {
        return faultReceiverName;
    }

    public void setFaultReceiverName(String faultReceiverName) {
        this.faultReceiverName = faultReceiverName;
    }

    public Long getFaultStageId() {
        return faultStageId;
    }

    public void setFaultStageId(Long faultStageId) {
        this.faultStageId = faultStageId;
    }

    public String getFaultStageName() {
        return faultStageName;
    }

    public void setFaultStageName(String faultStageName) {
        this.faultStageName = faultStageName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskReceiverName() {
        return taskReceiverName;
    }

    public void setTaskReceiverName(String taskReceiverName) {
        this.taskReceiverName = taskReceiverName;
    }

    public String getTaskReceiverAccount() {
        return taskReceiverAccount;
    }

    public void setTaskReceiverAccount(String taskReceiverAccount) {
        this.taskReceiverAccount = taskReceiverAccount;
    }

    public Long getTaskStageId() {
        return taskStageId;
    }

    public void setTaskStageId(Long taskStageId) {
        this.taskStageId = taskStageId;
    }

    public String getTaskStageName() {
        return taskStageName;
    }

    public void setTaskStageName(String taskStageName) {
        this.taskStageName = taskStageName;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public Long getStoryStageId() {
        return storyStageId;
    }

    public void setStoryStageId(Long storyStageId) {
        this.storyStageId = storyStageId;
    }

    public String getStoryStageName() {
        return storyStageName;
    }

    public void setStoryStageName(String storyStageName) {
        this.storyStageName = storyStageName;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Long getFeatureStageId() {
        return featureStageId;
    }

    public void setFeatureStageId(Long featureStageId) {
        this.featureStageId = featureStageId;
    }

    public String getFeatureStageName() {
        return featureStageName;
    }

    public void setFeatureStageName(String featureStageName) {
        this.featureStageName = featureStageName;
    }

    public Long getEpicId() {
        return epicId;
    }

    public void setEpicId(Long epicId) {
        this.epicId = epicId;
    }

    public String getEpicName() {
        return epicName;
    }

    public void setEpicName(String epicName) {
        this.epicName = epicName;
    }

    public Long getEpicStageId() {
        return epicStageId;
    }

    public void setEpicStageId(Long epicStageId) {
        this.epicStageId = epicStageId;
    }

    public String getEpicStageName() {
        return epicStageName;
    }

    public void setEpicStageName(String epicStageName) {
        this.epicStageName = epicStageName;
    }

    public String getBizNum() {
        return bizNum;
    }

    public void setBizNum(String bizNum) {
        this.bizNum = bizNum;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public String getSprintState() {
        return sprintState;
    }

    public void setSprintState(String sprintState) {
        this.sprintState = sprintState;
    }

    @Override
    public String toString() {
        return "CustomizeIssueDTO{" +
                "faultId=" + faultId +
                ", faultName='" + faultName + '\'' +
                ", faultReceiverName='" + faultReceiverName + '\'' +
                ", faultStageId=" + faultStageId +
                ", faultStageName='" + faultStageName + '\'' +
                ", taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskReceiverName='" + taskReceiverName + '\'' +
                ", taskReceiverAccount='" + taskReceiverAccount + '\'' +
                ", taskStageId=" + taskStageId +
                ", taskStageName='" + taskStageName + '\'' +
                ", storyId=" + storyId +
                ", storyName='" + storyName + '\'' +
                ", storyStageId=" + storyStageId +
                ", storyStageName='" + storyStageName + '\'' +
                ", featureId=" + featureId +
                ", featureName='" + featureName + '\'' +
                ", featureStageId=" + featureStageId +
                ", featureStageName='" + featureStageName + '\'' +
                ", epicId=" + epicId +
                ", epicName='" + epicName + '\'' +
                ", epicStageId=" + epicStageId +
                ", epicStageName='" + epicStageName + '\'' +
                ", bizNum='" + bizNum + '\'' +
                ", sprintId=" + sprintId +
                ", sprintName='" + sprintName + '\'' +
                ", sprintState='" + sprintState + '\'' +
                '}';
    }
}
