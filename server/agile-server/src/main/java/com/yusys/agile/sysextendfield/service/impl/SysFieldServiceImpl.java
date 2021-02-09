package com.yusys.agile.sysextendfield.service.impl;

import com.google.common.collect.Lists;
import com.yusys.agile.externalapiconfig.dao.util.ExternalApiConfigUtil;
import com.yusys.agile.issue.enums.IssueStateEnum;
import com.yusys.agile.sysextendfield.dao.SysFieldMapper;
import com.yusys.agile.sysextendfield.domain.SysExtendField;
import com.yusys.agile.sysextendfield.domain.SysField;
import com.yusys.agile.sysextendfield.domain.SysFieldExample;
import com.yusys.agile.sysextendfield.service.SysExtendFieldService;
import com.yusys.agile.sysextendfield.service.SysFieldService;
import com.yusys.agile.utils.ReflectObjectUtil;
import com.yusys.agile.versionmanager.constants.VersionConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * :
 *
 * @Date: 2021/3/15
 * @Description: TODO
 */
@Service
public class SysFieldServiceImpl implements SysFieldService {

    @Resource
    private SysFieldMapper sysFieldMapper;

    @Override
    public List<SysField> getAllSysField(Byte issueType) {

        SysFieldExample sysFieldExample = new SysFieldExample();
        SysFieldExample.Criteria criteria = sysFieldExample.createCriteria();
        criteria.andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        if (issueType != null) {
            criteria.andExtendTypeEqualTo(issueType);
        }
        return sysFieldMapper.selectByExample(sysFieldExample);
    }

}
