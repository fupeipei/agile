package com.yusys.agile.sysextendfield.service.impl;

import com.google.common.collect.Lists;
import com.yusys.agile.issue.enums.IssueStateEnum;
import com.yusys.agile.sysextendfield.dao.SysExtendFieldMapper;
import com.yusys.agile.sysextendfield.domain.SysExtendField;
import com.yusys.agile.sysextendfield.domain.SysExtendFieldExample;
import com.yusys.agile.sysextendfield.domain.SysField;
import com.yusys.agile.sysextendfield.service.SysExtendFieldService;
import com.yusys.agile.sysextendfield.service.SysFieldService;
import com.yusys.agile.utils.ReflectObjectUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * :
 *
 * @Date: 2021/3/10
 * @Description: TODO
 */
@Service
public class SysExtendFieldServiceImpl implements SysExtendFieldService {


    @Resource
    private SysExtendFieldMapper sysExtendFieldMapper;
    @Resource
    private SysFieldService sysFieldService;


    @Override
    public List<SysExtendField> getAllSysExtendField(Byte issueType) {
        SysExtendFieldExample example = new SysExtendFieldExample();
        SysExtendFieldExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        if (issueType != null) {
            criteria.andExtendTypeEqualTo(issueType);
        }
        return sysExtendFieldMapper.selectByExample(example);
    }

    @Override
    public List<SysExtendField> getField(Byte issueType) {
        List<SysExtendField> sysExtendFieldList = Lists.newArrayList();

        List<SysField> sysFields = sysFieldService.getAllSysField(issueType);
        if (sysFields.size() > 0) {
            List<SysExtendField> sysExtendFieldList1 = ReflectObjectUtil.copyProperties4List(sysFields, SysExtendField.class);
            sysExtendFieldList.addAll(sysExtendFieldList1);
        }
        return sysExtendFieldList;
    }
}
