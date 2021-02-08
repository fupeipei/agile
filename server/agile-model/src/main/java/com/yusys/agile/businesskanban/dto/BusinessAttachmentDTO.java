package com.yusys.agile.businesskanban.dto;

import java.util.Date;
/**
 *   : zhaoyd6
 * @Date: 2020/4/30
 * @Description:  事务附件
 */
public class BusinessAttachmentDTO {
    private Long attachmentId;

    private Long businessId;

    private String fileUri;

    private String fileName;

    private Date uploadTime;

    private Long uploadUid;

    private String tenantCode;

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
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

    @Override
    public String toString() {
        return "BusinessAttachmentDTO{" +
                "attachmentId=" + attachmentId +
                ", businessId=" + businessId +
                ", fileUri='" + fileUri + '\'' +
                ", fileName='" + fileName + '\'' +
                ", uploadTime=" + uploadTime +
                ", uploadUid=" + uploadUid +
                '}';
    }
}