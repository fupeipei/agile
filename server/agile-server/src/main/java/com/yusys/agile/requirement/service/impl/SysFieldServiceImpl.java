package com.yusys.agile.requirement.service.impl;

import com.yusys.agile.issue.enums.IssueStateEnum;
import com.yusys.agile.requirement.dao.SysFieldMapper;
import com.yusys.agile.requirement.domain.SysField;
import com.yusys.agile.requirement.domain.SysFieldExample;
import com.yusys.agile.requirement.service.SysFieldService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *   :
 * @Date: 2020/9/15
 * @Description: TODO
 */
@Service
public class SysFieldServiceImpl implements SysFieldService {



    @Resource
    private SysFieldMapper sysFieldMapper;
    @Override
    public List<SysField> getAllSysField(Byte issueType) {

        SysFieldExample sysFieldExample = new SysFieldExample();
        SysFieldExample.Criteria criteria =  sysFieldExample.createCriteria();
        criteria.andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        if(issueType!=null){
            criteria.andExtendTypeEqualTo(issueType);
        }
        return sysFieldMapper.selectByExample(sysFieldExample);
    }
}
