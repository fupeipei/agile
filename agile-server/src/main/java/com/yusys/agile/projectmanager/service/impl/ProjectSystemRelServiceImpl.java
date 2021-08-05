package com.yusys.agile.projectmanager.service.impl;

import com.yusys.agile.projectmanager.dao.SProjectSystemRelMapper;
import com.yusys.agile.projectmanager.domain.SProjectSystemRel;
import com.yusys.agile.projectmanager.domain.SProjectSystemRelExample;
import com.yusys.agile.projectmanager.service.ProjectSystemRelService;
import com.yusys.portal.model.common.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectSystemRelServiceImpl implements ProjectSystemRelService {

    @Autowired
    private SProjectSystemRelMapper sProjectSystemRelMapper;

    @Override
    public List<SProjectSystemRel> queryProjectSystemRelList(Long projectId) {
        SProjectSystemRelExample sProjectSystemRelExample = new SProjectSystemRelExample();
        sProjectSystemRelExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStateEqualTo(StateEnum.U.getValue());
        List<SProjectSystemRel> sProjectSystemRels = sProjectSystemRelMapper.selectByExample(sProjectSystemRelExample);
        return sProjectSystemRels;
    }


}
