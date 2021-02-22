package com.yusys.agile.changemanagement.dto;

import java.io.Serializable;

/**
 * @description 需求文档类
 * @date 2020/12/10
 */
public class EpicDocumentDTO implements Serializable {

    private static final long serialVersionUID = -9025863997356458545L;

    /**
     * 附件id
     */
    private Long attachmentId;

    /**
     * 文件uri
     */
    private String fileUri;

    /**
     * 文件名
     */
    private String fileName;

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "EpicDocumentDTO{" +
                "attachmentId=" + attachmentId +
                ", fileUri='" + fileUri + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
