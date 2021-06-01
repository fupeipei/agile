package com.yusys.agile.teamv3.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
import com.yusys.agile.team.dto.TeamSystemDTO;
import com.yusys.agile.team.dto.TeamUserDTO;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.dao.STeamMemberMapper;
import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.agile.teamv3.domain.STeamSystem;
import com.yusys.agile.teamv3.enums.TeamRoleEnum;
import com.yusys.agile.teamv3.response.QueryTeamResponse;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.dto.SsoSubjectUserDTO;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.model.facade.enums.RoleTypeEnum;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author zhaofeng
 * @Date 2021/5/8 10:52
 */
@Slf4j
@Service
public class Teamv3ServiceImpl implements Teamv3Service {
    @Resource
    private STeamMapper sTeamMapper;
    @Resource
    private STeamSystemMapper teamSystemMapper;
    @Resource
    private STeamMemberMapper sTeamMemberMapper;
    @Autowired
    private IFacadeUserApi iFacadeUserApi;
    @Autowired
    private IFacadeSystemApi iFacadeSystemApi;

    @Override
    public List<TeamListDTO> listTeam(TeamQueryDTO dto, SecurityDTO security) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Long userId = security.getUserId();
        String tenantCode = security.getTenantCode();
        //构建查询参数
        HashMap<String, Object> params = buildQueryParams(dto, userId);
        params.put("tenantCode", tenantCode);
        //判断角色，如果是租户管理员，则查询租户下所有，否则按类型查询
        List<TeamListDTO> rest = Lists.newArrayList();
        boolean check = iFacadeUserApi.checkIsTenantAdmin(userId);
        //如果是租户管理员
        if (check) {
            rest = sTeamMapper.queryAllTeam(params);
        } else { //不是租户管理员
            rest = sTeamMapper.queryMyHiveTeam(params);
        }
        //构建返回结果
        rest = buildResultList(rest);
        return rest;
    }

    /**
     * 构建返回值
     *
     * @param rest
     * @author zhaofeng
     * @date 2021/5/8 14:53
     */
    private List<TeamListDTO> buildResultList(List<TeamListDTO> rest) {
        //收集teamIds和创建人userIds
        List<Long> teamIds = Lists.newArrayList();
        List<Long> userIds = Lists.newArrayList();
        rest.forEach(item -> {
            teamIds.add(item.getTeamId());
            userIds.add(item.getCreateUid());
        });
        //按ids分别查询
        List<TeamUserDTO> teamMembers = sTeamMemberMapper.selectByTeamIds(teamIds);
        List<TeamSystemDTO> teamSystems = teamSystemMapper.selectByTeamIds(teamIds);
        //收集得到的systemids
        List<Long> systemIds = Lists.newArrayList();
        teamSystems.forEach(teamSystem -> {
            systemIds.add(teamSystem.getSystemId());
        });
        //远程查询出所有system信息
        List<SsoSystemRestDTO> ssoSystems = iFacadeSystemApi.getSystemByIds(systemIds);
        //给teamSystems中的systemName属性赋值
        teamSystems.forEach(teamSystem -> {
            ssoSystems.forEach(ssoSystem -> {
                if (teamSystem.getSystemId().equals(ssoSystem.getSystemId())) {
                    teamSystem.setSystemName(ssoSystem.getSystemName());
                }
            });
        });
        //查询出创建人信息
        List<SsoUser> users = iFacadeUserApi.listUsersByIds(userIds);
        rest.forEach(item -> {
            //po、sm、tm以及tm数量赋值
            List<TeamUserDTO> poUsers = Lists.newArrayList();
            List<TeamUserDTO> smUsers = Lists.newArrayList();
            List<TeamUserDTO> tmUsers = Lists.newArrayList();
            teamMembers.forEach(member -> {
                if (Objects.equals(item.getTeamId(), member.getTeamId()) && Objects.equals(member.getRoleId(), TeamRoleEnum.PRODUCT_OWNER.roleId)) {
                    poUsers.add(member);
                }
                if (Objects.equals(item.getTeamId(), member.getTeamId()) && Objects.equals(member.getRoleId(), TeamRoleEnum.SCRUM_MASTER.roleId)) {
                    smUsers.add(member);
                }
                if (Objects.equals(item.getTeamId(), member.getTeamId()) && Objects.equals(member.getRoleId(), TeamRoleEnum.TEAM_MEMBER.roleId)) {
                    tmUsers.add(member);
                }
            });
            //po
            item.setTeamPoNames(poUsers);
            //sm
            item.setTeamSmNames(smUsers);
            //团队成员
            item.setTeamUsers(tmUsers);
            //团队成员数量
            item.setTeamUserCount(tmUsers.size());
            //创建人
            users.forEach(user -> {
                if (Objects.equals(item.getCreateUid(), user.getUserId())) {
                    SsoUserDTO userDTO = new SsoUserDTO();
                    BeanUtils.copyProperties(user, userDTO);
                    userDTO.setUserPassword("");
                    item.setCreateUser(userDTO);
                }
            });
            //系统
            List<TeamSystemDTO> systems = Lists.newArrayList();
            teamSystems.forEach(teamSystem -> {
                if (item.getTeamId().equals(teamSystem.getTeamId())) {
                    systems.add(teamSystem);
                }
            });
            item.setSystemNames(systems);
        });
        return rest;
    }

    /**
     * 创建列表查询条件
     *
     * @param dto
     * @param userId
     * @author zhaofeng
     * @date 2021/5/8 14:11
     */
    private HashMap<String, Object> buildQueryParams(TeamQueryDTO dto, Long userId) {
        HashMap<String, Object> params = new HashMap<>();
        //按系统名称或code获取systemids
        String system = dto.getSystem();
        if (!StringUtils.isEmpty(system)) {
            List<SsoSystemRestDTO> systemList = iFacadeSystemApi.getSystemByLikeNameOrCode(system);
            List<Long> systemIds = Lists.newArrayList();
            if (!systemList.isEmpty()) {
                systemList.forEach(s -> {
                    systemIds.add(s.getSystemId());
                });
                params.put("systemIds", systemIds);
            } else {
                params.put("systemIds", Arrays.asList(-1L));
            }
        } else {
            params.put("systemIds", null);
        }
        //按po名称或账号获取poids
        String po = dto.getPo();
        if (!StringUtils.isEmpty(po)) {
            params.put("po", po);
        } else {
            params.put("po", null);
        }
        //按sm名称或账号获取smids
        String sm = dto.getSm();
        if (!StringUtils.isEmpty(sm)) {
            params.put("sm", sm);
        } else {
            params.put("sm", null);
        }
        params.put("team", dto.getTeam());
        params.put("userId", userId);
        return params;
    }

    /**
     * 新增团队
     *
     * @param team 团队
     * @return {@link String}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertTeam(STeam team) {
        String tenantCode = UserThreadLocalUtil.getTenantCode();
        if (sTeamMapper.teamNameNumber(team.getTeamId(), team.getTeamName(), tenantCode) > 0) {
            throw new BusinessException("团队名称已存在");
        }
        //取出数据
        List<STeamMember> teamPoS = team.getTeamPoS();
        List<STeamMember> teamSmS = team.getTeamSmS();
        List<STeamMember> teamUsers = team.getTeamUsers();
        //PO、SM不能重复
        teamPoS.forEach(po -> {
            teamSmS.forEach(sm -> {
                if (Objects.equals(po.getUserId(), sm.getUserId())) {
                    throw new BusinessException("团队PO、SM重复");
                }
            });
        });
        //收集PO的id、收集SM的id
        List<Long> poIds = teamPoS.stream().map(po -> {
            return po.getUserId();
        }).collect(Collectors.toList());
        List<Long> smIds = teamSmS.stream().map(sm -> {
            return sm.getUserId();
        }).collect(Collectors.toList());
        //插入团队
        sTeamMapper.insertSelective(team);
        //团队绑定系统
        teamSystemMapper.bindingTeamAndSystem(team, team.getSystemIds());
        //团队绑定PO
        sTeamMemberMapper.batchInsert(team, teamPoS, TeamRoleEnum.PRODUCT_OWNER.roleId);
        //团队绑定SM
        sTeamMemberMapper.batchInsert(team, teamSmS, TeamRoleEnum.SCRUM_MASTER.roleId);
        //团队绑定其他成员
        sTeamMemberMapper.batchInsert(team, teamUsers, TeamRoleEnum.TEAM_MEMBER.roleId);
        //调门户服务，新增PO角色
        SsoSubjectUserDTO po = new SsoSubjectUserDTO();
        po.setUserRelateType(RoleTypeEnum.PLATFORM.getValue());
        po.setRoleId((long) TeamRoleEnum.PRODUCT_OWNER.roleId);
        po.setSubjectId(team.getTeamId());
        po.setDataCreatorId(team.getCreateUid());
        po.setUserIds(poIds);
        //调用门户服务，新增SM角色
        SsoSubjectUserDTO sm = new SsoSubjectUserDTO();
        sm.setUserRelateType(RoleTypeEnum.PLATFORM.getValue());
        sm.setRoleId((long) TeamRoleEnum.SCRUM_MASTER.roleId);
        sm.setSubjectId(team.getTeamId());
        sm.setDataCreatorId(team.getCreateUid());
        sm.setUserIds(smIds);
        try {
            iFacadeUserApi.addUserRlats(po);
            //调用门户服务，新增SM角色
            iFacadeUserApi.addUserRlats(sm);
        } catch (Exception e) {
            log.error("调用门户服务添加PO、SM角色失败，失败原因:{}", e);
            throw new BusinessException("调用门户服务添加PO、SM角色失败");
        }
    }

    /**
     * 删除团队
     *
     * @param teamId 团队id
     * @return {@link String}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTeam(long teamId) {
        //逻辑删除团队
        sTeamMapper.updateStateById(teamId, StateEnum.E.getValue());
        //删除PO、SM的角色，直接按平台级删除
        iFacadeUserApi.deleteUserAndRole(teamId, RoleTypeEnum.PLATFORM.getValue());
    }

    /**
     * 更新团队
     *
     * @param team 团队
     * @return {@link String}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTeam(STeam team) {
        Long teamId = team.getTeamId();
        String tenantCode = UserThreadLocalUtil.getTenantCode();
        if (sTeamMapper.teamNameNumber(teamId, team.getTeamName(), tenantCode) > 0) {
            throw new BusinessException("团队名称已存在");
        }

        //删除团队与系统的绑定关系
        teamSystemMapper.deleteByTeamId(teamId);
        //删除团队与PO、SM、TM的绑定关系
        sTeamMemberMapper.deleteByTeamId(teamId);
        //删除PO、SM的角色，直接按平台级删除
        iFacadeUserApi.deleteUserAndRole(teamId, RoleTypeEnum.PLATFORM.getValue());


        //取出数据
        List<STeamMember> teamPoS = team.getTeamPoS();
        List<STeamMember> teamSmS = team.getTeamSmS();
        List<STeamMember> teamUsers = team.getTeamUsers();
        //PO、SM不能重复
        teamPoS.forEach(po -> {
            teamSmS.forEach(sm -> {
                if (Objects.equals(po.getUserId(), sm.getUserId())) {
                    throw new BusinessException("团队PO、SM重复");
                }
            });
        });
        //收集PO的id、收集SM的id
        List<Long> poIds = teamPoS.stream().map(po -> {
            return po.getUserId();
        }).collect(Collectors.toList());
        List<Long> smIds = teamSmS.stream().map(sm -> {
            return sm.getUserId();
        }).collect(Collectors.toList());
        //插入团队
        sTeamMapper.updateByPrimaryKeySelective(team);
        //团队绑定系统
        teamSystemMapper.bindingTeamAndSystem(team, team.getSystemIds());
        //团队绑定PO
        sTeamMemberMapper.batchInsert(team, teamPoS, TeamRoleEnum.PRODUCT_OWNER.roleId);
        //团队绑定SM
        sTeamMemberMapper.batchInsert(team, teamSmS, TeamRoleEnum.SCRUM_MASTER.roleId);
        //团队绑定其他成员
        sTeamMemberMapper.batchInsert(team, teamUsers, TeamRoleEnum.TEAM_MEMBER.roleId);
        //调门户服务，新增PO角色
        SsoSubjectUserDTO po = new SsoSubjectUserDTO();
        po.setUserRelateType(RoleTypeEnum.PLATFORM.getValue());
        po.setRoleId((long) TeamRoleEnum.PRODUCT_OWNER.roleId);
        po.setSubjectId(team.getTeamId());
        po.setDataCreatorId(team.getCreateUid());
        po.setUserIds(poIds);
        //调用门户服务，新增SM角色
        SsoSubjectUserDTO sm = new SsoSubjectUserDTO();
        sm.setUserRelateType(RoleTypeEnum.PLATFORM.getValue());
        sm.setRoleId((long) TeamRoleEnum.SCRUM_MASTER.roleId);
        sm.setSubjectId(team.getTeamId());
        sm.setDataCreatorId(team.getCreateUid());
        sm.setUserIds(smIds);
        try {
            iFacadeUserApi.addUserRlats(po);
            //调用门户服务，新增SM角色
            iFacadeUserApi.addUserRlats(sm);
        } catch (Exception e) {
            log.error("调用门户服务添加PO、SM角色失败，失败原因:{}", e);
            throw new BusinessException("调用门户服务添加PO、SM角色失败");
        }
    }

    /**
     * 查询团队
     *
     * @param teamId 团队id
     * @return {@link QueryTeamResponse}
     */
    @Override
    public QueryTeamResponse queryTeam(long teamId) {
        STeam team = sTeamMapper.queryTeam(teamId);
        if (ObjectUtil.isEmpty(team)) {
            throw new BusinessException("暂无该团队信息");
        }
        //构建返回值
        QueryTeamResponse queryTeamResponse = new QueryTeamResponse();
        queryTeamResponse.setSTeam(team);
        List<Long> list = teamSystemMapper.querySystemIdByTeamId(teamId);
        if (list.size() > 0) {
            queryTeamResponse.setSystems(iFacadeSystemApi.querySsoSystem(list));
        }
        //查询po
        queryTeamResponse.setTeamPoS(sTeamMemberMapper.selectByTeamIdAndRoleId(teamId, TeamRoleEnum.PRODUCT_OWNER.roleId));
        //查询sm
        queryTeamResponse.setTeamSmS(sTeamMemberMapper.selectByTeamIdAndRoleId(teamId, TeamRoleEnum.SCRUM_MASTER.roleId));
        //查询成员
        queryTeamResponse.setTeamUsers(sTeamMemberMapper.selectByTeamIdAndRoleId(teamId, TeamRoleEnum.TEAM_MEMBER.roleId));
        return queryTeamResponse;
    }

    @Override
    public List<STeam> getTeamLikeNameOrCode(String team) {
        List<STeam> list = sTeamMapper.getTeamLikeNameOrCode(team);
        return list;
    }


    @Override
    public List<SsoSystemRestDTO> querySystemByTeamId(long teamId) {
        List<Long> systemIdByTeamIds = teamSystemMapper.querySystemIdByTeamId(teamId);
        List<SsoSystemRestDTO> systemByIds = iFacadeSystemApi.getSystemByIds(systemIdByTeamIds);
        return systemByIds;
    }
}
