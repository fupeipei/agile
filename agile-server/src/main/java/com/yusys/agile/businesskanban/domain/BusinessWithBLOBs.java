package com.yusys.agile.businesskanban.domain;

import java.io.Serializable;

public class BusinessWithBLOBs extends Business implements Serializable {
    private String businessDesc;

    private String descText;

    private static final long serialVersionUID = 1L;

    public String getBusinessDesc() {
        return businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc == null ? null : businessDesc.trim();
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText == null ? null : descText.trim();
    }
}