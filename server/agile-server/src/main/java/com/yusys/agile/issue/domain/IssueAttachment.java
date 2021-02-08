package com.yusys.agile.issue.domain;

import java.io.Serializable;
import java.util.Date;

public class IssueAttachment implements Serializable {
    private Long attachmentId;

    private Long issueId;

    private String fileUri;

    private Long stageId;

    private String fileName;

    private Date uploadTime;

    public Long getUploadUid() {
        return uploadUid;
    }

    public void setUploadUid(Long uploadUid) {
        this.uploadUid = uploadUid;
    }

    private Long uploadUid;

    private String fileSize;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    private String attachmentDesc;

    private static final long serialVersionUID = 1L;

    public String getAttachmentDesc() {
        return attachmentDesc;
    }

    public void setAttachmentDesc(String attachmentDesc) {
        this.attachmentDesc = attachmentDesc;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri == null ? null : fileUri.trim();
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize == null ? null : fileSize.trim();
    }
}