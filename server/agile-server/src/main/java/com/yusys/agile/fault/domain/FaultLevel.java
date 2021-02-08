package com.yusys.agile.fault.domain;

import java.io.Serializable;

public class FaultLevel implements Serializable {
    private Long levelId;

    private String levelCode;

    private String levelDesc;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    private static final long serialVersionUID = 1L;

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode == null ? null : levelCode.trim();
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc == null ? null : levelDesc.trim();
    }
}