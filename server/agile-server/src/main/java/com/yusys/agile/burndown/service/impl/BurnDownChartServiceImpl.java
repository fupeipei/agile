package com.yusys.agile.burndown.service.impl;

import com.yusys.agile.burndown.dao.BurnDownChartDao;
import com.yusys.agile.burndown.dao.BurnDownChartStoryDao;
import com.yusys.agile.burndown.domain.BurnDownChart;
import com.yusys.agile.burndown.domain.BurnDownChartStory;
import com.yusys.agile.burndown.dto.BurnDownStory;
import com.yusys.agile.burndown.service.BurnDownChartService;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.domain.SprintWithBLOBs;
import com.yusys.agile.sprint.service.SprintService;
import com.google.common.collect.Lists;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeProjectApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SsoProjectDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.date.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BurnDownChartServiceImpl implements BurnDownChartService {
    @Resource
    private BurnDownChartDao burnDownChartDao;
    @Resource
    private BurnDownChartStoryDao burnDownChartStoryDao;
    @Resource
    private IFacadeProjectApi iFacadeProjectApi;
    @Resource
    private SprintMapper sprintMapper;
    @Resource
    private IssueMapper issueMapper;
    @Resource
    private SprintService sprintService;
    @Resource
    private IFacadeUserApi iFacadeUserApi;

    @Override
    @Transactional
    public void calculateWorkload() {
        ControllerResponse<List<SsoProjectDTO>> controllerResponse = iFacadeProjectApi.listAllProjectsNoPage("");
        List<SsoProjectDTO> projects = controllerResponse.getData();
        if (CollectionUtils.isNotEmpty(projects)) {
            for (SsoProjectDTO project : projects) {
                if (project != null) {
                    List<SprintWithBLOBs> sprints = sprintMapper.getByProjectId(project.getProjectId());
                    calculateWorkload(sprints, project.getProjectId());
                }
            }
        }
    }

    public void calculateWorkload(List<SprintWithBLOBs> sprints, Long projectId) {
        if (CollectionUtils.isNotEmpty(sprints)) {
            for (SprintWithBLOBs sprint : sprints) {
                if (sprint != null) {
                    calculateWorkload(projectId, sprint);
                }
            }
        }
    }

    private void calculateWorkload(Long projectId, SprintWithBLOBs sprint) {
        Long sprintId = sprint.getSprintId();
        Date target = DateUtil.preDay(new Date());
        if (sprintService.legalDate(sprint.getSprintDays(), target)) {
            List<Issue> tasks = issueMapper.getBySprint(sprintId);
            if (CollectionUtils.isNotEmpty(tasks)) {
                for (Issue task : tasks) {
                    if (task != null) {
                        int planWorkload = Optional.ofNullable(task.getPlanWorkload()).orElse(0);
                        int remainWorkload = Optional.ofNullable(task.getRemainWorkload()).orElse(0);
                        Long stageId = task.getStageId();
                        BurnDownChart chart = generateChart(projectId, sprintId, target, planWorkload - remainWorkload,
                                task.getIssueId(), stageId);
                        burnDownChartDao.create(chart);
                    }
                }
            } else {
                BurnDownChart chart = generateChart(projectId, sprintId, target, 0, -1L, -1L);
                burnDownChartDao.create(chart);
            }
        }
    }

    private BurnDownChart generateChart(Long projectId, Long sprintId, Date sprintTime, int finishedWorkload,
                                        Long taskId, Long taskState) {
        BurnDownChart chart = new BurnDownChart();
        chart.setProjectId(projectId);
        chart.setSprintId(sprintId);
        chart.setSprintTime(sprintTime);
        chart.setFinishedWorkload(finishedWorkload);
        chart.setTaskId(taskId.toString());
        if (null != taskState) {
            chart.setTaskState(taskState.byteValue());
        }
        return chart;
    }

    private BurnDownChart generateChart(Long projectId, Long sprintId, Date sprintTime, int remainWorkload) {
        BurnDownChart chart = new BurnDownChart();
        chart.setProjectId(projectId);
        chart.setSprintId(sprintId);
        chart.setSprintTime(sprintTime);
        chart.setRemainWorkload(remainWorkload);
        return chart;
    }

    private com.yusys.agile.burndown.dto.BurnDownTask generateTask(Date sprintTime, int remainTask) {
        com.yusys.agile.burndown.dto.BurnDownTask task = new com.yusys.agile.burndown.dto.BurnDownTask();
        task.setSprintTime(sprintTime);
        task.setRemainTask(remainTask);
        return task;
    }

    /**
     * 获得当前迭代时间以及对应剩余的故事数
     *
     * @param sprintTime
     * @param remainStory
     * @return
     */
    private com.yusys.agile.burndown.dto.BurnDownStory generateStory(Date sprintTime, int remainStory) {
        com.yusys.agile.burndown.dto.BurnDownStory story = new com.yusys.agile.burndown.dto.BurnDownStory();
        story.setSprintTime(sprintTime);
        story.setRemainStory(remainStory);
        return story;
    }

    /**
     * 创建每日剩余故事数
     *
     * @param projectId
     * @param sprintId
     * @param sprintTime
     * @param storyId
     * @param storyState
     * @return
     */
    private BurnDownChartStory generateChartStory(Long projectId, Long sprintId, Date sprintTime, Long storyId, Long storyState) {
        BurnDownChartStory chartStory = new BurnDownChartStory();
        chartStory.setProjectId(projectId);
        chartStory.setSprintId(sprintId);
        chartStory.setSprintTime(sprintTime);
        chartStory.setStoryId(storyId.toString());
        if (null != storyState) {
            chartStory.setStoryState(storyState.byteValue());
        }
        return chartStory;
    }

    @Override
    public com.yusys.agile.burndown.dto.BurnDownChartDTO getBySprint(Long sprintId) {
        com.yusys.agile.burndown.dto.BurnDownChartDTO burnDownChartDTO = new com.yusys.agile.burndown.dto.BurnDownChartDTO();
        // 预估工作量
        burnDownChartDTO.setPlanWorkload(sprintService.getWorkload(sprintId));
        Integer actualRemainWorkload = sprintMapper.getPlanWorkload(sprintId);
        // 实际剩余工作量
        burnDownChartDTO.setActualRemainWorkload(actualRemainWorkload);
        // 每天的剩余工作量
        burnDownChartDTO.setWorkloads(getWorkloads(sprintId, actualRemainWorkload));
        return burnDownChartDTO;
    }

    @Override
    public com.yusys.agile.burndown.dto.BurnDownTaskDTO getTasksBySprint(Long sprintId) {
        com.yusys.agile.burndown.dto.BurnDownTaskDTO burnDownTaskDTO = new com.yusys.agile.burndown.dto.BurnDownTaskDTO();
        Integer actualRemainTask = issueMapper.countTasks4Sprint(sprintId);
        burnDownTaskDTO.setActualRemainTask(actualRemainTask);
        burnDownTaskDTO.setPlanTask(actualRemainTask);
        burnDownTaskDTO.setTasks(getTasks(sprintId, actualRemainTask));
        return burnDownTaskDTO;
    }

    /**
     * 通过迭代id得到当前迭代中每日剩余故事数
     *
     * @param sprintId
     * @return
     */
    @Override
    public com.yusys.agile.burndown.dto.BurnDownStoryDTO getStorysBySprint(Long sprintId) {
        com.yusys.agile.burndown.dto.BurnDownStoryDTO burnDownStoryDTO = new com.yusys.agile.burndown.dto.BurnDownStoryDTO();
        Integer actualRemainStory = issueMapper.countStories4Sprint(sprintId);
        burnDownStoryDTO.setPlanStory(actualRemainStory);
        burnDownStoryDTO.setActualRemainStory(actualRemainStory);
        burnDownStoryDTO.setStorys(getStorys(sprintId, actualRemainStory));
        return burnDownStoryDTO;
    }

    /**
     * 计算每日剩余故事数
     */
    @Override
    public void calculateStorys() {
        ControllerResponse<List<SsoProjectDTO>> controllerResponse = iFacadeProjectApi.listAllProjectsNoPage("");
        List<SsoProjectDTO> projects = controllerResponse.getData();
        if (CollectionUtils.isNotEmpty(projects)) {
            for (SsoProjectDTO project : projects) {
                if (project != null) {
                    List<SprintWithBLOBs> sprints = sprintMapper.getByProjectId(project.getProjectId());
                    calculateStorys(sprints, project.getProjectId());
                }
            }
        }
    }

    @Override
    public List<com.yusys.agile.burndown.dto.HistogramTaskDTO> getTaskMemberAnalysis(Long sprintId) {
        List<com.yusys.agile.burndown.dto.HistogramTaskDTO> taskDTOList;
        taskDTOList = issueMapper.getTaskMemberAnalysis(sprintId);
        if (CollectionUtils.isNotEmpty(taskDTOList)) {
            taskDTOList.forEach(histogramTaskDTO -> {
                long handler = histogramTaskDTO.getHandler();
                if (handler == 1) {
                    histogramTaskDTO.setHandlerName("未领取");
                } else {
                    SsoUser ssoUserDTO = iFacadeUserApi.queryUserById(histogramTaskDTO.getHandler());
                    if (Optional.ofNullable(ssoUserDTO).isPresent()) {
                        histogramTaskDTO.setHandlerName(ssoUserDTO.getUserName());
                    }
                }

            });
        }
        return taskDTOList;
    }

    public void calculateStorys(List<SprintWithBLOBs> sprints, Long projectId) {
        if (CollectionUtils.isNotEmpty(sprints)) {
            for (SprintWithBLOBs sprint : sprints) {
                if (sprint != null) {
                    calculateStorys(projectId, sprint);
                }
            }
        }
    }

    private void calculateStorys(Long projectId, SprintWithBLOBs sprint) {
        Long sprintId = sprint.getSprintId();
        Date target = DateUtil.preDay(new Date());
        if (sprintService.legalDate(sprint.getSprintDays(), target)) {
            List<Issue> stories = issueMapper.getStoryBySprintId(sprintId);
            if (CollectionUtils.isNotEmpty(stories)) {
                for (Issue story : stories) {
                    if (story != null) {
                        Long stageId = story.getStageId();
                        BurnDownChartStory burnDownChartStory = generateChartStory(projectId, sprintId, target, story.getIssueId(), stageId);
                        burnDownChartStoryDao.create(burnDownChartStory);
                    }
                }
            } else {
                BurnDownChartStory chartStory = generateChartStory(projectId, sprintId, target, -1L, -1L);
                burnDownChartStoryDao.create(chartStory);
            }
        }
    }

    /**
     * 获得当前迭代中每一天的剩余故事数
     *
     * @param sprintId
     * @param actualRemainStory
     * @return
     */
    private List<com.yusys.agile.burndown.dto.BurnDownStory> getStorys(Long sprintId, Integer actualRemainStory) {
        SprintWithBLOBs sprint = sprintMapper.selectByPrimaryKey(sprintId);
        Optional.ofNullable(sprint).orElseThrow(() -> new BusinessException("迭代计划不存在"));
        String sprintDays = sprint.getSprintDays();
        Date start = sprint.getStartTime();
        Date end = sprint.getEndTime();
        /** 获得故事的迭代有效日期*/
        List<com.yusys.agile.burndown.dto.BurnDownStory> stories = getStorys(start, end, sprintDays);
        List<com.yusys.agile.burndown.dto.BurnDownStory> inSprintStorys = getSprintStorys(sprintId, actualRemainStory, sprintDays);
        com.yusys.agile.burndown.dto.BurnDownStory currentStory = getCurrentStory(sprintId, sprintDays);
        setRemainStorys(stories, inSprintStorys, currentStory, actualRemainStory);
        return stories;
    }

    private List<com.yusys.agile.burndown.dto.BurnDownTask> getTasks(Long sprintId, Integer actualRemainTask) {
        SprintWithBLOBs sprint = sprintMapper.selectByPrimaryKey(sprintId);
        Optional.ofNullable(sprint).orElseThrow(() -> new BusinessException("迭代计划不存在"));
        String sprintDays = sprint.getSprintDays();
        Date start = sprint.getStartTime();
        Date end = sprint.getEndTime();
        List<com.yusys.agile.burndown.dto.BurnDownTask> tasks = getTasks(start, end, sprintDays);
        List<com.yusys.agile.burndown.dto.BurnDownTask> inSprintTasks = getSprintTasks(sprintId, actualRemainTask, sprintDays);
        com.yusys.agile.burndown.dto.BurnDownTask currentTask = getCurrentTask(sprintId, sprintDays);
        setRemainTasks(tasks, inSprintTasks, currentTask, actualRemainTask);
        return tasks;
    }

    private List<com.yusys.agile.burndown.dto.BurnDownWorkloadDTO> getWorkloads(Long sprintId, Integer actualRemainWorkload) {
        SprintWithBLOBs sprint = sprintMapper.selectByPrimaryKey(sprintId);
        String sprintDays = sprint.getSprintDays();
        Date start = sprint.getStartTime();
        Date end = sprint.getEndTime();
        Long projectId = sprint.getProjectId();
        List<com.yusys.agile.burndown.dto.BurnDownWorkloadDTO> workloads = getWorkloads(start, end, sprintDays);
        List<com.yusys.agile.burndown.dto.BurnDownWorkloadDTO> inSprintWorkloads = getSprintWorkloads(sprintId, actualRemainWorkload,
                sprintDays);
        com.yusys.agile.burndown.dto.BurnDownWorkloadDTO currentWorkload = getCurrentWorkload(sprintId, projectId, sprintDays);
        setRemainWorkload(workloads, inSprintWorkloads, currentWorkload);
        return workloads;
    }

    private void setRemainWorkload(List<com.yusys.agile.burndown.dto.BurnDownWorkloadDTO> workloads, List<com.yusys.agile.burndown.dto.BurnDownWorkloadDTO> inSprintWorkloads,
                                   com.yusys.agile.burndown.dto.BurnDownWorkloadDTO currentWorkload) {
        if (CollectionUtils.isNotEmpty(workloads)) {
            Iterator<com.yusys.agile.burndown.dto.BurnDownWorkloadDTO> workloadIt = workloads.iterator();
            if (CollectionUtils.isNotEmpty(inSprintWorkloads)) {
                for (com.yusys.agile.burndown.dto.BurnDownWorkloadDTO sprintWorkload : inSprintWorkloads) {
                    while (workloadIt.hasNext()) {
                        com.yusys.agile.burndown.dto.BurnDownWorkloadDTO workload = workloadIt.next();
                        if (workload != null && sprintWorkload != null
                                && DateUtil.equalsByDay(workload.getSprintTime(), sprintWorkload.getSprintTime())) {
                            workload.setRemainWorkload(sprintWorkload.getRemainWorkload());
                            break;
                        }
                    }
                }
            }
            if (currentWorkload != null) {
                while (workloadIt.hasNext()) {
                    com.yusys.agile.burndown.dto.BurnDownWorkloadDTO workload = workloadIt.next();
                    if (workload != null
                            && DateUtil.equalsByDay(workload.getSprintTime(), currentWorkload.getSprintTime())) {
                        workload.setRemainWorkload(currentWorkload.getRemainWorkload());
                        break;
                    }
                }
            }
        }
    }

    /**
     * 设置剩余故事数
     *
     * @param storys
     * @param inSprintStorys
     * @param currentStory
     * @param actualRemainStory
     */
    private void setRemainStorys(List<com.yusys.agile.burndown.dto.BurnDownStory> storys, List<com.yusys.agile.burndown.dto.BurnDownStory> inSprintStorys,
                                 com.yusys.agile.burndown.dto.BurnDownStory currentStory, Integer actualRemainStory) {
        if (storys != null && !storys.isEmpty()) {
            Iterator<com.yusys.agile.burndown.dto.BurnDownStory> storyIt = storys.iterator();
            while (storyIt.hasNext()) {
                com.yusys.agile.burndown.dto.BurnDownStory story = storyIt.next();
                if (story.getSprintTime().compareTo(new Date()) > 0) {
                    break;
                }
                if (CollectionUtils.isNotEmpty(inSprintStorys)) {
                    for (com.yusys.agile.burndown.dto.BurnDownStory sprintStory : inSprintStorys) {
                        if (story != null && sprintStory != null && DateUtil.equalsByDay(story.getSprintTime(), sprintStory.getSprintTime())) {
                            story.setRemainStory(sprintStory.getRemainStory());
                            break;
                        }
                    }
                }
                if (null == story.getRemainStory()) {
                    story.setRemainStory(actualRemainStory);
                }

                if (null != currentStory && null != story && DateUtil.equalsByDay(story.getSprintTime(), currentStory.getSprintTime())) {
                    story.setRemainStory(currentStory.getRemainStory());
                    break;
                }
            }
        }
    }

    private void setRemainTasks(List<com.yusys.agile.burndown.dto.BurnDownTask> tasks, List<com.yusys.agile.burndown.dto.BurnDownTask> inSprintTasks,
                                com.yusys.agile.burndown.dto.BurnDownTask currentTask, Integer actualRemainTask) {
        if (CollectionUtils.isNotEmpty(tasks)) {
            Iterator<com.yusys.agile.burndown.dto.BurnDownTask> taskIt = tasks.iterator();
            while (taskIt.hasNext()) {
                com.yusys.agile.burndown.dto.BurnDownTask task = taskIt.next();
                if (task.getSprintTime().compareTo(new Date()) > 0) {
                    break;
                }
                if (CollectionUtils.isNotEmpty(inSprintTasks)) {
                    for (com.yusys.agile.burndown.dto.BurnDownTask sprintTask : inSprintTasks) {
                        if (null != task && null != sprintTask
                                && DateUtil.equalsByDay(task.getSprintTime(), sprintTask.getSprintTime())) {
                            task.setRemainTask(sprintTask.getRemainTask());
                            break;
                        }
                    }
                }
                if (null == task.getRemainTask()) {
                    task.setRemainTask(actualRemainTask);
                }
                if (null != currentTask && null != task && DateUtil.equalsByDay(task.getSprintTime(), currentTask.getSprintTime())) {
                    task.setRemainTask(currentTask.getRemainTask());
                    break;
                }
            }
        }
    }

    /**
     * 迭代中的剩余工作量
     */
    private List<com.yusys.agile.burndown.dto.BurnDownWorkloadDTO> getSprintWorkloads(Long sprintId, Integer actualRemainWorkload, String sprintDays) {
        List<com.yusys.agile.burndown.dto.BurnDownWorkloadDTO> workloads = Lists.newArrayList();
        List<BurnDownChart> inSprint = getInSprint(sprintId, actualRemainWorkload);
        if (CollectionUtils.isNotEmpty(inSprint)) {
            for (BurnDownChart chart : inSprint) {
                if (null != chart && sprintService.legalDate(sprintDays, chart.getSprintTime())) {
                    com.yusys.agile.burndown.dto.BurnDownWorkloadDTO workload = new com.yusys.agile.burndown.dto.BurnDownWorkloadDTO();
                    workload.setSprintTime(chart.getSprintTime());
                    workload.setRemainWorkload(chart.getRemainWorkload());
                    workloads.add(workload);
                }
            }
        }
        return workloads;
    }

    /**
     * 迭代中的剩余任务量
     */
    private List<com.yusys.agile.burndown.dto.BurnDownTask> getSprintTasks(Long sprintId, Integer planTask,
                                                                           String sprintDays) {
        List<com.yusys.agile.burndown.dto.BurnDownTask> tasks = Lists.newArrayList();
        List<com.yusys.agile.burndown.dto.BurnDownTask> inSprint = burnDownChartDao.getTasksBySprint(sprintId, planTask);
        if (CollectionUtils.isNotEmpty(inSprint)) {
            for (com.yusys.agile.burndown.dto.BurnDownTask task : inSprint) {
                if (null != task && sprintService.legalDate(sprintDays, task.getSprintTime())) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    /**
     * 当前迭代中的剩余故事数
     *
     * @param sprintId
     * @param planStory
     * @param sprintDays
     * @return
     */
    private List<com.yusys.agile.burndown.dto.BurnDownStory> getSprintStorys(Long sprintId, Integer planStory, String sprintDays) {
        List<com.yusys.agile.burndown.dto.BurnDownStory> stories = Lists.newArrayList();
        List<com.yusys.agile.burndown.dto.BurnDownStory> inSprint = burnDownChartStoryDao.getStorysBySprint(sprintId, planStory);
        if (CollectionUtils.isNotEmpty(inSprint)) {
            for (com.yusys.agile.burndown.dto.BurnDownStory story : inSprint) {
                if (null != story && sprintService.legalDate(sprintDays, story.getSprintTime())) {
                    com.yusys.agile.burndown.dto.BurnDownStory tempStory = new com.yusys.agile.burndown.dto.BurnDownStory();
                    tempStory.setSprintTime(story.getSprintTime());
                    tempStory.setRemainStory(story.getRemainStory());
                    stories.add(tempStory);
                }
            }
        }
        return stories;
    }

    /**
     * 当日的剩余工作量
     */
    private com.yusys.agile.burndown.dto.BurnDownWorkloadDTO getCurrentWorkload(Long sprintId, Long projectId,
                                                                                String sprintDays) {
        BurnDownChart current = getCurrent(sprintId, projectId, sprintDays);
        if (null != current) {
            com.yusys.agile.burndown.dto.BurnDownWorkloadDTO workload = new com.yusys.agile.burndown.dto.BurnDownWorkloadDTO();
            workload.setSprintTime(current.getSprintTime());
            workload.setRemainWorkload(current.getRemainWorkload());
            return workload;
        }
        return null;
    }

    /**
     * 当日的剩余任务量
     */
    private com.yusys.agile.burndown.dto.BurnDownTask getCurrentTask(Long sprintId, String sprintDays) {
        if (sprintService.legalDate(sprintDays, DateUtil.getTodayZeroTime())) {
            int currentTask = issueMapper.countUnFinishedTasks4Sprint(sprintId);
            return generateTask(new Date(), currentTask);
        }
        return null;
    }

    /**
     * 当日的剩余故事数
     *
     * @param sprintId
     * @param sprintDays
     * @return
     */
    private com.yusys.agile.burndown.dto.BurnDownStory getCurrentStory(Long sprintId, String sprintDays) {
        if (sprintService.legalDate(sprintDays, DateUtil.getTodayZeroTime())) {
            int currentStory = issueMapper.countInsprintBySprint(sprintId);
            return generateStory(new Date(), currentStory);
        }
        return null;
    }

    /**
     * @param start
     * @param end
     * @param sprintDays
     * @return
     */
    private List<com.yusys.agile.burndown.dto.BurnDownWorkloadDTO> getWorkloads(Date start, Date end, String sprintDays) {
        List<com.yusys.agile.burndown.dto.BurnDownWorkloadDTO> workloads = Lists.newArrayList();
        if (null != start && null != end) {
            while (DateUtil.compare(end, start)) {
                if (sprintService.legalDate(sprintDays, start)) {
                    com.yusys.agile.burndown.dto.BurnDownWorkloadDTO workload = new com.yusys.agile.burndown.dto.BurnDownWorkloadDTO();
                    workload.setSprintTime(DateUtil.formatDate(start));
                    workloads.add(workload);
                }
                start = DateUtil.nextDay(start);
            }
        }
        return workloads;
    }

    private List<com.yusys.agile.burndown.dto.BurnDownTask> getTasks(Date start, Date end, String sprintDays) {
        List<com.yusys.agile.burndown.dto.BurnDownTask> tasks = Lists.newArrayList();
        if (null != start && null != end) {
            while (DateUtil.compare(end, start)) {
                if (sprintService.legalDate(sprintDays, start)) {
                    com.yusys.agile.burndown.dto.BurnDownTask task = new com.yusys.agile.burndown.dto.BurnDownTask();
                    task.setSprintTime(DateUtil.formatDate(start));
                    tasks.add(task);
                }
                start = DateUtil.nextDay(start);
            }
        }
        return tasks;
    }

    /**
     * 获得故事的迭代有效日期
     *
     * @param start
     * @param end
     * @param sprintDays
     * @return
     */
    private List<com.yusys.agile.burndown.dto.BurnDownStory> getStorys(Date start, Date end, String sprintDays) {
        List<com.yusys.agile.burndown.dto.BurnDownStory> stories = new ArrayList<>();
        if (null != start && null != end) {
            while (DateUtil.compare(end, start)) {
                if (sprintService.legalDate(sprintDays, start)) {
                    com.yusys.agile.burndown.dto.BurnDownStory burnDownStory = new BurnDownStory();
                    burnDownStory.setSprintTime(DateUtil.formatDate(start));
                    stories.add(burnDownStory);
                }
                start = DateUtil.nextDay(start);
            }
        }
        return stories;
    }

    private List<BurnDownChart> getInSprint(Long sprintId, Integer planWorkload) {
        return burnDownChartDao.getBySprint(sprintId, planWorkload);
    }

    private BurnDownChart getCurrent(Long sprintId, Long projectId, String sprintDays) {
        if (sprintService.legalDate(sprintDays, DateUtil.getTodayZeroTime())) {
            int currentWorkload = sprintMapper.getRemainWorkload(sprintId);
            return generateChart(projectId, sprintId, new Date(), currentWorkload);
        }
        return null;
    }
}
