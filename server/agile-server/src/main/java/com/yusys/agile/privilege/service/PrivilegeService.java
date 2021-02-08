package com.yusys.agile.privilege.service;

import com.yusys.agile.privilege.domain.Privilege;

import java.util.List;

/**
 *
 * @description
 * @date 2020/11/17
 */
public interface PrivilegeService {

    /**
     *功能描述 根据权限id列表获取权限信息
     *
     * @date 2020/11/17
      * @param privilegeIdList
     * @return java.util.List<com.yusys.agile.privilege.domain.Privilege>
     */
    List<Privilege> getPrivilegesByids(List<Long> privilegeIdList);
}
