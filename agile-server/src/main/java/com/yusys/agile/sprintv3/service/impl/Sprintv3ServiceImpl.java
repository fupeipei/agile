package com.yusys.agile.sprintv3.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.yusys.agile.sprint.domain.UserSprintHour;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprint.dto.UserSprintHourDTO;
import com.yusys.agile.sprint.enums.SprintStatusEnum;
import com.yusys.agile.sprintV3.dto.*;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.dao.SSprintUserHourMapper;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintUserHour;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.team.domain.Team;
import com.yusys.agile.team.dto.TeamDTO;
import com.yusys.agile.teamv3.dao.*;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private SSprintUserHourMapper ssprintUserHourMapper;
    @Resource
    private STeamMapper sTeamMapper;
    @Resource
    private SSprintUserHourMapper sSprintUserHourMapper;
    @Resource
    private STeamSystemMapper STeamSystemMapper;
    @Resource
    private STeamPoMapper sTeamPoMapper;
    @Resource
    private STeamSmMapper sTeamSmMapper;
    @Resource
    private STeamUserMapper sTeamUserMapper;
    @Autowired
    private IFacadeSystemApi iFacadeSystemApi;
    @Autowired
    private IFacadeUserApi iFacadeUserApi;
    @Autowired
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
            return new ArrayList<>();
        }
        List<TeamDTO> teamDTOS = new ArrayList<>();
        for (Team team : teams) {
            TeamDTO teamDTO = ReflectUtil.copyProperties(team, TeamDTO.class);
            List<UserSprintHourDTO> userSprintHourDTOS = new ArrayList<>();
            //通过迭代id查询迭代时长表的userid，然后再查人员
            List<UserSprintHour> userSprintHours = sSprintUserHourMapper.getUserIds4Sprint(sprintId);
            if (CollectionUtils.isNotEmpty(userSprintHours)) {
                getUser(userSprintHourDTOS, userSprintHours);
            }
            teamDTO.setUsers(userSprintHourDTOS);
            //查询团队下的子系统
            List<Long> systemIds = STeamSystemMapper.querySystemIdByTeamId(teamDTO.getTeamId());
            List<SsoSystemRestDTO> systemByIds = iFacadeSystemApi.getSystemByIds(systemIds);
            teamDTO.setTeamSystems(systemByIds);

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
    public List<SprintListDTO> listSprint(SprintQueryDTO dto, SecurityDTO security) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Long userId = security.getUserId();
        Long systemId = security.getSystemId();

        //如果是租户管理员
        boolean isTenantAdmin = iFacadeUserApi.checkIsTenantAdmin(userId);
        if (isTenantAdmin) {
            //构建查询条件
            HashMap<String, Object> params = buildQueryParamsTenantAdmin(dto, security);
            List<SprintListDTO> rest = ssprintMapper.queryAllSprint(params);
            rest = buildResultList(rest);
            return rest;
        }
        //如果是系统负责人
        boolean isSystemOwner = iFacadeUserApi.checkIsSystemOwner(userId, systemId);
        if (isSystemOwner) {
            HashMap<String, Object> params = buildQueryParamsSystemOwner(dto, security);
            List<SprintListDTO> rest = ssprintMapper.queryAllSprint(params);
            rest = buildResultList(rest);
            return rest;
        }
        //po 、sm
        boolean isTeamPo = iFacadeUserApi.checkIsTeamPo(userId);
        boolean isTeamSm = iFacadeUserApi.checkIsTeamSm(userId);
        //如果既是po、又是sm
        if (isTeamPo && isTeamSm) {
            HashMap<String, Object> params = buildQueryParamsPoAndSm(dto, security);
            List<SprintListDTO> rest = ssprintMapper.queryAllSprint(params);
            rest = buildResultList(rest);
            return rest;
        } else if (isTeamPo) {
            HashMap<String, Object> params = buildQueryParamsTeamPo(dto, security);
            List<SprintListDTO> rest = ssprintMapper.queryAllSprint(params);
            rest = buildResultList(rest);
            return rest;
        } else if (isTeamSm) {
            HashMap<String, Object> params = buildQueryParamsTeamSm(dto, security);
            List<SprintListDTO> rest = ssprintMapper.queryAllSprint(params);
            rest = buildResultList(rest);
            return rest;
        }
        //如果是团队成员
        boolean isTeamMember = iFacadeUserApi.checkIsTeamMember(userId);
        if (isTeamMember) {
            HashMap<String, Object> params = buildQueryParamsTeamUser(dto, security);
            List<SprintListDTO> rest = ssprintMapper.queryAllSprint(params);
            rest = buildResultList(rest);
            return rest;
        }
        throw new BusinessException("该用户没有权限");
    }

    /**
     * 构建查询条件--是团队成员
     *
     * @return
     */
    private HashMap<String, Object> buildQueryParamsTeamUser(SprintQueryDTO dto, SecurityDTO security) {
        HashMap<String, Object> params = new HashMap<>();
        Long userId = security.getUserId();
        List<Long> teamAll = Lists.newArrayList();
        List<Long> teamuserids = sTeamUserMapper.queryTeamIdByUserId(userId);
        if (!teamuserids.isEmpty()) {
            teamAll.addAll(teamuserids);
        } else {
            teamAll.add(-1L);
        }
        /*
            按团队名称或编号模糊查询出teamids，如果不为空则与之前的teamids进行合并，否则不添加
         */
        String team = dto.getTeam();
        if (!StringUtils.isEmpty(team)) {
            List<STeam> teams = teamv3Service.getTeamLikeNameOrCode(team);
            if (!teams.isEmpty()) {
                //按团队名称模糊查询
                List<Long> ids = Lists.newArrayList();
                teams.forEach(item -> ids.add(item.getTeamId()));
                teamAll.addAll(ids);
            }
        }
        //最终的teamids
        params.put("teamIds", teamAll);
        //迭代名称或编号
        params.put("sprint", dto.getSprint());
        return params;
    }

    /**
     * 构建查询条件--是sm
     *
     * @return
     */
    private HashMap<String, Object> buildQueryParamsTeamSm(SprintQueryDTO dto, SecurityDTO security) {
        HashMap<String, Object> params = new HashMap<>();
        Long userId = security.getUserId();
        List<Long> teamAll = Lists.newArrayList();
        List<Long> smteamids = sTeamSmMapper.queryTeamIdByUserId(userId);
        if (!smteamids.isEmpty()) {
            teamAll.addAll(smteamids);
        } else {
            teamAll.add(-1L);
        }
        /*
            按团队名称或编号模糊查询出teamids，如果不为空则与之前的teamids进行合并，否则不添加
         */
        String team = dto.getTeam();
        if (!StringUtils.isEmpty(team)) {
            List<STeam> teams = teamv3Service.getTeamLikeNameOrCode(team);
            if (!teams.isEmpty()) {
                //按团队名称模糊查询
                List<Long> ids = Lists.newArrayList();
                teams.forEach(item -> ids.add(item.getTeamId()));
                teamAll.addAll(ids);
            }
        }
        //最终的teamids
        params.put("teamIds", teamAll);
        //迭代名称或编号
        params.put("sprint", dto.getSprint());
        return params;
    }

    /**
     * 构建查询条件--是po
     *
     * @return
     */
    private HashMap<String, Object> buildQueryParamsTeamPo(SprintQueryDTO dto, SecurityDTO security) {
        HashMap<String, Object> params = new HashMap<>();
        Long userId = security.getUserId();
        List<Long> teamAll = Lists.newArrayList();
        List<Long> poteamids = sTeamPoMapper.queryTeamIdByUserId(userId);
        if (!poteamids.isEmpty()) {
            teamAll.addAll(poteamids);
        } else {
            teamAll.add(-1L);
        }
        /*
            按团队名称或编号模糊查询出teamids，如果不为空则与之前的teamids进行合并，否则不添加
         */
        String team = dto.getTeam();
        if (!StringUtils.isEmpty(team)) {
            List<STeam> teams = teamv3Service.getTeamLikeNameOrCode(team);
            if (!teams.isEmpty()) {
                //按团队名称模糊查询
                List<Long> ids = Lists.newArrayList();
                teams.forEach(item -> ids.add(item.getTeamId()));
                teamAll.addAll(ids);
            }
        }
        //最终的teamids
        params.put("teamIds", teamAll);
        //迭代名称或编号
        params.put("sprint", dto.getSprint());
        return params;
    }

    /**
     * 构建查询条件--既是po、又是sm
     *
     * @return
     */
    private HashMap<String, Object> buildQueryParamsPoAndSm(SprintQueryDTO dto, SecurityDTO security) {
        HashMap<String, Object> params = new HashMap<>();
        Long userId = security.getUserId();
        /*
            查询出作为po的poteamids、再查询出作为sm的smteamids，然后将两者合并成teamAll
            之后按照teamName或teamid模糊查询出teamids，与teamAll进行最终合并，得到最终的teamids
         */
        List<Long> teamAll = Lists.newArrayList();
        List<Long> poteamids = sTeamPoMapper.queryTeamIdByUserId(userId);
        List<Long> smteamids = sTeamSmMapper.queryTeamIdByUserId(userId);
        if (poteamids.isEmpty() && smteamids.isEmpty()) {
            teamAll.add(-1L);
        } else if (poteamids.isEmpty()) {
            teamAll.addAll(smteamids);
        } else {
            teamAll.addAll(poteamids);
        }
        /*
            按团队名称或编号模糊查询出teamids，如果不为空则与之前的teamids进行合并，否则不添加
         */
        String team = dto.getTeam();
        if (!StringUtils.isEmpty(team)) {
            List<STeam> teams = teamv3Service.getTeamLikeNameOrCode(team);
            if (!teams.isEmpty()) {
                //按团队名称模糊查询
                List<Long> ids = Lists.newArrayList();
                teams.forEach(item -> ids.add(item.getTeamId()));
                teamAll.addAll(ids);
            }
        }
        //最终的teamids
        params.put("teamIds", teamAll);
        //迭代名称或编号
        params.put("sprint", dto.getSprint());
        return params;
    }

    /**
     * 构建查询条件--是系统负责人
     *
     * @return
     */
    private HashMap<String, Object> buildQueryParamsSystemOwner(SprintQueryDTO dto, SecurityDTO security) {
        HashMap<String, Object> params = new HashMap<>();
        Long userId = security.getUserId();
        /*
            查询出当前人负责的所有系统ids（systemids），然后根据systemids查询出关联的teamids
         */
        List<Long> systemIds = iFacadeSystemApi.querySystemIdByUserId(userId);
        List<Long> teamIds = STeamSystemMapper.queryTeamIdBySystemId(systemIds);
        List<Long> teamAll = Lists.newArrayList();
        if (!teamIds.isEmpty()) {
            teamAll.addAll(teamIds);
        } else {
            teamAll.add(-1L);
        }
        /*
            按团队名称或编号模糊查询出teamids，如果不为空则与之前的teamids进行合并，否则不添加
         */
        String team = dto.getTeam();
        if (!StringUtils.isEmpty(team)) {
            List<STeam> teams = teamv3Service.getTeamLikeNameOrCode(team);
            if (!teams.isEmpty()) {
                //按团队名称模糊查询
                List<Long> ids = Lists.newArrayList();
                teams.forEach(item -> ids.add(item.getTeamId()));
                teamAll.addAll(ids);
            }
        }
        //最终的teamids
        params.put("teamIds", teamAll);
        //迭代名称或编号
        params.put("sprint", dto.getSprint());
        return params;
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
        List<SprintV3UserHourDTO> userHours = ssprintUserHourMapper.listUserHourBySprintId(sprintIds);
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
            //TODO 故事数及完成数
            item.setStory(new SprintTaskDTO());
            //TODO 任务数及完成数
            item.setTask(new SprintTaskDTO());
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
        //按团队名称或编号模糊查询
        String team = dto.getTeam();
        if (!StringUtils.isEmpty(team)) {
            List<STeam> teams = teamv3Service.getTeamLikeNameOrCode(team);
            if (!teams.isEmpty()) {
                List<Long> teamIds = Lists.newArrayList();
                teams.forEach(item -> teamIds.add(item.getTeamId()));
                params.put("teamIds", teamIds);
            } else {
                params.put("teamIds", Arrays.asList(-1L));
            }
        } else {
            params.put("teamIds", null);
        }
        //迭代名称或编号
        params.put("sprint", dto.getSprint());
        return params;
    }

    /**
     * 新建迭代
     *
     * @param sprintDTO 迭代dto
     * @return {@link String}
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
        int i = ssprintMapper.insert(sprint);

        //插入迭代人员
        List<SprintV3UserHourDTO> members = sprintDTO.getMembers();
        ssprintUserHourMapper.batchInsert(members, sprint.getSprintId());
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
    public void updateSprint(SprintDTO sprintDTO) {
        if (!canEdit(sprintDTO.getSprintId())) {
            throw new BusinessException("迭代已结束或已完成，禁止编辑!");
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
        String str3 = "请选择团队";
        String str4 = "工作时间超长，不能大于24小时!";
        Preconditions.checkArgument(sprintDTO.getSprintName().length() <= 100, str1);
        Preconditions.checkArgument(sprintDTO.getTeamName().length() <= 100, str2);
        Preconditions.checkArgument(sprintDTO.getTeamId() != null || sprintDTO.getTeamName() != null, str3);
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
        ssprintUserHourMapper.deleteBySprintId(sprintId);
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
    public String cancelSprint(long sprintId) {
        int sprintNumber = ssprintMapper.sprintExist(sprintId);
        if (sprintNumber == 0) {
            throw new BusinessException("暂无该迭代");
        }
        ssprintMapper.cancelSprint(sprintId);
        return "迭代状态更新成功";
    }

    @Override
    public int sprintExist(long sprintId) {
        return ssprintMapper.sprintExist(sprintId);
    }
}
