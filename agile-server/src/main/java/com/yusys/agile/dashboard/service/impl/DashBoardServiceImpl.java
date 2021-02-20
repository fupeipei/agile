package com.yusys.agile.dashboard.service.impl;

import com.yusys.agile.dashboard.service.DashBoardService;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.IssueProjectStatus;
import com.yusys.agile.issue.domain.IssueStatus;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueProjectStatusService;
import com.yusys.agile.issue.service.IssueStatusService;
import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.domain.SprintWithBLOBs;
import com.yusys.agile.sprint.service.SprintService;
import com.yusys.portal.facade.client.api.IFacadeProjectApi;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SsoProjectDTO;
import com.yusys.portal.util.date.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Date: 2021/2/1
 */
@Service
public class DashBoardServiceImpl implements DashBoardService {
    @Resource
    private IFacadeProjectApi iFacadeProjectApi;
    @Resource
    private SprintMapper sprintMapper;
    @Resource
    private IssueMapper issueMapper;
    @Resource
    private SprintService sprintService;
    @Resource
    private IssueStatusService issueStatusService;
    @Resource
    private IssueProjectStatusService issueProjectStatusService;

    /**
     * 仪表盘-迭代-工作项状态个数统计
     */
    @Override
    @Transactional
    public void calculateIssueStatus() {
        ControllerResponse<List<SsoProjectDTO>> controllerResponse = iFacadeProjectApi.listAllProjectsNoPage("");
        List<SsoProjectDTO> projects = controllerResponse.getData();
        if (CollectionUtils.isNotEmpty(projects)) {
            for (SsoProjectDTO project : projects) {
                if (project != null) {
                    List<SprintWithBLOBs> sprints = sprintMapper.getByProjectId(project.getProjectId());
                    calculateStatus(sprints, project.getProjectId());
                }
            }
        }
    }

    private void calculateStatus(List<SprintWithBLOBs> sprints, Long projectId) {
        if (sprints != null && !sprints.isEmpty()) {
            for (SprintWithBLOBs sprint : sprints) {
                if (sprint != null) {
                    calculateStatus(projectId, sprint);
                }
            }
        }
    }

    private void calculateStatus(Long projectId, SprintWithBLOBs sprint) {
        Long sprintId = sprint.getSprintId();
        Date target = DateUtil.currentDay();
        if (sprintService.legalDate(sprint.getSprintDays(), target)) {
            //创建业务需求状况
            //createIssueStatus(projectId, sprintId, target, IssueTypeEnum.TYPE_EPIC.CODE);
            //创建研发需求状况
           // createIssueStatus(projectId, sprintId, target, IssueTypeEnum.TYPE_FEATURE.CODE);
            //创建故事状况
            createIssueStatus(projectId, sprintId, target, IssueTypeEnum.TYPE_STORY.CODE);
            //创建任务状况
            createIssueStatus(projectId, sprintId, target, IssueTypeEnum.TYPE_TASK.CODE);
        }
    }


    /**
     * 创建工作项状况
     */
    private void createIssueStatus(Long projectId, Long sprintId, Date target, Byte issueType) {
        Integer finished = 0;
        Integer insprint = 0;
        Integer notStarted = 0;
        if (issueType.equals(IssueTypeEnum.TYPE_TASK.CODE)) {
            finished = issueMapper.countFinishedTasks4Project(sprintId,projectId);
            insprint = issueMapper.countInsprintTaskBySprint(sprintId,projectId);
            notStarted = issueMapper.countNotStartTaskBySprint(sprintId,projectId);
        } else {
            finished = issueMapper.countAchievedIssues4Sprint(sprintId,projectId, issueType);
            insprint = issueMapper.countInsprintIssuesBySprint(sprintId,projectId, issueType);
            notStarted = issueMapper.countNotStartIssuesBySprint(sprintId,projectId, issueType);
        }
        IssueStatus currentStatus = issueStatusService.getBySprintAndDate(sprintId, target, issueType);
        if (currentStatus == null) {
            IssueStatus issueStatus = new IssueStatus();
            issueStatus.setSprintDate(target);
            issueStatus.setProjectId(projectId);
            issueStatus.setSprintId(sprintId);
            issueStatus.setFinished(finished);
            issueStatus.setInSprint(insprint);
            issueStatus.setNotStarted(notStarted);
            issueStatus.setIssueType(issueType);
            issueStatusService.create(issueStatus);
        } else {
            currentStatus.setFinished(finished);
            currentStatus.setInSprint(insprint);
            currentStatus.setNotStarted(notStarted);
            issueStatusService.update(currentStatus);
        }
    }


    @Override
    @Transactional
    public void calculateProjectStatus() {
        ControllerResponse<List<SsoProjectDTO>> controllerResponse = iFacadeProjectApi.listAllProjectsNoPage("");
        List<SsoProjectDTO> projects = controllerResponse.getData();
        if (CollectionUtils.isNotEmpty(projects)) {
            for (SsoProjectDTO project : projects) {
                if (project != null) {
                    calculateProjectStatus(project.getProjectId());
                }
            }
        }
    }


    private void calculateProjectStatus(Long projectId) {
        Date target = DateUtil.currentDay();
        //创建业务需求状况
        createIssueProjectStatus(projectId,target,IssueTypeEnum.TYPE_EPIC.CODE);
        //创建研发需求状况
         createIssueProjectStatus(projectId,target, IssueTypeEnum.TYPE_FEATURE.CODE);
        //创建故事状况
        createIssueProjectStatus(projectId,target,IssueTypeEnum.TYPE_STORY.CODE);
        //创建任务状况
        createIssueProjectStatus(projectId,target,IssueTypeEnum.TYPE_TASK.CODE);
    }


    /**
     * 创建工作项状况
     */
    private void createIssueProjectStatus(Long projectId, Date target, Byte issueType) {
        Integer finished = 0;
        Integer insprint = 0;
        Integer notStarted = 0;
        if (issueType.equals(IssueTypeEnum.TYPE_TASK.CODE)) {
            finished = issueMapper.countFinishedTasks4Project(null,projectId);
            insprint = issueMapper.countInsprintTaskBySprint(null,projectId);
            notStarted = issueMapper.countNotStartTaskBySprint(null,projectId);
        } else {
            finished = issueMapper.countAchievedIssues4Sprint(null,projectId, issueType);
            insprint = issueMapper.countInsprintIssuesBySprint(null,projectId, issueType);
            notStarted = issueMapper.countNotStartIssuesBySprint(null,projectId, issueType);
        }
        IssueProjectStatus currentStatus = issueProjectStatusService.getByProjectAndDate(projectId, target, issueType);
        if (currentStatus == null) {
            IssueProjectStatus issueStatus = new IssueProjectStatus();
            issueStatus.setProjectId(projectId);
            issueStatus.setCalculateDate(target);
            issueStatus.setFinished(finished);
            issueStatus.setInSprint(insprint);
            issueStatus.setNotStarted(notStarted);
            issueStatus.setIssueType(issueType);
            issueProjectStatusService.create(issueStatus);
        } else {
            currentStatus.setFinished(finished);
            currentStatus.setInSprint(insprint);
            currentStatus.setNotStarted(notStarted);
            issueProjectStatusService.update(currentStatus);
        }
    }
}
