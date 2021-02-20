package com.yusys.agile.privilege.service.impl;

import com.yusys.agile.privilege.dao.RolePrivilegeMapper;
import com.yusys.agile.privilege.domain.RolePrivilege;
import com.yusys.agile.privilege.domain.RolePrivilegeExample;
import com.yusys.agile.privilege.service.RolePrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @description
 * @date  2021/2/8
 */
@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService {
    @Resource
    private RolePrivilegeMapper rolePrivilegeMapper;
    @Override
    public List<RolePrivilege> getPrivilegesByRoleIdList(List<Long> roleList) {
        RolePrivilegeExample rolePrivilegeExample = new RolePrivilegeExample();
        rolePrivilegeExample.createCriteria().andRoleIdIn(roleList);
        return rolePrivilegeMapper.selectByExample(rolePrivilegeExample);
    }
}
