package com.yusys.agile.file.domain;

import com.yusys.agile.constant.StringConstant;

public class FileInfo {
    private String fileName;// 原始文件名
    private String remoteName;// 上传后的文件名
    private long size;// bytes
    private String suffix;
    private String fileServer;
    private String fileUri;

    public FileInfo() {
    }

    public FileInfo(String fileName, String remoteName, long size, String suffix, String fileServer) {
        this.fileName = fileName;
        this.remoteName = remoteName;
        this.size = size;
        this.suffix = suffix;
        this.fileServer = fileServer;
        this.fileUri = StringConstant.DOWNLOAD_STR1 + remoteName + StringConstant.DOWNLOAD_STR2 + fileName;
    }

    public String getFileServer() {
        return fileServer;
    }

    public void setFileServer(String fileServer) {
        this.fileServer = fileServer;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemoteName() {
        return remoteName;
    }

    public void setRemoteName(String remoteName) {
        this.remoteName = remoteName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }
}
