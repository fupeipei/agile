package com.yusys.agile.privilege.service;

import com.yusys.agile.privilege.domain.Privilege;

import java.util.List;

/**
 * @description
 * @date 2021/2/8
 */
public interface PrivilegeService {

    /**
     * 功能描述 根据权限id列表获取权限信息
     *
     * @param privilegeIdList
     * @return java.util.List<com.yusys.agile.privilege.domain.Privilege>
     * @date 2021/2/8
     */
    List<Privilege> getPrivilegesByids(List<Long> privilegeIdList);
}
