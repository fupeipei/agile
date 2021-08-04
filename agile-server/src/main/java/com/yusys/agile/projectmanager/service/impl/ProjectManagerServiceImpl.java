package com.yusys.agile.projectmanager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yusys.agile.projectmanager.dao.*;
import com.yusys.agile.projectmanager.domain.*;
import com.yusys.agile.projectmanager.dto.ProjectManagerDto;
import com.yusys.agile.projectmanager.service.ProjectManagerService;
import com.yusys.portal.model.common.enums.StateEnum;
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

    @Autowired
    private SProjectSystemRelMapper sProjectSystemRelMapper;

    @Autowired
    private SStaticProjectDataMapper sStaticProjectDataMapper;


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
        sProjectUserRelMapper.batchInsertProjectUsers(sProjectUserRels);
        //添加 产品线
        List<SProjectProductLineRel> sProjectProductLineRels = buildSProjectProductLineRel(projectId, projectManagerDto.getProductIds());
        sProjectProductLineRelMapper.batchInsertProjectProductLineRel(sProjectProductLineRels);
        //添加 系统
        List<SProjectSystemRel> sProjectSystemRels = buildSProjectSystemRel(projectId, projectManagerDto.getSystemIds());
        sProjectSystemRelMapper.batchInsertProjectSystemRelMapper(sProjectSystemRels);
        return projectManagerDto;
    }

    @Override
    public List<SStaticProjectData> queryStaticDataByType(Integer type) {
        SStaticProjectDataExample sStaticProjectDataExample = new SStaticProjectDataExample();
        sStaticProjectDataExample.createCriteria().andTypeEqualTo(type)
                .andStateEqualTo(StateEnum.U.getValue());
        List<SStaticProjectData> sStaticProjectData = sStaticProjectDataMapper.selectByExample(sStaticProjectDataExample);
        return sStaticProjectData;
    }

    @Override
    public PageInfo<ProjectManagerDto> queryProjectManagerPageInfo(Integer pageNum, Integer pageSize, String searchKey) {
        PageHelper.startPage(pageNum,pageSize);
        List<ProjectManagerDto> projectManagerDtos = sProjectManagerMapper.queryProjectManagerList(searchKey);
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

    private List<SProjectSystemRel> buildSProjectSystemRel(Long projectId,List<Long> systemIds){
        List<SProjectSystemRel> sProjectSystemRels = systemIds.stream().map(x -> {
            SProjectSystemRel sProjectSystemRel = new SProjectSystemRel();
            sProjectSystemRel.setProjectId(projectId);
            sProjectSystemRel.setRelSystemId(x);
            return sProjectSystemRel;
        }).collect(Collectors.toList());
        return sProjectSystemRels;
    }

    private List<SProjectProductLineRel> buildSProjectProductLineRel(Long projectId,List<Long> productIds){
        List<SProjectProductLineRel> projectProductLineRels = productIds.stream().map(x -> {
            SProjectProductLineRel sProjectProductLineRel = new SProjectProductLineRel();
            sProjectProductLineRel.setProjectId(projectId);
            sProjectProductLineRel.setRelProductId(x);
            return sProjectProductLineRel;
        }).collect(Collectors.toList());
        return projectProductLineRels;
    }


}
