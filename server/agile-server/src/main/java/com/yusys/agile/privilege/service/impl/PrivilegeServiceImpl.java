package com.yusys.agile.privilege.service.impl;

import com.yusys.agile.privilege.dao.PrivilegeMapper;
import com.yusys.agile.privilege.domain.Privilege;
import com.yusys.agile.privilege.domain.PrivilegeExample;
import com.yusys.agile.privilege.service.PrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description
 * @date 2021/2/8
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    @Resource
    private PrivilegeMapper privilegeMapper;

    @Override
    public List<Privilege> getPrivilegesByids(List<Long> privilegeIdList) {
        PrivilegeExample privilegeExample = new PrivilegeExample();
        privilegeExample.createCriteria().andPrivilegeIdIn(privilegeIdList);
        return privilegeMapper.selectByExample(privilegeExample);
    }
}
