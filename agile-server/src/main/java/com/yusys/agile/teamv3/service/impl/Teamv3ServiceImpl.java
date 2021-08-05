package com.yusys.agile.teamv3.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.yusys.agile.leankanban.service.LeanKanbanService;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.enums.SprintStatusEnum;
import com.yusys.agile.team.dto.*;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.dao.STeamMemberMapper;
import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.agile.teamv3.domain.*;
import com.yusys.agile.teamv3.enums.TeamRoleEnum;
import com.yusys.agile.teamv3.enums.TeamTypeEnum;
import com.yusys.agile.teamv3.response.QueryTeamResponse;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.common.enums.YesOrNoEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.dto.SsoSubjectUserDTO;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.model.facade.enums.AttentionTypeEnum;
import com.yusys.portal.model.facade.enums.RoleTypeEnum;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.ec.ScaleYPointMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
    @Resource
    private SSprintMapper sSprintMapper;

    @Autowired
    private LeanKanbanService leanKanbanService;

    @Autowired
    private IFacadeUserApi iFacadeUserApi;
    @Autowired
    private IFacadeSystemApi iFacadeSystemApi;

    @Override
    public List<TeamListDTO> listTeam(TeamQueryDTO dto, SecurityDTO security) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Long userId = security.getUserId();
        //构建查询参数
        HashMap<String, Object> params = buildQueryParams(dto, security);
        //判断角色，如果是租户管理员，则查询租户下所有，否则按类型查询
        List<TeamListDTO> rest = Lists.newArrayList();
        boolean check = iFacadeUserApi.checkIsTenantAdmin(userId);
        //如果是租户管理员
        if (check) {
            rest = sTeamMapper.queryAllTeam(params);
        } else { //不是租户管理员
            rest = sTeamMapper.queryMyHiveTeam(params);
        }
        rest.forEach(team -> {
            //设置关注标识
            team.setAttentionFlag(iFacadeUserApi.checkObjIsAttention(team.getTeamId(), AttentionTypeEnum.TEAM.getValue(), userId) ?
                    YesOrNoEnum.YES.getValue() : YesOrNoEnum.NO.getValue());
        });
        //构建返回结果
        rest = buildResultList(rest);
        return rest;
    }

    @Override
    public List<TeamListDTO> listAllTeam(TeamQueryDTO dto, SecurityDTO security) {
        //构建查询参数
        HashMap<String, Object> params = buildQueryParamsForAll(dto, security);
        List<TeamListDTO> rest = sTeamMapper.queryAllTeam(params);
        //构建返回结果
        //rest = buildResultList(rest);
        return rest;
    }

    @Override
    public List<STeam> listTeamByIds(List<Long> teamIdList) {
        STeamExample sTeamExample = new STeamExample();
        STeamExample.Criteria criteria = sTeamExample.createCriteria();
        criteria.andStateEqualTo(StateEnum.U.getValue()).andTeamIdIn(teamIdList);
        return sTeamMapper.selectByExample(sTeamExample);
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
            List<TeamUserDTO> leanUsers = Lists.newArrayList();
            List<TeamUserDTO> tmUsers = Lists.newArrayList();
            teamMembers.forEach(member -> {
                if (Objects.equals(item.getTeamId(), member.getTeamId()) && Objects.equals(member.getRoleId(), TeamRoleEnum.PRODUCT_OWNER.roleId)) {
                    poUsers.add(member);
                }
                if (Objects.equals(item.getTeamId(), member.getTeamId()) && Objects.equals(member.getRoleId(), TeamRoleEnum.SCRUM_MASTER.roleId)) {
                    smUsers.add(member);
                }
                if (Objects.equals(item.getTeamId(), member.getTeamId()) && Objects.equals(member.getRoleId(), TeamRoleEnum.LEAN_MASTER.roleId)) {
                    leanUsers.add(member);
                }
                if (Objects.equals(item.getTeamId(), member.getTeamId()) && Objects.equals(member.getRoleId(), TeamRoleEnum.TEAM_MEMBER.roleId)) {
                    tmUsers.add(member);
                }
            });
            //po
            item.setTeamPoNames(poUsers);
            //sm
            item.setTeamSmNames(smUsers);
            //精益教练
            item.setTeamLeanNames(leanUsers);
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
     * @author zhaofeng
     * @date 2021/5/8 14:11
     */
    private HashMap<String, Object> buildQueryParams(TeamQueryDTO dto, SecurityDTO security) {
        HashMap<String, Object> params = new HashMap<>();
        /*
            公共参数：系统、团队、user、租户、团队类型
            类型参数：po、sm、lean
         */
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
        //团队名称或编号
        params.put("team", dto.getTeam());

        //团队类型
        if ("".equals(dto.getTeamType())) {
            params.put("teamType", null);
        } else {
            params.put("teamType", dto.getTeamType());
        }
        return params;
    }

    private HashMap<String, Object> buildQueryParamsForAll(TeamQueryDTO dto, SecurityDTO security) {
        HashMap<String, Object> params = new HashMap<>();
        List<Long> systemIds = Lists.newArrayList();
        systemIds.add(dto.getSystemId());
        params.put("systemIds", systemIds);
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
        String teamType = team.getTeamType();
        String tenantCode = UserThreadLocalUtil.getTenantCode();
        if (sTeamMapper.teamNameNumber(team.getTeamId(), team.getTeamName(), tenantCode, teamType) > 0) {
            throw new BusinessException(TeamTypeEnum.getNameByCode(teamType) + "团队名称已存在");
        }
        //取出数据
        List<STeamMember> teamPoS = team.getTeamPoS();
        List<STeamMember> teamSmS = team.getTeamSmS();
        List<STeamMember> teamUsers = team.getTeamUsers();
        //PO、SM不能重复
//        teamPoS.forEach(po -> {
//            teamSmS.forEach(sm -> {
//                if (Objects.equals(po.getUserId(), sm.getUserId())) {
//                    throw new BusinessException("团队PO、SM重复");
//                }
//            });
//        });
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
        boolean deleteFlag = true;
        //删除前校验
        List<SSprint> sprints = sSprintMapper.querySprintByTeamId(teamId);
        if (CollectionUtils.isEmpty(sprints)) {
            deleteFlag = true;
        } else {
            for (SSprint item : sprints) {
                //如果包括进行中、已完成、未开始的有效迭代，就不能删除
                if (item.getStatus().equals(SprintStatusEnum.TYPE_FINISHED_STATE.CODE) ||
                        item.getStatus().equals(SprintStatusEnum.TYPE_ONGOING_STATE.CODE) ||
                        item.getStatus().equals(SprintStatusEnum.TYPE_NO_START_STATE.CODE)) {
                    deleteFlag = false;
                    break;
                }
                deleteFlag = true;
            }
        }
        if (deleteFlag) {
            //逻辑删除团队
            sTeamMapper.updateStateById(teamId, StateEnum.E.getValue());
            //删除PO、SM的角色，直接按平台级删除
            iFacadeUserApi.deleteUserAndRole(teamId, RoleTypeEnum.PLATFORM.getValue());
        } else {
            throw new BusinessException("该团队已经关联迭代，不允许删除");
        }
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
        STeam sTeam = sTeamMapper.selectByPrimaryKey(teamId);
        String tenantCode = UserThreadLocalUtil.getTenantCode();
        if (sTeamMapper.teamNameNumber(teamId, team.getTeamName(), team.getTeamName(), tenantCode) > 0) {
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
//        teamPoS.forEach(po -> {
//            teamSmS.forEach(sm -> {
//                if (Objects.equals(po.getUserId(), sm.getUserId())) {
//                    throw new BusinessException("团队PO、SM重复");
//                }
//            });
//        });
        //收集PO的id、收集SM的id
        List<Long> poIds = teamPoS.stream().map(po -> {
            return po.getUserId();
        }).collect(Collectors.toList());
        List<Long> smIds = teamSmS.stream().map(sm -> {
            return sm.getUserId();
        }).collect(Collectors.toList());
        //插入团队
        sTeam.setTeamName(team.getTeamName());
        sTeam.setTeamDesc(team.getTeamDesc());
        sTeamMapper.updateByPrimaryKeySelective(sTeam);
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
        String teamType = team.getTeamType();
        QueryTeamResponse queryTeamResponse = new QueryTeamResponse();
        //判断团队类型，如果是敏捷，查询团队po、sm、团队成员
        if (Objects.equals(teamType, TeamTypeEnum.agile_team.getCode())) {
            //构建返回值
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
        } else if (Objects.equals(teamType, TeamTypeEnum.lean_team.getCode())) {
            //如果是精益类型，则查精益教练
            //构建返回值
            queryTeamResponse.setSTeam(team);
            List<Long> list = teamSystemMapper.querySystemIdByTeamId(teamId);
            if (list.size() > 0) {
                queryTeamResponse.setSystems(iFacadeSystemApi.querySsoSystem(list));
            }
            //查询po
            queryTeamResponse.setTeamLean(sTeamMemberMapper.selectByTeamIdAndRoleId(teamId, TeamRoleEnum.LEAN_MASTER.roleId));
            //查询成员
            queryTeamResponse.setTeamUsers(sTeamMemberMapper.selectByTeamIdAndRoleId(teamId, TeamRoleEnum.TEAM_MEMBER.roleId));
        } else {
            throw new BusinessException("团队类型错误");
        }
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


    //----------------------------------- 精益团队部分（开始）--------------------------------------

    /**
     * 新增精益团队
     *
     * @param team
     * @author zhaofeng
     * @date 2021/6/11 15:50
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertTeamForLean(STeam team) {
        String teamType = team.getTeamType();
        String tenantCode = UserThreadLocalUtil.getTenantCode();
        if (sTeamMapper.teamNameNumber(team.getTeamId(), team.getTeamName(), tenantCode, teamType) > 0) {
            throw new BusinessException(TeamTypeEnum.getNameByCode(teamType) + "团队名称已存在");
        }
        //取出数据
        List<STeamMember> teamLean = team.getTeamLean();
        List<STeamMember> teamUsers = team.getTeamUsers();
        //收集PO的id、收集SM的id
        List<Long> leanIds = teamLean.stream().map(lean -> lean.getUserId()).collect(Collectors.toList());

        //插入团队
        sTeamMapper.insertSelective(team);
        //团队绑定系统
        teamSystemMapper.bindingTeamAndSystem(team, team.getSystemIds());
        //团队绑定 精益教练
        sTeamMemberMapper.batchInsert(team, teamLean, TeamRoleEnum.LEAN_MASTER.roleId);
        //团队绑定其他成员
        sTeamMemberMapper.batchInsert(team, teamUsers, TeamRoleEnum.TEAM_MEMBER.roleId);
        //调门户服务，新增精益教练角色
        SsoSubjectUserDTO lean = new SsoSubjectUserDTO();
        lean.setUserRelateType(RoleTypeEnum.PLATFORM.getValue());
        lean.setRoleId((long) TeamRoleEnum.LEAN_MASTER.roleId);
        lean.setSubjectId(team.getTeamId());
        lean.setDataCreatorId(team.getCreateUid());
        lean.setUserIds(leanIds);

        try {
            iFacadeUserApi.addUserRlats(lean);
        } catch (Exception e) {
            log.error("调用门户服务添加精益教练角色失败，失败原因:{}", e);
            throw new BusinessException("调用门户服务添加精益教练角色失败");
        }

        //创建精益看板
        leanKanbanService.createLeanKanban(team.getTeamId());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTeamForLean(STeam team) {
        Long teamId = team.getTeamId();
        STeam sTeam = sTeamMapper.selectByPrimaryKey(teamId);
        String tenantCode = UserThreadLocalUtil.getTenantCode();
        String teamType = team.getTeamType();
        if (sTeamMapper.teamNameNumber(teamId, team.getTeamName(), tenantCode, teamType) > 0) {
            throw new BusinessException(TeamTypeEnum.getNameByCode(teamType) + "团队名称已存在");
        }

        //删除团队与系统的绑定关系
        teamSystemMapper.deleteByTeamId(teamId);
        //删除团队与PO、SM、TM的绑定关系
        sTeamMemberMapper.deleteByTeamId(teamId);
        //删除PO、SM的角色，直接按平台级删除
        iFacadeUserApi.deleteUserAndRole(teamId, RoleTypeEnum.PLATFORM.getValue());


        //取出数据
        List<STeamMember> teamLean = team.getTeamLean();
        List<STeamMember> teamUsers = team.getTeamUsers();
        //收集PO的id、收集SM的id
        List<Long> leanIds = teamLean.stream().map(lean -> lean.getUserId()).collect(Collectors.toList());

        sTeam.setTeamDesc(team.getTeamDesc());
        sTeam.setTeamName(team.getTeamName());
        //插入团队
        sTeamMapper.updateByPrimaryKeySelective(sTeam);
        //团队绑定系统
        teamSystemMapper.bindingTeamAndSystem(team, team.getSystemIds());
        //团队绑定 精益教练
        sTeamMemberMapper.batchInsert(team, teamLean, TeamRoleEnum.LEAN_MASTER.roleId);
        //团队绑定其他成员
        sTeamMemberMapper.batchInsert(team, teamUsers, TeamRoleEnum.TEAM_MEMBER.roleId);
        //调门户服务，新增精益教练角色
        SsoSubjectUserDTO lean = new SsoSubjectUserDTO();
        lean.setUserRelateType(RoleTypeEnum.PLATFORM.getValue());
        lean.setRoleId((long) TeamRoleEnum.LEAN_MASTER.roleId);
        lean.setSubjectId(team.getTeamId());
        lean.setDataCreatorId(team.getCreateUid());
        lean.setUserIds(leanIds);

        try {
            iFacadeUserApi.addUserRlats(lean);
        } catch (Exception e) {
            log.error("调用门户服务添加精益教练角色失败，失败原因:{}", e);
            throw new BusinessException("调用门户服务添加精益教练角色失败");
        }
    }

    @Override
    public STeam getTeamById(long teamId) {
        STeam sTeam = sTeamMapper.selectByPrimaryKey(teamId);
        return sTeam;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTeamForLean(Long teamId) {
        //精益团队暂不能删除
        throw new BusinessException("精益团队暂不支持删除");
    }


    //----------------------------------- 精益团队部分（结束）--------------------------------------


    @Override
    public List<STeam> listTeamByTenantCode(String tenantCode) {
        STeamExample sTeamExample = new STeamExample();
        STeamExample.Criteria criteria = sTeamExample.createCriteria();
        criteria.andStateEqualTo(StateEnum.U.getValue());
        if (Optional.ofNullable(tenantCode).isPresent()) {
            criteria.andTenantCodeEqualTo(tenantCode);
        }
        return sTeamMapper.selectByExampleWithBLOBs(sTeamExample);
    }

    @Override
    public List<TeamListDTO> queryTeams(List<Long> teamIds, String teamName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TeamListDTO> sTeams = sTeamMapper.queryTeams(teamIds, teamName);
        //按ids分别查询团队/系统
        return buildResultList(sTeams);
    }

    /**
     * 根据系统ids查询团队
     * @param systemIdList
     * @return
     */
    @Override
    public List<TeamListDTO> queryTeamsBySystemIdList(List<Long> systemIdList) {
        List<Long> teamIds = teamSystemMapper.queryTeamIdBySystemId(systemIdList);

        if(CollectionUtils.isEmpty(teamIds)){
            return Lists.newArrayList();
        }

        STeamExample teamExample = new STeamExample();
        teamExample.createCriteria().andStateEqualTo(StateEnum.U.getValue()).andTeamTypeEqualTo(TeamTypeEnum.agile_team.getCode()).andTeamIdIn(teamIds);
        List<STeam> sTeams = sTeamMapper.selectByExample(teamExample);
        List<TeamListDTO> teams = new ArrayList<>();
        try {
            teams = ReflectUtil.copyProperties4List(sTeams, TeamListDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teams;
    }
}
