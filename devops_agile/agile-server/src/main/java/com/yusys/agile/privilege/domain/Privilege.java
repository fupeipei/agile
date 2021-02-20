package com.yusys.agile.privilege.domain;

import java.io.Serializable;
import java.util.Date;

public class Privilege implements Serializable {
    private Long privilegeId;

    private String privilegeName;

    private Long privilegeType;

    private String path;

    private String reType;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName == null ? null : privilegeName.trim();
    }

    public Long getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(Long privilegeType) {
        this.privilegeType = privilegeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getReType() {
        return reType;
    }

    public void setReType(String reType) {
        this.reType = reType == null ? null : reType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}