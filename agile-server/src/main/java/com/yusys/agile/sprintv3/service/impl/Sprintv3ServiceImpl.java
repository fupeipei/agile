package com.yusys.agile.sprintv3.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.yusys.agile.sprint.domain.UserSprintHour;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprint.dto.UserSprintHourDTO;
import com.yusys.agile.sprint.enums.SprintStatusEnum;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintV3.dto.SprintQueryDTO;
import com.yusys.agile.sprintV3.dto.SprintV3DTO;
import com.yusys.agile.sprintV3.dto.SprintV3UserHourDTO;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.dao.SSprintUserHourMapper;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintUserHour;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.team.domain.Team;
import com.yusys.agile.team.dto.TeamDTO;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.dao.STeamSystemMapper;
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
    @Autowired
    private IFacadeSystemApi iFacadeSystemApi;
    @Autowired
    private IFacadeUserApi iFacadeUserApi;
    @Autowired
    private Teamv3Service teamv3Service;


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

        HashMap<String, Object> params = buildQueryParams(dto, security);

        //如果是租户管理员
        boolean isTenantAdmin = iFacadeUserApi.checkIsTenantAdmin(userId);
        if(isTenantAdmin){
            List<SprintListDTO> rest =  ssprintMapper.queryAllSprint(params);
            rest = buildResultList(rest);
            return rest;
        }
        //如果是系统负责人
        boolean isSystemOwner = iFacadeUserApi.checkIsSystemOwner(userId, systemId);
        if(isSystemOwner){
            return null;
        }
        //如果是po
        boolean isTeamPo = iFacadeUserApi.checkIsTeamPo(userId);
        if(isTeamPo){
            return null;
        }
        //如果是sm
        boolean isTeamSm = iFacadeUserApi.checkIsTeamSm(userId);
        if(isTeamSm){
            return null;
        }
        //如果是团队成员
        boolean isTeamMember = iFacadeUserApi.checkIsTeamMember(userId);
        if(isTeamMember){
            return null;
        }
        throw new BusinessException("该用户没有权限");
    }

    /**
     * 构建返回结果
     * @author zhaofeng
     * @date 2021/5/12 10:11
     * @param rest
     */
    private List<SprintListDTO> buildResultList(List<SprintListDTO> rest) {
        //收集teamids，查询team，收集createuid，查询创建人
        List<Long> teamIds = Lists.newArrayList();
        List<Long> createIds = Lists.newArrayList();
        rest.forEach(item->{
            teamIds.add(item.getTeamId());
            createIds.add(item.getCreateUid());
        });
        List<STeam> sTeams = sTeamMapper.listTeamByIds(teamIds);
        List<SsoUser> ssoUsers = iFacadeUserApi.listUsersByIds(createIds);
        //拼接返回值
        rest.forEach(item->{
            //团队
            sTeams.forEach(t->{
                if(Objects.equals(item.getTeamId(), t.getTeamId())){
                    item.setTeamName(t.getTeamName());
                    item.setTeamUserCount(t.getTeamUsers().size());
                }
            });
            //状态
            item.setStatusStr(SprintStatusEnum.getName(item.getStatus()));
            //迭代周期
            List<Date> dates = convertStrToDate(item.getSprintDays());
            item.setSprintDayList(dates);
            //迭代天数
            item.setPlanDays(dates.size());
            //TODO 故事数及完成数
            //TODO 任务数及完成数
            //创建人
            ssoUsers.forEach(u->{
                if(Objects.equals(item.getCreateUid(), u.getUserId())){
                    item.setCreateUser(u);
                }
            });
        });
        return rest;
    }
    /**
     * 构建查询条件
     * @author zhaofeng
     * @date 2021/5/12 10:11
     * @param dto
     * @param security
     */
    private HashMap<String, Object> buildQueryParams(SprintQueryDTO dto, SecurityDTO security) {
        HashMap<String, Object> params = new HashMap<>();
        //按团队名称或编号模糊查询
        String team = dto.getTeam();
        if(!StringUtils.isEmpty(team)){
            List<STeam> teams = teamv3Service.getTeamLikeNameOrCode(team);
            if(!team.isEmpty()){
                List<Long> teamIds = Lists.newArrayList();
                teams.forEach(item-> teamIds.add(item.getTeamId()));
                params.put("teamIds", teamIds);
            }else{
                params.put("teamIds", Arrays.asList(-1L));
            }
        }else{
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
//        sprintDTO.setTenantCode(UserThreadLocalUtil.getTenantCode());
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
            throw new BusinessException("迭代开始,结束时间填充异常 list.size小于2");
        }

        //版本号判断
        Matcher m = null;
        try {
            String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regEx);
            m = p.matcher(sprintDTO.getVersionNumber());
            if (m.find()) {
                throw new BusinessException("版本号只能是英文数字_.等常用字符！");
            }
        } catch (NullPointerException e) {
            throw new BusinessException("迭代版本号异常");
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
        int sprintNameNumber = ssprintMapper.CheckSprintName(sprintDTO.getSprintName(), sprintDTO.getTenantCode());
        if (sprintNameNumber > 0) {
            throw new BusinessException("当前租户下迭代名称重复");
        }
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
}
