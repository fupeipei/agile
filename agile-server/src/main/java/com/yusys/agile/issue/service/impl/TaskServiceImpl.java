package com.yusys.agile.issue.service.impl;

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
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintExample;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.enums.SprintStatusEnum;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.utils.ObjectUtil;
import com.yusys.agile.utils.StringUtil;
import com.yusys.agile.versionmanager.constants.VersionConstants;
import com.yusys.agile.issue.enums.*;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @Date: 13:33
 */
@Service
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTask(Long taskId, Boolean deleteChild) {
        Issue issue = issueMapper.selectByPrimaryKey(taskId);
        if (null != issue) {
            this.ckeckTaksParams(issue.getSprintId());
        }
        issueFactory.deleteIssue(taskId, deleteChild);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editTask(IssueDTO issueDTO) {
        //校验参数
        Issue oldTask = issueMapper.selectByPrimaryKey(issueDTO.getIssueId());
        this.ckeckTaksParams(oldTask.getSprintId());

        Long projectId = oldTask.getProjectId();
        Issue task = issueFactory.editIssue(issueDTO, oldTask, projectId);

        if (IssueStateEnum.TYPE_VALID.CODE.equals(oldTask.getState())) {
            Long[] stages = issueDTO.getStages();
            if (null != stages) {
                issueDTO.setStageId(stages[0]);
                if (!ObjectUtil.equals(oldTask.getStageId(), issueDTO.getStageId())) {
                    //创建任务状态变更历史记录
                    createIssueHistoryRecords(oldTask.getStageId(), issueDTO.getStageId(), oldTask);
                }
            }
        }

        //如果任务状态是未领取状态，编辑任务时，实际工作量置为0,剩余工作量等于预计工作量
        if (null != task && TaskStageIdEnum.TYPE_ADD_STATE.CODE.equals(issueDTO.getStageId())) {
            task.setReallyWorkload(0);
            task.setRemainWorkload(issueDTO.getPlanWorkload());
        }

        //如果任务在已领取中编辑，剩余工作量置为预计工作量
        if (null != task && TaskStageIdEnum.TYPE_RECEIVED_STATE.CODE.equals(issueDTO.getStageId())) {
            task.setRemainWorkload(task.getPlanWorkload());
            task.setReallyWorkload(0);
        }

        //如果任务拖到已完成，剩余工作量置为0
        if (null != task && TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(issueDTO.getStageId()) && !TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(oldTask.getStageId())) {
            task.setRemainWorkload(0);
            task.setReallyWorkload(issueDTO.getPlanWorkload());
        }
        //如果任务拖到进行中，剩余工作量置为预计工作量
        if (null != task && TaskStageIdEnum.TYPE_MODIFYING_STATE.CODE.equals(issueDTO.getStageId()) && !TaskStageIdEnum.TYPE_MODIFYING_STATE.CODE.equals(oldTask.getStageId())) {
            task.setRemainWorkload(task.getPlanWorkload());
            task.setReallyWorkload(0);
        }
        if (null != task && TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(oldTask.getStageId()) && !TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(issueDTO.getStageId())) {
            task.setRemainWorkload(task.getPlanWorkload());
        }

        int count;
        count = issueMapper.updateByPrimaryKeySelectiveWithNull(task);
        if (count != 1) {
            throw new BusinessException("更新任务失败！");
        }

        // 拖到完成
        if (null != task && TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(issueDTO.getStageId()) && !TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(oldTask.getStageId())) {
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
        return issueFactory.createIssue(issueDTO, "任务名称已存在！", "新增任务", IssueTypeEnum.TYPE_TASK.CODE);
    }

    private void ckeckTaksParams(Long sprintId) {
        SSprintExample sSprintExample = new SSprintExample();
        sSprintExample.createCriteria().andSprintIdEqualTo(sprintId)
                .andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        List<SSprint> sSprintList = sSprintMapper.selectByExample(sSprintExample);
        if (CollectionUtils.isNotEmpty(sSprintList)) {
            SSprint sSprint = sSprintList.get(0);
            if (sSprint.getStatus().equals(SprintStatusEnum.TYPE_FINISHED_STATE.CODE)) {
                throw new BusinessException("迭代已完成不能再新建任务");
            }
            boolean compareTime = LocalDateTime.now().isAfter(LocalDateTime.ofInstant(sSprint.getEndTime().toInstant(), ZoneId.systemDefault()));
            if (compareTime) {
                throw new BusinessException("迭代结束日期 < 当前时间 不能再新建任务");
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
                    records.add(nameHistory);
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
    public void dragTask(Long issueId, Long from, Long to) {
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
        if (to.equals(TaskStageIdEnum.TYPE_ADD_STATE.CODE)) {
            task.setHandler(null);
        } else {
            if (null != loginUserId) {
                task.setHandler(loginUserId);
            }
        }
        task.setStageId(to);
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

        if (TaskStageIdEnum.TYPE_CLOSED_STATE.CODE.equals(to)) {

            Long storyId = task.getParentId();
            // 故事的上层feature规整
            //rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, storyId);
            issueUpRegularFactory.commonIssueUpRegular(storyId);
//            // feature的上层也就是epic规整
//            Long featureId = faultSyncService.getParentIdByIssueId(storyId);
//            if(null != featureId){
//                rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, featureId);
//            }

        }

        //发送邮件通知
        SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
        IssueMailSendDto issueMailSendDto = new IssueMailSendDto(task, NumberConstant.THREE, userInfo);
        rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE, issueMailSendDto);
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
