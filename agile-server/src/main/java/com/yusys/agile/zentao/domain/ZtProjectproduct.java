package com.yusys.agile.zentao.domain;

import java.io.Serializable;

public class ZtProjectproduct extends ZtProjectproductKey implements Serializable {
    private Integer branch;

    private Integer plan;

    private static final long serialVersionUID = 1L;

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }
}