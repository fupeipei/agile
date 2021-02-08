package com.yusys.agile.privilege.service;

import com.yusys.agile.privilege.domain.RolePrivilege;

import java.util.List;

public interface RolePrivilegeService {

    /**
     *功能描述 根据角色列表获取权限列表
     *
     * @date 2020/11/17
      * @param roleList
     * @return java.util.List<com.yusys.agile.privilege.domain.RolePrivilege>
     */
    List<RolePrivilege> getPrivilegesByRoleIdList(List<Long> roleList);
}
