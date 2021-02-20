package com.yusys.agile.servicemanager.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @description 服务治理类
 *  
 * @date 2020/10/27
 */
public class ServiceManageIssue implements Serializable {

    private static final long serialVersionUID = 6561475538834586533L;

    /**
     * 工作项id
     */
    @JSONField(serialize = false)
    private Long issueId;

    /**
     * 客户需求编号
     */
    private String bizNum;

    /**
     * 客户需求名称(title)
     */
    private String bizName;

    /**
     * 局方需求编号(formalReqCode)
     */
    private String partaReqnum;

    /**
     * 局方需求责任人(responsiblePerson)
     */
    private String bizRes;

    /**
     * 需求排期(reqScheduling)
     */
    private String bizScheduling;

    /**
     * 要求上线时间(askLineTime)
     */
    private String askLineTime;

    /**
     * 业务状态(stageId)
     */
    private Long bizStatus;

    /**
     * 需求创建时间
     */
    private String createTime;

    /**
     * 需求负责人邮箱
     */
    private String bizResEmail;

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getBizNum() {
        return bizNum;
    }

    public void setBizNum(String bizNum) {
        this.bizNum = bizNum;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getPartaReqnum() {
        return partaReqnum;
    }

    public void setPartaReqnum(String partaReqnum) {
        this.partaReqnum = partaReqnum;
    }

    public String getBizRes() {
        return bizRes;
    }

    public void setBizRes(String bizRes) {
        this.bizRes = bizRes;
    }

    public String getBizScheduling() {
        return bizScheduling;
    }

    public void setBizScheduling(String bizScheduling) {
        this.bizScheduling = bizScheduling;
    }

    public String getAskLineTime() {
        return askLineTime;
    }

    public void setAskLineTime(String askLineTime) {
        this.askLineTime = askLineTime;
    }

    public Long getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(Long bizStatus) {
        this.bizStatus = bizStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBizResEmail() {
        return bizResEmail;
    }

    public void setBizResEmail(String bizResEmail) {
        this.bizResEmail = bizResEmail;
    }

    @Override
    public String toString() {
        return "ServiceManageIssue{" +
                "issueId=" + issueId +
                ", bizNum='" + bizNum + '\'' +
                ", bizName='" + bizName + '\'' +
                ", partaReqnum='" + partaReqnum + '\'' +
                ", bizRes='" + bizRes + '\'' +
                ", bizScheduling='" + bizScheduling + '\'' +
                ", askLineTime='" + askLineTime + '\'' +
                ", bizStatus=" + bizStatus +
                ", createTime='" + createTime + '\'' +
                ", bizResEmail='" + bizResEmail + '\'' +
                '}';
    }
}
