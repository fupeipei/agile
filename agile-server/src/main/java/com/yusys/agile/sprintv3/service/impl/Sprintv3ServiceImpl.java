package com.yusys.agile.sprintv3.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.StoryStatusEnum;
import com.yusys.agile.issue.enums.TaskStatusEnum;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprint.enums.SprintStatusEnum;
import com.yusys.agile.sprintV3.dto.*;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.dao.SSprintUserHourMapper;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintExample;
import com.yusys.agile.sprintv3.domain.SSprintUserHour;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.responseModel.SprintMembersWorkHours;
import com.yusys.agile.sprintv3.responseModel.SprintOverView;
import com.yusys.agile.sprintv3.responseModel.SprintStatisticalInformation;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetail;
import com.yusys.agile.team.domain.Team;
import com.yusys.agile.team.dto.TeamDTO;
import com.yusys.agile.teamV3.dto.TeamV3DTO;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.dao.STeamMemberMapper;
import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.agile.teamv3.domain.STeamSystem;
import com.yusys.agile.teamv3.service.STeamSystemService;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.dto.SsoSystemDTO;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.date.DateUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.yusys.agile.sprintv3.enums.SprintStatusEnum.TYPE_NO_START_STATE;
import static com.yusys.agile.sprintv3.enums.SprintStatusEnum.TYPE_ONGOING_STATE;

/**
 * @Author zhaofeng
 * @Date 2021/5/11 14:48
 */
@Service
@Slf4j
public class Sprintv3ServiceImpl implements Sprintv3Service {

    @Resource
    private SSprintMapper ssprintMapper;
    @Resource
    private SSprintUserHourMapper sSprintUserHourMapper;
    @Resource
    private STeamMapper sTeamMapper;
    @Resource
    private STeamSystemMapper sTeamSystemMapper;
    @Resource
    private STeamMemberMapper sTeamMemberMapper;
    @Autowired
    private IFacadeSystemApi iFacadeSystemApi;
    @Autowired
    private IFacadeUserApi iFacadeUserApi;
    @Autowired
    private StoryService storyService;
    @Resource
    private IssueMapper issueMapper;
    @Resource
    private Teamv3Service teamv3Service;
    @Resource
    private STeamSystemService teamSystemService;



    String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    @Override
    public SprintV3DTO viewEdit(Long sprintId) {
        SSprintWithBLOBs sprint = ssprintMapper.selectByPrimaryKey(sprintId);
        if (null == sprint || !StringUtils.equals(sprint.getState(), StateEnum.U.getValue())) {
            return new SprintV3DTO();
        }
        SprintV3DTO sprintDTO = ReflectUtil.copyProperties(sprint, SprintV3DTO.class);
        STeam team = sTeamMapper.selectByPrimaryKey(sprint.getTeamId());
        if (null != team) {
            sprintDTO.setTeamName(sTeamMapper.selectByPrimaryKey(sprint.getTeamId()).getTeamName());
        }
        //将有效日期由String转为timeStamp
        List<Date> dateList;
        if (StringUtils.isNotEmpty(sprint.getSprintDays())) {
            dateList = convertStrToDate(sprint.getSprintDays());
            sprintDTO.setSprintDayList(dateList);
        }
        //获取团队信息
        List<TeamV3DTO> teamDTOS = getOptionalMembers4Team(sprint.getTeamId(), sprintId);
        sprintDTO.setTeamDTOList(teamDTOS);

        return sprintDTO;
    }

    /**
     * string 转date
     *
     * @param str
     * @return
     */
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
     * @param sprintId
     * @Date 2021/05/11 16:24
     * @Description 获取团队以及团队中人员信息
     * @Return java.util.List<com.yusys.agile.team.dto.TeamDTO>
     */
    private List<TeamV3DTO> getOptionalMembers4Team(Long teamId, Long sprintId) {
        //通过projectId查询所有团队
        List<STeam> teams = sTeamMapper.getTeamsByTeamId(teamId);
        // 空的直接返回
        if (CollectionUtils.isEmpty(teams)) {
            List<TeamV3DTO> teamDTOS = new ArrayList<>();
            List<SprintV3UserHourDTO> userSprintHourDTOS = new ArrayList<>();
            List<SSprintUserHour> userSprintHours = sSprintUserHourMapper.getUserIds4Sprint(sprintId);
            if (CollectionUtils.isNotEmpty(userSprintHours)) {
                getUser(userSprintHourDTOS, userSprintHours);
            }
            TeamV3DTO teamDTO = new TeamV3DTO();
            teamDTO.setUsers(userSprintHourDTOS);
            teamDTOS.add(teamDTO);
            return teamDTOS;
        }
        List<TeamV3DTO> teamDTOS = new ArrayList<>();
        for (STeam team : teams) {
            TeamV3DTO teamDTO = ReflectUtil.copyProperties(team, TeamV3DTO.class);
            List<SprintV3UserHourDTO> userSprintHourDTOS = this.queryUsersBySprintId(sprintId);
            teamDTO.setUsers(userSprintHourDTOS);
            //查询团队下的子系统
            List<Long> systemIds = sTeamSystemMapper.querySystemIdByTeamId(teamDTO.getTeamId());
            List<SsoSystemRestDTO> systemByIds = iFacadeSystemApi.getSystemByIds(systemIds);
            teamDTO.setTeamSystems(systemByIds);

            teamDTOS.add(teamDTO);
        }
        return teamDTOS;
    }

    private List<SprintV3UserHourDTO> queryUsersBySprintId(Long sprintId) {
        List<SprintV3UserHourDTO> userSprintHourDTOS = new ArrayList<>();
        //通过迭代id查询迭代时长表的userid，然后再查人员
        List<SSprintUserHour> userSprintHours = sSprintUserHourMapper.getUserIds4Sprint(sprintId);
        if (CollectionUtils.isNotEmpty(userSprintHours)) {
            getUser(userSprintHourDTOS, userSprintHours);
        }
        return userSprintHourDTOS;
    }

    private void getUser(List<SprintV3UserHourDTO> userSprintHourDTOS, List<SSprintUserHour> userSprintHours) {
        for (SSprintUserHour userSprintHour : userSprintHours) {
            SsoUser user = iFacadeUserApi.queryUserById(userSprintHour.getUserId());
            SprintV3UserHourDTO userSprintHourDTO = ReflectUtil.copyProperties(userSprintHour, SprintV3UserHourDTO.class);
            if (null != user) {
                userSprintHourDTO.setUserId(user.getUserId());
                userSprintHourDTO.setUserName(user.getUserName());
                userSprintHourDTO.setUserAccount(user.getUserAccount());
                userSprintHourDTOS.add(userSprintHourDTO);
            }
        }
    }

    @Override
    public List<SprintListDTO> listSprint(SprintQueryDTO dto, SecurityDTO security) {
        Long userId = security.getUserId();
        //如果是租户管理员，查询租下所有迭代
        boolean isTenantAdmin = iFacadeUserApi.checkIsTenantAdmin(userId);
        if (isTenantAdmin) {
            HashMap<String, Object> params = buildQueryParamsTenantAdmin(dto, security);
            PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
            List<SprintListDTO> rest = ssprintMapper.queryAllSprint(params);
            rest = buildResultList(rest);
            return rest;
        } else { //不是租户管理员，查询与我相关的迭代
            HashMap<String, Object> params = buildQueryParamsOthers(dto, security);
            PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
            List<SprintListDTO> rest = ssprintMapper.queryOtherSprint(params);
            rest = buildResultList(rest);
            return rest;
        }
    }

    /**
     * 构建返回结果
     *
     * @param rest
     * @author zhaofeng
     * @date 2021/5/12 10:11
     */
    private List<SprintListDTO> buildResultList(List<SprintListDTO> rest) {
        //收集teamids，查询team，收集createuid，查询创建人
        List<Long> sprintIds = Lists.newArrayList();
        List<Long> teamIds = Lists.newArrayList();
        List<Long> createIds = Lists.newArrayList();
        rest.forEach(item -> {
            sprintIds.add(item.getSprintId());
            teamIds.add(item.getTeamId());
            createIds.add(item.getCreateUid());
        });
        List<STeam> sTeams = sTeamMapper.listTeamByIds(teamIds);
        List<SsoUser> ssoUsers = iFacadeUserApi.listUsersByIds(createIds);
        List<SprintV3UserHourDTO> userHours = sSprintUserHourMapper.listUserHourBySprintId(sprintIds);
        //拼接返回值
        rest.forEach(item -> {
            //团队
            sTeams.forEach(t -> {
                if (Objects.equals(item.getTeamId(), t.getTeamId())) {
                    item.setTeamName(t.getTeamName());
                }
            });
            //迭代成员人数
            long count = userHours.stream().filter(userHour -> Objects.equals(item.getSprintId(), userHour.getSprintId())).count();
            item.setSprintUserCount((int) count);
            //状态
            item.setStatusStr(SprintStatusEnum.getName(item.getStatus()));
            //迭代周期
            List<Date> dates = convertStrToDate(item.getSprintDays());
            item.setSprintDayList(dates);
            //迭代天数
            item.setPlanDays(dates.size());
            //故事数及完成数
            SprintStatisticalInformation information = sprintStatisticalInformation(item.getSprintId());
            SprintTaskDTO story = new SprintTaskDTO();
            story.setAll(information.getUserStorySum());
            story.setDone(information.getUserStory());
            item.setStory(story);
            //任务数及完成数
            SprintTaskDTO task = new SprintTaskDTO();
            task.setAll(information.getTaskSum());
            task.setDone(information.getTask());
            item.setTask(task);
            //创建人
            ssoUsers.forEach(u -> {
                if (Objects.equals(item.getCreateUid(), u.getUserId())) {
                    item.setCreateUser(u);
                }
            });
        });
        return rest;
    }

    /**
     * 构建查询条件--租户管理员角色下的
     *
     * @author zhaofeng
     * @date 2021/5/12 10:11
     */
    private HashMap<String, Object> buildQueryParamsTenantAdmin(SprintQueryDTO dto, SecurityDTO security) {
        HashMap<String, Object> params = new HashMap<>();
        //团队名称或编号
        params.put("team", dto.getTeam());
        //迭代名称或编号
        params.put("sprint", dto.getSprint());
        //租户code
        params.put("tenantCode", security.getTenantCode());
        return params;
    }

    private HashMap<String, Object> buildQueryParamsOthers(SprintQueryDTO dto, SecurityDTO security) {
        HashMap<String, Object> params = new HashMap<>();
        //团队名称或编号
        params.put("team", dto.getTeam());
        //迭代名称或编号
        params.put("sprint", dto.getSprint());
        //系统id
        params.put("system", security.getSystemId());
        //登录人id
        params.put("user", security.getUserId());
        //租户code
        params.put("tenantCode", security.getTenantCode());
        return params;
    }

    @Override
    public List<SprintListDTO> teamInSprint(Long teamId, Integer pageSize, Integer pageNum, String sprint) {
        /*
         * 按团队ID查询出所有（进行中、未开始）状态下的有效迭代
         */
        PageHelper.startPage(pageNum, pageSize);
        List<SprintListDTO> result = ssprintMapper.selectByIdAndName(teamId, sprint);
        //属性值翻译
        result.forEach(item -> {
            //状态
            item.setStatusStr(SprintStatusEnum.getName(item.getStatus()));
        });
        return result;
        //        SSprintExample example = new SSprintExample();
//        example.createCriteria()
//                .andStateEqualTo(StateEnum.U.getValue())
//                .andStatusIn(Arrays.asList(SprintStatusEnum.TYPE_NO_START_STATE.CODE, SprintStatusEnum.TYPE_ONGOING_STATE.CODE, SprintStatusEnum.TYPE_FINISHED_STATE.CODE))
//                .andTeamIdEqualTo(teamId);
//
//        example.setOrderByClause("create_time desc");
//        List<SSprint> list = ssprintMapper.selectByExample(example);

    }


    /**
     * 创建迭代
     *
     * @param sprintDTO 迭代dto
     * @return {@link Long}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSprint(SprintV3DTO sprintDTO) {
        Preconditions.checkArgument(sprintDTO.getSprintName().length() <= 100, "迭代名称过长,不能大于100!");
        Preconditions.checkArgument(sprintDTO.getWorkHours().intValue() <= 24, "工作时间超长，不能大于24小时!");
        List<Date> sprintDayList = sprintDTO.getSprintDayList();
        int sprintNameNumber = ssprintMapper.CheckSprintName(sprintDTO.getSprintName(), sprintDTO.getTenantCode());
        if (sprintNameNumber > 0) {
            throw new BusinessException("当前租户下迭代名称重复");
        }
        //迭代开始结束时间判断
        try {
            Date startTime = sprintDayList.get(0);
            Date endTime = DateUtil.getAfterDay(sprintDayList.get(sprintDayList.size() - 1));
            sprintDTO.setStartTime(startTime);
            sprintDTO.setEndTime(endTime);
        } catch (Exception e) {
            throw new BusinessException("迭代开始,结束时间填充异常" + e);
        }

        //版本号判断
        Matcher m = null;
        try {
            Pattern p = Pattern.compile(regEx);
            m = p.matcher(sprintDTO.getVersionNumber());
            if (m.find()) {
                throw new BusinessException("版本号只能是英文数字_.等常用字符！");
            }
        } catch (NullPointerException e) {
            throw new BusinessException("迭代版本号异常" + e);
        }

//团队真实性校验
//        int teamNember = sTeamMapper.teamExist(sprintDTO.getTeamId(), sprintDTO.getTenantCode());
//        if (teamNember == 0) {
//            throw new BusinessException("当前租户下无此团队");
//        }

        //迭代状态判断
        Date now = new Date(System.currentTimeMillis());
        if (sprintDTO.getStartTime().getTime() > now.getTime()) {
            // 未开始
            sprintDTO.setStatus(SprintStatusEnum.TYPE_NO_START_STATE.CODE);
        } else {
            // 已开始
            sprintDTO.setStatus(SprintStatusEnum.TYPE_ONGOING_STATE.CODE);
        }

        sprintDTO.setState("U");
        sprintDTO.setCreateTime(new Date());
        //转换迭代有效日期为String，中间以|隔开
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < sprintDayList.size(); i++) {
            stringBuffer.append(sprintDayList.get(i).getTime());
            if (i < sprintDayList.size() - 1) {
                stringBuffer.append("|");
            }
        }
        sprintDTO.setSprintDays(stringBuffer.toString());

        //插入迭代
        SSprintWithBLOBs sprint = new SSprintWithBLOBs();
        BeanUtils.copyProperties(sprintDTO, sprint);
        ssprintMapper.insert(sprint);

        //插入迭代人员
        List<SprintV3UserHourDTO> members = sprintDTO.getMembers();
        sSprintUserHourMapper.batchInsert(members, sprint.getSprintId());
        return sprint.getSprintId();
    }

    @Override
    public boolean canEdit(Long sprintId) {
        SSprint sprint = ssprintMapper.selectByPrimaryKey(sprintId);
        Byte status = sprint.getStatus();
        if (status.equals(SprintStatusEnum.TYPE_ONGOING_STATE.CODE) || status.equals(SprintStatusEnum.TYPE_FINISHED_STATE.CODE)) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSprint(SprintV3DTO sprintDTO, SecurityDTO securityDTO) {
        if (!canEdit(sprintDTO.getSprintId())) {
            throw new BusinessException("只有【未开始】状态的迭代才允许修改");
        }
        checkParameter(sprintDTO);
        editSprint(sprintDTO);
    }

    /**
     * @param sprintDTO
     * @Date 2021/5/11
     * @Description 对传来的参数做判断
     * @Return void
     */
    private void checkParameter(@RequestBody SprintV3DTO sprintDTO) {
        String str1 = "迭代名称过长,不能大于100!";
        String str2 = "团队名称过长，不能大于100!";
        // String str3 = "请选择团队";
        String str4 = "工作时间超长，不能大于24小时!";
        Preconditions.checkArgument(sprintDTO.getSprintName().length() <= 100, str1);
        Preconditions.checkArgument(sprintDTO.getTeamName().length() <= 100, str2);
        //Preconditions.checkArgument(sprintDTO.getTeamId() != null || sprintDTO.getTeamName() != null, str3);
        Preconditions.checkArgument(sprintDTO.getWorkHours().intValue() <= 24, str4);
    }

    public void editSprint(SprintV3DTO sprintDTO) {
        final SSprintWithBLOBs sprint = ReflectUtil.copyProperties(sprintDTO, SSprintWithBLOBs.class);
        final Long sprintId = sprint.getSprintId();
        final SSprintWithBLOBs origin = ssprintMapper.selectByPrimaryKey(sprintId);
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
        sprint.setTeamId(sprint.getTeamId());
        //判断版本号只能是英文数字_.常用字符
        versionNumberRegex(sprintDTO);
        int i = ssprintMapper.updateByPrimaryKeySelective(sprint);
        if (i == 0) {
            throw new BusinessException("更新迭代失败");
        }
        // 移除sprintUser
        sSprintUserHourMapper.deleteBySprintId(sprintId);
        // 再新建
        createUserSprintHour(sprintDTO, sprintId, sprint.getCreateUid());
    }

    /**
     * @param sprintDTO
     * @param sprint
     * @Date 2020/4/24
     * @Description 时间戳排序，结束时间取最后日期后一天
     * @Return java.util.Date
     */
    private Date getDate(SprintV3DTO sprintDTO, SSprintWithBLOBs sprint) {
        List<Date> sprintDays = sprintDTO.getSprintDayList();
        List<Date> dateList = sprintDays.stream().sorted().collect(Collectors.toList());
        sprint.setSprintDays(convertDateToStr(dateList));
        Date startTime = dateList.get(0);
        Date endTime = DateUtil.getAfterDay(dateList.get(dateList.size() - 1));
        sprint.setStartTime(startTime);
        sprint.setEndTime(endTime);
        return startTime;
    }

    private String convertDateToStr(List<Date> dateList) {
        StringBuilder sprintDaysStr = new StringBuilder();
        for (int i = 0; i < dateList.size(); i++) {
            sprintDaysStr.append(dateList.get(i).getTime());
            if (i < dateList.size() - 1) {
                sprintDaysStr.append("|");
            }
        }
        return sprintDaysStr.toString();
    }

    /**
     * @param sprintDTO
     * @Date 2021/05/12
     * @Description 判断版本号只能是英文数字_.常用字符
     * @Return void
     */
    private void versionNumberRegex(SprintV3DTO sprintDTO) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(sprintDTO.getVersionNumber());
        if (m.find()) {
            throw new BusinessException("版本号只能是英文数字_.等常用字符！");
        }
    }

    /**
     * @param sprintDTO
     * @param sprintId
     * @param userId
     * @Date 2020/4/26 16:23
     * @Description 创建迭代人员
     * @Return void
     */
    private void createUserSprintHour(SprintV3DTO sprintDTO, Long sprintId, Long userId) {
        List<SprintV3UserHourDTO> sprintHourDTOS = sprintDTO.getMembers();
        if (CollectionUtils.isNotEmpty(sprintHourDTOS)) {
            for (SprintV3UserHourDTO userSprintHourDTO : sprintHourDTOS) {
                SSprintUserHour userSprintHour = ReflectUtil.copyProperties(userSprintHourDTO, SSprintUserHour.class);
                userSprintHour.setSprintId(sprintId);
                userSprintHour.setCreateUid(userId);
                userSprintHour.setCreateTime(new Date());
                int i = sSprintUserHourMapper.insert(userSprintHour);
                if (i != 1) {
                    throw new BusinessException("创建迭代人员失败!");
                }
            }
        }
    }

    /**
     * 每天改变状态
     *
     * @author 张宇
     */
    @Override
    public void changeStatusDaily() {
        final List<Long> sprintIds = ssprintMapper.getUnStartIds(new Date(System.currentTimeMillis()));
        log.debug("change state daily sprintIds:{}", sprintIds);
        if (null != sprintIds && !sprintIds.isEmpty()) {
            ssprintMapper.changeStatusTOProgressByIds(sprintIds);
        }
    }

    /**
     * 取消迭代
     *
     * @param sprintId 迭代id
     * @return {@link String}
     * @author 张宇
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String cancelSprint(long sprintId, long userId) {
        //迭代未开始
        if (!TYPE_NO_START_STATE.CODE.equals(ssprintMapper.querySprintStatus(sprintId))) {
            throw new BusinessException("只有未开始的迭代才允许取消");
        }
        //迭代po
        boolean SprintPo = ssprintMapper.checkSprintPo(sprintId, userId);
        //迭代创建人
        boolean creatUser = ssprintMapper.creatUser(sprintId, userId);
        if (!(SprintPo || creatUser)) {
            throw new BusinessException("只有迭代创建人和迭代所属的团队PO才允许取消迭代");
        }
        if (0 == ssprintMapper.sprintExist(sprintId)) {
            throw new BusinessException("暂无该迭代");
        }

        //修改故事状态
        ssprintMapper.changeIssueStatusBySprintId(sprintId, IssueTypeEnum.TYPE_STORY.CODE, StoryStatusEnum.TYPE_ADD_STATE.CODE);
        ssprintMapper.changeIssueStatusBySprintId(sprintId, IssueTypeEnum.TYPE_TASK.CODE, TaskStatusEnum.TYPE_ADD_STATE.CODE);

        ssprintMapper.cancelSprint(sprintId);
        return "迭代取消,解除任务关联";
    }

    /**
     * 迭代完成
     *
     * @param sprintId 迭代id
     * @return {@link String}
     * @author 张宇
     */
    @Override
    public String sprintFinish(long sprintId) {
        //迭代下未完成的故事数
        if (0 != ssprintMapper.querySprintUnfinishedStoryNumber(sprintId)) {
            throw new BusinessException("该迭代下有未完成的用户故事，不允许迭代完成");
        }
        //只有进行中的迭代允许完成
        if (!TYPE_ONGOING_STATE.CODE.equals(ssprintMapper.querySprintStatus(sprintId))) {
            throw new BusinessException("只有进行中的迭代允许完成");
        }
        ssprintMapper.sprintFinish(sprintId);
        return "迭代完成成功";
    }

    /**
     * 迭代视图 - 迭代详情
     *
     * @param sprintId 迭代id
     * @return {@link SprintOverView}
     * @author 张宇
     */
    @Override
    public SprintOverView sprintOverView(long sprintId) {
        SprintOverView sprintOverView = new SprintOverView();
        SSprintWithBLOBs sprint = ssprintMapper.queryValidSprintById(sprintId);
        if (ObjectUtil.isEmpty(sprint)) {
            throw new BusinessException("迭代失效或暂无此迭代");
        }
        BeanUtils.copyProperties(sprint, sprintOverView);
        sprintOverView.setTeamName(sTeamMapper.queryTeamNameByTeamId(sprint.getTeamId()));
        //List<STeamMember> sprintUSer = sTeamMapper.queryUserInfoByUserId(sprintId, sprint.getTeamId());
        List<STeamMember> sprintUSer = querySprintUser(sprintId);
        sprintOverView.setSprintUSer(sprintUSer);
        List<Long> sprintSystemIds = sTeamMapper.queryTeamSystem(sprint.getTeamId());
        List<SsoSystemRestDTO> ssoSystemRestDTOS = iFacadeSystemApi.getSystemByIds(sprintSystemIds);
        sprintOverView.setSprintSystem(ssoSystemRestDTOS);
        return sprintOverView;
    }

    /**
     * 迭代视图 - 迭代统计详情
     *
     * @param sprintId 迭代id
     * @return {@link SprintStatisticalInformation}
     * @author 张宇
     */
    @Override
    public SprintStatisticalInformation sprintStatisticalInformation(long sprintId) {
        SprintStatisticalInformation statisticalInformation = new SprintStatisticalInformation();

        //story
        statisticalInformation.setUserStory(ssprintMapper.querySprintFinishedStoryNumber(sprintId, IssueTypeEnum.TYPE_STORY.CODE, StoryStatusEnum.TYPE_CLOSED_STATE.CODE));
        statisticalInformation.setUserStorySum(ssprintMapper.querySprintStoryNumBer(sprintId, IssueTypeEnum.TYPE_STORY.CODE));
        statisticalInformation.setUserStoryCompleteness(NumberUtilDiv(statisticalInformation.getUserStory(), statisticalInformation.getUserStorySum()));

        //StoryPoint
        statisticalInformation.setStoryPoint(ssprintMapper.querySprintFinishedStoryPoint(sprintId, IssueTypeEnum.TYPE_STORY.CODE, StoryStatusEnum.TYPE_CLOSED_STATE.CODE));
        statisticalInformation.setStoryPointSum(ssprintMapper.querySprintStoryPoint(sprintId, IssueTypeEnum.TYPE_STORY.CODE));
        statisticalInformation.setStoryPointCompleteness(NumberUtilDiv(statisticalInformation.getStoryPoint(), statisticalInformation.getStoryPointSum()));

        //Workload
        statisticalInformation.setWorkload(ssprintMapper.querySprintFinishedWorkload(sprintId, IssueTypeEnum.TYPE_TASK.CODE, TaskStatusEnum.TYPE_CLOSED_STATE.CODE));
        statisticalInformation.setWorkloadSum(ssprintMapper.querySprintWorkload(sprintId, IssueTypeEnum.TYPE_TASK.CODE));
        statisticalInformation.setWorkloadCompleteness(NumberUtilDiv(statisticalInformation.getWorkload(), statisticalInformation.getWorkloadSum()));

        //Task
        statisticalInformation.setTask(ssprintMapper.querySprintFinishedTaskNumber(sprintId, IssueTypeEnum.TYPE_TASK.CODE, TaskStatusEnum.TYPE_CLOSED_STATE.CODE));
        statisticalInformation.setTaskSum(ssprintMapper.querySprintTaskNumber(sprintId, IssueTypeEnum.TYPE_TASK.CODE));
        statisticalInformation.setTaskCompleteness(NumberUtilDiv(statisticalInformation.getTask(), statisticalInformation.getTaskSum()));

        return statisticalInformation;
    }

    /**
     * 迭代视图 - 成员工时
     *
     * @param sprintId 迭代id
     * @return {@link List<SprintMembersWorkHours>}
     * @author 张宇
     */
    @Override
    public List<SprintMembersWorkHours> sprintMembersWorkHours(long sprintId) {
        //迭代相关人员
        List<STeamMember> userList = sTeamMapper.querySprintUser(sprintId);
        List<SprintMembersWorkHours> list = new ArrayList<>();
        //未领取
        SprintMembersWorkHours unclaimedWorkHours = new SprintMembersWorkHours();
        unclaimedWorkHours.setUserId(0);
        unclaimedWorkHours.setUserAccount("未领取任务");
        unclaimedWorkHours.setUserName("未领取任务");
        unclaimedWorkHours.setResidueWorkload(ssprintMapper.unclaimedWorkHours(sprintId, IssueTypeEnum.TYPE_TASK.CODE, TaskStatusEnum.TYPE_ADD_STATE.CODE));
        list.add(unclaimedWorkHours);

        for (int i = 0; i < userList.size(); i++) {
            SprintMembersWorkHours sprintMembersWorkHours = new SprintMembersWorkHours();
            sprintMembersWorkHours.setUserId(userList.get(i).getUserId());
            sprintMembersWorkHours.setUserName(userList.get(i).getUserName());
            sprintMembersWorkHours.setUserAccount(userList.get(i).getUserAccount());
            sprintMembersWorkHours.setActualWorkload(ssprintMapper.queryUserActualWorkload(sprintId, userList.get(i).getUserId()));
            sprintMembersWorkHours.setResidueWorkload(ssprintMapper.queryUserResidueWorkload(sprintId, userList.get(i).getUserId(), IssueTypeEnum.TYPE_TASK.CODE, TaskStatusEnum.TYPE_MODIFYING_STATE.CODE));
            sprintMembersWorkHours.setTaskNumber(ssprintMapper.queryUserTaskNumber(sprintId, userList.get(i).getUserId()));
            list.add(sprintMembersWorkHours);
        }
        return list;
    }

    @Override
    public List<SprintV3UserHourDTO> getUsersBySprintId(Long sprintId) {
        List<SprintV3UserHourDTO> userSprintHourDTOList = this.queryUsersBySprintId(sprintId);
        return userSprintHourDTOList;
    }

    @Override
    public List<SprintListDTO> getEffectiveSprintsBySystemId(Long systemId) {
        List<SprintListDTO> sprints = ssprintMapper.selectBySystemId(systemId);
        return sprints;
    }

    @Override
    public List<IssueDTO> queryNotRelationStorys(String title, Long teamId, List<Long> systemIds, Integer pageNum, Integer pageSize) {
        List<IssueDTO> issueDTOS = new ArrayList<>();
        List<Long> systemIdInfo = new ArrayList<>();
        if (CollectionUtils.isEmpty(systemIds)) {
            //如果没有系统id 查询团队下的所有系统
            List<SsoSystemRestDTO> ssoSystemRestDTOS = teamv3Service.querySystemByTeamId(teamId);
            for (SsoSystemRestDTO ssoSystemRestDTO : ssoSystemRestDTOS) {
                Long systemId = ssoSystemRestDTO.getSystemId();
                systemIdInfo.add(systemId);
            }
            PageHelper.startPage(pageNum, pageSize);
            issueDTOS = issueMapper.queryNotRelationStory(title, systemIdInfo);
            issueDTOS.stream().map(issueDTO -> {
                ssoSystemRestDTOS.forEach(ssoSystemRestDTO -> {
                    if (issueDTO.getSystemId().equals(ssoSystemRestDTO.getSystemId())) {
                        issueDTO.setSystemCode(ssoSystemRestDTO.getSystemCode());
                    }
                });
                return issueDTO;
            }).collect(Collectors.toList());
            //属性值翻译
            issueDTOS.forEach(item -> {
                //状态
                item.setStoryStatusName(StoryStatusEnum.getName(item.getLaneId()));
            });
        } else {
            PageHelper.startPage(pageNum, pageSize);
            issueDTOS = issueMapper.queryNotRelationStory(title, systemIds);
            SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(systemIds.get(0));
            issueDTOS.stream().map(issueDTO -> {
                issueDTO.setSystemCode(ssoSystem.getSystemCode());
                return issueDTO;
            }).collect(Collectors.toList());

            //属性值翻译
            issueDTOS.forEach(item -> {
                //状态
                item.setStoryStatusName(StoryStatusEnum.getName(item.getLaneId()));
            });
        }
        return issueDTOS;
    }

    @Override
    public List<STeamMember> querySprintVagueUser(Long sprintId, String userName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<STeamMember> sTeamMembers = sTeamMapper.querySprintVagueUser(sprintId, userName);
        return sTeamMembers;
    }

    @Override
    public List<SsoSystemDTO> querySystemBySprint(Long sprintId) {
        SSprintWithBLOBs sSprintWithBLOBs = ssprintMapper.selectByPrimaryKey(sprintId);
        SprintListDTO sprintListDTO = ReflectUtil.copyProperties(sSprintWithBLOBs, SprintListDTO.class);
        List<SprintListDTO> sprintListDTOS = Lists.newArrayList();
        sprintListDTOS.add(sprintListDTO);

        //系统
        Long teamId = sSprintWithBLOBs.getTeamId();
        List<Long> systemIds = sTeamSystemMapper.querySystemIdByTeamId(teamId);
        List<SsoSystem> ssoSystems = iFacadeSystemApi.querySsoSystem(systemIds);
        List<SsoSystemDTO> ssoSystemDTOS = null;
        try {
            ssoSystemDTOS = ReflectUtil.copyProperties4List(ssoSystems, SsoSystemDTO.class);
        } catch (Exception e) {
            log.error("数据转换异常:{}", e.getMessage());
        }
        return ssoSystemDTOS;
    }

    /**
     * 查询迭代用户
     *
     * @param sprintId 迭代id
     * @return {user_id,user_account,user_name}
     */
    @Override
    public List<STeamMember> querySprintUser(long sprintId) {
        List<STeamMember> sTeamMembers = sTeamMapper.querySprintUser(sprintId);
        return sTeamMembers;
    }

    @Override
    public boolean arrangeIssue(SprintDTO sprintDTO) {
        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        Long teamId = sprintDTO.getTeamId();
        boolean b = iFacadeUserApi.checkIsTeamPo(userId, teamId);
        if (!b) {
            throw new BusinessException("只有本迭代的PO权限才允许关联/移除用户故事");
        }
        List<Long> issueIds = sprintDTO.getIssueIds();
        if (CollectionUtils.isNotEmpty(issueIds)) {
            for (Long issueId : issueIds) {
                //工作项加入迭代
                storyService.distributeSprint(issueId, sprintDTO.getSprintId());
            }
        } else {
            throw new BusinessException("查不到工作项");
        }


        return true;
    }

    public double NumberUtilDiv(int a, int b) {
        if (0 == b || 0 == a) {
            return 0.00;
        } else {
            double div;
            div = NumberUtil.div(a, b, 2);
            return div;
        }
    }

    @Override
    public List<SSprint> queryAllSprint() {
        SSprintExample sSprintExample = new SSprintExample();
        sSprintExample.createCriteria()
                .andStateEqualTo(StateEnum.U.getValue());
        return ssprintMapper.selectByExample(sSprintExample);
    }

    @Override
    public boolean legalDate(String sprintDays, Date target) {
        List<Date> dateList = convertStrToDate(sprintDays);
        return DateUtil.isBetween(dateList, target);
    }


    @Override
    public List<SSprintWithBLOBs> querySprintList() {
        SSprintExample sSprintExample = new SSprintExample();
        sSprintExample.createCriteria()
                .andStateEqualTo(StateEnum.U.getValue());
        return ssprintMapper.selectByExampleWithBLOBs(sSprintExample);
    }

    @Override
    public Integer getWorkload(Long sprintId) {
        List<SSprintUserHour> userSprintHours = sSprintUserHourMapper.getUserIds4Sprint(sprintId);
        int sprintHours = 0;
        for (SSprintUserHour userSprintHour : userSprintHours) {
            sprintHours += userSprintHour.getReallyHours().intValue();
        }
        return sprintHours;
    }

    @Override
    public List<SprintListDTO> querySprintBySystemId(Long systemId) {
        List<SprintListDTO> sprintListDTOList = Lists.newArrayList();
        List<STeamSystem> sTeamSystemList = teamSystemService.listTeamBySystem(systemId);
        List<Long> teamIdList;
        if(CollectionUtils.isNotEmpty(sTeamSystemList)){
            teamIdList = sTeamSystemList.stream().map(STeamSystem::getTeamId).collect(Collectors.toList());

            Map<Long,String> mapTeamName = new HashMap<>();
            List<STeam> sTeamList = sTeamMapper.listTeamByIds(teamIdList);
            for(STeam sTeam : sTeamList){
                mapTeamName.put(sTeam.getTeamId(), sTeam.getTeamName());
            }

            SSprintExample sSprintExample = new SSprintExample();
            sSprintExample.createCriteria().andTeamIdIn(teamIdList).andStateEqualTo(StateEnum.U.getValue());
            List<SSprint> sSprintList = ssprintMapper.selectByExample(sSprintExample);

            for(SSprint sSprint : sSprintList){
                String sprintName = sSprint.getSprintName();
                String teamName = mapTeamName.get(sSprint.getTeamId());
                sprintName = StrUtil.builder().append(sprintName).append("(").append(teamName).append(")").toString();
                sSprint.setSprintName(sprintName);
            }
            try {
                sprintListDTOList = ReflectUtil.copyProperties4List(sSprintList, SprintListDTO.class);
            } catch (Exception e) {
               log.error("sprintListDTOList transfer errer:{}", e.getMessage());
            }
        }
        return sprintListDTOList;
    }
}
