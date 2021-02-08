package com.yusys.agile.fault.domain;

import java.io.Serializable;

public class FaultType implements Serializable {
    private Long typeId;

    private String typeCode;

    private String typeDesc;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    private static final long serialVersionUID = 1L;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc == null ? null : typeDesc.trim();
    }

    @Override
    public String toString() {
        return "FaultType{" +
                "typeId=" + typeId +
                ", typeCode='" + typeCode + '\'' +
                ", typeDesc='" + typeDesc + '\'' +
                '}';
    }
}