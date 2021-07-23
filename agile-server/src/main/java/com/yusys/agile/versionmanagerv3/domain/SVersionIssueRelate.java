package com.yusys.agile.versionmanagerv3.domain;

import java.io.Serializable;

public class SVersionIssueRelate implements Serializable {
    private Long relateId;

    private Long issueId;

    private Byte issueType;

    private Long versionId;

    private static final long serialVersionUID = 1L;

    public Long getRelateId() {
        return relateId;
    }

    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Byte getIssueType() {
        return issueType;
    }

    public void setIssueType(Byte issueType) {
        this.issueType = issueType;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }
}