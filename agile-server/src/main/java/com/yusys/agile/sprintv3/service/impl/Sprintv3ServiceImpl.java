package com.yusys.agile.sprintv3.service.impl;

import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.sprint.domain.UserSprintHour;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprint.dto.UserSprintHourDTO;
import com.yusys.agile.sprint.enums.SprintStatusEnum;
import com.yusys.agile.sprintV3.dto.*;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.dao.SSprintUserHourMapper;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintExample;
import com.yusys.agile.sprintv3.domain.SSprintUserHour;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.queryModel.UserWorkloadQueryModel;
import com.yusys.agile.sprintv3.responseModel.SprintMembersWorkHours;
import com.yusys.agile.sprintv3.responseModel.SprintOverView;
import com.yusys.agile.sprintv3.responseModel.SprintStatisticalInformation;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.team.domain.Team;
import com.yusys.agile.team.dto.TeamDTO;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.dao.STeamMemberMapper;
import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.agile.utils.exception.ExceptionCodeEnum;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    private STeamSystemMapper STeamSystemMapper;
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

    String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    @Override
    public SprintDTO viewEdit(Long sprintId) {
        SSprintWithBLOBs sprint = ssprintMapper.selectByPrimaryKey(sprintId);
        if (null == sprint || !StringUtils.equals(sprint.getState(), StateEnum.U.getValue())) {
            return new SprintDTO();
        }
        SprintDTO sprintDTO = ReflectUtil.copyProperties(sprint, SprintDTO.class);
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
        List<TeamDTO> teamDTOS = getOptionalMembers4Team(sprint.getTeamId(), sprintId);
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
    private List<TeamDTO> getOptionalMembers4Team(Long teamId, Long sprintId) {
        //通过projectId查询所有团队
        List<Team> teams = sTeamMapper.getTeamsByTeamId(teamId);
        // 空的直接返回
        if (CollectionUtils.isEmpty(teams)) {
            List<TeamDTO> teamDTOS = new ArrayList<>();
            List<UserSprintHourDTO> userSprintHourDTOS = new ArrayList<>();
            List<UserSprintHour> userSprintHours = sSprintUserHourMapper.getUserIds4Sprint(sprintId);
            if (CollectionUtils.isNotEmpty(userSprintHours)) {
                getUser(userSprintHourDTOS, userSprintHours);
            }
            TeamDTO teamDTO = new TeamDTO();
            teamDTO.setUsers(userSprintHourDTOS);
            teamDTOS.add(teamDTO);
            return teamDTOS;
        }
        List<TeamDTO> teamDTOS = new ArrayList<>();
        for (Team team : teams) {
            TeamDTO teamDTO = ReflectUtil.copyProperties(team, TeamDTO.class);
            List<UserSprintHourDTO> userSprintHourDTOS = this.queryUsersBySprintId(sprintId);
            teamDTO.setUsers(userSprintHourDTOS);
            //查询团队下的子系统
            List<Long> systemIds = STeamSystemMapper.querySystemIdByTeamId(teamDTO.getTeamId());
            List<SsoSystemRestDTO> systemByIds = iFacadeSystemApi.getSystemByIds(systemIds);
            teamDTO.setTeamSystems(systemByIds);

            teamDTOS.add(teamDTO);
        }
        return teamDTOS;
    }

    private List<UserSprintHourDTO> queryUsersBySprintId(Long sprintId) {
        List<UserSprintHourDTO> userSprintHourDTOS = new ArrayList<>();
        //通过迭代id查询迭代时长表的userid，然后再查人员
        List<UserSprintHour> userSprintHours = sSprintUserHourMapper.getUserIds4Sprint(sprintId);
        if (CollectionUtils.isNotEmpty(userSprintHours)) {
            getUser(userSprintHourDTOS, userSprintHours);
        }
        return userSprintHourDTOS;
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
    public List<SprintListDTO> listSprint(SprintQueryDTO dto, SecurityDTO security) {
        Long userId = security.getUserId();
        //如果是租户管理员
        boolean isTenantAdmin = iFacadeUserApi.checkIsTenantAdmin(userId);
        if (isTenantAdmin) {
            HashMap<String, Object> params = buildQueryParamsTenantAdmin(dto, security);
            PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
            List<SprintListDTO> rest = ssprintMapper.queryAllSprint(params);
            rest = buildResultList(rest);
            return rest;
        } else {
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
    public void updateSprint(SprintDTO sprintDTO, SecurityDTO securityDTO) {
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
    private void checkParameter(@RequestBody SprintDTO sprintDTO) {
        String str1 = "迭代名称过长,不能大于100!";
        String str2 = "团队名称过长，不能大于100!";
        // String str3 = "请选择团队";
        String str4 = "工作时间超长，不能大于24小时!";
        Preconditions.checkArgument(sprintDTO.getSprintName().length() <= 100, str1);
        Preconditions.checkArgument(sprintDTO.getTeamName().length() <= 100, str2);
        //Preconditions.checkArgument(sprintDTO.getTeamId() != null || sprintDTO.getTeamName() != null, str3);
        Preconditions.checkArgument(sprintDTO.getWorkHours().intValue() <= 24, str4);
    }

    public void editSprint(SprintDTO sprintDTO) {
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
    private Date getDate(SprintDTO sprintDTO, SSprintWithBLOBs sprint) {
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
     * 改变迭代状态
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
     */
    @Override
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
        ssprintMapper.cancelSprint(sprintId);
        return "迭代状态更新成功";
    }

    /**
     * 迭代完成
     *
     * @param sprintId 迭代id
     * @return {@link String}
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
     */
    @Override
    public SprintStatisticalInformation sprintStatisticalInformation(long sprintId) {
        SprintStatisticalInformation statisticalInformation = new SprintStatisticalInformation();

        //story
        statisticalInformation.setUserStory(ssprintMapper.querySprintFinishedStoryNumber(sprintId));
        statisticalInformation.setUserStorySum(ssprintMapper.querySprintStoryNumBer(sprintId));
        statisticalInformation.setUserStoryCompleteness(NumberUtilDiv(statisticalInformation.getUserStory(), statisticalInformation.getUserStorySum()));

        //StoryPoint
        statisticalInformation.setStoryPoint(ssprintMapper.querySprintFinishedStoryPoint(sprintId));
        statisticalInformation.setStoryPointSum(ssprintMapper.querySprintStoryPoint(sprintId));
        statisticalInformation.setStoryPointCompleteness(NumberUtilDiv(statisticalInformation.getStoryPoint(), statisticalInformation.getStoryPointSum()));

        //Workload
        statisticalInformation.setWorkload(ssprintMapper.querySprintFinishedWorkload(sprintId));
        statisticalInformation.setWorkloadSum(ssprintMapper.querySprintWorkload(sprintId));
        statisticalInformation.setWorkloadCompleteness(NumberUtilDiv(statisticalInformation.getWorkload(), statisticalInformation.getWorkloadSum()));

        //Task
        statisticalInformation.setTask(ssprintMapper.querySprintFinishedTaskNumber(sprintId));
        statisticalInformation.setTaskSum(ssprintMapper.querySprintTaskNumber(sprintId));
        statisticalInformation.setTaskCompleteness(NumberUtilDiv(statisticalInformation.getTask(), statisticalInformation.getTaskSum()));

        return statisticalInformation;
    }

    @Override
    public List<UserSprintHourDTO> getUsersBySprintId(Long sprintId) {
        List<UserSprintHourDTO> userSprintHourDTOList = this.queryUsersBySprintId(sprintId);
        return userSprintHourDTOList;
    }

    @Override
    public List<SprintListDTO> getEffectiveSprintsBySystemId(Long systemId) {
        List<SprintListDTO> sprints = ssprintMapper.selectBySystemId(systemId);
        return sprints;
    }


    /**
     * 迭代视图 - 成员工时
     *
     * @param sprintId 迭代id
     * @return {@link List<SprintMembersWorkHours>}
     */
    @Override
    public List<SprintMembersWorkHours> sprintMembersWorkHours(long sprintId) {
        List<STeamMember> userList = sTeamMapper.querySprintUser(sprintId);
        List<SprintMembersWorkHours> list = new ArrayList<>();

        for (int i = 0; i < userList.size(); i++) {
            SprintMembersWorkHours sprintMembersWorkHours = new SprintMembersWorkHours();
            sprintMembersWorkHours.setUserId(userList.get(i).getUserId());
            sprintMembersWorkHours.setUserName(userList.get(i).getUserName());
            sprintMembersWorkHours.setUserAccount(userList.get(i).getUserAccount());
            UserWorkloadQueryModel userWorkloadQueryModel = ssprintMapper.queryUserWorkload(userList.get(i).getUserId());
            sprintMembersWorkHours.setActualWorkload(userWorkloadQueryModel.getFirstData());
            sprintMembersWorkHours.setTaskNumber(userWorkloadQueryModel.getSecondData());
            sprintMembersWorkHours.setResidueWorkload(ssprintMapper.queryUserResidueWorkload(userList.get(i).getUserId()));
            list.add(sprintMembersWorkHours);
        }
        return list;
    }

    @Override
    public List<IssueDTO> queryNotRelationStorys(String title, Long teamId, Long systemIds, Integer pageNum, Integer pageSize) {
        List<Long> systemIdInfo = new ArrayList<>();
        // 不传page信息时查全部数据
        List<IssueDTO> issueDTOS = new ArrayList<>();
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        if (null == systemIds) {
            //如果没有系统id 查询团队下的所有系统
            List<SsoSystemRestDTO> ssoSystemRestDTOS = teamv3Service.querySystemByTeamId(teamId);
            for (SsoSystemRestDTO ssoSystemRestDTO : ssoSystemRestDTOS) {
                Long systemId = ssoSystemRestDTO.getSystemId();
                String systemCode = ssoSystemRestDTO.getSystemCode();
                List<IssueDTO> issueDTOS1 = issueMapper.queryNotRelationStory(title, systemId);
                if (CollectionUtils.isNotEmpty(issueDTOS1)) {
                    for (IssueDTO issueDTO : issueDTOS1) {
                        issueDTO.setSystemCode(systemCode);
                    }
                    issueDTOS.addAll(issueDTOS1);
                }
            }
        } else {
            issueDTOS = issueMapper.queryNotRelationStory(title,systemIds);
            SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(systemIds);
            for (IssueDTO issueDTO : issueDTOS) {
                issueDTO.setSystemCode(ssoSystem.getSystemCode());
            }
        }
        return issueDTOS;
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
                .andStateEqualTo(StateEnum.U.getValue()).andSprintIdEqualTo(100035L);
        return ssprintMapper.selectByExampleWithBLOBs(sSprintExample);
    }
}
