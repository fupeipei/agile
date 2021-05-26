package com.yusys.agile.issue.service.impl;

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
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.service.TaskService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.issue.utils.IssueHistoryRecordFactory;
import com.yusys.agile.issue.utils.IssueRuleFactory;
import com.yusys.agile.issue.utils.IssueUpRegularFactory;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.set.stage.domain.StageInstance;
import com.yusys.agile.set.stage.service.IStageService;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintExample;
import com.yusys.agile.sprintv3.enums.SprintStatusEnum;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.team.dto.TeamUserDTO;
import com.yusys.agile.teamv3.response.QueryTeamResponse;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.agile.utils.ObjectUtil;
import com.yusys.agile.issue.enums.*;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
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
    private StoryService storyService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private IssueUpRegularFactory issueUpRegularFactory;

    @Resource
    private ExternalApiConfigUtil externalApiConfigUtil;

    @Resource
    private SSprintMapper sSprintMapper;

    @Resource
    private Teamv3Service teamv3Service;

    @Resource
    private Sprintv3Service sprintv3Service;

    @Autowired
    private IStageService stageService;

    @Autowired
    private SActionLogService logService;

    @Autowired
    private IFacadeUserApi iFacadeUserApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTask(Long taskId, Boolean deleteChild) {
        Issue issue = issueMapper.selectByPrimaryKey(taskId);
        if (null != issue) {
            this.ckeckTaksParams(issue.getSprintId(),"无法删除任务");
        }
        issueFactory.deleteIssue(taskId, deleteChild);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editTask(IssueDTO issueDTO,SecurityDTO securityDTO) {
        Issue oldTask = issueMapper.selectByPrimaryKey(issueDTO.getIssueId());
        Boolean isPass = checkAuth(issueDTO, oldTask, securityDTO);
        if (!isPass){
            throw new BusinessException("团队成员角色,只允许更新领取人为自己的卡片信息,但不允许更新领取人");
        }
        //校验参数
        this.ckeckTaksParams(oldTask.getSprintId(),"无法编辑任务");

        Long projectId = oldTask.getProjectId();
        Issue task = issueFactory.editIssue(issueDTO, oldTask, projectId);

        if (IssueStateEnum.TYPE_VALID.CODE.equals(oldTask.getState())) {
            Long[] stages = issueDTO.getStages();
            if (null != stages) {
                issueDTO.setStageId(stages[0]);
                if (!ObjectUtil.equals(oldTask.getLaneId(), issueDTO.getLaneId())) {
                    //创建任务状态变更历史记录
                    createIssueHistoryRecords(oldTask.getLaneId(), issueDTO.getLaneId(), oldTask);
                }
            }
        }

        //如果任务状态是未领取状态和已领取，编辑任务时,剩余工作量等于预计工作量 多了一项实际工时
        if (null != task && (TaskStageIdEnum.TYPE_ADD_STATE.CODE.equals(issueDTO.getLaneId()) || TaskStageIdEnum.TYPE_RECEIVED_STATE.CODE.equals(issueDTO.getLaneId()))) {
            //实际工时
            task.setReallyWorkload(issueDTO.getReallyWorkload());
            task.setRemainWorkload(0);
        }

        //如果任务拖到进行中和已完成，多了一项剩余工时
        if (null != task &&  (TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(issueDTO.getLaneId()) || TaskStageIdEnum.TYPE_MODIFYING_STATE.CODE.equals(issueDTO.getLaneId()))) {
            task.setRemainWorkload(issueDTO.getRemainWorkload());
            task.setReallyWorkload(0);
        }

        //如果任务在已领取中编辑，剩余工作量置为预计工作量
        /*if (null != task && TaskStageIdEnum.TYPE_RECEIVED_STATE.CODE.equals(issueDTO.getStageId())) {
            task.setRemainWorkload(task.getPlanWorkload());
            task.setReallyWorkload(0);
        }*/

        //如果任务拖到已完成，剩余工作量置为0
        /*if (null != task && TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(issueDTO.getStageId()) && !TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(oldTask.getStageId())) {
            task.setRemainWorkload(0);
            task.setReallyWorkload(issueDTO.getPlanWorkload());
        }*/
        //如果任务拖到进行中，剩余工作量置为预计工作量
        /*if (null != task && TaskStageIdEnum.TYPE_MODIFYING_STATE.CODE.equals(issueDTO.getStageId()) && !TaskStageIdEnum.TYPE_MODIFYING_STATE.CODE.equals(oldTask.getStageId())) {
            task.setRemainWorkload(task.getPlanWorkload());
            task.setReallyWorkload(0);
        }*/
        /*if (null != task && TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(oldTask.getStageId()) && !TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(issueDTO.getStageId())) {
            task.setRemainWorkload(task.getPlanWorkload());
        }*/

        int count;
        count = issueMapper.updateByPrimaryKeySelectiveWithNull(task);
        if (count != 1) {
            throw new BusinessException("更新任务失败！");
        }

        // 拖到完成
        /*if (null != task && TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(issueDTO.getStageId()) && !TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(oldTask.getStageId())) {
        }*/

    }

    public Boolean checkAuth(IssueDTO issueDTO,Issue oldTask,SecurityDTO securityDTO){
        //        校验权限 1.SM角色，可以更新卡片上的任意信息
        //                2.团队成员角色，只允许更新领取人为自己的卡片信息
        //根据task获得team，根据team及当前登录人员进行判断：
        SprintDTO sprintDTO = sprintv3Service.viewEdit(issueDTO.getSprintId());
        if(null == sprintDTO.getSprintId()){
            throw new BusinessException("该任务下的迭代不存在"+issueDTO.getSprintId());
        }
        QueryTeamResponse queryTeamResponse = teamv3Service.queryTeam(sprintDTO.getTeamId());
        //该团队下的团队成员
        List<TeamUserDTO> teamUsers = queryTeamResponse.getTeamUsers();
        //该团队下的sm
        List<TeamUserDTO> teamSmS = queryTeamResponse.getTeamSmS();
        int sm = Optional.ofNullable(teamSmS).orElse(new ArrayList<>()).stream().filter(x -> x.getUserId().equals(securityDTO.getUserId())).collect(Collectors.toList()).size();
        int teamMember = Optional.ofNullable(teamUsers).orElse(new ArrayList<>()).stream().filter(x -> x.getUserId().equals(securityDTO.getUserId())).collect(Collectors.toList()).size();
        //1.SM角色，可以更新卡片上的任意信息
        if (sm > 0){
            return Boolean.TRUE;
        //2.团队成员角色，只允许更新领取人为自己的卡片信息
        }else if (teamMember > 0
                && !TaskStageIdEnum.TYPE_ADD_STATE.CODE.equals(oldTask.getLaneId())
                && Optional.ofNullable(oldTask.getHandler()).isPresent()
                && oldTask.getHandler().equals(issueDTO.getHandler())
                && securityDTO.getUserId().equals(oldTask.getHandler())){
            return Boolean.TRUE;
        }else if (sm ==0 && teamMember == 0){
            throw new BusinessException("您无权限进行该操作");
        }else {
            return Boolean.FALSE;
        }
    }



    /*@Override
    public IssueDTO queryTask(Long taskId, Long projectId) {
        return issueFactory.queryIssue(taskId, projectId);
    }*/

    @Override
    public IssueDTO queryTask(Long taskId) {
        Long projectId = issueFactory.getProjectIdByIssueId(taskId);
        return issueFactory.queryIssue(taskId, projectId);
    }

    @Override
    public Long createTask(IssueDTO issueDTO) {
        //设置默认创建
        Long[] stages = issueDTO.getStages();
        if(!Optional.ofNullable(stages).isPresent()){
            stages = new Long[2];
            List<StageInstance> stageInstances = stageService.getSecondStageListByParentId(StageConstant.FirstStageEnum.READY_STAGE.getValue());
            stages[0] = StageConstant.FirstStageEnum.READY_STAGE.getValue();
            //创建用户故事下任务默认放在开发中的就绪阶段、如果关联迭代信息则放在进行中阶段（todo 阶段优化）
            if(CollectionUtils.isNotEmpty(stageInstances)){
                Long sprintId = issueDTO.getSprintId();
                StageInstance stageInstance = Optional.ofNullable(sprintId).isPresent()? stageInstances.get(1):stageInstances.get(0);
                stages[1] = stageInstance.getStageId();
            }
            issueDTO.setStages(stages);
        }else {
            //任务选择处理人就是已领取，否则就是未领取
            if (Optional.ofNullable(issueDTO.getHandler()).isPresent()){
                issueDTO.setStageId(StageConstant.FirstStageEnum.READY_STAGE.getValue());
                issueDTO.setLaneId(TaskStageIdEnum.TYPE_RECEIVED_STATE.CODE);
            }
        }
        return issueFactory.createIssue(issueDTO, "任务名称已存在！", "新增任务", IssueTypeEnum.TYPE_TASK.CODE);
    }

    private void ckeckTaksParams(Long sprintId,String errorMsg) {
        SSprintExample sSprintExample = new SSprintExample();
        sSprintExample.createCriteria().andSprintIdEqualTo(sprintId)
                .andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        List<SSprint> sSprintList = sSprintMapper.selectByExample(sSprintExample);
        if (CollectionUtils.isNotEmpty(sSprintList)) {
            SSprint sSprint = sSprintList.get(0);
            if (sSprint.getStatus().equals(SprintStatusEnum.TYPE_FINISHED_STATE.CODE)) {
                throw new BusinessException("迭代已完成"+errorMsg);
            }
            boolean compareTime = LocalDateTime.now().isAfter(LocalDateTime.ofInstant(sSprint.getEndTime().toInstant(), ZoneId.systemDefault()));
            if (compareTime) {
                throw new BusinessException("迭代结束日期 < 当前时间 "+errorMsg);
            }
        }
    }

    @Override
    public Long copyTask(Long taskId, Long projectId) {
        return issueFactory.copyIssue(taskId, projectId, "该复制的任务已失效！", "任务名称已存在！", "新增任务", IssueTypeEnum.TYPE_TASK.CODE);
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
    public List<IssueDTO> queryUnlinkedTask(Long projectId, Integer pageNum, Integer pageSize, String title) {
        return issueFactory.queryUnlinkedIssue(projectId, IssueTypeEnum.TYPE_TASK.CODE, pageNum, pageSize, title, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dragTask(Long issueId, Long from, Long to,Long userId) {
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
        if (!from.equals(task.getStageId()) || from.equals(to)) {
            throw new BusinessException("拖拽状态不正确或与原始状态不一致！");
        }
        /** 判断工作项流转规则是否允许 */
        Byte issueType = task.getIssueType();
        if (!ruleFactory.getIssueRulesCheckFlag(issueType, task.getStageId(), null, to, null, task.getProjectId())) {
            throw new BusinessException("该工作项不允许流转到目标阶段！");
        }
        Long loginUserId = UserThreadLocalUtil.getUserInfo().getUserId();
        //Long loginUserId=807906052370849792L;
        if (null != loginUserId) {
            task.setHandler(loginUserId);
        }
        //根据task获得team，根据team及当前登录人员进行判断：
        SprintDTO sprintDTO1 = sprintv3Service.viewEdit(task.getSprintId());
        if(sprintDTO1==null){
            throw new BusinessException("根据迭代标识获取迭代信息为空"+task.getSprintId());
        }

        QueryTeamResponse queryTeamResponse = teamv3Service.queryTeam(sprintDTO1.getTeamId());
        //判断当前登录人员是否为sm
        long smCount = Optional.ofNullable(queryTeamResponse.getTeamSmS()).orElse(new ArrayList<>()).
                stream().
                filter(teamUserDTO -> teamUserDTO.getUserId().equals(loginUserId)).count();
        long memCount = Optional.ofNullable(queryTeamResponse.getTeamUsers()).orElse(new ArrayList<>())
                .stream()
                .filter(teamUserDTO -> teamUserDTO.getUserId().equals(loginUserId)).count();
        long poCount = Optional.ofNullable(queryTeamResponse.getTeamPoS()).orElse(new ArrayList<>())
                .stream()
                .filter(teamUserDTO -> teamUserDTO.getUserId().equals(loginUserId)).count();
        log.info("团队人员信息smCount"+smCount+" memCount"+memCount+" poCount"+poCount+" userId"+userId+" loginUserId"+loginUserId);
        String actionRemark="";
        if(poCount>0&&memCount==0){
            throw new BusinessException("对于PO，不允许修改任务信息");
        }
        if(smCount>0){
            //：对于SM角色  当前任务的团队的sm
            //1）SM可以拖动看板下的任意卡片，当卡片已被团队成员领取时，拖动时不改变卡片领取人信息，当卡片从未领取拖动其他状态列时，未指定领取人时，需要提示：SM拖动卡片需要指定领取人
            //需要弹框，指定卡片领取人，需要预研交互

            if(TaskStageIdEnum.TYPE_ADD_STATE.CODE.equals(task.getStageId())&&userId==null&&memCount==0){
                throw new BusinessException("SM拖动卡片需要指定领取人");
            }
            //sm自己领任务
            if(TaskStageIdEnum.TYPE_ADD_STATE.CODE.equals(task.getStageId())&&userId==null&&memCount>0){
                task.setStageId(to);
                task.setHandler(loginUserId);
                actionRemark="sm自己领任务";
            }
            else {//sm指派或非指派拖拽
                task.setStageId(to);
                task.setHandler(task.getHandler());
                task.setAssessRemarks(task.getAssessRemarks()+"by s");
                if(userId!=null&&userId>0){//sm指派任务的情况
                    task.setHandler(userId);
                }
                actionRemark+="sm指派或非指派拖拽";
            }
        }else if(memCount>0){
            //：对于团队成员角色：
            /*2、对于团队成员角色：
            1）团队成员角色可以将卡片从未领取拖到任意状态列，卡片状态根据卡片所在的状态列进行更新，卡片领取人，为拖动的团队成员
            2）当卡片从其他状态列拖动到未领取时，卡片领取人需要清除
            3）在非未领取状态列，团队成员只允许拖动领取人为自己的任务卡片，否则给出提示：当前任务已被他人领取，不允许拖动*/
            if(TaskStageIdEnum.TYPE_ADD_STATE.CODE.equals(task.getStageId())){
                task.setStageId(to);
                task.setHandler(loginUserId);
            }else if(!TaskStageIdEnum.TYPE_ADD_STATE.CODE.equals(task.getStageId())&&!loginUserId.equals(task.getHandler())){
                throw new BusinessException("当前任务已被他人领取，不允许拖动!");
            }
            if(!TaskStageIdEnum.TYPE_ADD_STATE.CODE.equals(task.getStageId())&&TaskStageIdEnum.TYPE_ADD_STATE.CODE.equals(to)){
                task.setStageId(to);
                task.setHandler(null);
                actionRemark+="领取人需要清除";
            }

        }else{
            throw new BusinessException("不是团队成员不允许该操作!");
        }

        //上面是特殊情况，这里兜底
        if(!task.getStageId().equals(to)){
            task.setStageId(to);
            task.setHandler(loginUserId);
        }


        //如果任务拖到已完成，剩余工作量置为0
        if (TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(to)) {
            task.setRemainWorkload(0);
            task.setReallyWorkload(task.getPlanWorkload());
        }
        //如果任务拖到进行中，剩余工作量置为预计工作量
        if (TaskStageIdEnum.TYPE_MODIFYING_STATE.CODE.equals(to)) {
            task.setRemainWorkload(task.getPlanWorkload());
        }
        if (null != task && TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(task.getStageId()) && !TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(to)) {
            task.setRemainWorkload(task.getPlanWorkload());
        }
        //创建历史记录
        createIssueHistoryRecords(from, to, task);
        // 修改数据库
        issueMapper.updateByPrimaryKey(task);

        //TODU  根据故事id查询有效的、未完成的任务，如果为0，则更新故事为完成，否则 进行中。
        updateStoryStageIdByTaskCound(task);

        if (TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(to)) {

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

        logService.insertLog("dragTask",issueId,IssueTypeEnum.TYPE_TASK.CODE.longValue(),actionRemark+"from="+TaskStageIdEnum.getName(from)+from
                +" to="+TaskStageIdEnum.getName(to)+to,"1");

        //发送邮件通知
        SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
        IssueMailSendDto issueMailSendDto = new IssueMailSendDto(task, NumberConstant.THREE, userInfo);
        rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE, issueMailSendDto);
    }

    //根据故事id查询有效的、未完成的任务，如果为0，则更新故事为完成，否则 进行中。
    private void updateStoryStageIdByTaskCound(Issue task) {
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
            nameHistory.setOldValue(TaskStageIdEnum.getName(from));
            if (to.equals(TaskStageIdEnum.TYPE_RECEIVED_STATE.CODE)) {
                nameHistory.setNewValue("任务已领取");
            }
            if (to.equals(TaskStageIdEnum.TYPE_MODIFYING_STATE.CODE)) {
                nameHistory.setNewValue("任务开发中");
            }
            if (to.equals(TaskStageIdEnum.TYPE_CLOSED_STATE.CODE)) {
                nameHistory.setNewValue("任务开发完成");
            }
        }
        records.add(nameHistory);
        createIssueHistoryRecords(records);
        return records;
    }
}
