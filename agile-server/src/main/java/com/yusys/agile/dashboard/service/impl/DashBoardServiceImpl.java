package com.yusys.agile.dashboard.service.impl;

import com.yusys.agile.dashboard.service.DashBoardService;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.dao.IssueStatusMapper;
import com.yusys.agile.issue.domain.IssueProjectStatus;
import com.yusys.agile.issue.domain.IssueStatus;
import com.yusys.agile.issue.enums.IssueStateEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueProjectStatusService;
import com.yusys.agile.issue.service.IssueStatusService;
import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.domain.SprintWithBLOBs;
import com.yusys.agile.sprint.service.SprintService;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
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
    private IssueMapper issueMapper;
    @Resource
    private Sprintv3Service sprintv3Service;
    @Resource
    private IssueStatusService issueStatusService;
    @Resource
    private IssueProjectStatusService issueProjectStatusService;
    @Resource
    private IssueStatusMapper statusMapper;

    /**
     * 仪表盘-迭代-工作项状态个数统计
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void calculateIssueStatus() {
        //查询所有迭代
        List<SSprintWithBLOBs> sSprints  = sprintv3Service.querySprintList();
        if (CollectionUtils.isNotEmpty(sSprints)) {
            for (SSprintWithBLOBs sprint : sSprints) {
                if (null != sprint) {
                    calculateStatus(null,sprint);
                }
            }
        }
    }
    private void calculateStatus(Long projectId, SSprintWithBLOBs sprint) {
        Long sprintId = sprint.getSprintId();
        Date target = DateUtil.currentDay();
        if (sprintv3Service.legalDate(sprint.getSprintDays(), target)) {
            //创建业务需求状况
            //createIssueStatus(projectId, sprintId, target, IssueTypeEnum.TYPE_EPIC.CODE);
            //创建研发需求状况
            // createIssueStatus(projectId, sprintId, target, IssueTypeEnum.TYPE_FEATURE.CODE);
            //创建故事状况
            createIssueStoryStatus(projectId, sprintId, target);
            //创建任务状况
            createIssueTaskStatus(projectId, sprintId, target);
        }
    }


    /**
     * 创建工作项-用户故事状况
     */
    private void createIssueStoryStatus(Long projectId, Long sprintId, Date target) {
        //获取状态
        IssueStatus status = statusMapper.getStoryStatus(sprintId);
        //查询当前记录是否存在，存在则修改，不存在则新增
        Byte issueType = IssueTypeEnum.TYPE_STORY.CODE;
        IssueStatus currentStatus = issueStatusService.getBySprintAndDate(sprintId, target, issueType);
        if (currentStatus == null) {
            IssueStatus issueStatus = new IssueStatus();
            issueStatus.setSprintDate(target);
            issueStatus.setProjectId(projectId);
            issueStatus.setSprintId(sprintId);
            issueStatus.setFinished(status.getFinished());
            issueStatus.setInSprint(status.getInSprint());
            issueStatus.setNotStarted(status.getNotStarted());
            issueStatus.setFinishedStoryPoint(status.getFinishedStoryPoint());
            issueStatus.setIssueType(issueType);
            issueStatusService.create(issueStatus);
        } else {
            currentStatus.setFinished(status.getFinished());
            currentStatus.setInSprint(status.getInSprint());
            currentStatus.setNotStarted(status.getNotStarted());
            currentStatus.setFinishedStoryPoint(status.getFinishedStoryPoint());
            issueStatusService.update(currentStatus);
        }
    }
    /**
     * 创建工作项-任务状况
     */
    private void createIssueTaskStatus(Long projectId, Long sprintId, Date target) {
        //获取状态
        IssueStatus status = statusMapper.getTaskStatus(sprintId);
        //查询当前记录是否存在，存在则修改，不存在则新增
        Byte issueType = IssueTypeEnum.TYPE_STORY.CODE;
        IssueStatus currentStatus = issueStatusService.getBySprintAndDate(sprintId, target, issueType);
        if (currentStatus == null) {
            IssueStatus issueStatus = new IssueStatus();
            issueStatus.setSprintDate(target);
            issueStatus.setProjectId(projectId);
            issueStatus.setSprintId(sprintId);
            issueStatus.setFinished(status.getFinished());
            issueStatus.setInSprint(status.getInSprint());
            issueStatus.setNotStarted(status.getNotStarted());
            issueStatus.setReallyWorkload(status.getReallyWorkload());
            issueStatus.setPlanWorkload(status.getPlanWorkload());
            issueStatus.setIssueType(issueType);
            issueStatusService.create(issueStatus);
        } else {
            currentStatus.setFinished(status.getFinished());
            currentStatus.setInSprint(status.getInSprint());
            currentStatus.setNotStarted(status.getNotStarted());
            currentStatus.setReallyWorkload(status.getReallyWorkload());
            currentStatus.setPlanWorkload(status.getPlanWorkload());
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
        createIssueProjectStatus(projectId, target, IssueTypeEnum.TYPE_EPIC.CODE);
        //创建研发需求状况
        createIssueProjectStatus(projectId, target, IssueTypeEnum.TYPE_FEATURE.CODE);
        //创建故事状况
        createIssueProjectStatus(projectId, target, IssueTypeEnum.TYPE_STORY.CODE);
        //创建任务状况
        createIssueProjectStatus(projectId, target, IssueTypeEnum.TYPE_TASK.CODE);
    }


    /**
     * 创建工作项状况
     */
    private void createIssueProjectStatus(Long projectId, Date target, Byte issueType) {
        Integer finished = 0;
        Integer insprint = 0;
        Integer notStarted = 0;
        if (issueType.equals(IssueTypeEnum.TYPE_TASK.CODE)) {
            finished = issueMapper.countFinishedTasks4Project(null, projectId);
            insprint = issueMapper.countInsprintTaskBySprint(null, projectId);
            notStarted = issueMapper.countNotStartTaskBySprint(null, projectId);
        } else {
            finished = issueMapper.countAchievedIssues4Sprint(null, projectId, issueType);
            insprint = issueMapper.countInsprintIssuesBySprint(null, projectId, issueType);
            notStarted = issueMapper.countNotStartIssuesBySprint(null, projectId, issueType);
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
