package com.yusys.agile.issue.service.impl;

import com.google.common.collect.Lists;
import com.yusys.agile.actionlog.service.SActionLogService;
import com.yusys.agile.burndown.dao.BurnDownChartDao;
import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.consumer.constant.AgileConstant;
import com.yusys.agile.consumer.dto.IssueMailSendDto;
import com.yusys.agile.externalapiconfig.dao.util.ExternalApiConfigUtil;
import com.yusys.agile.headerfield.enums.IsCustomEnum;
import com.yusys.agile.issue.dao.IssueHistoryRecordMapper;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.StoryCreatePrepInfoDTO;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.service.TaskService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.issue.utils.IssueHistoryRecordFactory;
import com.yusys.agile.issue.utils.IssueRuleFactory;
import com.yusys.agile.issue.utils.IssueUpRegularFactory;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.set.stage.service.IStageService;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintV3.dto.SprintV3DTO;
import com.yusys.agile.sprintV3.dto.SprintV3UserHourDTO;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.dao.SSprintUserHourMapper;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintExample;
import com.yusys.agile.sprintv3.domain.SSprintUserHour;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.enums.SprintStatusEnum;
import com.yusys.agile.sprintv3.responseModel.SprintMembersWorkHours;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.teamv3.dao.STeamMemberMapper;
import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.agile.teamv3.domain.STeamMemberExample;
import com.yusys.agile.teamv3.enums.TeamRoleEnum;
import com.yusys.agile.teamv3.response.QueryTeamResponse;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.agile.utils.ObjectUtil;
import com.yusys.agile.issue.enums.*;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.dto.SsoSystemDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Date: 13:33
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private IssueMapper issueMapper;

    @Resource
    private IssueFactory issueFactory;

    @Resource
    private BurnDownChartDao burnDownChartDao;

    @Resource
    private IssueHistoryRecordMapper issueHistoryRecordMapper;

    @Resource
    private IssueRuleFactory ruleFactory;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private SSprintMapper sSprintMapper;

    @Resource
    private Teamv3Service teamv3Service;

    @Resource
    private Sprintv3Service sprintv3Service;

    @Autowired
    private SActionLogService logService;

    @Autowired
    private IFacadeUserApi iFacadeUserApi;

    @Autowired
    private IFacadeSystemApi iFacadeSystemApi;

    @Autowired
    private SSprintUserHourMapper sSprintUserHourMapper;
    @Autowired
    private STeamMemberMapper sTeamMemberMapper;
    @Autowired
    private IssueService issueService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTask(Long taskId, Boolean deleteChild) {
        Issue issue = issueMapper.selectByPrimaryKey(taskId);
        if (null != issue) {
            IssueDTO issueDTO = ReflectUtil.copyProperties(issue, IssueDTO.class);
            //校验权限
            this.checkAuth(issueDTO, issue, "delete", "无法删除任务");
        }
        issueFactory.deleteIssue(taskId, deleteChild);

        Long kanbanId = issue.getKanbanId();
        if (Optional.ofNullable(kanbanId).isPresent()) {
            issueService.updateTaskParentStatus(issue.getIssueId(), kanbanId);
        } else {
            int i = issueFactory.updateStoryLaneIdByTaskCount(issue);
            log.info("deleteTask_updateStoryStageIdByTaskCount=" + i);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editTask(IssueDTO issueDTO, SecurityDTO securityDTO) {
        Issue oldTask = issueMapper.selectByPrimaryKey(issueDTO.getIssueId());
        if (null == oldTask) {
            return;
        }
        //校验权限
        this.checkAuth(issueDTO, oldTask, "edit", "无法编辑任务");
        Long projectId = oldTask.getProjectId();
        Issue task = issueFactory.editIssue(issueDTO, oldTask, projectId);

        if (IssueStateEnum.TYPE_VALID.CODE.equals(oldTask.getState())) {
            Long[] stages = issueDTO.getStages();
            if (null != stages) {
                issueDTO.setStageId(stages[0]);
                if (stages.length > 1) {
                    issueDTO.setLaneId(stages[1]);
                } else {
                    issueDTO.setLaneId(null);
                }
                if (!ObjectUtil.equals(oldTask.getLaneId(), issueDTO.getLaneId())) {
                    //创建任务状态变更历史记录
                    createIssueHistoryRecords(oldTask.getLaneId(), issueDTO.getLaneId(), oldTask);
                }
            }
        }

        //如果任务状态是未领取状态和已领取，编辑任务时,剩余工作量等于预计工作量 多了一项实际工时
        if (null != task && (TaskStatusEnum.TYPE_ADD_STATE.CODE.equals(issueDTO.getLaneId()) || TaskStatusEnum.TYPE_RECEIVED_STATE.CODE.equals(issueDTO.getLaneId()))) {
            //实际工时
            task.setReallyWorkload(issueDTO.getReallyWorkload());
            task.setRemainWorkload(0);
        }

        //如果任务拖到进行中和已完成，多了一项剩余工时
        if (null != task && (TaskStatusEnum.TYPE_CLOSED_STATE.CODE.equals(issueDTO.getLaneId()) || TaskStatusEnum.TYPE_MODIFYING_STATE.CODE.equals(issueDTO.getLaneId()))) {
            task.setRemainWorkload(issueDTO.getRemainWorkload());
            task.setReallyWorkload(0);
        }

        //如果任务在已领取中编辑，剩余工作量置为预计工作量
        /*if (null != task && TaskStatusEnum.TYPE_RECEIVED_STATE.CODE.equals(issueDTO.getStageId())) {
            task.setRemainWorkload(task.getPlanWorkload());
            task.setReallyWorkload(0);
        }*/

        //如果任务拖到已完成，剩余工作量置为0
        /*if (null != task && TaskStatusEnum.TYPE_CLOSED_STATE.CODE.equals(issueDTO.getStageId()) && !TaskStatusEnum.TYPE_CLOSED_STATE.CODE.equals(oldTask.getStageId())) {
            task.setRemainWorkload(0);
            task.setReallyWorkload(issueDTO.getPlanWorkload());
        }*/
        //如果任务拖到进行中，剩余工作量置为预计工作量
        /*if (null != task && TaskStatusEnum.TYPE_MODIFYING_STATE.CODE.equals(issueDTO.getStageId()) && !TaskStatusEnum.TYPE_MODIFYING_STATE.CODE.equals(oldTask.getStageId())) {
            task.setRemainWorkload(task.getPlanWorkload());
            task.setReallyWorkload(0);
        }*/
        /*if (null != task && TaskStatusEnum.TYPE_CLOSED_STATE.CODE.equals(oldTask.getStageId()) && !TaskStatusEnum.TYPE_CLOSED_STATE.CODE.equals(issueDTO.getStageId())) {
            task.setRemainWorkload(task.getPlanWorkload());
        }*/

        int count;
        count = issueMapper.updateByPrimaryKeySelectiveWithNull(task);
        if (count != 1) {
            throw new BusinessException("更新任务失败！");
        }

        Long kanbanId = issueDTO.getKanbanId();
        if (Optional.ofNullable(kanbanId).isPresent()) {
            issueService.updateTaskParentStatus(issueDTO.getIssueId(), kanbanId);
        } else {
            int i = issueFactory.updateStoryLaneIdByTaskCount(task);
            log.info("editTask_updateStoryStageIdByTaskCound=" + i);
        }


        // 拖到完成
        /*if (null != task && TaskStatusEnum.TYPE_CLOSED_STATE.CODE.equals(issueDTO.getStageId()) && !TaskStatusEnum.TYPE_CLOSED_STATE.CODE.equals(oldTask.getStageId())) {
        }*/

    }

    private void checkAuth(IssueDTO issueDTO, Issue oldTask, String checkType, String errorMsg) {
        //        校验权限 1.SM角色，可以更新卡片上的任意信息
        //                2.团队成员角色，只允许更新领取人为自己的卡片信息
        //根据task获得team，根据team及当前登录人员进行判断：
        Long storyId = oldTask.getParentId();
        if (Optional.ofNullable(storyId).isPresent()) {
            Issue issue = issueMapper.selectByPrimaryKey(storyId);
            if (!Optional.ofNullable(issue).isPresent()) {
                return;
            }
            SSprintWithBLOBs sSprintWithBLOBs = sSprintMapper.selectByPrimaryKey(issue.getSprintId());
            if (!Optional.ofNullable(sSprintWithBLOBs).isPresent()) {
                return;
            }
            //校验参数
            this.ckeckTaksParams(sSprintWithBLOBs.getSprintId(), errorMsg, checkType);
            Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
            boolean isSM = iFacadeUserApi.checkIsTeamSm(userId, sSprintWithBLOBs.getTeamId());
            //查询该迭代下的成员
            List<SprintV3UserHourDTO> sprintUsers = sprintv3Service.getUsersBySprintId(sSprintWithBLOBs.getSprintId());
            //1.SM角色，可以更新卡片上的任意信息
            if (isSM) {
                return;
            } else if (CollectionUtils.isEmpty(sprintUsers)) {
                throw new BusinessException("您无权限对此功能进行操作");
                //2.当卡片没有领取时可以自己领取该卡片,且必须是是本人领取
            } else if ("edit".equals(checkType)
                    && Optional.ofNullable(oldTask.getHandler()).isPresent()
                    && !userId.equals(oldTask.getHandler())) {
                throw new BusinessException("当前任务已被他人领取,不允许修改");
            } else if (("edit".equals(checkType)
                    && !Optional.ofNullable(oldTask.getHandler()).isPresent()
                    && Optional.ofNullable(issueDTO.getHandler()).isPresent()
                    && !userId.equals(issueDTO.getHandler()))
                    || ("create".equals(checkType)
                    && Optional.ofNullable(issueDTO.getHandler()).isPresent()
                    && !userId.equals(issueDTO.getHandler()))) {
                throw new BusinessException("只有SM角色才允许分配任务");
            } else if ("delete".equals(checkType)
                    && Optional.ofNullable(oldTask.getHandler()).isPresent()
                    && !userId.equals(oldTask.getHandler())) {
                throw new BusinessException("当前任务已被他人领取,不允许删除");
            }
        }
    }

    @Override
    public IssueDTO queryTask(Long taskId) {
        Long systemId = UserThreadLocalUtil.getUserInfo().getSystemId();
        return issueFactory.queryIssue(taskId, systemId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTask(IssueDTO issueDTO) {
        Issue issue = ReflectUtil.copyProperties(issueDTO, Issue.class);
        this.checkAuth(issueDTO, issue, "create", "无法新建任务");
        //设置默认创建
        Long[] stages = issueDTO.getStages();
        if (!Optional.ofNullable(stages).isPresent()) {
            stages = new Long[2];
            stages[0] = StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue();
            stages[1] = TaskStatusEnum.TYPE_ADD_STATE.CODE;
        }
        issueDTO.setStages(stages);
        Long taskId = issueFactory.createIssue(issueDTO, "任务名称已存在！", "新增任务", IssueTypeEnum.TYPE_TASK.CODE);
        Issue task = Optional.ofNullable(issueMapper.selectByPrimaryKey(taskId)).orElseThrow(() -> new BusinessException("任务不存在，taskId=" + taskId));

        Long kanbanId = task.getKanbanId();
        if (Optional.ofNullable(kanbanId).isPresent()) {
            issueService.updateTaskParentStatus(taskId, kanbanId);
        } else {
            int i = issueFactory.updateStoryLaneIdByTaskCount(task);
            log.info("createTask_updateStoryStageIdByTaskCount=" + i);
        }

        return taskId;
    }

    private List<STeamMember> checkIsTeamMember(Long teamId, Long userId) {
        STeamMemberExample sTeamMemberExample = new STeamMemberExample();
        sTeamMemberExample.createCriteria().andTeamIdEqualTo(teamId).andRoleIdEqualTo(TeamRoleEnum.TEAM_MEMBER.getRoleId());
        List<STeamMember> sTeamMembers = sTeamMemberMapper.selectByExample(sTeamMemberExample);
        List<STeamMember> isTeamUsers = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(sTeamMembers)) {
            isTeamUsers = sTeamMembers.stream().filter(x -> userId.equals(x.getUserId())).collect(Collectors.toList());
        }
        return isTeamUsers;
    }

    private void ckeckTaksParams(Long sprintId, String errorMsg, String checkType) {
        //不允许新建删除  允许修改，
        //需要将删除权限加上。已完成／迭代结束日期《当前时间的迭代，不允许删除任务
        if (!Optional.ofNullable(sprintId).isPresent() || "edit".equals(checkType)) {
            return;
        }
        SSprintExample sSprintExample = new SSprintExample();
        sSprintExample.createCriteria().andSprintIdEqualTo(sprintId)
                .andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        List<SSprint> sSprintList = sSprintMapper.selectByExample(sSprintExample);
        if (CollectionUtils.isNotEmpty(sSprintList)) {
            SSprint sSprint = sSprintList.get(0);
            if (sSprint.getStatus().equals(SprintStatusEnum.TYPE_FINISHED_STATE.CODE)) {
                throw new BusinessException("迭代已完成" + errorMsg);
            }
            boolean compareTime = LocalDateTime.now().isAfter(LocalDateTime.ofInstant(sSprint.getEndTime().toInstant(), ZoneId.systemDefault()));
            if (compareTime) {
                throw new BusinessException("迭代结束日期 < 当前时间 " + errorMsg);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long copyTask(Long taskId) {
        Long issue = issueFactory.copyIssue(taskId, "该复制的任务已失效！", "任务名称已存在！", "新增任务", IssueTypeEnum.TYPE_TASK.CODE);

        Issue task = Optional.ofNullable(issueMapper.selectByPrimaryKey(issue)).orElseThrow(() -> new BusinessException("任务不存在，taskId=" + issue));

        int i = issueFactory.updateStoryLaneIdByTaskCount(task);
        log.info("copyTask_updateStoryStageIdByTaskCount=" + i);
        return issue;
    }

    @Override
    public int cancel4Story(Long storyId) {
        IssueExample issueExample = new IssueExample();
        IssueExample.Criteria criteria = issueExample.createCriteria();
        criteria.andParentIdEqualTo(storyId).andStateEqualTo(StateEnum.U.getValue());
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        List<IssueHistoryRecord> records = new ArrayList<>();
        int i = 0;
        if (CollectionUtils.isEmpty(issues)) {
            return i;
        } else {
            for (Issue issue : issues) {
                if (null != issue.getIssueId()) {
                    IssueHistoryRecord nameHistory = new IssueHistoryRecord();
                    nameHistory.setOldValue("任务在迭代中");
                    nameHistory.setNewValue("任务移除迭代");
                    burnDownChartDao.cancel(issue.getIssueId().toString());
                    i = issueMapper.updateStatusByParentId(storyId);
                    createIssueHistoryRecords(records);
                }
            }
        }
        return i;
    }

    @Override
    public void createIssueHistoryRecords(List<IssueHistoryRecord> records) {
        int i;
        if (CollectionUtils.isNotEmpty(records)) {
            for (IssueHistoryRecord historyRecord : records) {
                i = issueHistoryRecordMapper.insert(historyRecord);
                if (i != 1) {
                    throw new BusinessException("新增历史记录失败！");
                }
            }
        }
    }

    @Override
    public List<IssueDTO> queryUnlinkedTask(Long systemId, Integer pageNum, Integer pageSize, String title) {
        return issueFactory.queryUnlinkedIssue(systemId, IssueTypeEnum.TYPE_TASK.CODE, pageNum, pageSize, title, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Issue dragTask(Long issueId, Long from, Long to, Long userId) {
        if (null == issueId || null == from || null == to) {
            throw new BusinessException("入参为空！");
        }
        // 先判断任务是否存在
        Issue task = issueMapper.selectByPrimaryKey(issueId);
        if (task == null || !IssueTypeEnum.TYPE_TASK.CODE.equals(task.getIssueType())
                || !StateEnum.U.getValue().equalsIgnoreCase(task.getState())) {
            throw new BusinessException("不存在任务!");
        }

        // 拖拽的from必须和数据库中一致
        if (!from.equals(task.getLaneId()) || from.equals(to)) {
            throw new BusinessException("拖拽状态不正确或与原始状态不一致！");
        }
        /** 判断工作项流转规则是否允许 */
        Byte issueType = task.getIssueType();
        if (!ruleFactory.getIssueRulesCheckFlag(issueType, task.getLaneId(), null, to, null, task.getProjectId())) {
            throw new BusinessException("该工作项不允许流转到目标阶段！");
        }
        Long loginUserId = UserThreadLocalUtil.getUserInfo().getUserId();
        //Long loginUserId=807906052370849792L;
       /* if (null != loginUserId) {
            task.setHandler(loginUserId);
        }*/
        //根据task获得team，根据team及当前登录人员进行判断：
        SprintV3DTO sprintDTO1 = sprintv3Service.viewEdit(task.getSprintId());
        if (sprintDTO1 == null) {
            throw new BusinessException("根据迭代标识获取迭代信息为空" + task.getSprintId());
        }

        List<SprintMembersWorkHours> sprintMembersWorkHours = sprintv3Service.sprintMembersWorkHours(task.getSprintId());

        if (sprintMembersWorkHours == null || sprintMembersWorkHours.size() == 0) {
            throw new BusinessException("根据迭代标识获取迭代成员信息为空" + task.getSprintId());
        }

        QueryTeamResponse queryTeamResponse = teamv3Service.queryTeam(sprintDTO1.getTeamId());
        //判断当前登录人员是否为sm
        long smCount = Optional.ofNullable(queryTeamResponse.getTeamSmS()).orElse(new ArrayList<>()).
                stream().
                filter(teamUserDTO -> teamUserDTO.getUserId().equals(loginUserId)).count();

        //团队成员，暂时注释掉
        /*
        long memCount = Optional.ofNullable(queryTeamResponse.getTeamUsers()).orElse(new ArrayList<>())
                .stream()
                .filter(teamUserDTO -> teamUserDTO.getUserId().equals(loginUserId)).count();

         */

        long memCount = Optional.ofNullable(sprintMembersWorkHours).orElse(new ArrayList<>())
                .stream()
                .filter(sprintMen -> sprintMen.getUserId() == loginUserId.longValue()).count();


        long poCount = Optional.ofNullable(queryTeamResponse.getTeamPoS()).orElse(new ArrayList<>())
                .stream()
                .filter(teamUserDTO -> teamUserDTO.getUserId().equals(loginUserId)).count();
        log.info("团队人员信息smCount" + smCount + " memCount" + memCount + " poCount" + poCount + " userId" + userId + " loginUserId" + loginUserId);
        String actionRemark = "";
        if (poCount > 0 && memCount == 0) {
            throw new BusinessException("对于PO，不允许修改任务信息");
        }
        if (smCount > 0) {
            //：对于SM角色  当前任务的团队的sm
            //1）SM可以拖动看板下的任意卡片，当卡片已被团队成员领取时，拖动时不改变卡片领取人信息，当卡片从未领取拖动其他状态列时，未指定领取人时，需要提示：SM拖动卡片需要指定领取人
            //需要弹框，指定卡片领取人，需要预研交互

            if (TaskStatusEnum.TYPE_ADD_STATE.CODE.equals(task.getLaneId()) && userId == null && memCount == 0) {
                throw new BusinessException("SM拖动卡片需要指定领取人");
            }
            //sm自己领任务
            if (TaskStatusEnum.TYPE_ADD_STATE.CODE.equals(task.getLaneId()) && userId == null && memCount > 0) {
                task.setLaneId(to);
                task.setHandler(loginUserId);
                actionRemark = "sm自己领任务";
            } else {//sm指派或非指派拖拽
                task.setLaneId(to);
                task.setHandler(task.getHandler());
                task.setAssessRemarks(task.getAssessRemarks() + "by s");
                if (userId != null && userId > 0) {//sm指派任务的情况
                    task.setHandler(userId);
                }
                actionRemark += "sm指派或非指派拖拽";
            }
        } else if (memCount > 0) {
            //：对于团队成员角色：
            /*2、对于团队成员角色：
            1）团队成员角色可以将卡片从未领取拖到任意状态列，卡片状态根据卡片所在的状态列进行更新，卡片领取人，为拖动的团队成员
            2）当卡片从其他状态列拖动到未领取时，卡片领取人需要清除
            3）在非未领取状态列，团队成员只允许拖动领取人为自己的任务卡片，否则给出提示：当前任务已被他人领取，不允许拖动*/
            if (TaskStatusEnum.TYPE_ADD_STATE.CODE.equals(task.getLaneId())) {
                task.setLaneId(to);
                task.setHandler(loginUserId);
            } else if (!TaskStatusEnum.TYPE_ADD_STATE.CODE.equals(task.getLaneId()) && !loginUserId.equals(task.getHandler())) {
                throw new BusinessException("当前任务已被他人领取，不允许拖动!");
            }
        } else {
            throw new BusinessException("不是团队成员不允许该操作!");
        }

        if (!TaskStatusEnum.TYPE_ADD_STATE.CODE.equals(from) && TaskStatusEnum.TYPE_ADD_STATE.CODE.equals(to)) {
            task.setLaneId(to);
            task.setHandler(null);
            actionRemark += "领取人需要清除";
        }

        //上面是特殊情况，这里兜底
        if (!task.getLaneId().equals(to)) {
            task.setLaneId(to);
            if (task.getHandler() == null) {
                task.setHandler(loginUserId);
            }
        }


        //如果任务拖到已完成，剩余工作量置为0
        if (TaskStatusEnum.TYPE_CLOSED_STATE.CODE.equals(to)) {
            task.setRemainWorkload(0);
            task.setReallyWorkload(task.getPlanWorkload());
        }
        //如果任务拖到进行中，剩余工作量置为预计工作量
        if (TaskStatusEnum.TYPE_MODIFYING_STATE.CODE.equals(to)) {
            task.setRemainWorkload(task.getPlanWorkload());
        }
        if (null != task && TaskStatusEnum.TYPE_CLOSED_STATE.CODE.equals(task.getLaneId()) && !TaskStatusEnum.TYPE_CLOSED_STATE.CODE.equals(to)) {
            task.setRemainWorkload(task.getPlanWorkload());
        }
        //创建历史记录
        createIssueHistoryRecords(from, to, task);
        // 修改数据库
        int taskCount = issueMapper.updateByPrimaryKey(task);

        if (TaskStatusEnum.TYPE_CLOSED_STATE.CODE.equals(to)) {

            Long storyId = task.getParentId();
            // 故事的上层feature规整
            //rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, storyId);

            //先注释，目前迭代没有这部分内容  by dushan
            //issueUpRegularFactory.commonIssueUpRegular(storyId);
//            // feature的上层也就是epic规整
//            Long featureId = faultSyncService.getParentIdByIssueId(storyId);
//            if(null != featureId){
//                rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, featureId);
//            }

        }

        //  根据故事id查询有效的、未完成的任务，如果为0，则更新故事为完成，否则 进行中。
        int storyCount = issueFactory.updateStoryLaneIdByTaskCount(task);

        logService.insertLog("dragTask", issueId, IssueTypeEnum.TYPE_TASK.CODE.longValue(), actionRemark + "from=" + TaskStatusEnum.getName(from) + from
                + " to=" + TaskStatusEnum.getName(to) + to + " storyCount=" + storyCount + " taskCount=" + taskCount, "1");

        //发送邮件通知
        SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
        IssueMailSendDto issueMailSendDto = new IssueMailSendDto(task, NumberConstant.THREE, userInfo);
        rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE, issueMailSendDto);

        return task;
    }

    @Override
    public List<IssueDTO> queryTaskForStory(Long projectId, Byte issuType, Long storyId) {
        return issueFactory.queryAllIssue(projectId, issuType, null, null, null, storyId);
    }

    @Override
    public List<IssueDTO> queryFaultForStory(Long projectId, Byte code, Long storyId) {
        return null;
    }

    @Override
    public List<IssueDTO> queryAllTask(Long projectId, Integer pageNum, Integer pageSize, String title) {
        return issueFactory.queryAllIssue(projectId, IssueTypeEnum.TYPE_TASK.CODE, null, null, null, null);
    }

    @Override
    public List<Long> listStoryIdsByTaskIds(List<Long> taskIds) {
        if (CollectionUtils.isEmpty(taskIds)) {
            return new ArrayList<>();
        }
        // 去重
        Set<Long> storyIds = new HashSet<>();
        for (Long taskId : taskIds) {
            Issue task = issueMapper.selectByPrimaryKey(taskId);
            if (null != task && null != task.getParentId()) {
                storyIds.add(task.getParentId());
            }
        }
        return new ArrayList<>(storyIds);
    }

    /**
     * 3。 列表中从故事拆分任务
     * 执行人：如果故事上有迭代，从迭代内选人，没有迭代从系统选人
     * 系统：当前系统
     * 迭代：前端不采集，后端以故事所属迭代为准
     * 4。 从kanban新建任务
     * 执行人：迭代内成员
     * 系统：传入当前故事下系统
     * 迭代：前端不采集，后端以故事所属迭代为准
     *
     * @param systemId
     * @param storyId
     * @param createType
     * @return
     */
    @Override
    public StoryCreatePrepInfoDTO getTaskPreInfo(String userName, Integer page, Integer pageSize, Long systemId, Long storyId, Integer createType) {
        StoryCreatePrepInfoDTO storyCreatePrepInfoDTO = new StoryCreatePrepInfoDTO();
        Issue issue = issueMapper.selectByPrimaryKey(storyId);
        Long sysId = null;
        Long sprintId = issue.getSprintId();
        if (CreateTypeEnum.LIST.CODE.equals(createType)) {
            if (Optional.ofNullable(issue.getSprintId()).isPresent()) {
                //执行人：如果故事上有迭代，从迭代内选人，
                try {
                    List<SsoUserDTO> ssoUserDTOList = this.querySpringtUsersBySprintId(sprintId, userName, page, pageSize);
                    storyCreatePrepInfoDTO.setUserDTOS(ssoUserDTOList);
                } catch (Exception e) {
                    log.error("数据转换异常");
                }
                //没有迭代从系统选人
            } else {
                //人员信息
                List<SsoUserDTO> ssoUserDTOS = iFacadeUserApi.queryUsersByCondition(userName, page, pageSize, UserThreadLocalUtil.getUserInfo().getSystemId());
                storyCreatePrepInfoDTO.setUserDTOS(ssoUserDTOS);
            }
            sysId = UserThreadLocalUtil.getUserInfo().getSystemId();
        } else if (CreateTypeEnum.KANBAN.CODE.equals(createType)) {
            //迭代内人员
            List<SsoUserDTO> ssoUserDTOList = this.querySpringtUsersBySprintId(sprintId, userName, page, pageSize);
            storyCreatePrepInfoDTO.setUserDTOS(ssoUserDTOList);
            //系统信息
            sysId = Optional.ofNullable(issue.getSystemId()).orElse(systemId);
        }
        //迭代：前端不采集，后端以故事所属迭代为准,故事下可能没有迭代
        if (Optional.ofNullable(sprintId).isPresent()) {
            SSprintWithBLOBs sSprintWithBLOBs = sSprintMapper.selectByPrimaryKey(sprintId);
            List<SprintListDTO> sprintListDTOs = Lists.newArrayList();
            SprintListDTO sprintListDTO = ReflectUtil.copyProperties(sSprintWithBLOBs, SprintListDTO.class);
            sprintListDTOs.add(sprintListDTO);
            storyCreatePrepInfoDTO.setSprintDTOS(sprintListDTOs);
        }
        SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(sysId);
        SsoSystemDTO ssoSystemDTO = ReflectUtil.copyProperties(ssoSystem, SsoSystemDTO.class);
        List<SsoSystemDTO> ssoSystemDTOS = Lists.newArrayList();
        ssoSystemDTOS.add(ssoSystemDTO);
        storyCreatePrepInfoDTO.setSystemDTOS(ssoSystemDTOS);
        return storyCreatePrepInfoDTO;
    }

    private List<SsoUserDTO> querySpringtUsersBySprintId(Long sprintId, String userName, Integer pageNum, Integer pageSize) {
        List<SsoUserDTO> ssoUserDTOList = Lists.newArrayList();
        List<SSprintUserHour> userSprintHours = sSprintUserHourMapper.getUserIds4Sprint(sprintId);
        if (CollectionUtils.isEmpty(userSprintHours)) {
            return ssoUserDTOList;
        }
        List<Long> userIds = userSprintHours.stream().map(SSprintUserHour::getUserId).distinct().collect(Collectors.toList());
        return iFacadeUserApi.queryUsersByUserIdsAndConditions(userIds, pageNum, pageSize, userName);
    }

    /**
     * @param from
     * @param to
     * @param task
     * @Date 2021/2/15
     * @Description 任务卡片拖动增加历史记录
     * @Return java.util.List<com.yusys.agile.issue.domain.IssueHistoryRecord>
     */
    private List<IssueHistoryRecord> createIssueHistoryRecords(Long from, Long to, Issue task) {
        List<IssueHistoryRecord> records = new ArrayList<>();
        IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(
                task.getIssueId(), IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.STAGEID.getDesc());
        if (null != task.getIssueId()) {
            nameHistory.setOldValue(TaskStatusEnum.getName(from));
            if (to.equals(TaskStatusEnum.TYPE_RECEIVED_STATE.CODE)) {
                nameHistory.setNewValue("任务已领取");
            }
            if (to.equals(TaskStatusEnum.TYPE_MODIFYING_STATE.CODE)) {
                nameHistory.setNewValue("任务开发中");
            }
            if (to.equals(TaskStatusEnum.TYPE_CLOSED_STATE.CODE)) {
                nameHistory.setNewValue("任务开发完成");
            }
        }
        records.add(nameHistory);
        createIssueHistoryRecords(records);
        return records;
    }


    /**
     * @param from
     * @param to
     * @param story
     * @Date 2021/7/02
     * @Description 任务卡片拖动增加历史记录
     * @Return java.util.List<com.yusys.agile.issue.domain.IssueHistoryRecord>
     */
    @Override
    public List<IssueHistoryRecord> createIssueHistoryRecordsForStory(Long from, Long to, Issue story) {
        if(story==null||from==null||to==null||to==from){
            return null;
        }

        List<IssueHistoryRecord> records = new ArrayList<>();
        IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(
                story.getIssueId(), IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.STAGEID.getDesc());
        if (null != story.getIssueId()) {
            nameHistory.setOldValue(StoryStatusEnum.getName(from));
            if (null != story.getIssueId() && Optional.ofNullable(to).isPresent()) {
                nameHistory.setOldValue(StoryStatusEnum.getName(from));
                nameHistory.setNewValue(StoryStatusEnum.getName(to));
            }
            records.add(nameHistory);
            createIssueHistoryRecords(records);
        }
        return records;
    }

}
