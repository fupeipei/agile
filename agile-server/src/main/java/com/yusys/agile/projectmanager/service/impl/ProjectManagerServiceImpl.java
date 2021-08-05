package com.yusys.agile.projectmanager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.enums.EpicStageEnum;
import com.yusys.agile.issue.enums.IssueStateEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.leankanban.dto.SLeanKanbanDTO;
import com.yusys.agile.leankanban.service.LeanKanbanService;
import com.yusys.agile.projectmanager.dao.*;
import com.yusys.agile.projectmanager.domain.*;
import com.yusys.agile.projectmanager.dto.*;
import com.yusys.agile.projectmanager.enmu.StaticProjectDataEnum;
import com.yusys.agile.projectmanager.service.ProjectManagerService;
import com.yusys.agile.projectmanager.service.ProjectSystemRelService;
import com.yusys.agile.projectmanager.service.StaticProjectDataService;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.dto.KanbanStageInstanceDTO;
import com.yusys.agile.versionmanager.enums.OperateTypeEnum;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 李艮艮
 */
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

    @Autowired
    private IFacadeUserApi iFacadeUserApi;

    @Autowired
    private ProjectSystemRelService projectSystemRelService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private KanbanStageInstanceMapper kanbanStageInstanceMapper;

    @Autowired
    private LeanKanbanService leanKanbanService;


    @Autowired
    private StaticProjectDataService staticProjectDataService;


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
    public List<ProjectDataDto> queryStaticData() throws Exception {
        SStaticProjectDataExample sStaticProjectDataExample = new SStaticProjectDataExample();
        sStaticProjectDataExample.createCriteria()
                .andStateEqualTo(StateEnum.U.getValue());
        List<SStaticProjectData> sStaticProjectData = sStaticProjectDataMapper.selectByExample(sStaticProjectDataExample);
        List<ProjectDataDto> result = Lists.newArrayList();
        List<SStaticProjectData> projectStatus = Optional.ofNullable(sStaticProjectData).orElse(new ArrayList<>()).stream().filter(x -> StaticProjectDataEnum.PROJECT_STATUS.getCODE().equals(x.getType())).collect(Collectors.toList());
        List<SStaticProjectDataDto> sStaticProjectDataDtos = ReflectUtil.copyProperties4List(projectStatus, SStaticProjectDataDto.class);
        ProjectDataDto projectDataDto = new ProjectDataDto();
        projectDataDto.setCode(StaticProjectDataEnum.PROJECT_STATUS.getCODE());
        projectDataDto.setSStaticProjectDataDtoList(sStaticProjectDataDtos);
        result.add(projectDataDto);
        List<SStaticProjectData> projectType = Optional.ofNullable(sStaticProjectData).orElse(new ArrayList<>()).stream().filter(x -> StaticProjectDataEnum.PROJECT_TYPE.getCODE().equals(x.getType())).collect(Collectors.toList());
        List<SStaticProjectDataDto> sStaticProjectDataDtos1 = ReflectUtil.copyProperties4List(projectType, SStaticProjectDataDto.class);

        ProjectDataDto projectDataDto2 = new ProjectDataDto();
        projectDataDto2.setCode(StaticProjectDataEnum.PROJECT_TYPE.getCODE());
        projectDataDto2.setSStaticProjectDataDtoList(sStaticProjectDataDtos1);
        result.add(projectDataDto2);
        List<SStaticProjectData> projectModel = Optional.ofNullable(sStaticProjectData).orElse(new ArrayList<>()).stream().filter(x -> StaticProjectDataEnum.PROJECT_MODE.getCODE().equals(x.getType())).collect(Collectors.toList());
        List<SStaticProjectDataDto> sStaticProjectDataDtos2 = ReflectUtil.copyProperties4List(projectModel, SStaticProjectDataDto.class);
        ProjectDataDto projectDataDto3 = new ProjectDataDto();
        projectDataDto3.setCode(StaticProjectDataEnum.PROJECT_MODE.getCODE());
        projectDataDto3.setSStaticProjectDataDtoList(sStaticProjectDataDtos2);
        result.add(projectDataDto3);
        return result;
    }

    @Override
    public PageInfo<ProjectManagerDto> queryProjectManagerPageInfo(Integer pageNum, Integer pageSize, String searchKey) {
        PageHelper.startPage(pageNum,pageSize);
        List<ProjectManagerDto> projectManagerDtos = sProjectManagerMapper.queryProjectManagerList(searchKey);
        if (CollectionUtils.isEmpty(projectManagerDtos)){
            return new PageInfo<>();
        }
        List<ProjectManagerDto> projectManagerDtos1 = buildProjectManagerDtoPageInfo(projectManagerDtos);
        return new PageInfo<>(projectManagerDtos1);
    }

    @Override
    public ProjectManagerDto queryProjectManagerByProjectId(Long projectId) {
        ProjectManagerDto projectManagerDto = new ProjectManagerDto();
        SProjectManager sProjectManager = sProjectManagerMapper.selectByPrimaryKey(projectId);
        if (Optional.ofNullable(sProjectManager).isPresent()){
            projectManagerDto = ReflectUtil.copyProperties(sProjectManager, ProjectManagerDto.class);
            //项目状态
            SStaticProjectData sStaticProjectData1 = staticProjectDataService.queryStaticProjectDataById(projectManagerDto.getProjectStatusId());
            projectManagerDto.setProjectName(sStaticProjectData1.getName());
            //项目类型
            SStaticProjectData sStaticProjectData2 = staticProjectDataService.queryStaticProjectDataById(projectManagerDto.getProjectTypeId());
            projectManagerDto.setProjectTypeName(sStaticProjectData2.getName());
            //项目模式
            SStaticProjectData sStaticProjectData3 = staticProjectDataService.queryStaticProjectDataById(projectManagerDto.getResearchModelId());
            projectManagerDto.setResearchModeName(sStaticProjectData3.getName());
            //查询人员
            List<SsoUserDTO> userList = querySsoUserByProjectId(projectId);
            projectManagerDto.setUserList(userList);
            List<Long> userIds = userList.stream().map(SsoUserDTO::getUserId).collect(Collectors.toList());
            projectManagerDto.setUserIds(userIds);
            //查询负责人
            Long principal = projectManagerDto.getPrincipal();
            SsoUser ssoUser = iFacadeUserApi.queryUserById(principal);
            if (Optional.ofNullable(ssoUser).isPresent()){
                projectManagerDto.setPrincipalUserName(ssoUser.getUserName());
                projectManagerDto.setPrincipalUserAccount(ssoUser.getUserAccount());
            }
            //查询系统
            SProjectSystemRelExample sProjectSystemRelExample = new SProjectSystemRelExample();
            sProjectSystemRelExample.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                    .andProjectIdEqualTo(projectId);
            List<SProjectSystemRel> sProjectSystemRels = sProjectSystemRelMapper.selectByExample(sProjectSystemRelExample);
            List<Long> systemIds = sProjectSystemRels.stream().map(SProjectSystemRel::getRelSystemId).distinct().collect(Collectors.toList());
            projectManagerDto.setSystemIds(systemIds);
            //查询产品线
            SProjectProductLineRelExample sProjectProductLineRelExample = new SProjectProductLineRelExample();
            sProjectProductLineRelExample.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                    .andProjectIdEqualTo(projectId);
            List<SProjectProductLineRel> sProjectProductLineRels = sProjectProductLineRelMapper.selectByExample(sProjectProductLineRelExample);
            List<Long> productLineIds = sProjectProductLineRels.stream().map(SProjectProductLineRel::getRelProductId).distinct().collect(Collectors.toList());
            projectManagerDto.setProductIds(productLineIds);
        }
        return projectManagerDto;
    }


    //根据项目查询项目人员
    @Override
    public List<SsoUserDTO> querySsoUserByProjectId(Long projectId) {
        List<SsoUserDTO> userList = Lists.newArrayList();
        SProjectUserRelExample sProjectUserRelExample = new SProjectUserRelExample();
        sProjectUserRelExample.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                .andProjectIdEqualTo(projectId);
        List<SProjectUserRel> sProjectUserRels = sProjectUserRelMapper.selectByExample(sProjectUserRelExample);
        List<Long> userIds = Optional.ofNullable(sProjectUserRels).orElse(new ArrayList<>()).stream().map(SProjectUserRel::getUserId).distinct().collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(userIds)){
            userIds.stream().forEach(userId->{
                SsoUser ssoUser = iFacadeUserApi.queryUserById(userId);
                if (Optional.ofNullable(ssoUser).isPresent()){
                    SsoUserDTO ssoUserDTO = ReflectUtil.copyProperties(ssoUser, SsoUserDTO.class);
                    userList.add(ssoUserDTO);
                }
            });
        }
        return userList;
    }

    @Override
    @Transactional
    public ProjectManagerDto updateProjectManager(ProjectManagerDto projectManagerDto) {
        //
        SProjectManager sProjectManager = ReflectUtil.copyProperties(projectManagerDto, SProjectManager.class);
        int i = sProjectManagerMapper.updateByPrimaryKeySelective(sProjectManager);
        //保存人员
        SProjectUserRelExample sProjectUserRelExample = new SProjectUserRelExample();
        sProjectUserRelExample.createCriteria()
                .andProjectIdEqualTo(projectManagerDto.getProjectId());
        sProjectUserRelMapper.deleteByExample(sProjectUserRelExample);

        List<Long> userIds = projectManagerDto.getUserIds();
        Long projectId = sProjectManager.getProjectId();
        List<SProjectUserRel> sProjectUserRels = buildProjectUserRels(projectId, userIds);
        sProjectUserRelMapper.batchInsertProjectUsers(sProjectUserRels);
        //保存 产品线
        SProjectProductLineRelExample sProjectProductLineRelExample = new SProjectProductLineRelExample();
        sProjectProductLineRelExample.createCriteria().andProjectIdEqualTo(projectManagerDto.getProjectId());
        sProjectProductLineRelMapper.deleteByExample(sProjectProductLineRelExample);

        List<SProjectProductLineRel> sProjectProductLineRels = buildSProjectProductLineRel(projectId, projectManagerDto.getProductIds());
        sProjectProductLineRelMapper.batchInsertProjectProductLineRel(sProjectProductLineRels);
        //保存系统
        SProjectSystemRelExample sProjectSystemRelExample = new SProjectSystemRelExample();
        sProjectSystemRelExample.createCriteria().andProjectIdEqualTo(projectManagerDto.getProjectId());
        sProjectSystemRelMapper.deleteByExample(sProjectSystemRelExample);

        List<SProjectSystemRel> sProjectSystemRels = buildSProjectSystemRel(projectId, projectManagerDto.getSystemIds());
        sProjectSystemRelMapper.batchInsertProjectSystemRelMapper(sProjectSystemRels);

        return projectManagerDto;
    }

    @Override
    public List<SProjectManager> queryProjectManagers() {
        SProjectManagerExample sProjectManagerExample = new SProjectManagerExample();
        sProjectManagerExample.createCriteria().andStateEqualTo(StateEnum.U.getValue());
        sProjectManagerExample.setOrderByClause("create_time desc");
        List<SProjectManager> sProjectManagers = sProjectManagerMapper.selectByExample(sProjectManagerExample);
        return sProjectManagers;
    }

    @Override
    public List<SsoUser> queryUserByProjectId(Long projectId) {
        List<Long> userIds = sProjectUserRelMapper.queryUserIdListByProId(projectId);
        List<SsoUser> ssoUsers = iFacadeUserApi.listUsersByIds(userIds);
        return ssoUsers;
    }

    @Override
    public List<ProjectDemandDto> queryProjectDemandList(Long projectId) {
        SProjectSystemRelExample sProjectSystemRelExample = new SProjectSystemRelExample();
        sProjectSystemRelExample.createCriteria().andProjectIdEqualTo(projectId)
                .andStateEqualTo(StateEnum.U.getValue());
        List<SProjectSystemRel> sProjectSystemRels = sProjectSystemRelMapper.selectByExample(sProjectSystemRelExample);
        List<Long> systemIds = sProjectSystemRels.stream().map(SProjectSystemRel::getRelSystemId).collect(Collectors.toList());

        return null;
    }

    @Override
    public List<ProjectManagerDto> queryProjectManagerList() {
        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        List<ProjectManagerDto> projectManagerDtos = sProjectManagerMapper.queryProjectManagerListByUserId(userId);
//        List<ProjectManagerDto> result = Lists.newArrayList();
//        if (CollectionUtils.isNotEmpty(projectManagerDtos)){
//            projectManagerDtos.stream().forEach(x->{
//                ProjectManagerDto projectManagerDto = this.queryProjectManagerByProjectId(x.getProjectId());
//                result.add(projectManagerDto);
//            });
//        }
        return projectManagerDtos;
    }

    @Override
    public List<ProjectUserTotalHourDto> queryUserIdListByProIdAndUId(Long projectId, Long userId) {
        List<ProjectUserTotalHourDto> userTotalHourDtos = sProjectUserRelMapper.queryUserIdListByProIdAndUId(projectId, userId);
        return userTotalHourDtos;
    }

    @Override
    public SProjectManager queryProjectManagerInfo(Long projectId) {
        SProjectManager sProjectManager = sProjectManagerMapper.queryProjectManagerInfo(projectId);
        return sProjectManager;
    }

    private void buildProjectDemandList(List<Long> systemIds){
        for (Long systemId : systemIds) {
//            issueService.get
        }
    }

    private List<ProjectManagerDto> buildProjectManagerDtoPageInfo (List<ProjectManagerDto> projectManagerDtos){
        List<ProjectManagerDto> result = projectManagerDtos.stream().map(x -> {
            Long principal = x.getPrincipal();
            //项目负责人
            SsoUser ssoUser = iFacadeUserApi.queryUserById(principal);
            x.setPrincipalUserName(ssoUser.getUserName());
            x.setPrincipalUserAccount(ssoUser.getUserAccount());
            //项目进度
            List<SProjectSystemRel> sProjectSystemRels = projectSystemRelService.queryProjectSystemRelList(x.getProjectId());
            List<Long> systemIds = Optional.ofNullable(sProjectSystemRels).orElse(new ArrayList<>()).stream().map(SProjectSystemRel::getRelSystemId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(systemIds)) {
                List<Issue> issues = issueService.queryIssueListBySystemIds(systemIds, IssueTypeEnum.TYPE_EPIC.CODE);
                if (CollectionUtils.isNotEmpty(issues)) {
                    List<Issue> doneIssueList = Lists.newArrayList();
                    for (Issue issue : issues) {
                        Long stageId = issue.getStageId();
                        //epic的完成阶段
                        if (Optional.ofNullable(stageId).isPresent()
                                && EpicStageEnum.COMPLETE.getStageId().equals(stageId)) {
                            doneIssueList.add(issue);
                        }
                    }
                    Integer projectProgress = (doneIssueList.size() / issues.size());
                    x.setProjectProgress(projectProgress);
                }
            }
            return x;
        }).collect(Collectors.toList());
        return result;
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
