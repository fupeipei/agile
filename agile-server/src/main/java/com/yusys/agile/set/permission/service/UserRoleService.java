package com.yusys.agile.set.permission.service;


import com.yusys.portal.model.facade.entity.SsoRole;

import java.util.List;

public interface UserRoleService {

    /**
     * @return
     * @description 查询项目级角色列表
     * @date 2020/05/20
     */
    public List<SsoRole> queryProjectRoleList(Integer roleType, Integer pageNum, Integer pageSize);
}
