package com.yusys.agile.team.service.impl;

import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.dao.UserSprintHourMapper;
import com.yusys.agile.sprint.domain.Sprint;
import com.yusys.agile.sprint.domain.UserSprintHour;
import com.yusys.agile.sprint.dto.UserSprintHourDTO;
import com.yusys.agile.team.dao.TeamMapper;
import com.yusys.agile.team.domain.Team;
import com.yusys.agile.team.domain.TeamExample;
import com.yusys.agile.team.dto.TeamDTO;
import com.yusys.agile.team.service.TeamService;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private SprintMapper sprintMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserSprintHourMapper userSprintHourMapper;
    @Autowired
    private IFacadeUserApi iFacadeUserApi;

    private static final String CREATE_TIME_DESC = "CREATE_TIME DESC";

    @Override
    public List<TeamDTO> getListTeam(Long projectId) {
        TeamExample example = new TeamExample();
        TeamExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(projectId).andStateEqualTo(StateEnum.U.getValue());
        example.setOrderByClause(CREATE_TIME_DESC);
        List<Team> teams = teamMapper.selectByExample(example);
        // 空的直接返回
        if (CollectionUtils.isEmpty(teams)) {
            return new ArrayList<>();
        }
        List<TeamDTO> teamDTOS = new ArrayList<>();
        for (Team team : teams) {
            TeamDTO teamDTO = ReflectUtil.copyProperties(team, TeamDTO.class);
            // 取上一个迭代人员放入Team
            Sprint sprint = sprintMapper.getOneSprintByTeamId(teamDTO.getTeamId());
            if (null != sprint) {
                List<UserSprintHourDTO> userSprintHourDTOS = new ArrayList<>();
                List<UserSprintHour> userSprintHours = userSprintHourMapper.getUserIds4Sprint(sprint.getSprintId());
                if (CollectionUtils.isNotEmpty(userSprintHours)) {
                    getUserSprintHour(userSprintHourDTOS, userSprintHours, iFacadeUserApi);
                }
                teamDTO.setUsers(userSprintHourDTOS);
            }
            teamDTOS.add(teamDTO);
        }
        return teamDTOS;
    }

    @Override
    public void getUserSprintHour(List<UserSprintHourDTO> userSprintHourDTOS, List<UserSprintHour> userSprintHours, IFacadeUserApi iFacadeUserApi) {
        for (UserSprintHour userSprintHour : userSprintHours) {
            SsoUser user = iFacadeUserApi.queryUserById(userSprintHour.getUserId());
            UserSprintHourDTO userSprintHourDTO = ReflectUtil.copyProperties(userSprintHour, UserSprintHourDTO.class);
            if (null != user) {
                userSprintHourDTO.setUserId(user.getUserId());
                userSprintHourDTO.setUserName(user.getUserName());
                userSprintHourDTO.setUserAccount(user.getUserAccount());
                userSprintHourDTO.setReallyHours(0);
                userSprintHourDTOS.add(userSprintHourDTO);
            }
        }
    }

    @Override
    public List<SsoUser> listMemberUsers(Long subjectId, Integer userRelateType) {
        List<SsoUser> ssoUserList = iFacadeUserApi.ListMemberUsers(subjectId, userRelateType);
        if (CollectionUtils.isNotEmpty(ssoUserList)) {
            for (SsoUser ssoUser : ssoUserList) {
                ssoUser.setUserPassword(null);
            }
        }
        return ssoUserList;
    }
}
