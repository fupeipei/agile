package com.yusys.agile.scheduleplan.domain;

import java.io.Serializable;
import java.util.Date;

public class SEpicSystemRelate implements Serializable {
    private Long relateId;

    private Long epicId;

    private Long systemId;

    private Long systemUid;

    private Date createTime;

    private Long createUid;

    private String state;

    private Byte isHandle;

    private String tenantCode;

    private Byte master;

    private static final long serialVersionUID = 1L;

    public Long getRelateId() {
        return relateId;
    }

    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }

    public Long getEpicId() {
        return epicId;
    }

    public void setEpicId(Long epicId) {
        this.epicId = epicId;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public Long getSystemUid() {
        return systemUid;
    }

    public void setSystemUid(Long systemUid) {
        this.systemUid = systemUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Byte getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(Byte isHandle) {
        this.isHandle = isHandle;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    public Byte getMaster() {
        return master;
    }

    public void setMaster(Byte master) {
        this.master = master;
    }
}