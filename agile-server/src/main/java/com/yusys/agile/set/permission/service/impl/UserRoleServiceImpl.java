package com.yusys.agile.set.permission.service.impl;

import com.yusys.agile.set.permission.constant.UserRoleConstant;
import com.yusys.agile.set.permission.service.UserRoleService;
import com.yusys.portal.facade.client.api.IFacadeRoleApi;
import com.yusys.portal.model.facade.entity.SsoRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    private IFacadeRoleApi iFacadeRoleApi;

    /**
     * @param roleType
     * @param pageNum
     * @param pageSize
     * @return
     * @description 查询项目级角色列表
     * @date 2020/05/20
     */
    @Override
    public List<SsoRole> queryProjectRoleList(Integer roleType, Integer pageNum, Integer pageSize) {
        return iFacadeRoleApi.queryRoleListByRoleType(UserRoleConstant.UserRoleEnum.PROJECT_ROLE.getCode(), pageNum, pageSize);
    }
}
