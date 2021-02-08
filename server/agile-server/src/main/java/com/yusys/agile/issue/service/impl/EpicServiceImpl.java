package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.IssueStageIdCountDTO;
import com.yusys.agile.issue.enums.IsAchiveEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.EpicService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import com.google.common.collect.Lists;
import com.yusys.agile.versionmanager.service.VersionManagerService;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.entity.SsoSystem;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Date: 15:57
 */
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

    @Override
    public Long createEpic(IssueDTO issueDTO) {
        return issueFactory.createIssue(issueDTO,"业务需求名称已存在！","新增业务需求", IssueTypeEnum.TYPE_EPIC.CODE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEpic(Long issueId,Boolean deleteChild) {
         issueFactory.deleteIssue(issueId,deleteChild);
    }

    /*@Override
    public IssueDTO queryEpic(Long issueId,Long projectId) {
        return issueFactory.queryIssue(issueId,projectId);
    }*/

    @Override
    public IssueDTO queryEpic(Long issueId) {
        Long projectId = issueFactory.getProjectIdByIssueId(issueId);
        return issueFactory.queryIssue(issueId,projectId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editEpic(IssueDTO issueDTO) {
        Issue oldEpic = issueMapper.selectByPrimaryKey(issueDTO.getIssueId());
        Long projectId = oldEpic.getProjectId();
        Issue epic = issueFactory.editIssue(issueDTO,oldEpic, projectId);
        int count;
        count = issueMapper.updateByPrimaryKeySelectiveWithNull(epic);
        if (count != 1) {
            throw new BusinessException("更新业务需求失败！");
        }
    }

    @Override
    public Long copyEpic(Long epicId, Long projectId) {
        return issueFactory.copyIssue(epicId,projectId,"该复制的业务需求已失效！","业务需求名称已存在！","新增业务需求", IssueTypeEnum.TYPE_EPIC.CODE);
    }

    @Override
    public List<IssueDTO> queryAllEpic(Long projectId, Integer pageNum, Integer pageSize,String title) {
        return issueFactory.queryAllIssue(projectId, IssueTypeEnum.TYPE_EPIC.CODE, pageNum, pageSize,title,null);
    }

    /**
     *   :
     * @Date: 2020/9/30
     * @Description: 按版本统计系统各个阶段需求个数
     * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueStageIdCountDTO>
     */
    @Override
    public List<IssueStageIdCountDTO> queryAllEpicCountByVersionId(Long projectId) {
        List<SsoSystem> ssoSystems = iFacadeSystemApi.querySystemsByProjectId(projectId);
        List<VersionManagerDTO> versionManagerDTOs = versionManagerService.getAllVersionInfo(projectId);
        List<IssueStageIdCountDTO> issueStageIdCountDTOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ssoSystems) && CollectionUtils.isNotEmpty(versionManagerDTOs)) {
            for (SsoSystem ssoSystem : ssoSystems) {
                for(VersionManagerDTO versionManagerDTO : versionManagerDTOs){
                    IssueStageIdCountDTO stageIdCountDTO = new IssueStageIdCountDTO();
                    List<IssueDTO> epicDTOS = issueMapper.selectBySystemIdAndVersion(projectId, ssoSystem.getSystemId(),versionManagerDTO.getId(), IssueTypeEnum.TYPE_EPIC.CODE);
                    countStageId(projectId, ssoSystem, versionManagerDTO,stageIdCountDTO, epicDTOS);
                    if (CollectionUtils.isEmpty(epicDTOS)) {
                        setStageIdCountDTO(projectId, ssoSystem, versionManagerDTO,stageIdCountDTO, 0, 0, 0, 0, 0, 0, 0);
                    }
                    issueStageIdCountDTOS.add(stageIdCountDTO);
                }
            }
        }
        return issueStageIdCountDTOS;
    }

    /**
     * 功能描述:根据epicId查询下面所有的featureId

     * @date 2020/10/13
     * @param epicId
     * @return java.util.List<java.lang.Long>
     */
    @Override
    public List<Long> queryFeatureIdsByEpicId(Long epicId) {

        List<Long> featureIds = Lists.newArrayList();
        IssueExample example = new IssueExample();
        example.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                .andParentIdEqualTo(epicId)
                .andIssueTypeEqualTo(IssueTypeEnum.TYPE_FEATURE.CODE).andIsArchiveEqualTo(IsAchiveEnum.ACHIVEA_FALSE.CODE);
        List<Issue> features = issueMapper.selectByExample(example);

        if(CollectionUtils.isNotEmpty(features)){
            for(Issue feature:features){
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
     *
     * @Date 2020/9/30
     * @Description 统计需求各个阶段的个数
     * @Return void
     */
    private void countStageId(Long projectId, SsoSystem ssoSystem,VersionManagerDTO versionManagerDTO, IssueStageIdCountDTO stageIdCountDTO, List<IssueDTO> issueDTOS) {
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
                setStageIdCountDTO(projectId, ssoSystem, versionManagerDTO,stageIdCountDTO, readyStageNum, analysisStageNum, designStageNum, developStageNum, testStageNum, onlineStageNum, finishStageNum);
            }
        }
    }

    /**
     * @param projectId
     * @param ssoSystem
     * @param stageIdCountDTO
     * @param versionManagerDTO
     * @param i，i2，i3，i4，i5，i5，i7
     *
     * @Date 2020/9/30
     * @Description 各阶段个数赋值
     * @Return void
     */
    private void setStageIdCountDTO(Long projectId, SsoSystem ssoSystem, VersionManagerDTO versionManagerDTO,IssueStageIdCountDTO stageIdCountDTO,
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
