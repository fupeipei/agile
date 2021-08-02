package com.yusys.agile.issue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.IssueStageIdCountDTO;
import com.yusys.agile.issue.enums.IsAchiveEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.StoryStatusEnum;
import com.yusys.agile.issue.service.EpicService;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.scheduleplan.dto.ScheduleplanDTO;
import com.yusys.agile.scheduleplan.service.SchedulePlanService;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.enums.TeamTypeEnum;
import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import com.google.common.collect.Lists;
import com.yusys.agile.versionmanager.service.VersionManagerService;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.entity.SsoSystem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Date: 15:57
 */
@Slf4j
@Service
public class EpicServiceImpl implements EpicService {

    @Resource
    private IssueFactory issueFactory;

    @Resource
    private IssueMapper issueMapper;

    @Resource
    private IFacadeSystemApi iFacadeSystemApi;

    @Resource
    private VersionManagerService versionManagerService;

    @Resource
    private STeamMapper sTeamMapper;

    @Autowired
    private SchedulePlanService schedulePlanService;

    @Override
    public Long createEpic(IssueDTO issueDTO) {
        //设置默认创建
        Long[] stages = issueDTO.getStages();
        if(!Optional.ofNullable(stages).isPresent()){
            stages = new Long[]{
                    StageConstant.FirstStageEnum.READY_STAGE.getValue()};
            issueDTO.setStages(stages);
        }
        Long issueId = issueFactory.createIssue(issueDTO, "业务需求名称已存在！", "新增业务需求", IssueTypeEnum.TYPE_EPIC.CODE);
        ScheduleplanDTO scheduleplan = issueDTO.getScheduleplan();
        if(Optional.ofNullable(scheduleplan).isPresent()){
            scheduleplan.setEpicId(issueId);

            log.info("需求排期获取接口入参:{}", JSONObject.toJSONString(scheduleplan));
            schedulePlanService.saveSchedulePlan(scheduleplan);
        }
        return issueId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEpic(Long epicId,Boolean deleteChild) {
        issueFactory.deleteIssue(epicId,deleteChild);
    }


    @Override
    public IssueDTO queryEpic(Long issueId) {
        Long projectId = issueFactory.getSystemIdByIssueId(issueId);
        IssueDTO issueDTO = issueFactory.queryIssue(issueId, projectId);
        ScheduleplanDTO schedulePlan = schedulePlanService.getSchedulePlan(issueId);
        issueDTO.setScheduleplan(schedulePlan);
        return issueDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editEpic(IssueDTO issueDTO) {
        Issue oldEpic = issueMapper.selectByPrimaryKey(issueDTO.getIssueId());
        //校验epic下面的feature是否存在精益模式，如果存在则不允许变更阶段和状态
        Long[] stages = issueDTO.getStages();
        //阶段发生变更
        if(!oldEpic.getStageId().equals(stages[0]) && hasLeanFeature(issueDTO.getIssueId())){
            throw new BusinessException("epic下面存在精益看板Feature不允许变更阶段！");
        }
        //状态发生变更
        if(stages.length > 1){
            if(!oldEpic.getLaneId().equals(stages[1]) && hasLeanFeature(issueDTO.getIssueId()) ){
                throw new BusinessException("epic下面存在精益看板Feature不允许变更状态！");
            }
        }
        Issue epic = issueFactory.editIssue(issueDTO, oldEpic, null);
        int count;
        count = issueMapper.updateByPrimaryKeySelectiveWithNull(epic);
        if (count != 1) {
            throw new BusinessException("更新业务需求失败！");
        }
        //排期
        ScheduleplanDTO scheduleplan = issueDTO.getScheduleplan();
        if(Optional.ofNullable(scheduleplan).isPresent()){
            scheduleplan.setEpicId(issueDTO.getIssueId());
            schedulePlanService.saveSchedulePlan(scheduleplan);
        }
    }

    private boolean hasLeanFeature(Long epicId) {
       IssueExample issueExample = new IssueExample();
       issueExample.createCriteria().andParentIdEqualTo(epicId).andStateEqualTo(StateEnum.U.getValue());
       List<Issue> featureList = issueMapper.selectByExample(issueExample);
       List<Long> teamIdList = featureList.stream().map(Issue::getTeamId).collect(Collectors.toList());
       List<STeam> teamList = sTeamMapper.listTeamByIds(teamIdList);
       for(STeam sTeam : teamList){
           if(TeamTypeEnum.lean_team.getCode().equals(sTeam.getTeamType())){
               return true;
           }
       }
       return false;
    }

    @Override
    public Long copyEpic(Long epicId) {
        return issueFactory.copyIssue(epicId,"该复制的业务需求已失效！", "业务需求名称已存在！", "新增业务需求", IssueTypeEnum.TYPE_EPIC.CODE);
    }

    @Override
    public List<IssueDTO> queryAllEpic(Long systemId, Integer pageNum, Integer pageSize, String title) {
        return issueFactory.queryAllIssue(null, IssueTypeEnum.TYPE_EPIC.CODE, pageNum, pageSize, title, null);
    }

    /**
     * :
     *
     * @param projectId
     * @Date: 2021/3/30
     * @Description: 按版本统计系统各个阶段需求个数
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueStageIdCountDTO>
     */
    @Override
    public List<IssueStageIdCountDTO> queryAllEpicCountByVersionId(Long projectId) {
        List<SsoSystem> ssoSystems = iFacadeSystemApi.querySystemsByProjectId(projectId);
        List<VersionManagerDTO> versionManagerDTOs = versionManagerService.getAllVersionInfo(projectId);
        List<IssueStageIdCountDTO> issueStageIdCountDTOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ssoSystems) && CollectionUtils.isNotEmpty(versionManagerDTOs)) {
            for (SsoSystem ssoSystem : ssoSystems) {
                for (VersionManagerDTO versionManagerDTO : versionManagerDTOs) {
                    IssueStageIdCountDTO stageIdCountDTO = new IssueStageIdCountDTO();
                    List<IssueDTO> epicDTOS = issueMapper.selectBySystemIdAndVersion(projectId, ssoSystem.getSystemId(), versionManagerDTO.getId(), IssueTypeEnum.TYPE_EPIC.CODE);
                    countStageId(projectId, ssoSystem, versionManagerDTO, stageIdCountDTO, epicDTOS);
                    if (CollectionUtils.isEmpty(epicDTOS)) {
                        setStageIdCountDTO(projectId, ssoSystem, versionManagerDTO, stageIdCountDTO, 0, 0, 0, 0, 0, 0, 0);
                    }
                    issueStageIdCountDTOS.add(stageIdCountDTO);
                }
            }
        }
        return issueStageIdCountDTOS;
    }

    /**
     * 功能描述:根据epicId查询下面所有的featureId
     *
     * @param epicId
     * @return java.util.List<java.lang.Long>
     * @date 2020/10/13
     */
    @Override
    public List<Long> queryFeatureIdsByEpicId(Long epicId) {

        List<Long> featureIds = Lists.newArrayList();
        IssueExample example = new IssueExample();
        example.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                .andParentIdEqualTo(epicId)
                .andIssueTypeEqualTo(IssueTypeEnum.TYPE_FEATURE.CODE).andIsArchiveEqualTo(IsAchiveEnum.ACHIVEA_FALSE.CODE);
        List<Issue> features = issueMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(features)) {
            for (Issue feature : features) {
                featureIds.add(feature.getIssueId());
            }
        }

        return featureIds;
    }

    /**
     * @param projectId
     * @param ssoSystem
     * @param stageIdCountDTO
     * @param issueDTOS
     * @param versionManagerDTO
     * @Date 2021/3/30
     * @Description 统计需求各个阶段的个数
     * @Return void
     */
    private void countStageId(Long projectId, SsoSystem ssoSystem, VersionManagerDTO versionManagerDTO, IssueStageIdCountDTO stageIdCountDTO, List<IssueDTO> issueDTOS) {
        if (CollectionUtils.isNotEmpty(issueDTOS)) {
            int readyStageNum = 0;
            int analysisStageNum = 0;
            int designStageNum = 0;
            int developStageNum = 0;
            int testStageNum = 0;
            int onlineStageNum = 0;
            int finishStageNum = 0;
            for (IssueDTO issueDTO : issueDTOS) {
                if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.READY_STAGE.getValue())) {
                    readyStageNum++;
                } else if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.ANALYSIS_STAGE.getValue())) {
                    analysisStageNum++;
                } else if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.DESIGN_STAGE.getValue())) {
                    designStageNum++;
                } else if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue())) {
                    developStageNum++;
                } else if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.TEST_STAGE.getValue())) {
                    testStageNum++;
                } else if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.ONLINE_STAGE.getValue())) {
                    onlineStageNum++;
                } else if (issueDTO.getStageId().equals(StageConstant.FirstStageEnum.FINISH_STAGE.getValue())) {
                    finishStageNum++;
                }
                setStageIdCountDTO(projectId, ssoSystem, versionManagerDTO, stageIdCountDTO, readyStageNum, analysisStageNum, designStageNum, developStageNum, testStageNum, onlineStageNum, finishStageNum);
            }
        }
    }

    /**
     * @param projectId
     * @param ssoSystem
     * @param stageIdCountDTO
     * @param versionManagerDTO
     * @param i，i2，i3，i4，i5，i5，i7
     * @Date 2021/3/30
     * @Description 各阶段个数赋值
     * @Return void
     */
    private void setStageIdCountDTO(Long projectId, SsoSystem ssoSystem, VersionManagerDTO versionManagerDTO, IssueStageIdCountDTO stageIdCountDTO,
                                    int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        stageIdCountDTO.setProjectId(projectId);
        stageIdCountDTO.setSystemId(ssoSystem.getSystemId());
        stageIdCountDTO.setSystemName(ssoSystem.getSystemName());
        stageIdCountDTO.setVersionId(versionManagerDTO.getId());
        stageIdCountDTO.setVersionName(versionManagerDTO.getVersionName());
        stageIdCountDTO.setReadyStageNum(i);
        stageIdCountDTO.setAnalysisStageNum(i2);
        stageIdCountDTO.setDesignStageNum(i3);
        stageIdCountDTO.setDevelopStageNum(i4);
        stageIdCountDTO.setTestStageNum(i5);
        stageIdCountDTO.setOnlineStageNum(i6);
        stageIdCountDTO.setFinishStageNum(i7);
    }


}
