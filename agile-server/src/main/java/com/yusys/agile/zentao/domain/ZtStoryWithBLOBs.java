package com.yusys.agile.zentao.domain;

import java.io.Serializable;

public class ZtStoryWithBLOBs extends ZtStory implements Serializable {
    private String plan;

    private String mailto;

    private static final long serialVersionUID = 1L;

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan == null ? null : plan.trim();
    }

    public String getMailto() {
        return mailto;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto == null ? null : mailto.trim();
    }
}