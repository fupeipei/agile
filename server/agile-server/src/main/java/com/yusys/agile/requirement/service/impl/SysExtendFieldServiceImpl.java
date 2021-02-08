package com.yusys.agile.requirement.service.impl;

import com.yusys.agile.issue.enums.IssueStateEnum;
import com.yusys.agile.requirement.dao.SysExtendFieldMapper;
import com.yusys.agile.requirement.domain.SysExtendField;
import com.yusys.agile.requirement.domain.SysExtendFieldExample;
import com.yusys.agile.requirement.service.SysExtendFieldService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *   :
 * @Date: 2021/3/10
 * @Description: TODO
 */
@Service
public class SysExtendFieldServiceImpl implements SysExtendFieldService {


    @Resource
    private SysExtendFieldMapper sysExtendFieldMapper;


    @Override
    public List<SysExtendField> getAllSysExtendField(Byte issueType) {
        SysExtendFieldExample example = new SysExtendFieldExample();
        SysExtendFieldExample.Criteria  criteria = example.createCriteria();
        criteria.andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        if(issueType!=null){
            criteria.andExtendTypeEqualTo(issueType);
        }
        return sysExtendFieldMapper.selectByExample(example);
    }
}
