package com.yusys.agile.issue.dto;

import java.util.List;

/**
 *
 * @description
 * @date 2020/10/23
 */
public class PanoramasEpicDTO {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getEpicSatus() {
        return epicSatus;
    }

    public void setEpicSatus(String epicSatus) {
        this.epicSatus = epicSatus;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public List<PanoramasFeatureDTO> getFeatures() {
        return features;
    }

    public void setFeatures(List<PanoramasFeatureDTO> features) {
        this.features = features;
    }

    private Long issueId;
    private String bizNum;
    private String title;
    private String handler;
    private String epicSatus;
    private String release;
    private String finish;

    public String getFormalReqCode() {
        return formalReqCode;
    }

    public void setFormalReqCode(String formalReqCode) {
        this.formalReqCode = formalReqCode;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    private String formalReqCode;
    private String responsiblePerson;
    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    private String versionName;
    private List<PanoramasFeatureDTO> features;

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getApprovalStatusDesc() {
        return approvalStatusDesc;
    }

    public void setApprovalStatusDesc(String approvalStatusDesc) {
        this.approvalStatusDesc = approvalStatusDesc;
    }

    private String approvalResult;
    private String approvalStatusDesc;
}
