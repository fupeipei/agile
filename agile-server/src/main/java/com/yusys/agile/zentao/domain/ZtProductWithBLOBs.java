package com.yusys.agile.zentao.domain;

import java.io.Serializable;

public class ZtProductWithBLOBs extends ZtProduct implements Serializable {
    private String desc;

    private String whitelist;

    private static final long serialVersionUID = 1L;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(String whitelist) {
        this.whitelist = whitelist == null ? null : whitelist.trim();
    }
}