package com.yusys.agile.teamv3.enums;

/**
 * 用户角色枚举 1：po，2：sm，3：普通成员
 *
 * @date 2021/04/28
 */
public enum TeamRoleEnum {

    SCRUM_MASTER(103,"SM","Scrum Master"),
    PRODUCT_OWNER(104,"PO","Product Owner"),
    TEAM_MEMBER(105,"MEMBER","Team Member"),
    ;

    public int roleId;
    public String roleCode;
    public String roleName;

    private TeamRoleEnum(int roleId, String roleCode, String roleName){
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
