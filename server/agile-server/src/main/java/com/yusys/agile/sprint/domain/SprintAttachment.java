package com.yusys.agile.sprint.domain;

import java.io.Serializable;
import java.util.Date;

public class SprintAttachment implements Serializable {
    private Long attachmentId;

    private Long sprintId;

    private String fileUri;

    private String fileName;

    private Integer fileSize;

    private Date uploadTime;

    private Long uploadUid;

    private String tenantCode;

    private static final long serialVersionUID = 1L;

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri == null ? null : fileUri.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Long getUploadUid() {
        return uploadUid;
    }

    public void setUploadUid(Long uploadUid) {
        this.uploadUid = uploadUid;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}