package com.yusys.agile.projectmanager.service.impl;

import com.yusys.agile.projectmanager.dao.SProjectManagerMapper;
import com.yusys.agile.projectmanager.dao.SProjectProductLineRelMapper;
import com.yusys.agile.projectmanager.dao.SProjectUserRelMapper;
import com.yusys.agile.projectmanager.domain.SProjectManager;
import com.yusys.agile.projectmanager.domain.SProjectUserRel;
import com.yusys.agile.projectmanager.dto.ProjectManagerDto;
import com.yusys.agile.projectmanager.service.ProjectManagerService;
import com.yusys.portal.util.code.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectManagerServiceImpl implements ProjectManagerService {


    @Autowired
    private SProjectManagerMapper sProjectManagerMapper;

    @Autowired
    private SProjectProductLineRelMapper sProjectProductLineRelMapper;

    @Autowired
    private SProjectUserRelMapper sProjectUserRelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProjectManagerDto insertProjectManager(ProjectManagerDto projectManagerDto) {
        //保存项目
        SProjectManager sProjectManager = ReflectUtil.copyProperties(projectManagerDto, SProjectManager.class);
        sProjectManagerMapper.insertSelective(sProjectManager);
        //保存项目下的人员
        List<Long> userIds = projectManagerDto.getUserIds();
        Long projectId = sProjectManager.getProjectId();
        List<SProjectUserRel> sProjectUserRels = buildProjectUserRels(projectId, userIds);
//        sProjectUserRelMapper.batchInsertProjectUsers(sProjectUserRels);
        //添加 产品线
//        sProjectProductLineRelMapper.batchInsertProjectProductLineRel(projectId,sy);
        return null;
    }

    private List<SProjectUserRel> buildProjectUserRels(Long projectId,List<Long> userIds){
        List<SProjectUserRel> projectUserRels = userIds.stream().map(x -> {
            SProjectUserRel sProjectUserRel = new SProjectUserRel();
            sProjectUserRel.setProjectId(projectId);
            sProjectUserRel.setUserId(x);
            return sProjectUserRel;
        }).collect(Collectors.toList());
        return projectUserRels;
    }


}
