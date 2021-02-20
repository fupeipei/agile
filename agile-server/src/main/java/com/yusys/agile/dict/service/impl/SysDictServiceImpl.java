package com.yusys.agile.dict.service.impl;

import com.yusys.agile.dict.dao.SysDictDetailMapper;
import com.yusys.agile.dict.domain.SysDictDetail;
import com.yusys.agile.dict.domain.SysDictDetailExample;
import com.yusys.agile.dict.service.SysDictService;
import com.github.pagehelper.PageHelper;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description
 * @date 2021/2/1
 */
@Service
public class SysDictServiceImpl implements SysDictService {
    @Resource
    private SysDictDetailMapper sysDictDetailMapper;

    @Override
    public List<SysDictDetail> getResponsiblePerson(Integer pageNum, Integer pageSize, String detailName) {
        PageHelper.startPage(pageNum, pageSize);
        SysDictDetailExample sysDictDetailExample = new SysDictDetailExample();
        SysDictDetailExample.Criteria criteria = sysDictDetailExample.createCriteria();
        criteria.andDictCodeEqualTo("responsiblePerson");
        if(StringUtil.isNotBlank(detailName)){
            criteria.andDetailNameLike("%"+detailName+"%");
        }
        return sysDictDetailMapper.selectByExample(sysDictDetailExample);
    }
}
