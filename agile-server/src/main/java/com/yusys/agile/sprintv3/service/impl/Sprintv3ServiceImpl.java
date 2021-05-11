package com.yusys.agile.sprintv3.service.impl;

import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprint.enums.SprintStatusEnum;
import com.yusys.agile.sprint.domain.UserSprintHour;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprint.dto.UserSprintHourDTO;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintV3.dto.SprintQueryDTO;
import com.yusys.agile.sprintV3.dto.SprintV3DTO;
import com.yusys.agile.sprintV3.dto.SprintV3UserHourDTO;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.dao.SSprintUserHourMapper;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.agile.team.domain.Team;
import com.yusys.agile.team.dto.TeamDTO;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.date.DateUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yusys.agile.team.dto.TeamDTO;
import javax.annotation.Resource;
import java.util.Date;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author zhaofeng
 * @Date 2021/5/11 14:48
 */
@Service
public class Sprintv3ServiceImpl implements Sprintv3Service {

    @Resource
    private SSprintMapper ssprintMapper;
    @Resource
    private SSprintUserHourMapper ssprintUserHourMapper;
    @Resource
    private STeamMapper sTeamMapper;
    @Resource
    private IFacadeUserApi iFacadeUserApi;
    @Resource
    private SSprintUserHourMapper sSprintUserHourMapper;
    @Resource
    private IFacadeSystemApi iFacadeSystemApi;
    @Resource
    private com.yusys.agile.teamv3.dao.STeamSystemMapper STeamSystemMapper;


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
            List<Long> systemIds=STeamSystemMapper.querySystemIdByTeamId(teamDTO.getTeamId());
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
        return null;
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
}
