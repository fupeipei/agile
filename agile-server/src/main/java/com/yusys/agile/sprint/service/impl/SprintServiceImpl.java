package com.yusys.agile.sprint.service.impl;

import com.yusys.agile.burndown.dao.BurnDownChartDao;
import com.yusys.agile.burndown.dao.BurnDownChartStoryDao;
import com.yusys.agile.constant.StringConstant;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.enums.IssueStateEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.project.dto.ProjectFaultDTO;
import com.yusys.agile.project.dto.ProjectStoryDTO;
import com.yusys.agile.project.dto.ProjectTaskDTO;
import com.yusys.agile.review.dto.StoryCheckResultDTO;
import com.yusys.agile.review.service.ReviewService;
import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.dao.UserSprintHourMapper;
import com.yusys.agile.sprint.domain.*;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprint.dto.UserSprintHourDTO;
import com.yusys.agile.sprint.enums.SprintStatusEnum;
import com.yusys.agile.sprint.service.SprintReviewService;
import com.yusys.agile.sprint.service.SprintService;
import com.yusys.agile.team.dao.TeamMapper;
import com.yusys.agile.team.domain.Team;
import com.yusys.agile.team.domain.TeamExample;
import com.yusys.agile.team.dto.TeamDTO;
import com.yusys.agile.user.service.ProjectUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeProjectApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.entity.SsoProject;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.date.DateUtil;
import com.yusys.portal.util.number.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Date 2020/4/10
 */
@Service("sprintService")
public class SprintServiceImpl implements SprintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintServiceImpl.class);
    private static final String START_TIME_DESC = "START_TIME ASC";

    @Resource
    private SprintMapper sprintMapper;
    @Resource
    private TeamMapper teamMapper;
    @Resource
    private UserSprintHourMapper userSprintHourMapper;
    @Resource
    private IFacadeUserApi iFacadeUserApi;
    @Resource
    private IssueMapper issueMapper;
    @Resource
    private StoryService storyService;
    @Resource
    private IFacadeProjectApi iFacadeProjectApi;
    @Resource
    private SprintReviewService sprintReviewService;
    @Resource
    private ProjectUserService projectUserService;
    @Resource
    private BurnDownChartDao burnDownChartDao;
    @Resource
    private BurnDownChartStoryDao burnDownChartStoryDao;

    @Resource
    private ReviewService reviewService;
    /**
     * 百分号
     */
    private static final String PERCENT_SIGN = "%";

    private static final String CREATE_TIME_DESC = "CREATE_TIME DESC";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSprint(final SprintDTO sprintDTO, Long projectId) {
        sprintDTO.setProjectId(projectId);
        final SprintWithBLOBs sprint = ReflectUtil.copyProperties(sprintDTO, SprintWithBLOBs.class);
        //转换迭代有效日期为String，中间以|隔开
        Date startTime = getDate(sprintDTO, sprint);
        // 根据时间设置迭代状态
        Date now = new Date(System.currentTimeMillis());
        if (null != startTime) {
            if (startTime.getTime() > now.getTime()) {
                // 未开始
                sprint.setStatus(SprintStatusEnum.TYPE_NO_START_STATE.CODE);
            } else {
                // 已开始
                sprint.setStatus(SprintStatusEnum.TYPE_ONGOING_STATE.CODE);
            }
        }
        //判断版本号只能是英文数字_.常用字符
        versionNumberRegex(sprintDTO);
        int count = sprintMapper.insertSelective(sprint);
        // 创建失败抛异常
        if (count == 0) {
            throw new BusinessException("新建迭代失败!");
        }
        Long sprintId = arrangeSprintUser(sprintDTO, sprint.getSprintId(), sprint.getCreateUid(), projectId);
        return sprintId;

    }

    /**
     * @param sprintDTO
     * @param sprint
     * @Date 2020/4/24
     * @Description 时间戳排序，结束时间取最后日期后一天
     * @Return java.util.Date
     */
    private Date getDate(SprintDTO sprintDTO, SprintWithBLOBs sprint) {
        List<Date> sprintDays = sprintDTO.getSprintDayList();
        List<Date> dateList = sprintDays.stream().sorted().collect(Collectors.toList());
        sprint.setSprintDays(convertDateToStr(dateList));
        Date startTime = dateList.get(0);
        Date endTime = DateUtil.getAfterDay(dateList.get(dateList.size() - 1));
        sprint.setStartTime(startTime);
        sprint.setEndTime(endTime);
        return startTime;
    }

    /**
     * @param sprintDTO
     * @param sprintId
     * @param userId
     * @param projectId
     * @Date 2020/4/26
     * @Description 新建团队，创建迭代人员
     * @Return java.lang.Long
     */
    public Long arrangeSprintUser(SprintDTO sprintDTO, Long sprintId, Long userId, Long projectId) {
        // 根据团队id查，有团队就更新团队状态，没有就新建团队，然后分配给迭代
        Long teamId = sprintDTO.getTeamId();
        Team oldTeam = teamMapper.selectByPrimaryKey(teamId);
        if (null == oldTeam) {
            if (checkDuplicateTeamName(sprintDTO.getTeamName(), projectId)) {
                throw new BusinessException("团队名称已存在！");
            }
            Team team = new Team();
            team.setTeamName(sprintDTO.getTeamName());
            team.setCreateUid(userId);
            team.setProjectId(sprintDTO.getProjectId());
            team.setCreateTime(new Date());
            int i = teamMapper.insertSelective(team);
            if (i == 1) {
                i = sprintMapper.arrangeTeam(sprintId, team.getTeamId());
            }
            if (i != 1) {
                throw new BusinessException("迭代新建团队失败!");
            }
        } else {
            Team team = new Team();
            team.setTeamId(sprintDTO.getTeamId());
            if (!StringUtils.equals(oldTeam.getTeamName(), sprintDTO.getTeamName())) {
                if (checkDuplicateTeamName(sprintDTO.getTeamName(), projectId)) {
                    throw new BusinessException("团队名称已存在！");
                }
            }
            team.setTeamName(sprintDTO.getTeamName());
            team.setState(StateEnum.U.getValue());
            teamMapper.updateByPrimaryKeySelective(team);
        }
        // 创建迭代人员，失败抛异常
        createUserSprintHour(sprintDTO, sprintId, userId);
        return sprintId;
    }

    /**
     * @param sprintDTO
     * @param sprintId
     * @param userId
     * @Date 2020/4/26 16:23
     * @Description 创建迭代人员
     * @Return void
     */
    private void createUserSprintHour(SprintDTO sprintDTO, Long sprintId, Long userId) {
        List<UserSprintHourDTO> sprintHourDTOS = sprintDTO.getMembers();
        if (CollectionUtils.isNotEmpty(sprintHourDTOS)) {
            for (UserSprintHourDTO userSprintHourDTO : sprintHourDTOS) {
                UserSprintHour userSprintHour = ReflectUtil.copyProperties(userSprintHourDTO, UserSprintHour.class);
                userSprintHour.setSprintId(sprintId);
                userSprintHour.setCreateUid(userId);
                userSprintHour.setCreateTime(new Date());
                int i = userSprintHourMapper.insert(userSprintHour);
                if (i != 1) {
                    throw new BusinessException("创建迭代人员失败!");
                }
            }
        }
    }

    @Override
    public SprintDTO viewEdit(Long sprintId, Long projectId) {
        SprintWithBLOBs sprint = sprintMapper.selectByPrimaryKey(sprintId);
        if (null == sprint || !StringUtils.equals(sprint.getState(), StateEnum.U.getValue())) {
            return new SprintDTO();
        }
        SprintDTO sprintDTO = ReflectUtil.copyProperties(sprint, SprintDTO.class);

        Team team = teamMapper.selectByPrimaryKey(sprint.getTeamId());
        if (null != team) {
            sprintDTO.setTeamName(teamMapper.selectByPrimaryKey(sprint.getTeamId()).getTeamName());
        }
        //将有效日期由String转为timeStamp
        List<Date> dateList;
        if (StringUtils.isNotEmpty(sprint.getSprintDays())) {
            dateList = convertStrToDate(sprint.getSprintDays());
            sprintDTO.setSprintDayList(dateList);
        }
        //获取团队信息
        List<TeamDTO> teamDTOS = getOptionalMembers4Team(projectId, sprintId);
        sprintDTO.setTeamDTOList(teamDTOS);
        //统计迭代中的故事总数和已完成数以及任务总数和已完成数
        countStoryAndTask(sprintDTO);
        //统计迭代中的缺陷总数和已完成数
        countFaults4Sprint(sprintDTO);
        //统计迭代的已完成工作量和剩余工作量
        countWorkload(sprintDTO);
        return sprintDTO;
    }

    /**
     * @param sprintDTO
     * @Date 2021/2/14
     * @Description 统计迭代的已完成工作量和剩余工作量
     * @Return void
     */
    private void countWorkload(SprintDTO sprintDTO) {
        Integer planWorkload = issueMapper.sumPlanWorkload4Sprint(sprintDTO.getSprintId());
        Integer remainWorkload = issueMapper.sumRemainWorkload4Sprint(sprintDTO.getSprintId());
        if (null != planWorkload && null != remainWorkload) {
            Integer finishWorkload = planWorkload - remainWorkload;
            sprintDTO.setPlanWorkload(planWorkload);
            sprintDTO.setFinishWorkload(finishWorkload);
        }
    }

    /**
     * @param sprintDTO
     * @Date 2021/2/14
     * @Description 统计项目中的缺陷总数和已完成数
     * @Return void
     */
    private void countFaults4Sprint(SprintDTO sprintDTO) {
        Integer countFaults4Sprint = issueMapper.countFaults4SprintId(sprintDTO.getSprintId());
        Integer countFinishedFaults4Sprint = issueMapper.countFinishedFaults4SprintId(sprintDTO.getSprintId());
        if (null != countFaults4Sprint && null != countFinishedFaults4Sprint) {
            sprintDTO.setFault(new ProjectFaultDTO(countFaults4Sprint, countFinishedFaults4Sprint));
        }
    }

    /**
     * @param projectId
     * @param sprintId
     * @Date 2020/4/26 16:24
     * @Description 获取团队以及团队中人员信息
     * @Return java.util.List<com.yusys.agile.team.dto.TeamDTO>
     */
    private List<TeamDTO> getOptionalMembers4Team(Long projectId, Long sprintId) {
        //通过projectId查询所有团队
        List<Team> teams = teamMapper.getTeams4Project(projectId);
        // 空的直接返回
        if (CollectionUtils.isEmpty(teams)) {
            return new ArrayList<>();
        }
        List<TeamDTO> teamDTOS = new ArrayList<>();
        for (Team team : teams) {
            TeamDTO teamDTO = ReflectUtil.copyProperties(team, TeamDTO.class);
            List<UserSprintHourDTO> userSprintHourDTOS = new ArrayList<>();
            //通过迭代id查询迭代时长表
            List<UserSprintHour> userSprintHours = userSprintHourMapper.getUserIds4Sprint(sprintId);
            if (CollectionUtils.isNotEmpty(userSprintHours)) {
                getUser(userSprintHourDTOS, userSprintHours);
            }
            teamDTO.setUsers(userSprintHourDTOS);
            teamDTOS.add(teamDTO);
        }
        return teamDTOS;
    }

    private void getUser(List<UserSprintHourDTO> userSprintHourDTOS, List<UserSprintHour> userSprintHours) {
        for (UserSprintHour userSprintHour : userSprintHours) {
            SsoUser user = iFacadeUserApi.queryUserById(userSprintHour.getUserId());
            UserSprintHourDTO userSprintHourDTO = ReflectUtil.copyProperties(userSprintHour, UserSprintHourDTO.class);
            if (null != user) {
                userSprintHourDTO.setUserId(user.getUserId());
                userSprintHourDTO.setUserName(user.getUserName());
                userSprintHourDTO.setUserAccount(user.getUserAccount());
                userSprintHourDTOS.add(userSprintHourDTO);
            }
        }
    }

    @Override
    public String convertDateToStr(List<Date> dateList) {
        StringBuilder sprintDaysStr = new StringBuilder();
        for (int i = 0; i < dateList.size(); i++) {
            sprintDaysStr.append(dateList.get(i).getTime());
            if (i < dateList.size() - 1) {
                sprintDaysStr.append("|");
            }
        }
        return sprintDaysStr.toString();
    }

    @Override
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

    @Override
    public boolean canEdit(Long sprintId) {
        Sprint sprint = sprintMapper.selectByPrimaryKey(sprintId);
        Byte status = sprint.getStatus();
        if (status.equals(SprintStatusEnum.TYPE_CANCEL_STATE.CODE) || status.equals(SprintStatusEnum.TYPE_FINISHED_STATE.CODE)) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editSprint(SprintDTO sprintDTO, Long projectId) {
        final SprintWithBLOBs sprint = ReflectUtil.copyProperties(sprintDTO, SprintWithBLOBs.class);
        final Long sprintId = sprint.getSprintId();
        final SprintWithBLOBs origin = sprintMapper.selectByPrimaryKey(sprintId);
        // 根据时间设置迭代状态
        Date now = new Date(System.currentTimeMillis());
        Date startTime = getDate(sprintDTO, sprint);
        if (null != startTime) {
            if (startTime.getTime() > now.getTime()) {
                // 未开始
                sprint.setStatus(SprintStatusEnum.TYPE_NO_START_STATE.CODE);
            } else {
                // 已开始
                sprint.setStatus(SprintStatusEnum.TYPE_ONGOING_STATE.CODE);
            }
        }
        sprint.setUpdateTime(new Date());
        if (null == sprintDTO.getTeamId()) {
            sprint.setTeamId(-1L);
        }
        long originTeamId = origin.getTeamId();
        //判断版本号只能是英文数字_.常用字符
        versionNumberRegex(sprintDTO);
        int i = sprintMapper.updateByPrimaryKeySelective(sprint);
        if (i == 0) {
            return i;
        }
        //清除没迭代的原始团队
        deleteUselessTeam(originTeamId);

        // 移除sprintUser
        userSprintHourMapper.deleteBySprintId(sprintId);

        // 再新建
        Long canArrange = arrangeSprintUser(sprintDTO, sprintId, sprint.getCreateUid(), projectId);
        if (null != canArrange) {
            i = -1;
        }
        return i;
    }

    /**
     * @param sprintDTO
     * @Date 2021/2/16
     * @Description 判断版本号只能是英文数字_.常用字符
     * @Return void
     */
    private void versionNumberRegex(SprintDTO sprintDTO) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(sprintDTO.getVersionNumber());
        if (m.find()) {
            throw new BusinessException("版本号只能是英文数字_.等常用字符！");
        }
    }

    /**
     * @param sprintDTO
     * @Date 2020/4/27
     * @Description 检查版本号是否重复
     * @Return boolean
     */
    private void checkDuplicateVersionNumber(SprintDTO sprintDTO, Long projectId) {
        SprintExample sprintExample = new SprintExample();
        SprintExample.Criteria criteria = sprintExample.createCriteria();
        criteria.andVersionNumberEqualTo(sprintDTO.getVersionNumber()).andStateEqualTo(StateEnum.U.getValue());
        List<Sprint> sprints = sprintMapper.selectByExample(sprintExample);
        if (CollectionUtils.isNotEmpty(sprints)) {
            for (Sprint sprint : sprints) {
                if (null == sprintDTO.getSprintId()) {
                    checkDuplicate(sprintDTO, projectId, sprint);
                } else {
                    if (!sprintDTO.getSprintId().equals(sprint.getSprintId())) {
                        checkDuplicate(sprintDTO, projectId, sprint);
                    }
                }
            }
        }
    }

    /**
     * @param sprintDTO
     * @param projectId
     * @param sprint
     * @Date 2021/2/21
     * @Description 确定版本编号与哪个项目重复
     * @Return void
     */
    private void checkDuplicate(SprintDTO sprintDTO, Long projectId, Sprint sprint) {
        if (StringUtils.equals(sprintDTO.getVersionNumber(), sprint.getVersionNumber())) {
            SsoProject project = iFacadeProjectApi.getProjectInfoById(sprint.getProjectId());
            if (projectId.equals(sprint.getProjectId())) {
                throw new BusinessException("迭代编号与本项目中迭代编号重复！");
            }
            throw new BusinessException("迭代编号与《" + project.getProjectName() + "》项目中迭代编号重复！");
        }
    }

    /**
     * @param teamName
     * @param projectId
     * @Date 2020/4/27
     * @Description 检查团队名称是否重复
     * @Return boolean
     */
    private boolean checkDuplicateTeamName(String teamName, Long projectId) {
        TeamExample example = new TeamExample();
        TeamExample.Criteria criteria = example.createCriteria();
        criteria.andTeamNameEqualTo(teamName).andStateEqualTo(StateEnum.U.getValue()).andProjectIdEqualTo(projectId);
        return CollectionUtils.isNotEmpty(teamMapper.selectByExample(example));
    }

    @Override
    public List<SprintDTO> getSprintByTeamId(Long teamId, String sprintName, Integer pageNum, Integer pageSize, Long projectId) {
        if (null == projectId) {
            LOGGER.info("查询迭代的项目id为空！");
            return new ArrayList<>();
        }
        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        SprintExample sprintExample = new SprintExample();
        sprintExample.setOrderByClause(CREATE_TIME_DESC);
        SprintExample.Criteria criteria = sprintExample.createCriteria().andProjectIdEqualTo(projectId).andTeamIdEqualTo(teamId).andStateEqualTo(StateEnum.U.getValue()).
                andStatusNotEqualTo(SprintStatusEnum.TYPE_INVALID_STAE.CODE).andSprintNameLike(PERCENT_SIGN + sprintName + PERCENT_SIGN);
        List<SprintDTO> sprintDTOS = sprintMapper.selectByExampleDTO(sprintExample);
        if (CollectionUtils.isNotEmpty(sprintDTOS)) {
            for (SprintDTO sprintDTO : sprintDTOS) {
                Integer countTasks4Sprint = issueMapper.countTasks4Sprint(sprintDTO.getSprintId());
                Integer countUnFinishedTasks4Sprint = issueMapper.countUnFinishedTasks4Sprint(sprintDTO.getSprintId());
                Integer countFinishedTasks4Sprint = countTasks4Sprint - countUnFinishedTasks4Sprint;
                if (countTasks4Sprint != 0) {
                    sprintDTO.setProgress(NumberUtil.scale((double) countFinishedTasks4Sprint / countTasks4Sprint, 2));
                } else {
                    sprintDTO.setProgress(0.0);
                }
            }
        }
        return sprintDTOS;
    }

    @Override
    public Integer getWorkload(Long sprintId) {
        List<UserSprintHour> userSprintHours = userSprintHourMapper.getUserIds4Sprint(sprintId);
        int sprintHours = 0;
        for (UserSprintHour userSprintHour : userSprintHours) {
            sprintHours += userSprintHour.getReallyHours().intValue();
        }
        return sprintHours;
    }

    @Override
    public boolean legalDate(String sprintDays, Date target) {
        List<Date> dateList = convertStrToDate(sprintDays);
        return DateUtil.isBetween(dateList, target);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSprint(Long sprintId, Long projectId) {
        Sprint sprint = sprintMapper.selectByPrimaryKey(sprintId);
        IssueExample example = new IssueExample();
        IssueExample.Criteria criteria = example.createCriteria();
        criteria.andSprintIdEqualTo(sprintId);
        List<Issue> issues = issueMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(issues)) {
            for (Issue issue : issues) {
                //移除迭代下的故事和任务工作项
                if (issue.getIssueType().equals(IssueTypeEnum.TYPE_STORY.CODE)) {
                    storyService.removeStory4Sprint(sprintId, issue.getIssueId());
                }
                //移除迭代下的缺陷
                if (issue.getIssueType().equals(IssueTypeEnum.TYPE_FAULT.CODE)) {
                    issue.setSprintId(null);
                    issue.setUpdateTime(new Date());
                    issueMapper.updateByPrimaryKey(issue);
                }
            }
        }
        sprint.setSprintId(sprintId);
        sprint.setState(StateEnum.E.getValue());
        int i = sprintMapper.updateByPrimaryKey(sprint);
        if (i > 0) {
            //解除团队关系
            Long teamId = sprint.getTeamId();
            deleteUselessTeam(teamId);
            // 移除sprintUser
            userSprintHourMapper.deleteBySprintId(sprintId);
            //删除迭代回顾信息
            sprintReviewService.deleteSprintReviewBySprintId(sprintId);
            //删除迭代回顾附件信息
            sprintReviewService.deleteAttachmentBySprintId(sprintId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean arrangeIssue(SprintDTO sprintDTO) {
        List<Long> issueIds = sprintDTO.getIssueIds();
        if (CollectionUtils.isNotEmpty(issueIds)) {
            for (Long issueId : issueIds) {

                // 评审拦截
                if (IssueTypeEnum.TYPE_STORY.CODE.equals(sprintDTO.getIssueType())) {
                    StoryCheckResultDTO storyCheckResultDTO = reviewService.allowStoryInSprint(issueId, sprintDTO.getProjectId());
                    if (null != storyCheckResultDTO && !storyCheckResultDTO.getHasPassed()) {
                        LOGGER.info("由于未通过评审，迭代关联故事失败！storyId = {}", issueId);
                        throw new BusinessException(storyCheckResultDTO.getMsg());
                    }
                }

                //工作项加入迭代
                storyService.distributeSprint(issueId, sprintDTO.getSprintId());
            }
        }
        return true;
    }

    @Override
    public List<SprintDTO> queryUnFinishedByProjectId(String idOrName, Long projectId, Integer pageNum, Integer pageSize) {
        if (null == projectId) {
            return new ArrayList<>();
        }
        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<Byte> status = new ArrayList<>();
        status.add(SprintStatusEnum.TYPE_NO_START_STATE.CODE);
        status.add(SprintStatusEnum.TYPE_ONGOING_STATE.CODE);
        SprintExample sprintExample = new SprintExample();
        sprintExample.setOrderByClause(CREATE_TIME_DESC);
        SprintExample.Criteria criteria = sprintExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId).andStateEqualTo(StateEnum.U.getValue()).andStatusIn(status);
        SprintExample.Criteria criteria2 = sprintExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId).andStateEqualTo(StateEnum.U.getValue()).andStatusIn(status);

        // 判断是根据id还是name
        judgeIdOrName(idOrName, sprintExample, criteria, criteria2);

        List<SprintDTO> sprintDTOS = sprintMapper.selectByExampleDTO(sprintExample);
        if (CollectionUtils.isEmpty(sprintDTOS)) {
            return new ArrayList<>();
        }
        //获取团队名称
        for (SprintDTO sprintDTO : sprintDTOS) {
            getTeamName(sprintDTO);
        }
        return sprintDTOS;
    }

    @Override
    public List<SprintDTO> viewSprints(String sprintName, String sprintType, String versionNumber, Long projectId, Integer pageNum, Integer pageSize) {
        if (null == projectId) {
            return new ArrayList<>();
        }
        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        SprintExample sprintExample = new SprintExample();
        sprintExample.setOrderByClause(CREATE_TIME_DESC);
        SprintExample.Criteria criteria = sprintExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId).andStateEqualTo(StateEnum.U.getValue()).
                andStatusNotEqualTo(SprintStatusEnum.TYPE_INVALID_STAE.CODE).andSprintNameLike(PERCENT_SIGN + sprintName + PERCENT_SIGN);

        if (StringUtils.isNotEmpty(versionNumber)) {
            criteria.andVersionNumberEqualTo(versionNumber);
        }
        List<SprintDTO> sprintDTOS = sprintMapper.selectByExampleDTO(sprintExample);
        if (CollectionUtils.isNotEmpty(sprintDTOS)) {
            for (SprintDTO sprintDTO : sprintDTOS) {
                List<Date> dateList = convertStrToDate(sprintDTO.getSprintDays());
                sprintDTO.setPlanDays(dateList.size());
                //统计迭代中的故事总数和已完成数以及任务总数和已完成数
                countStoryAndTask(sprintDTO);
                //获取团队名称
                getTeamName(sprintDTO);
                //获取团队人数
                Integer userNum = userSprintHourMapper.countSprintUser4Sprint(sprintDTO.getSprintId());
                if (null != userNum) {
                    sprintDTO.setUserNum(userNum);
                }
                //获取迭代下的关联用户故事IDS
                getSprintStorys(sprintDTO, sprintType);
            }

        }
        return sprintDTOS;
    }

    private void getSprintStorys(SprintDTO sprintDTO, String sprintType) {
        if (StringConstant.SPRINT_TYPE.equals(sprintType)) {
            List<Long> storyIds = new ArrayList<>();
            Long sprintId = sprintDTO.getSprintId();
            IssueExample example = new IssueExample();
            example.createCriteria().andSprintIdEqualTo(sprintId)
                    .andStateEqualTo(StateEnum.U.toString())
                    .andIssueTypeEqualTo(IssueTypeEnum.TYPE_STORY.CODE);
            List<Issue> issueList = issueMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(issueList)) {
                issueList.forEach(issue -> {
                    storyIds.add(issue.getIssueId());
                });
            }
            sprintDTO.setStoryIds(storyIds);
        }


    }

    /**
     * @param sprintDTO
     * @Date 2021/2/7
     * @Description 统计迭代中的故事总数和已完成数以及任务总数和已完成数
     * @Return void
     */
    private void countStoryAndTask(SprintDTO sprintDTO) {
        Integer countTasks4Sprint = issueMapper.countTasks4SprintId(sprintDTO.getSprintId());
        Integer countFinishedTasks4Sprint = issueMapper.countFinishedTasks4SprintId(sprintDTO.getSprintId());
        if (null != countTasks4Sprint && null != countFinishedTasks4Sprint) {
            sprintDTO.setTask(new ProjectTaskDTO(countTasks4Sprint, countFinishedTasks4Sprint));
        }
        Integer countStory4Sprint = issueMapper.countStories4Sprint(sprintDTO.getSprintId());
        Integer countUnFinishedStorys4Sprint = issueMapper.countInsprintBySprint(sprintDTO.getSprintId());
        if (null != countStory4Sprint && null != countUnFinishedStorys4Sprint) {
            Integer countFinishedStorys4Sprint = countStory4Sprint - countUnFinishedStorys4Sprint;
            sprintDTO.setStory(new ProjectStoryDTO(countStory4Sprint, countFinishedStorys4Sprint));
        }
    }

    /**
     * @param sprintDTO
     * @Date 2021/2/4
     * @Description 获取团队名称
     * @Return void
     */
    private void getTeamName(SprintDTO sprintDTO) {
        if (null != sprintDTO.getTeamId()) {
            Team team = teamMapper.selectByPrimaryKey(sprintDTO.getTeamId());
            sprintDTO.setTeamName(team.getTeamName());
        }
    }

    /**
     * @param idOrName
     * @param sprintExample
     * @param criteria
     * @param criteria2
     * @Date 2020/4/28
     * @Description 判断传来的参数是id还是name
     * @Return void
     */
    private void judgeIdOrName(String idOrName, SprintExample sprintExample, SprintExample.Criteria criteria, SprintExample.Criteria criteria2) {
        if (StringUtils.isNotBlank(idOrName)) {
            try {
                Long id = Long.valueOf(idOrName);
                // 能转成long，说明可能是id，也可能是name
                criteria.andSprintIdEqualTo(id);
                // 同时赋值给标题
                criteria2.andSprintNameLike(PERCENT_SIGN + idOrName + PERCENT_SIGN);
                sprintExample.or(criteria2);
            } catch (Exception e) {
                LOGGER.info("迭代列表转换异常e:{}", e);
                // 存在异常说明只能查name
                criteria.andSprintNameLike(PERCENT_SIGN + idOrName + PERCENT_SIGN);
            }
        }
    }

    /**
     * @param issueIds
     * @Date 2020/4/27
     * @Description 通过工作项id判断数据是否有效
     * @Return boolean
     */
    private boolean canArrange4Storys(List<Long> issueIds) {
        return issueMapper.count4ArrangedByIds(issueIds) > 0 ? false : true;
    }

    /**
     * @param teamId
     * @Date 2020/4/26 16:28
     * @Description 把没迭代的原始团队数据设置无效
     * @Return void
     */
    public void deleteUselessTeam(Long teamId) {
        SprintExample sprintExample = new SprintExample();
        SprintExample.Criteria criteria = sprintExample.createCriteria();
        criteria.andTeamIdEqualTo(teamId).andStateEqualTo(StateEnum.U.getValue());
        List<Sprint> sprints = sprintMapper.selectByExample(sprintExample);
        if (CollectionUtils.isEmpty(sprints)) {
            Team team = teamMapper.selectByPrimaryKey(teamId);
            team.setState(StateEnum.E.getValue());
            teamMapper.updateByPrimaryKey(team);
        }
    }

    @Override
    public List<SsoUser> listUsersBySprintId(Long projectId, Long sprintId) {
        List<SsoUser> ssoUserList = new ArrayList<>();
        Sprint sprint = sprintMapper.selectByPrimaryKey(sprintId);
        if (!projectId.equals(sprint.getProjectId())) {
            LOGGER.info("当前项目与当前迭代项目不一致!");
            return new ArrayList<>();
        }
        UserSprintHourExample example = new UserSprintHourExample();
        UserSprintHourExample.Criteria criteria = example.createCriteria();
        criteria.andSprintIdEqualTo(sprintId);
        List<UserSprintHour> userSprintHours = userSprintHourMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(userSprintHours)) {
            for (UserSprintHour userSprintHour : userSprintHours) {
                SsoUser ssoUserDTO = iFacadeUserApi.queryUserById(userSprintHour.getUserId());
                if (Optional.ofNullable(ssoUserDTO).isPresent()) {
                    SsoUser ssoUser = ReflectUtil.copyProperties(ssoUserDTO, SsoUser.class);
                    ssoUserList.add(ssoUser);
                }

            }
        }
        return ssoUserList;
    }

    @Override
    public List<SprintDTO> selectSprint(Long sprintId, Long projectId) {
        List<SprintDTO> sprintDTOS = Lists.newArrayList();
        SprintExample sprintExample = new SprintExample();
        SprintExample.Criteria c = sprintExample.createCriteria();
        if (sprintId != null) {
            c.andProjectIdEqualTo(sprintId);
        }
        c.andProjectIdEqualTo(projectId);
        List<SprintWithBLOBs> sprintWithBLOBs = sprintMapper.selectByExampleWithBLOBs(sprintExample);
        try {
            sprintDTOS = ReflectUtil.copyProperties4List(sprintWithBLOBs, SprintDTO.class);

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return sprintDTOS;
    }

    /**
     * 功能描述  根据迭代id集合查询迭代信息
     *
     * @param sprintIds
     * @return com.yusys.agile.sprint.dto.SprintDTO
     * @date 2020/10/21
     */
    @Override
    public List<SprintDTO> selectSprintsBySprintIdList(List<Long> sprintIds) {
        List<SprintDTO> sprintDTOS = Lists.newArrayList();
        SprintExample sprintExample = new SprintExample();
        SprintExample.Criteria c = sprintExample.createCriteria();
        if (CollectionUtils.isNotEmpty(sprintIds)) {
            c.andSprintIdIn(sprintIds);
        }
        List<SprintWithBLOBs> sprintWithBLOBs = sprintMapper.selectByExampleWithBLOBs(sprintExample);
        try {
            sprintDTOS = ReflectUtil.copyProperties4List(sprintWithBLOBs, SprintDTO.class);

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return sprintDTOS;
    }

    @Override
    public int completeSprint(Long projectId, Long sprintId) {
        LOGGER.info("关闭迭代入参projectId={},sprintId={}", projectId, sprintId);
        Sprint sprint = sprintMapper.selectByPrimaryKey(sprintId);
        if (!projectId.equals(sprint.getProjectId())) {
            LOGGER.info("当前项目与当前迭代项目不一致!");
            return -2;
        }
        if (sprint.getStatus().equals(SprintStatusEnum.TYPE_NO_START_STATE.CODE)) {
            throw new BusinessException("迭代未开始，不许完成！");
        }
        Integer countTasks4Sprint = issueMapper.countTasks4SprintId(sprintId);
        Integer countFinishedTasks4Sprint = issueMapper.countFinishedTasks4SprintId(sprintId);
        if (null != countTasks4Sprint && null != countFinishedTasks4Sprint) {
            if (countTasks4Sprint - countFinishedTasks4Sprint != 0) {
                LOGGER.info("迭代下任务数大于已完成的任务数！countTasks4Sprint={},countFinishedTasks4Sprint={}", countTasks4Sprint, countFinishedTasks4Sprint);
                return -1;
            }
        }
        Integer countFaults4Sprint = issueMapper.countFaults4SprintId(sprintId);
        Integer countFinishedFaults4Sprint = issueMapper.countFinishedFaults4SprintId(sprintId);
        if (null != countFaults4Sprint && null != countFinishedFaults4Sprint) {
            if (countFaults4Sprint - countFinishedFaults4Sprint != 0) {
                LOGGER.info("迭代下缺陷数大于已完成的缺陷数！countFaults4Sprint={},countFinishedFaults4Sprint={}", countFaults4Sprint, countFinishedFaults4Sprint);
                return -1;
            }
        }
        return sprintMapper.completeById(sprintId);
    }

    @Override
    public void changeStatusDaily() {
        final List<Long> sprintIds = sprintMapper.getUnStartIds(new Date(System.currentTimeMillis()));
        LOGGER.info("change state daily sprintIds:{}", sprintIds);
        if (null != sprintIds && !sprintIds.isEmpty()) {
            sprintMapper.changeStatusTOProgressByIds(sprintIds);
        }
    }

    @Override
    public PageInfo sprintUserInfo(Long projectId, Long sprintId, Integer pageNum, Integer pageSize) {
        List<SsoUser> ssoUserList = listUsersBySprintId(projectId, sprintId);
        return projectUserService.commitUserInfo(projectId, sprintId, pageNum, pageSize, ssoUserList);
    }

    @Override
    public List<SprintDTO> getNotStartedSprint(Long projectId) {
        SprintExample example = new SprintExample();
        example.createCriteria().andProjectIdEqualTo(projectId).andStatusEqualTo(SprintStatusEnum.TYPE_NO_START_STATE.CODE).andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        example.setOrderByClause(START_TIME_DESC);
        List<SprintDTO> sprintDTOList = sprintMapper.selectByExampleDTO(example);
        for (SprintDTO sprintDTO : sprintDTOList) {
            getTeamName(sprintDTO);
        }
        return sprintDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void distributeStorys(List<Long> listStorys, Long projectId, Long oldSprintId, Long sprintId) {
        burnDownChartDao.cancelByStorys(oldSprintId, listStorys);
        burnDownChartStoryDao.cancelByStorys(oldSprintId, listStorys);
        //storyService.distributeSprints(listStorys, sprintId);
        storyService.distributeStoryAndTaskAndFaultToSprint(listStorys, projectId, oldSprintId, sprintId);

        // 处理只关联迭代的缺陷
        List<Issue> faults = storyService.dealFaultsOnlyInSprint(oldSprintId, sprintId);
        //处理燃尽图表里面的缺陷
        dealFaultsOnlyInBurnDownChart(faults, oldSprintId);
        completeSprint(projectId, oldSprintId);
    }

    /**
     * @param faults
     * @param oldSprintId
     * @description 处理燃尽图中迭代关联的缺陷
     * @date 2020/09/01
     */
    private void dealFaultsOnlyInBurnDownChart(List<Issue> faults, Long oldSprintId) {
        if (CollectionUtils.isNotEmpty(faults)) {
            List<Long> faultIdList = Lists.newArrayList();
            for (Issue fault : faults) {
                faultIdList.add(fault.getIssueId());
            }
            burnDownChartDao.cancelByFaults(oldSprintId, faultIdList);
        }
    }

    /**
     * 功能描述:提供cicd接口，根据项目id查询迭代信息
     *
     * @param projectId
     * @return java.util.List<com.yusys.agile.sprint.dto.SprintDTO>
     * @date 2020/8/19
     */
    @Override
    public List<SprintDTO> listUnFinisherSprintsByProjectId(Long projectId, String name, Integer pageNum, Integer pageSize) {
        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        SprintExample sprintExample = new SprintExample();
        sprintExample.setOrderByClause(CREATE_TIME_DESC);
        SprintExample.Criteria criteria = sprintExample.createCriteria();
        List<Byte> notFinishedSprint = Lists.newArrayList();
        notFinishedSprint.add(SprintStatusEnum.TYPE_VALID_STATE.CODE);
        notFinishedSprint.add(SprintStatusEnum.TYPE_NO_START_STATE.CODE);
        notFinishedSprint.add(SprintStatusEnum.TYPE_ONGOING_STATE.CODE);
        criteria.andProjectIdEqualTo(projectId).andStateEqualTo(StateEnum.U.getValue()).
                andStatusIn(notFinishedSprint);

        if (StringUtils.isNotBlank(name)) {
            criteria.andSprintNameLike("%" + name + "%");
        }

        List<SprintDTO> sprintDTOS = sprintMapper.selectByExampleDTO(sprintExample);

        return sprintDTOS;
    }

    /**
     * 功能描述:根据项目id查询所有的迭代信息
     *
     * @param projectId
     * @param
     * @return java.util.List<com.yusys.agile.sprint.dto.SprintDTO>
     * @date 2021/2/5
     */
    @Override
    public List<SprintDTO> listAllSprintsByProjectId(Long projectId, String sprintName) {
        if (null == projectId) {
            return new ArrayList<>();
        }

        SprintExample sprintExample = new SprintExample();
        sprintExample.setOrderByClause(CREATE_TIME_DESC);
        SprintExample.Criteria criteria = sprintExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId).andStateEqualTo(StateEnum.U.getValue()).
                andStatusNotEqualTo(SprintStatusEnum.TYPE_INVALID_STAE.CODE);

        if (StringUtils.isNotEmpty(sprintName)) {
            criteria.andSprintNameLike(PERCENT_SIGN + sprintName + PERCENT_SIGN);
        }
        List<SprintDTO> sprintDTOS = sprintMapper.selectByExampleDTO(sprintExample);
        return sprintDTOS;
    }
}