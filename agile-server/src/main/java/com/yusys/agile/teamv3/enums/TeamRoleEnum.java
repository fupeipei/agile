package com.yusys.agile.teamv3.enums;

/**
 * 用户角色枚举 1：po，2：sm，3：普通成员
 *
 * @date 2021/04/28
 */
public enum TeamRoleEnum {

    TEAM_PO( new Byte("1"),"po"),
    TEAM_SM(new Byte("2"), "sm"),
    TEAM_USER(new Byte("3"), "user");

    public Byte roleCode;
    public String roleName;

    TeamRoleEnum(Byte roleCode, String roleName) {
        this.roleCode = roleCode;
        this.roleName = roleName;
    }


    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Byte roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
