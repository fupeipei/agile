package com.yusys.agile.issue.domain;

import java.io.Serializable;

public class SIssueRichtextWithBLOBs extends SIssueRichtext implements Serializable {
    private String description;

    private String acceptanceCriteria;

    private static final long serialVersionUID = 1L;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria == null ? null : acceptanceCriteria.trim();
    }
}