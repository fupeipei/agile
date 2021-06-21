package com.yusys.agile.issue.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.yusys.agile.burndown.dao.BurnDownChartStoryDao;
import com.yusys.agile.constant.StringConstant;
import com.yusys.agile.fault.enums.FaultStatusEnum;
import com.yusys.agile.headerfield.enums.IsCustomEnum;
import com.yusys.agile.issue.dao.IssueAcceptanceMapper;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.dao.IssueSystemRelpMapper;
import com.yusys.agile.issue.dao.SIssueRichtextMapper;
import com.yusys.agile.issue.domain.*;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.StoryCreatePrepInfoDTO;
import com.yusys.agile.issue.service.IssueSystemRelpService;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.service.TaskService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.issue.utils.IssueHistoryRecordFactory;
import com.yusys.agile.issue.utils.IssueRichTextFactory;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetail;
import com.yusys.agile.sysextendfield.service.SysExtendFieldDetailService;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.sprint.enums.SprintStatusEnum;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.enums.TeamTypeEnum;
import com.yusys.agile.utils.ObjectUtil;
import com.yusys.agile.utils.exception.ExceptionCodeEnum;
import com.yusys.agile.versionmanager.constants.VersionConstants;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.yusys.agile.issue.enums.*;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SsoSystemDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.date.DateUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  @Description: 用户故事实现类
 *  @author: zhao_yd
 *  @Date: 2021/5/24 1:43 下午
 *
 */

@Service
public class StoryServiceImpl implements StoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoryServiceImpl.class);

    @Resource
    private IssueFactory issueFactory;
    @Resource
    private IssueMapper issueMapper;
    @Resource
    private BurnDownChartStoryDao burnDownChartStoryDao;
    @Resource
    private TaskService taskService;
    @Resource
    private IFacadeUserApi iFacadeUserApi;
    @Resource
    private IssueAcceptanceMapper issueAcceptanceMapper;
    @Resource
    private SysExtendFieldDetailService sysExtendFieldDetailService;
    @Resource
    private IssueRichTextFactory issueRichTextFactory;
    @Resource
    private SSprintMapper sSprintMapper;
    @Resource
    private Sprintv3Service sprintv3Service;
    @Resource
    private IFacadeSystemApi iFacadeSystemApi;
    @Resource
    private STeamSystemMapper teamSystemMapper;

    @Resource
    private IssueSystemRelpMapper issueSystemRelpMapper;

    @Resource
    private IssueSystemRelpService issueSystemRelpService;
    @Resource
    private SIssueRichtextMapper sIssueRichtextMapper;

    @Resource
    private STeamMapper sTeamMapper;

    /**
     * 功能描述 百分号
     *
     * @date 2021/2/01
     * @return
     */
    private static final String PERCENT_SIGN = "%";

    @Override
    public Long createStory(IssueDTO issueDTO) {
        //设置默认创建
        Long[] stages = issueDTO.getStages();
        if(!Optional.ofNullable(issueDTO.getSprintId()).isPresent()){
            stages = new Long[]{
                    StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue(),
                    StoryStatusEnum.TYPE_ADD_STATE.CODE};

            issueDTO.setStages(stages);
        }
        Long storyId = issueFactory.createIssue(issueDTO, "用户故事名称已存在！", "新增用户故事", IssueTypeEnum.TYPE_STORY.CODE);
        Issue issue = ReflectUtil.copyProperties(issueDTO, Issue.class);
        issue.setIssueType(IssueTypeEnum.TYPE_STORY.CODE);

        return storyId;
    }


    @Override
    public IssueDTO queryStory(Long storyId) {
        Long systemId = issueFactory.getSystemIdByIssueId(storyId);
        SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(systemId);
        IssueDTO issueDTO = issueFactory.queryIssue(storyId, systemId);
        issueDTO.setSystemCode(ssoSystem.getSystemCode());
        return issueDTO;
    }

    @Override
    public void deleteStory(Long storyId, Boolean deleteChild,Long userId) {
        if(null != userId){
            Issue story = issueMapper.selectByPrimaryKey(storyId);
            if(Optional.ofNullable(story).isPresent() && Optional.ofNullable(story.getSprintId()).isPresent()){
                Long teamId = getTeamIdBySprintId(story.getSprintId());
                if(!iFacadeUserApi.checkIsTeamPo(userId, teamId)){
                    throw  new BusinessException("非迭代PO不允许移除故事！");
                }
            }
        }
        issueFactory.deleteIssue(storyId, deleteChild);
    }

    private Long getTeamIdBySprintId(Long sprintId) {
        if(Optional.ofNullable(sprintId).isPresent()){
            SSprintWithBLOBs sprintWithBLOBs = sSprintMapper.selectByPrimaryKey(sprintId);
            return sprintWithBLOBs.getTeamId();
        }
        return null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editStory(IssueDTO issueDTO,Long userId) {
        Issue oldStory = issueMapper.selectByPrimaryKey(issueDTO.getIssueId());
        if(null == oldStory){
            return;
        }

        //校验故事在迭代内不允许更改阶段和状态
        if(Optional.ofNullable(oldStory.getSprintId()).isPresent()){
            Long teamId = getTeamIdBySprintId(oldStory.getSprintId());
            if(!iFacadeUserApi.checkIsTeamPo(userId, teamId)){
                throw  new BusinessException("非迭代PO不允许编辑故事！");
            }

           Long[] stages = issueDTO.getStages();
           if(!oldStory.getStageId().equals(stages[0])){
               throw  new BusinessException("故事在迭代内不允许变更阶段！");
           }
           if(!oldStory.getLaneId().equals(stages[1])){
               throw  new BusinessException("故事在迭代内不允许变更状态！");
           }
        }

        Long projectId = oldStory.getProjectId();

        //校验故事下有未完成任务或未修复缺陷不允许改为完成阶段
        Long[] stages = issueDTO.getStages();
        if (StageConstant.FirstStageEnum.FINISH_STAGE.getValue().equals(stages[0])) {
            if (checkHasUnfinishOrNotRepairIssue(issueDTO.getIssueId())) {
                throw new BusinessException("故事下有未完成任务或未修复缺陷不允许改为完成阶段！");
            }
        }

        Issue story = issueFactory.editIssue(issueDTO, oldStory, projectId);
        int count;
        count = issueMapper.updateByPrimaryKeySelectiveWithNull(story);
        if (count != 1) {
            throw new BusinessException("更新用户故事失败！");
        }

        //故事修改了迭代，那么需要同时修改故事对应任务上面的迭代信息
        if (!ObjectUtil.equals(issueDTO.getSprintId(), oldStory.getSprintId())) {
            updateStoryOfTaskSprint(issueDTO);
            oldStory.setSprintId(issueDTO.getSprintId());
            issueMapper.updatePrint(oldStory);
        }
        //如果故事上的系统修改了，那么需要修改任务上的系统信息
        this.updateStoryOfTasks(issueDTO);
    }


    private void updateStoryOfTasks(IssueDTO issueDTO){
        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria().andParentIdEqualTo(issueDTO.getIssueId())
                .andStateEqualTo(StateEnum.U.getValue());
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        if (CollectionUtils.isNotEmpty(issues)){
            List<Long> newSystemIds = issueDTO.getSystemIds();
            IssueSystemRelpExample issueSystemRelpExample = new IssueSystemRelpExample();
            issueSystemRelpExample.createCriteria()
                    .andIssueIdEqualTo(issueDTO.getIssueId());
            List<IssueSystemRelp> issueSystemRelps = issueSystemRelpMapper.selectByExample(issueSystemRelpExample);
            List<Long> oldTaskSystemIds = Optional.ofNullable(issueSystemRelps).orElse(new ArrayList<IssueSystemRelp>())
                    .stream().map(IssueSystemRelp::getSystemId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(newSystemIds) && CollectionUtils.isEqualCollection(oldTaskSystemIds,newSystemIds)){
                issueSystemRelpService.deleteByIssueId(issueDTO.getIssueId());
                issueSystemRelpService.batchInsert(issueDTO.getIssueId(), newSystemIds);
            }
        }



    }
    /**
     * 1、从列表新建故事
     *       执行人：当前系统内查询所有人
     *       迭代：关联本系统所有团队下，未开始，进行中迭代
     *       系统：默认当前系统
     *       阶段：设计开发/开发阶段->未开始（默认）
     * 2、从kanban新建故事
     *       迭代：默认本迭代
     *       系统：迭代所属团队关联系统中选其一
     *       执行人：迭代所属团队关联系统（其一）内所有人
     *       设计开发/开发阶段->未开始（默认）
     * @param crateType
     * @param sprintId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public StoryCreatePrepInfoDTO getStoryPreInfo(Integer crateType, Long sprintId,Long systemId, Integer page, Integer pageSize,String userName){
        StoryCreatePrepInfoDTO storyCreatePrepInfoDTO = new StoryCreatePrepInfoDTO();
        Long sysId = Optional.ofNullable(systemId).orElse(UserThreadLocalUtil.getUserInfo().getSystemId());

        //如果从列表创建
        if(CreateTypeEnum.LIST.CODE.equals(crateType)){
            //迭代信息
            List<SprintListDTO> sprintListDTOS = sprintv3Service.getEffectiveSprintsBySystemId(systemId);
            storyCreatePrepInfoDTO.setSprintDTOS(sprintListDTOS);
            //系统信息
            SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(systemId);
            SsoSystemDTO ssoSystemDTO = ReflectUtil.copyProperties(ssoSystem, SsoSystemDTO.class);
            List<SsoSystemDTO> ssoSystemDTOS = Lists.newArrayList();
            ssoSystemDTOS.add(ssoSystemDTO);
            storyCreatePrepInfoDTO.setSystemDTOS(ssoSystemDTOS);
            //人员信息
            List<SsoUserDTO> ssoUserDTOS = iFacadeUserApi.queryUsersByCondition(userName, page, pageSize, sysId);
            storyCreatePrepInfoDTO.setUserDTOS(ssoUserDTOS);
        }else if(CreateTypeEnum.KANBAN.CODE.equals(crateType)){
            SSprintWithBLOBs sSprintWithBLOBs = sSprintMapper.selectByPrimaryKey(sprintId);
            SprintListDTO sprintListDTO = ReflectUtil.copyProperties(sSprintWithBLOBs, SprintListDTO.class);
            List<SprintListDTO> sprintListDTOS = Lists.newArrayList();
            sprintListDTOS.add(sprintListDTO);
            storyCreatePrepInfoDTO.setSprintDTOS(sprintListDTOS);

            //系统
            Long teamId = sSprintWithBLOBs.getTeamId();
            List<Long> systemIds = teamSystemMapper.querySystemIdByTeamId(teamId);
            List<SsoSystem> ssoSystems = iFacadeSystemApi.querySsoSystem(systemIds);
            List<SsoSystemDTO> ssoSystemDTOS = null;
            try {
                ssoSystemDTOS = ReflectUtil.copyProperties4List(ssoSystems, SsoSystemDTO.class);
            } catch (Exception e) {
                LOGGER.error("数据转换异常:{}",e.getMessage());
            }
            storyCreatePrepInfoDTO.setSystemDTOS(ssoSystemDTOS);
            //人员信息
            if(Optional.ofNullable(systemId).isPresent()){
                List<SsoUserDTO> ssoUserDTOS = iFacadeUserApi.queryUsersByCondition(userName, page, pageSize, sysId);
                storyCreatePrepInfoDTO.setUserDTOS(ssoUserDTOS);
            }
        }
        return storyCreatePrepInfoDTO;
    }

    /**
     * @Date: 2021/2/21 10:42
     * @Description: 更新故事对应任务上面的迭代ID
     * @Param: * @param issueDTO
     * @Return: void
     */
    public void updateStoryOfTaskSprint(IssueDTO issueDTO) {
        IssueExample example = new IssueExample();
        IssueExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(issueDTO.getIssueId()).andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        List<Issue> issueList = issueMapper.selectByExample(example);
        for (Issue issue : issueList) {
            issue.setSprintId(issueDTO.getSprintId());
            issueMapper.updatePrint(issue);
        }
    }

    @Override
    public Long copyStory(Long storyId) {
        return issueFactory.copyIssue(storyId,"该复制的用户故事已失效！", "用户故事名称已存在！", "新增用户故事", IssueTypeEnum.TYPE_STORY.CODE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeStory4Sprint(Long sprintId, Long storyId) {
        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        SSprint sprint = sSprintMapper.selectByPrimaryKey(sprintId);
        Long teamId = sprint.getTeamId();
        boolean b = iFacadeUserApi.checkIsTeamPo(userId, teamId);
        if(!b){
            throw new BusinessException("只有本迭代的PO权限才允许关联/移除用户故事");
        }
        Issue issue = issueMapper.selectByPrimaryKey(storyId);
        if (!issue.getSprintId().equals(sprintId)) {
            return 1;
        }

        if (null == sprint || !StringUtils.equals(sprint.getState(), StateEnum.U.getValue())) {
            throw new BusinessException(ExceptionCodeEnum.PARAM_ERROR.getDesc());
        }
        //故事的迭代id为null 故事的状态为开发阶段的未开始
        issue.setSprintId(null);
        issue.setUpdateTime(new Date());
        issue.setStageId(4L);
        issue.setLaneId(104L);
        issue.setHandler(null);
        int i = issueMapper.updateByPrimaryKey(issue);
        if (i != 1) {
            throw new BusinessException(ExceptionCodeEnum.SYS_ERROR.getDesc());
        }
        burnDownChartStoryDao.cancelByStorys(sprintId, Lists.newArrayList(storyId));
        // 故事下的任务迭代id为null,任务状态置为未领取状态
        taskService.cancel4Story(storyId);
        //创建故事历史
        List<IssueHistoryRecord> records = new ArrayList<>();
        IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(storyId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.SPRINT.getDesc());
        if (null != issue.getIssueId()) {
            nameHistory.setOldValue("故事在迭代“" + sprint.getSprintName() + "”中");
            nameHistory.setNewValue("故事移出迭代“" + sprint.getSprintName() + "”");
        }
        records.add(nameHistory);
        taskService.createIssueHistoryRecords(records);


        issue.setSprintId(-1L);

        return 1;
    }

    @Override
    public List<IssueDTO> queryUnlinkedStory(Long featureId, Integer pageNum, Integer pageSize, String title) {
        return issueFactory.queryUnlinkedStory(featureId, pageNum, pageSize, title);
    }

    @Override
    public List<IssueDTO> listStorysAndTasks( IssueDTO issueDTO) {
        String filter = issueDTO.getStoryKeyWord();
        String taskKeyWord = issueDTO.getTaskKeyWord();
        Long sprintId = issueDTO.getSprintId();
        Integer pageNum = issueDTO.getPageNum();
        Integer pageSize = issueDTO.getPageSize();
        List<Long> laneIds = issueDTO.getLaneIds();
        List<Integer> taskTypes = issueDTO.getTaskTypes();
        List<Long> handlers = issueDTO.getHandlers();

        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        IssueExample example = new IssueExample();
        example.setOrderByClause("priority desc,create_time desc");

        IssueExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(StateEnum.U.getValue())
                .andSprintIdEqualTo(sprintId).andIssueTypeEqualTo(IssueTypeEnum.TYPE_STORY.CODE);
        IssueExample.Criteria criteria2 = example.createCriteria();
        criteria2.andStateEqualTo(StateEnum.U.getValue())
                .andSprintIdEqualTo(sprintId).andIssueTypeEqualTo(IssueTypeEnum.TYPE_STORY.CODE);

        // 判断是根据id还是name
        doFilterCondition(filter, example, criteria, criteria2);

        List<IssueDTO> issueDTOS = issueMapper.selectByExampleDTO(example);
        if (CollectionUtils.isNotEmpty(issueDTOS)) {
            //获取故事下的任务
            issueDTOS = getChildren(sprintId, issueDTOS, taskKeyWord, laneIds, taskTypes, handlers);
        }

        return issueDTOS;
    }

    private void doFilterCondition(String filter, IssueExample example, IssueExample.Criteria criteria, IssueExample.Criteria criteria2) {
        if (StringUtils.isNotBlank(filter) && NumberUtil.isLong(filter)) {
            Long id = Long.valueOf(filter);
            // 能转成long，说明可能是id，也可能是name
            criteria.andIssueIdEqualTo(id);
            // 同时赋值给标题
            criteria2.andTitleLike(PERCENT_SIGN + filter + PERCENT_SIGN);
            example.or(criteria2);
        }else{
            criteria.andTitleLike(PERCENT_SIGN + filter + PERCENT_SIGN);
        }
    }

    /**
     * @param sprintId
     * @param issueDTOS
     * @Date 2021/2/12
     * @Description 获取故事下的任务
     * @Return void
     */
    private List<IssueDTO> getChildren(Long sprintId, List<IssueDTO> issueDTOS,
                                       String taskKeyWord, List<Long> laneIds, List<Integer> taskTypes, List<Long> handlers) {
        List<IssueDTO> issueDTOSTmp = new ArrayList<>();
        List<Long> systemIds = new ArrayList<>();
        Map<Long, String> systemMap = new HashedMap();
        SSprint sprint = sSprintMapper.selectByPrimaryKey(sprintId);
        if (CollectionUtils.isNotEmpty(issueDTOS)) {
            issueDTOS.forEach(issueDTO -> {
                if(Optional.ofNullable(issueDTO.getSystemId()).isPresent()){
                    systemIds.add(issueDTO.getSystemId());
                }
            });

            List<SsoSystem> ssoSystems = iFacadeSystemApi.querySsoSystem(systemIds);
            if(CollectionUtils.isNotEmpty(ssoSystems)){
                ssoSystems.forEach(ssoSystem -> systemMap.put(ssoSystem.getSystemId(), ssoSystem.getSystemCode()));
            }
            for (IssueDTO issueDTO : issueDTOS) {
                /** 查询富文本 */
                issueRichTextFactory.queryIssueRichText(issueDTO);
                issueDTO.setFaultNum(0);
                issueDTO.setCaseNum(0);
                issueDTO.setSystemCode(systemMap.get(issueDTO.getSystemId()));
                IssueExample issueExample = new IssueExample();
                IssueExample.Criteria criteria1 = issueExample.createCriteria();
                criteria1.andStateEqualTo(StateEnum.U.getValue()).andSprintIdEqualTo(sprintId).
                        andIssueTypeEqualTo(IssueTypeEnum.TYPE_TASK.CODE).andParentIdEqualTo(issueDTO.getIssueId());
                IssueExample.Criteria criteria2 = issueExample.createCriteria();
                criteria2.andStateEqualTo(StateEnum.U.getValue()).andSprintIdEqualTo(sprintId).
                        andIssueTypeEqualTo(IssueTypeEnum.TYPE_TASK.CODE).andParentIdEqualTo(issueDTO.getIssueId());

                if (CollectionUtils.isNotEmpty(laneIds)) {
                    criteria1.andLaneIdIn(laneIds);
                    criteria2.andLaneIdIn(laneIds);
                }
                if (CollectionUtils.isNotEmpty(taskTypes)) {
                    criteria1.andTaskTypeIn(taskTypes);
                    criteria2.andTaskTypeIn(taskTypes);
                }
                if (CollectionUtils.isNotEmpty(handlers)) {
                    criteria1.andHandlerIn(handlers);
                    criteria2.andHandlerIn(handlers);
                }

                // 判断是根据id还是name
                doFilterCondition(taskKeyWord, issueExample, criteria1, criteria2);
                List<IssueDTO> taskList = issueMapper.selectByExampleDTO(issueExample);
                if (CollectionUtils.isNotEmpty(taskList)) {
                    for (IssueDTO task : taskList) {
                        //增加任务类型描述
                        task.setTaskTypeDesc(TaskTypeEnum.getName(task.getTaskType()));
                        //设置是否超时
                        Date start = task.getBeginDate();
                        Date end = task.getEndDate();
                        if (null != start) {
                            task.setOverTime(isTaskOverTime(start, end, task.getPlanWorkload(), sprint.getWorkHours()));
                        }
                        if (null != task.getHandler()) {
                            SsoUser ssoUser = iFacadeUserApi.queryUserById(task.getHandler());
                            if (null != ssoUser) {
                                ssoUser.setUserPassword(null);
                                SsoUserDTO userDTO = ReflectUtil.copyProperties(ssoUser, SsoUserDTO.class);
                                task.setOwner(userDTO);
                            }
                        }
                    }
                    issueDTO.setChildren(taskList);
                    issueDTOSTmp.add(issueDTO);
                }else{
                    //查询故事下的Task的查询条件某一个不等于空的情况，判断Children的Task是为空，为空则进行过滤
                    Boolean checkFlag =  StringUtils.isBlank(taskKeyWord) && CollectionUtils.isEmpty(laneIds)
                            && CollectionUtils.isEmpty(taskTypes) && CollectionUtils.isEmpty(handlers) && CollectionUtils.isEmpty(taskList);
                    if(checkFlag){
                        issueDTO.setChildren(taskList);
                        issueDTOSTmp.add(issueDTO);
                    }
                }
            }
        }
        return issueDTOSTmp;
    }

    /**
     * @param start
     * @param end
     * @param planWorkload
     * @param workHours
     * @Date 2020/4/30
     * @Description 设置是否超时
     * @Return boolean
     */
    private boolean isTaskOverTime(Date start, Date end, int planWorkload, int workHours) {
        int cost = DateUtil.getCostDays(start, end == null ? new Date(System.currentTimeMillis()) : end);
        return (cost - 1) * workHours >= planWorkload;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int distributeSprint(Long issueId, Long sprintId) {
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        if (null == issue || !StringUtils.equals(issue.getState(), StateEnum.U.getValue())) {
            throw new BusinessException(ExceptionCodeEnum.PARAM_ERROR.getDesc());
        }
        SSprint sprint = sSprintMapper.selectByPrimaryKeyNotText(sprintId);
        int count = 0;
        if (null != sprint) {
            if (sprint.getStatus().equals(SprintStatusEnum.TYPE_FINISHED_STATE.CODE) || sprint.getStatus().equals(SprintStatusEnum.TYPE_CANCEL_STATE.CODE)) {
                throw new BusinessException("迭代状态进行中/未开始才允许关联用户故事");
            }
            if (sprint.getEndTime().before(new Date())) {
                throw new BusinessException("迭代结束日期小于当前时间的迭代，不允许新增用户故事");
            }
            //为故事分配迭代用户故事状态由未开始变为进行中
            if (issue.getIssueType().equals(IssueTypeEnum.TYPE_STORY.CODE)) {
                count = issueMapper.updateBySprintId(issueId, sprintId);
                if (count != 1) {
                    throw new BusinessException("故事加入迭代失败");
                }
                //将故事下的任务和缺陷加入迭代
                switchSprint(issueId, sprintId);
            }
            if (issue.getIssueType().equals(IssueTypeEnum.TYPE_FAULT.CODE)) {
                issue.setSprintId(sprintId);
                issue.setStageId(FaultStatusEnum.NEW.getCode());
                issue.setFixedUid(null);
                issue.setTestUid(null);
                count = issueMapper.updateByPrimaryKey(issue);
                if (count != 1) {
                    throw new BusinessException("缺陷加入迭代失败");
                }
            }
            //增加修改迭代的历史记录
            createRecord(issueId, sprintId);

        }
        return count;
    }

    /**
     * 功能描述: 未完成的故事和下面的任务缺陷移到新迭代中
     *
     * @param storyId
     * @param projectId
     * @param oldSprintId
     * @param sprintId
     * @return void
     * @date 2020/8/31
     */
    void distributeStoryToSprint(Long storyId, Long projectId, Long oldSprintId, Long sprintId) {
        Issue issue = issueMapper.selectByPrimaryKey(storyId);
        if (null == issue || !StringUtils.equals(issue.getState(), StateEnum.U.getValue())) {
            throw new BusinessException(ExceptionCodeEnum.PARAM_ERROR.getDesc());
        }
        SSprint sprint = sSprintMapper.selectByPrimaryKeyNotText(sprintId);
        int count = 0;
        if (null != sprint) {
            if (sprint.getStatus().equals(SprintStatusEnum.TYPE_FINISHED_STATE.CODE)) {
                throw new BusinessException("迭代已完成不能再关联工作项");
            }
            //为故事分配迭代
            if (issue.getIssueType().equals(IssueTypeEnum.TYPE_STORY.CODE)) {
                count = issueMapper.updateBySprintId(storyId, sprintId);
                if (count != 1) {
                    throw new BusinessException("故事加入迭代失败");
                }
                //将故事下的任务和缺陷加入迭代
                switchTaskAndFaultToSprint(storyId, sprintId);
            }
            //增加修改迭代的历史记录
            createRecord(storyId, sprintId);
            // 同步给itc
            issue.setSprintId(sprintId);
        }
    }


    /**
     * @param storyId
     * @param sprintId
     * @Date 2020/4/26
     * @Description 将故事下的任务加入迭代, 状态初始化为未领取，处理人为null
     * @Return void
     */
    private void switchSprint(Long storyId, Long sprintId) {
        IssueExample issueExample = new IssueExample();
        IssueExample.Criteria criteria = issueExample.createCriteria();
        criteria.andParentIdEqualTo(storyId).andStateEqualTo(StateEnum.U.getValue());
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        if (CollectionUtils.isEmpty(issues)) {
            return;
        }
        for (Issue issue : issues) {
            issue.setIssueId(issue.getIssueId());
            issue.setSprintId(sprintId);
                if (IssueTypeEnum.TYPE_TASK.CODE.equals(issue.getIssueType())) {
                issue.setStageId(StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue());
            } else {
                issue.setStageId(FaultStatusEnum.NEW.CODE);
            }

            if (null != issue.getHandler()) {
                issue.setHandler(null);
            }
            issue.setUpdateTime(new Date());
            int count = issueMapper.updateByPrimaryKey(issue);
            if (count != 1) {
                throw new BusinessException("任务或缺陷加入迭代失败!");
            }
            //新增任务历史记录
            createRecord(issue.getIssueId(), sprintId);
        }
    }

    /**
     * 功能描述: 把故事下的任务和缺陷迁移迭代
     *
     * @param storyId
     * @param sprintId
     * @return void
     * @date 2020/8/31
     */
    private void switchTaskAndFaultToSprint(Long storyId, Long sprintId) {
        IssueExample issueExample = new IssueExample();
        IssueExample.Criteria criteria = issueExample.createCriteria();
        criteria.andParentIdEqualTo(storyId).andStateEqualTo(StateEnum.U.getValue());
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        if (CollectionUtils.isEmpty(issues)) {
            return;
        }
        for (Issue issue : issues) {
            issue.setIssueId(issue.getIssueId());
            issue.setSprintId(sprintId);
            int count = issueMapper.updateByPrimaryKey(issue);
            if (count != 1) {
                throw new BusinessException("任务或缺陷加入迭代失败!");
            }
            //新增任务历史记录
            createRecord(issue.getIssueId(), sprintId);
        }
    }

    /**
     * @param storyId
     * @param sprintId
     * @Date 2020/4/26
     * @Description 增加工作项修改迭代的历史记录
     * @Return void
     */
    private void createRecord(Long storyId, Long sprintId) {
        List<IssueHistoryRecord> records = new ArrayList<>();

        IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(
                storyId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.SPRINT.getDesc());

        if (null != sprintId) {
            nameHistory.setOldValue(null);
            nameHistory.setNewValue("加入迭代");
        }
        records.add(nameHistory);
        taskService.createIssueHistoryRecords(records);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editStoryOrTask(IssueDTO issueDTO, Long projectId) {
        Issue oldIssue = issueMapper.selectByPrimaryKey(issueDTO.getIssueId());
        final String state = oldIssue.getState();
        int count = 0;
        if (IssueStateEnum.TYPE_VALID.CODE.equals(state)) {
            Issue issue = ReflectUtil.copyProperties(issueDTO, Issue.class);
            Long[] stages = issueDTO.getStages();
            List<IssueHistoryRecord> history = new ArrayList<>();
            //如果是故事就处理阶段
            if (issueDTO.getIssueType().equals(IssueTypeEnum.TYPE_STORY.CODE)) {
                taskService.createIssueHistoryRecords(issueFactory.dealStages(issue, oldIssue, stages, history));
            }
            if (issueDTO.getIssueType().equals(IssueTypeEnum.TYPE_TASK.CODE)) {
                issue.setBlockState(issueDTO.getBlockState());
                IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(
                        issue.getIssueId(), IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.BLOCKSTATE.getDesc());
                if (null != issue.getBlockState()) {
                    nameHistory.setOldValue(IssueBlockStateEnum.getName(oldIssue.getBlockState()));
                    nameHistory.setNewValue(IssueBlockStateEnum.getName(issue.getBlockState()));
                }
                history.add(nameHistory);
                taskService.createIssueHistoryRecords(history);
            }
            /**
             * @description 修复迭代管理变更带二级泳道状态到只包括一级阶段过程中，二级状态未清除导致泳道视图卡片重复展示问题
             * @fixer
             * @date 2021/1/8
             */
            if (null != stages && stages.length > 0) {
                issue.setStageId(stages[0]);
                if (stages.length == 2) {
                    issue.setLaneId(stages[1]);
                }
            }
            count = issueMapper.updateByPrimaryKeySelectiveWithNotNull(issue);
        }

        if (issueDTO.getIssueType().equals(IssueTypeEnum.TYPE_STORY.CODE)
                && !StringUtils.startsWith(oldIssue.getTitle(), StringConstant.FAULT_STORY)
                && ObjectUtil.equals(issueDTO.getStages()[0], StageConstant.FirstStageEnum.TEST_STAGE.getValue())
                && !ObjectUtil.equals(oldIssue.getStageId(), StageConstant.FirstStageEnum.TEST_STAGE.getValue())) {
            Issue issue = issueMapper.selectByPrimaryKey(issueDTO.getIssueId());
            ;
            // 不是删除的时候才需要查询一次测试负责人塞入issue
            SysExtendFieldDetail detail = sysExtendFieldDetailService.getSysExtendFieldDetail(issue.getIssueId(), "externalHandlerId");
            if (null == detail || null == detail.getValue()) {
                throw new BusinessException("该story没有指定对应的测试负责人信息!");
            }

        }
        return count;
    }

    @Override
    public List<IssueDTO> listStoryAcceptance(IssueDTO issueDTO, Integer pageNum, Integer pageSize) {
        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }

        IssueExample example = new IssueExample();
        example.setOrderByClause("`order` desc,create_time desc");

        IssueExample.Criteria criteria = example.createCriteria().andSprintIdEqualTo(issueDTO.getSprintId()).andStateEqualTo(StateEnum.U.getValue()).
                andIssueTypeEqualTo(IssueTypeEnum.TYPE_STORY.CODE);
        if (null != issueDTO.getCreateUid()) {
            criteria.andCreateUidEqualTo(issueDTO.getCreateUid());
        }
        if (null != issueDTO.getTitle()) {
            criteria.andTitleLike(PERCENT_SIGN + issueDTO.getTitle() + PERCENT_SIGN);
        }
        List<IssueDTO> issueDTOList = issueMapper.selectByExampleDTO(example);
        if (CollectionUtils.isNotEmpty(issueDTOList)) {
            for (IssueDTO dto : issueDTOList) {
                if (null != dto.getCreateUid()) {
                    SsoUser ssoUser = iFacadeUserApi.queryUserById(dto.getCreateUid());
                    if (null != ssoUser) {
                        dto.setCreateName(ssoUser.getUserName());
                    }
                    if(null != dto.getSystemId()){
                        SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(dto.getSystemId());
                        String systemCode = ssoSystem.getSystemCode();
                        dto.setSystemCode(systemCode);
                    }
                }
                SIssueRichtextWithBLOBs issueRichText = issueRichTextFactory.getIssueRichText(dto.getIssueId());
                dto.setAcceptanceCriteria(issueRichText.getAcceptanceCriteria());
                //issueFactory.getAcceptanceList(dto.getIssueId(), dto);
            }
        }
        return issueDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editStoryAssess(IssueDTO issueDTO) {
        String acceptanceCriteria = issueDTO.getAcceptanceCriteria();
       // List<IssueAcceptanceDTO> issueAcceptanceDTOS = issueDTO.getIssueAcceptanceDTOS();
        if (StringUtils.isNotEmpty(acceptanceCriteria)) {
            SIssueRichtextWithBLOBs sIssueRichtextWithBLOB=new SIssueRichtextWithBLOBs();
            SIssueRichtextExample sIssueRichtextExample=new SIssueRichtextExample();
            SIssueRichtextExample.Criteria criteria = sIssueRichtextExample.createCriteria();
            criteria.andIssueIdEqualTo(issueDTO.getIssueId()).andStateEqualTo(StateEnum.U.getValue());

            sIssueRichtextWithBLOB.setAcceptanceCriteria(acceptanceCriteria);

            sIssueRichtextMapper.updateByExampleSelective(sIssueRichtextWithBLOB,sIssueRichtextExample);
//            for (IssueAcceptanceDTO issueAcceptanceDTO : issueAcceptanceDTOS) {
//                IssueAcceptance issueAcceptance = ReflectUtil.copyProperties(issueAcceptanceDTO, IssueAcceptance.class);
//                issueAcceptanceMapper.updateByPrimaryKey(issueAcceptance);
//            }
        }
        //编辑故事评审状态及备注
        return editAssess(issueDTO);
    }

    @Override
    public List<IssueDTO> queryAllStory(Long projectId, Integer pageNum, Integer pageSize, String title) {
        return issueFactory.queryAllIssue(projectId, IssueTypeEnum.TYPE_STORY.CODE, pageNum, pageSize, title, null);
    }

    @Override
    public List<IssueDTO> queryStoryForEpic(Long projectId, Long epicId) {
        List<IssueDTO> issueDTOList = issueMapper.queryStoryForEpic(projectId, epicId);
        List<Long> handlers = new ArrayList<>();
        issueFactory.setHandlersAndStageName(issueDTOList, handlers, IssueTypeEnum.TYPE_STORY.CODE);
        issueFactory.setHandlerName(handlers, issueDTOList);
        return issueDTOList;
    }

    @Override
    public List<IssueDTO> queryStoryForFeature(Long projectId, Long featureId) {
        return issueFactory.queryAllIssue(projectId, IssueTypeEnum.TYPE_STORY.CODE, null, null, null, featureId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void distributeSprints(List<Long> listStorys, Long sprintId) {
        for (Long id : listStorys) {
            distributeSprint(id, sprintId);
        }
    }

    /**
     * 功能描述: 故事及下面的任务缺陷移到新迭代
     *
     * @param listStorys
     * @param projectId
     * @param oldSprintId
     * @param sprintId
     * @return void
     * @date 2020/8/31
     */
    @Override
    public void distributeStoryAndTaskAndFaultToSprint(List<Long> listStorys, Long projectId, Long oldSprintId, Long sprintId) {
        for (Long storyId : listStorys) {
            distributeStoryToSprint(storyId, projectId, oldSprintId, sprintId);
        }
    }

    /**
     * 功能描述: 只关联迭代的缺陷移到新迭代
     *
     * @param oldSprintId
     * @param sprintId
     * @return void
     * @date 2020/8/31
     */
    @Override
    public List<Issue> dealFaultsOnlyInSprint(Long oldSprintId, Long sprintId) {
        // 查询是否存在只关联迭代的缺陷
        IssueExample example = new IssueExample();
        example.createCriteria().andIssueTypeEqualTo(IssueTypeEnum.TYPE_FAULT.CODE)
                .andSprintIdEqualTo(oldSprintId)
                .andParentIdIsNull()
                .andStateEqualTo(StateEnum.U.getValue());
        List<Issue> faultsOnlyInSprint = issueMapper.selectByExample(example);

        for (Issue issue : faultsOnlyInSprint) {
            issue.setIssueId(issue.getIssueId());
            issue.setSprintId(sprintId);
            int count = issueMapper.updateByPrimaryKey(issue);
            if (count != 1) {
                throw new BusinessException("缺陷加入迭代失败!");
            }
            //新增任务历史记录
            createRecord(issue.getIssueId(), sprintId);
        }
        return faultsOnlyInSprint;
    }

    /**
     * 功能描述: 查询项目下未完成的故事
     *
     * @param projectId
     * @param pageNum
     * @param pageSize
     * @param
     * @return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     * @date 2021/3/8
     */
    @Override
    public List<IssueDTO> listUnFinisherStorysByProjectId(Long projectId, String name, Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        IssueExample example = new IssueExample();
        IssueExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(projectId).andStateEqualTo(StateEnum.U.getValue())
                .andIssueTypeEqualTo(IssueTypeEnum.TYPE_STORY.CODE)
                .andStageIdNotEqualTo(StageConstant.FirstStageEnum.FINISH_STAGE.getValue());
        if (StringUtils.isNotBlank(name)) {
            criteria.andTitleLike("%" + name + "%");
        }

        example.setOrderByClause("create_time desc");
        // 先查询所有的故事
        List<IssueDTO> storys = issueMapper.selectByExampleDTO(example);
        return storys;
    }


    @Override
    public List<IssueDTO> getUnfinishedStoryList(Long sprintId) {
        IssueExample example = new IssueExample();
        example.createCriteria().andSprintIdEqualTo(sprintId).andStateEqualTo(StateEnum.U.getValue()).
                andIssueTypeEqualTo(IssueTypeEnum.TYPE_STORY.CODE);
        // 先查询所有的故事
        List<IssueDTO> storys = issueMapper.selectByExampleDTO(example);

        // 对故事进行过滤，只要下面有未完成的故事或者任务，那么这个故事就是未完成的，返回
        List<IssueDTO> unfinishedStorys = Lists.newArrayList();

        for (IssueDTO tempStory : storys) {
            // 任务
            Boolean taskExist = hasUnfinishedTaskOrFault(tempStory.getIssueId(), IssueTypeEnum.TYPE_TASK.CODE, TaskStatusEnum.TYPE_CLOSED_STATE.CODE);
            // 缺陷
            Boolean faultExist = hasUnfinishedTaskOrFault(tempStory.getIssueId(), IssueTypeEnum.TYPE_FAULT.CODE, FaultStatusEnum.CLOSED.CODE);
            if (taskExist || faultExist) {
                unfinishedStorys.add(tempStory);
            }
        }

        return unfinishedStorys;
    }

    /**
     * 功能描述: 判断故事是否有未完成的任务或缺陷
     *
     * @param storyId
     * @param issueType
     * @param code
     * @return java.lang.Boolean
     * @date 2020/8/31
     */
    private Boolean hasUnfinishedTaskOrFault(Long storyId, Byte issueType, Long code) {
        Boolean hasUnfinished = false;

        IssueExample example = new IssueExample();
        example.createCriteria().andStateEqualTo(StateEnum.U.getValue()).andParentIdEqualTo(storyId)
                .andIssueTypeEqualTo(issueType).andStageIdNotEqualTo(code);
        Long count = issueMapper.countByExample(example);

        if (count != null && count > 0) {
            hasUnfinished = true;
        }

        return hasUnfinished;

    }

    @Override
    public boolean checkHasUnfinishOrNotRepairIssue(Long storyId) {
        return issueMapper.countUnfinishOrNotRepairIssue(storyId) > 0;
    }

    /**
     * @param issueDTO
     * @Date 2021/2/1
     * @Description 编辑故事评审状态及备注
     * @Return int
     */
    private int editAssess(IssueDTO issueDTO) {
        int count;
        Issue issue = issueMapper.selectByPrimaryKey(issueDTO.getIssueId());
        List<IssueHistoryRecord> history = new ArrayList<>();
        IssueHistoryRecord nameHistory = null;
        if(issueDTO.getAssessIsPass()!=1){
            throw new BusinessException("评审未通过时，需填写评审备注");
        }
        if (!ObjectUtil.equals(issueDTO.getAssessIsPass(), issue.getAssessIsPass())) {
            nameHistory = IssueHistoryRecordFactory.createHistoryRecord(
                    issue.getIssueId(), IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.ASSESSISPASS.getDesc());
            nameHistory.setOldValue(IssueAssessIsPassEnum.getName(issue.getAssessIsPass()));
            nameHistory.setNewValue(IssueAssessIsPassEnum.getName(issueDTO.getAssessIsPass()));
            history.add(nameHistory);
        }
        if (!ObjectUtil.equals(issue.getAssessRemarks(), issueDTO.getAssessRemarks())) {
            nameHistory = IssueHistoryRecordFactory.createHistoryRecord(
                    issue.getIssueId(), IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.ASSESSREMARKS.getDesc());

            nameHistory.setOldValue(issue.getAssessRemarks());
            nameHistory.setNewValue(issueDTO.getAssessRemarks());
            history.add(nameHistory);
        }
        taskService.createIssueHistoryRecords(history);
        Issue record = new Issue();
        record.setIssueId(issueDTO.getIssueId());
        record.setAssessIsPass(issueDTO.getAssessIsPass());
        record.setAssessRemarks(issueDTO.getAssessRemarks());
        count = issueMapper.updateByPrimaryKeySelective(record);
        return count;
    }

    @Override
    public List<IssueDTO> getUnfinishedStorysBySprintId(Long sprintId, Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        IssueExample example = new IssueExample();
        example.setOrderByClause("issue_id desc");
        IssueExample.Criteria criteria = example.createCriteria();
        criteria.andSprintIdEqualTo(sprintId)
                .andStateEqualTo(StateEnum.U.getValue())
                .andIssueTypeEqualTo(IssueTypeEnum.TYPE_STORY.CODE)
                .andStageIdNotEqualTo(StageConstant.FirstStageEnum.FINISH_STAGE.getValue());
        // 先查询所有的故事
        List<IssueDTO> storys = issueMapper.selectByExampleDTO(example);
        return storys;
    }

    @Override
    public Map<Long, String> getDevlopManager(Long storyId) {
        Map<Long, String> mapUser = new HashMap(1);
        SysExtendFieldDetail sysExtendFieldDetail = sysExtendFieldDetailService.getSysExtendFieldDetail(storyId, VersionConstants.SysExtendFiledConstant.DEVLOPMANAGER);
        if (Optional.ofNullable(sysExtendFieldDetail).isPresent()) {
            Long userId = Long.valueOf(sysExtendFieldDetail.getValue());
            SsoUser ssoUser = iFacadeUserApi.queryUserById(userId);
            mapUser.put(userId, ssoUser.getUserName());
        }
        return mapUser;
    }


    /**
     *
     *判断迭代已完成，已取消，以及迭代结束日期小于当前时间的迭代
     *
     * @param sprintId 迭代id
     */
    @Override
    public void checkSprintParam(Long issueId,Long sprintId) {
        //只有敏捷团队才需要校验
        Issue feature = getFeature(issueId);
        if(null != feature){
            STeam sTeam = sTeamMapper.queryTeam(feature.getTeamId());
            if(TeamTypeEnum.agile_team.getCode().equals(sTeam.getTeamType())){
                SSprint sprint = sSprintMapper.selectByPrimaryKeyNotText(sprintId);
                if (null != sprint) {
                    if (sprint.getStatus().equals(SprintStatusEnum.TYPE_FINISHED_STATE.CODE) || sprint.getStatus().equals(SprintStatusEnum.TYPE_CANCEL_STATE.CODE)) {
                        throw new BusinessException("只有未开始的任务可以关联工作项");
                    }
                    if (sprint.getEndTime().before(new Date())) {
                        throw new BusinessException("迭代结束日期小于当前时间的迭代，不允许关联/取消关联用户故事");
                    }
                }else{
                    throw new BusinessException("迭代不存在");
                }
            }
        }
    }

    private Issue getFeature(Long issueId) {
        Issue feature = null;
        Issue issue = issueMapper.getIssue(issueId);
        if(null != issue){
            if(IssueTypeEnum.TYPE_FEATURE.CODE.equals(issue.getIssueType())){
                feature  = issue;
            }else if(IssueTypeEnum.TYPE_STORY.CODE.equals(issue.getIssueType())){
                issue = issueMapper.getParentIssue(issueId);
                feature = issue;
            }else if(IssueTypeEnum.TYPE_TASK.CODE.equals(issue.getIssueType())){
                issue = issueMapper.getParentIssue(issueId);
                issue = issueMapper.getParentIssue(issue.getParentId());
                feature = issue;
            }
        }
        return feature;
    }

    @Override
    public List<IssueDTO> queryStoryBySystemId(Long systemId,String storyName,Integer pageNum,Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        IssueExample issueExample = new IssueExample();
        List<Long> listLageId = Lists.newArrayList(104L,105L);
        IssueExample.Criteria criteria = issueExample.createCriteria();
        criteria.andSystemIdEqualTo(systemId).andStateEqualTo(StateEnum.U.getValue()).
                andStageIdEqualTo(StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue()).
                andLaneIdIn(listLageId);
        if(StringUtils.isNotBlank(storyName)){
            criteria.andTitleLike(storyName);
        }
        issueExample.setOrderByClause("create_time desc");
        List<IssueDTO> storys = issueMapper.selectByExampleDTO(issueExample);
        return storys;
    }
}