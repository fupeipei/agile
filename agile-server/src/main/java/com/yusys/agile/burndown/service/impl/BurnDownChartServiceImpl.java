package com.yusys.agile.burndown.service.impl;

import com.google.common.collect.Lists;
import com.yusys.agile.burndown.dao.BurnDownChartDao;
import com.yusys.agile.burndown.dao.BurnDownChartStoryDao;
import com.yusys.agile.burndown.domain.BurnDownChart;
import com.yusys.agile.burndown.domain.BurnDownChartStory;
import com.yusys.agile.burndown.dto.*;
import com.yusys.agile.burndown.service.BurnDownChartService;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.domain.SprintWithBLOBs;
import com.yusys.agile.sprint.service.SprintService;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeProjectApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SsoProjectDTO;
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
    /*
        新写的迭代mapper
     */
    @Resource
    private SSprintMapper sSprintMapper;
    @Resource
    private Sprintv3Service sprintv3Service;
    @Resource
    private SprintMapper sprintMapper;
    @Resource
    private IssueMapper issueMapper;
    @Resource
    private SprintService sprintService;
    @Resource
    private IFacadeUserApi iFacadeUserApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void calculateWorkload() {
        //查询所有迭代
        List<SSprintWithBLOBs> sSprints  = sprintv3Service.querySprintList();
        if (CollectionUtils.isNotEmpty(sSprints)) {
            for (SSprintWithBLOBs sprint : sSprints) {
                if (sprint != null) {
                    calculateWorkload(sprint);
                }
            }
        }
    }

    private void calculateWorkload(SSprintWithBLOBs sprint) {
        Long sprintId = sprint.getSprintId();
        Date target = DateUtil.preDay(new Date());
        if (sprintv3Service.legalDate(sprint.getSprintDays(), target)) {
            List<Issue> tasks = issueMapper.getBySprint(sprintId);
            if (CollectionUtils.isNotEmpty(tasks)) {
                for (Issue task : tasks) {
                    if (task != null) {
                        int planWorkload = Optional.ofNullable(task.getPlanWorkload()).orElse(0);
                        int remainWorkload = Optional.ofNullable(task.getRemainWorkload()).orElse(0);
                        Long laneId = task.getLaneId();
                        BurnDownChart chart = generateChart(sprintId, target, planWorkload - remainWorkload,
                                task.getIssueId(), laneId);
                        burnDownChartDao.create(chart);
                    }
                }
            } else {
                BurnDownChart chart = generateChart(sprintId, target, 0, -1L, -1L);
                burnDownChartDao.create(chart);
            }
        }
    }

    private BurnDownChart generateChart(Long sprintId, Date sprintTime, int finishedWorkload,
                                        Long taskId, Long taskState) {
        BurnDownChart chart = new BurnDownChart();
        chart.setSprintId(sprintId);
        chart.setSprintTime(sprintTime);
        chart.setFinishedWorkload(finishedWorkload);
        chart.setTaskId(taskId.toString());
        if (null != taskState) {
            chart.setTaskState(taskState.byteValue());
        }
        return chart;
    }

    private BurnDownChart generateChart(Long sprintId, Date sprintTime, int remainWorkload) {
        BurnDownChart chart = new BurnDownChart();
        chart.setSprintId(sprintId);
        chart.setSprintTime(sprintTime);
        chart.setRemainWorkload(remainWorkload);
        return chart;
    }

    private BurnDownTask generateTask(Date sprintTime, int remainTask) {
        BurnDownTask task = new BurnDownTask();
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
    private BurnDownStory generateStory(Date sprintTime, int remainStory) {
        BurnDownStory story = new BurnDownStory();
        story.setSprintTime(sprintTime);
        story.setRemainStory(remainStory);
        return story;
    }

    /**
     * 创建每日剩余故事数
     * @param sprintId
     * @param sprintTime
     * @param storyId
     * @param storyState
     * @return
     */
    private BurnDownChartStory generateChartStory(Long sprintId, Date sprintTime, Long storyId, Long storyState) {
        BurnDownChartStory chartStory = new BurnDownChartStory();
        chartStory.setSprintId(sprintId);
        chartStory.setSprintTime(sprintTime);
        chartStory.setStoryId(storyId.toString());
        if (null != storyState) {
            chartStory.setStoryState(storyState.byteValue());
        }
        return chartStory;
    }

    @Override
    public BurnDownChartDTO getBySprint(Long sprintId) {
        BurnDownChartDTO burnDownChartDTO = new BurnDownChartDTO();
        // 预估工作量
        burnDownChartDTO.setPlanWorkload(sprintService.getWorkload(sprintId));
        Integer actualRemainWorkload = issueMapper.getPlanWorkload(sprintId);
        // 实际剩余工作量
        burnDownChartDTO.setActualRemainWorkload(actualRemainWorkload);
        // 每天的剩余工作量
        burnDownChartDTO.setWorkloads(getWorkloads(sprintId, actualRemainWorkload));
        return burnDownChartDTO;
    }

    @Override
    public BurnDownTaskDTO getTasksBySprint(Long sprintId) {
        BurnDownTaskDTO burnDownTaskDTO = new BurnDownTaskDTO();
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
    public BurnDownStoryDTO getStorysBySprint(Long sprintId) {
        BurnDownStoryDTO burnDownStoryDTO = new BurnDownStoryDTO();
        Integer actualRemainStory = issueMapper.countStories4Sprint(sprintId);
        burnDownStoryDTO.setPlanStory(actualRemainStory);
        burnDownStoryDTO.setActualRemainStory(actualRemainStory);
        burnDownStoryDTO.setStorys(getStorys(sprintId, actualRemainStory));
        return burnDownStoryDTO;
    }

    @Override
    public BurnDownStoryPointDTO getStoryPointBySprint(Long sprintId) {
        SSprintWithBLOBs sprint = sSprintMapper.selectByPrimaryKey(sprintId);
        //如果迭代不存在，抛出异常
        Optional.ofNullable(sprint).orElseThrow(()-> new BusinessException("迭代计划不存在"));
        //查询出迭代下故事点数的总数
        Integer count = issueMapper.countStoryPointsForSprint(sprintId);
        BurnDownStoryPointDTO storyPointDTO = new BurnDownStoryPointDTO();
        /*
         * 设置计划故事点数、实际故事点数、迭代日期剩余故事点数
         */
        storyPointDTO.setPlanStoryPoint(count == null ? 0 : count);
        storyPointDTO.setActualRemainStoryPoint(count == null ? 0 : count);
        storyPointDTO.setStoryPoints(getStoryPoints(sprint, count == null ? 0 : count));
        return storyPointDTO;
    }
    /**
     * 获取迭代日期对应的剩余故事点数
     * @author zhaofeng
     * @date 2021/5/26 14:47
     * @param sprint    当前迭代
     * @param count     迭代下故事点数的总数
     */
    private List<BurnDownStoryPoint> getStoryPoints(SSprintWithBLOBs sprint, Integer count) {
        //获取迭代周期、迭代开始时间、迭代结束时间
        Long sprintId = sprint.getSprintId();
        String sprintDays = sprint.getSprintDays();
        Date startTime = sprint.getStartTime();
        Date endTime = sprint.getEndTime();
        List<BurnDownStoryPoint> rest  = Lists.newArrayList();
        if (null != startTime && null != endTime) {
            /*
             * 当开始日期小于等于结束日期时，判断这个日期是否在迭代内
             * 如果存在，则获取剩余的故事点数，
             * 获取剩余故事点数的逻辑：按当前日期查询是否有完成的故事、如果有则得到当前日期完成的故事总数currCount
             * 然后递减 count-=currCount，将count赋值到 DTO中，如果没有查询到，那就直接将count赋值到 DTO中
             * 最后当前日期+1
             */
            while (DateUtil.compare(endTime, startTime)) {
                if (legalDate(sprintDays, startTime)) {
                    BurnDownStoryPoint burnDownStoryPoint = new BurnDownStoryPoint();
                    burnDownStoryPoint.setSprintTime(DateUtil.formatDate(startTime));
                    //查询当前startTime完成的故事点总数
                    Integer currCount = issueMapper.countCurrTimeStoryPointsForSprintId(sprintId, startTime);
                    if(currCount != null){
                        count -= currCount;
                    }
                    //如果startTime > 今天 则不赋值
                    if(!startTime.after(DateUtil.currentDay())){
                        burnDownStoryPoint.setRemainStoryPoint(count);
                    }
                    rest.add(burnDownStoryPoint);
                }
                startTime = DateUtil.nextDay(startTime);
            }
        }
        return rest;
    }
    public boolean legalDate(String sprintDays, Date target) {
        List<Date> dateList = convertStrToDate(sprintDays);
        return DateUtil.isBetween(dateList, target);
    }
    public List<Date> convertStrToDate(String str) {
        if (null == str || str.isEmpty()) {
            return new ArrayList<>();
        }
        List<Date> dateList = new ArrayList<>();
        String[] dateArray = str.split("\\|");
        for (int i = 0; i < dateArray.length; i++) {
            long longVaule = Long.parseLong(dateArray[i]);
            dateList.add(new Date(longVaule));
        }
        return dateList;
    }
    /**
     * 计算每日剩余故事数
     */
    @Override
    public void calculateStorys() {
        //查询所有迭代
        List<SSprintWithBLOBs> sSprints  = sprintv3Service.querySprintList();
        if (CollectionUtils.isNotEmpty(sSprints)) {
            for (SSprintWithBLOBs sprint : sSprints) {
                if (sprint != null) {
                    calculateStorys(sprint);
                }
            }
        }
    }

    @Override
    public List<HistogramTaskDTO> getTaskMemberAnalysis(Long sprintId) {
        List<HistogramTaskDTO> taskDTOList;
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

//    public void calculateStorys(List<SprintWithBLOBs> sprints) {
//        if (CollectionUtils.isNotEmpty(sprints)) {
//            for (SprintWithBLOBs sprint : sprints) {
//                if (sprint != null) {
//                    calculateStorys(projectId, sprint);
//                }
//            }
//        }
//    }

    private void calculateStorys(SSprintWithBLOBs sprint) {
        Long sprintId = sprint.getSprintId();
        Date target = DateUtil.preDay(new Date());
        if (sprintService.legalDate(sprint.getSprintDays(), target)) {
            List<Issue> stories = issueMapper.getStoryBySprintId(sprintId);
            if (CollectionUtils.isNotEmpty(stories)) {
                for (Issue story : stories) {
                    if (story != null) {
                        Long laneId = story.getLaneId();
                        BurnDownChartStory burnDownChartStory = generateChartStory(sprintId, target, story.getIssueId(), laneId);
                        burnDownChartStoryDao.create(burnDownChartStory);
                    }
                }
            } else {
                BurnDownChartStory chartStory = generateChartStory(sprintId, target, -1L, -1L);
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
    private List<BurnDownStory> getStorys(Long sprintId, Integer actualRemainStory) {
        SSprintWithBLOBs sprint = sSprintMapper.selectByPrimaryKey(sprintId);
        Optional.ofNullable(sprint).orElseThrow(() -> new BusinessException("迭代计划不存在"));
        String sprintDays = sprint.getSprintDays();
        Date start = sprint.getStartTime();
        Date end = sprint.getEndTime();
        /** 获得故事的迭代有效日期*/
        //取迭代日期
        List<BurnDownStory> stories = getStorys(start, end, sprintDays);

        List<BurnDownStory> inSprintStorys = getSprintStorys(sprintId, actualRemainStory, sprintDays);

        BurnDownStory currentStory = getCurrentStory(sprintId, sprintDays);
        setRemainStorys(stories, inSprintStorys, currentStory, actualRemainStory);
        return stories;
    }

    private List<BurnDownTask> getTasks(Long sprintId, Integer actualRemainTask) {
        SSprintWithBLOBs sprint = sSprintMapper.selectByPrimaryKey(sprintId);
        Optional.ofNullable(sprint).orElseThrow(() -> new BusinessException("迭代计划不存在"));
        String sprintDays = sprint.getSprintDays();
        Date start = sprint.getStartTime();
        Date end = sprint.getEndTime();
        List<BurnDownTask> tasks = getTasks(start, end, sprintDays);
        List<BurnDownTask> inSprintTasks = getSprintTasks(sprintId, actualRemainTask, sprintDays);
        BurnDownTask currentTask = getCurrentTask(sprintId, sprintDays);
        setRemainTasks(tasks, inSprintTasks, currentTask, actualRemainTask);
        return tasks;
    }

    private List<BurnDownWorkloadDTO> getWorkloads(Long sprintId, Integer actualRemainWorkload) {
        SSprintWithBLOBs sprint = sSprintMapper.selectByPrimaryKey(sprintId);
        String sprintDays = sprint.getSprintDays();
        Date start = sprint.getStartTime();
        Date end = sprint.getEndTime();
        List<BurnDownWorkloadDTO> workloads = getWorkloads(start, end, sprintDays);
        List<BurnDownWorkloadDTO> inSprintWorkloads = getSprintWorkloads(sprintId, actualRemainWorkload,
                sprintDays);
        BurnDownWorkloadDTO currentWorkload = getCurrentWorkload(sprintId,sprintDays);
        setRemainWorkload(workloads, inSprintWorkloads, currentWorkload);
        return workloads;
    }

    private void setRemainWorkload(List<BurnDownWorkloadDTO> workloads, List<BurnDownWorkloadDTO> inSprintWorkloads,
                                   BurnDownWorkloadDTO currentWorkload) {
        if (CollectionUtils.isNotEmpty(workloads)) {
            Iterator<BurnDownWorkloadDTO> workloadIt = workloads.iterator();
            if (CollectionUtils.isNotEmpty(inSprintWorkloads)) {
                for (BurnDownWorkloadDTO sprintWorkload : inSprintWorkloads) {
                    while (workloadIt.hasNext()) {
                        BurnDownWorkloadDTO workload = workloadIt.next();
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
    private void setRemainStorys(List<BurnDownStory> storys, List<BurnDownStory> inSprintStorys,
                                 BurnDownStory currentStory, Integer actualRemainStory) {
        if (storys != null && !storys.isEmpty()) {
            Iterator<BurnDownStory> storyIt = storys.iterator();
            while (storyIt.hasNext()) {
               BurnDownStory story = storyIt.next();
                if (story.getSprintTime().compareTo(new Date()) > 0) {
                    break;
                }
                if (CollectionUtils.isNotEmpty(inSprintStorys)) {
                    for (BurnDownStory sprintStory : inSprintStorys) {
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
    private List<BurnDownWorkloadDTO> getSprintWorkloads(Long sprintId, Integer actualRemainWorkload, String sprintDays) {
        List<BurnDownWorkloadDTO> workloads = Lists.newArrayList();
        List<BurnDownChart> inSprint = getInSprint(sprintId, actualRemainWorkload);
        if (CollectionUtils.isNotEmpty(inSprint)) {
            for (BurnDownChart chart : inSprint) {
                if (null != chart && sprintv3Service.legalDate(sprintDays, chart.getSprintTime())) {
                    BurnDownWorkloadDTO workload = new BurnDownWorkloadDTO();
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
    private List<BurnDownTask> getSprintTasks(Long sprintId, Integer planTask,
                                                                           String sprintDays) {
        List<BurnDownTask> tasks = Lists.newArrayList();
        List<BurnDownTask> inSprint = burnDownChartDao.getTasksBySprint(sprintId, planTask);
        if (CollectionUtils.isNotEmpty(inSprint)) {
            for (BurnDownTask task : inSprint) {
                if (null != task && sprintv3Service.legalDate(sprintDays, task.getSprintTime())) {
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
    private List<BurnDownStory> getSprintStorys(Long sprintId, Integer planStory, String sprintDays) {
        List<BurnDownStory> stories = Lists.newArrayList();
        List<BurnDownStory> inSprint = burnDownChartStoryDao.getStorysBySprint(sprintId, planStory);
        if (CollectionUtils.isNotEmpty(inSprint)) {
            for (BurnDownStory story : inSprint) {
                if (null != story && sprintService.legalDate(sprintDays, story.getSprintTime())) {
                    BurnDownStory tempStory = new BurnDownStory();
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
    private BurnDownWorkloadDTO getCurrentWorkload(Long sprintId,String sprintDays) {
        BurnDownChart current = getCurrent(sprintId,sprintDays);
        if (null != current) {
           BurnDownWorkloadDTO workload = new BurnDownWorkloadDTO();
            workload.setSprintTime(current.getSprintTime());
            workload.setRemainWorkload(current.getRemainWorkload());
            return workload;
        }
        return null;
    }

    /**
     * 当日的剩余任务量
     */
    private BurnDownTask getCurrentTask(Long sprintId, String sprintDays) {
        if (sprintv3Service.legalDate(sprintDays, DateUtil.getTodayZeroTime())) {
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
    private BurnDownStory getCurrentStory(Long sprintId, String sprintDays) {
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
    private List<BurnDownWorkloadDTO> getWorkloads(Date start, Date end, String sprintDays) {
        List<BurnDownWorkloadDTO> workloads = Lists.newArrayList();
        if (null != start && null != end) {
            while (DateUtil.compare(end, start)) {
                if (sprintv3Service.legalDate(sprintDays, start)) {
                    BurnDownWorkloadDTO workload = new BurnDownWorkloadDTO();
                    workload.setSprintTime(DateUtil.formatDate(start));
                    workloads.add(workload);
                }
                start = DateUtil.nextDay(start);
            }
        }
        return workloads;
    }

    private List<BurnDownTask> getTasks(Date start, Date end, String sprintDays) {
        List<BurnDownTask> tasks = Lists.newArrayList();
        if (null != start && null != end) {
            while (DateUtil.compare(end, start)) {
                if (sprintv3Service.legalDate(sprintDays, start)) {
                    BurnDownTask task = new BurnDownTask();
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
    private List<BurnDownStory> getStorys(Date start, Date end, String sprintDays) {
        List<BurnDownStory> stories = new ArrayList<>();
        if (null != start && null != end) {
            while (DateUtil.compare(end, start)) {
                if (sprintService.legalDate(sprintDays, start)) {
                    BurnDownStory burnDownStory = new BurnDownStory();
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

    private BurnDownChart getCurrent(Long sprintId,String sprintDays) {
        if (sprintv3Service.legalDate(sprintDays, DateUtil.getTodayZeroTime())) {
            int currentWorkload = issueMapper.getRemainWorkload(sprintId);
            return generateChart(sprintId, new Date(), currentWorkload);
        }
        return null;
    }
}
