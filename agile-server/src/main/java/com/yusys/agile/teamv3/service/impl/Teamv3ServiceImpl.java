package com.yusys.agile.teamv3.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
import com.yusys.agile.team.dto.TeamSystemDTO;
import com.yusys.agile.team.dto.TeamUserDTO;
import com.yusys.agile.teamv3.dao.*;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamUser;
import com.yusys.agile.teamv3.response.QueryTeamResponse;
import com.yusys.agile.teamv3.service.TeamSystemv3Service;
import com.yusys.agile.teamv3.service.TeamUserv3Service;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.common.id.IdGenerator;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author zhaofeng
 * @Date 2021/5/8 10:52
 */
@Service
public class Teamv3ServiceImpl implements Teamv3Service {
    @Resource
    private STeamMapper sTeamMapper;
    @Resource
    private STeamSystemMapper teamSystemMapper;
    @Resource
    private STeamUserMapper teamUserMapper;
    @Resource
    private STeamPoMapper teamPoMapper;
    @Resource
    private STeamSmMapper teamSmMapper;
    @Autowired
    private TeamUserv3Service teamUserv3Service;
    @Autowired
    private TeamSystemv3Service teamSystemv3Service;

    @Autowired
    private IFacadeUserApi iFacadeUserApi;
    @Autowired
    private IFacadeSystemApi iFacadeSystemApi;

    @Override
    public List<TeamListDTO> listTeam(TeamQueryDTO dto, SecurityDTO security) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Long userId = security.getUserId();

        //构建查询参数
        HashMap<String, Object> params = buildQueryParams(dto, userId);

        //判断角色，如果是租户管理员，则查询租户下所有，否则按类型查询
        boolean check = iFacadeUserApi.checkIsTenantAdmin(userId);
        List<TeamListDTO> rest = Lists.newArrayList();
        //如果是租户管理员
        if(check){
            rest = sTeamMapper.queryAllTeam(params);
        }else{ //不是租户管理员
            rest = sTeamMapper.queryMyHiveTeam(params);
        }
        //构建返回结果
        rest = buildResultList(rest);
        return rest;
    }

    /**
     * 构建返回值
     * @author zhaofeng
     * @date 2021/5/8 14:53
     * @param rest
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
        List<TeamUserDTO> teamPoUsers = teamPoMapper.selectByTeamIds(teamIds);
        List<TeamUserDTO> teamSmUsers = teamSmMapper.selectByTeamIds(teamIds);
        List<TeamUserDTO> teamUsers = teamUserMapper.selectByTeamIds(teamIds);
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
            //po、sm赋值
            List<TeamUserDTO> poUsers = Lists.newArrayList();
            List<TeamUserDTO> smUsers = Lists.newArrayList();
            teamPoUsers.forEach(user -> {
                if (Objects.equals(item.getTeamId(),user.getTeamId())) {
                    poUsers.add(user);
                }
            });
            teamSmUsers.forEach(user -> {
                if (Objects.equals(item.getTeamId(),user.getTeamId())) {
                    smUsers.add(user);
                }
            });
            item.setTeamPoNames(poUsers);
            item.setTeamSmNames(smUsers);
            //团队成员赋值
            List<TeamUserDTO> otherUsers = Lists.newArrayList();
            teamUsers.forEach(user->{
                if (Objects.equals(item.getTeamId(),user.getTeamId())) {
                    otherUsers.add(user);
                }
            });
            item.setTeamUsers(otherUsers);
            //创建人
            users.forEach(user -> {
                if (Objects.equals(item.getCreateUid(),user.getUserId())) {
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
     * @author zhaofeng
     * @date 2021/5/8 14:11
     * @param dto
     * @param userId
     */
    private HashMap<String, Object> buildQueryParams(TeamQueryDTO dto, Long userId) {
        HashMap<String, Object> params = new HashMap<>();
        //按系统名称或code获取systemids
        String system = dto.getSystem();
        if (!StringUtils.isEmpty(system)) {
            List<SsoSystemRestDTO> systemList = iFacadeSystemApi.getSystemByLikeNameOrCode(system);
            List<Long> systemIds = Lists.newArrayList();
            if(!systemList.isEmpty()){
                systemList.forEach(s -> {
                    systemIds.add(s.getSystemId());
                });
                params.put("systemIds", systemIds);
            }else{
                params.put("systemIds", Arrays.asList(-1L));
            }
        } else {
            params.put("systemIds", null);
        }
        //按po名称或账号获取poids
        String po = dto.getPo();
        if(!StringUtils.isEmpty(po)){
            params.put("po", po);
        }else{
            params.put("po", null);
        }
        //按sm名称或账号获取smids
        String sm = dto.getSm();
        if(!StringUtils.isEmpty(sm)){
            params.put("sm", sm);
        }else{
            params.put("sm", null);
        }
        params.put("team", dto.getTeam());
        params.put("userId", userId);
        return params;
    }

    @Override
    public List<TeamListDTO> list() {
        //查询
        List<TeamListDTO> teams = sTeamMapper.selectAll();
        //收集id
        List<Long> teamIds = Lists.newArrayList();
        teams.forEach(team -> {
            teamIds.add(team.getTeamId());
        });
        //查询系统,收集systemIds
        List<TeamSystemDTO> teamSystems = teamSystemv3Service.selectByTeamIds(teamIds);
        List<Long> systemIds = Lists.newArrayList();
        teamSystems.forEach(teamSystem -> {
            systemIds.add(teamSystem.getSystemId());
        });
        //远程查询系统，并给teamSystem赋值
        List<SsoSystemRestDTO> ssoSystem = iFacadeSystemApi.getSystemByIds(systemIds);
        teamSystems.forEach(teamSystem -> {
            ssoSystem.forEach(system -> {
                if (teamSystem.getSystemId().equals(system.getSystemId())) {
                    teamSystem.setSystemName(system.getSystemName());
                }
            });
        });
        //查询成员
        List<TeamUserDTO> teanUsers = teamUserv3Service.selectByTeamIds(teamIds);
        //结果集赋值
        teams.forEach(item -> {
            //团队
            List<TeamSystemDTO> teamSystemRest = Lists.newArrayList();
            teamSystems.forEach(teamSystem -> {
                if (item.getTeamId().equals(teamSystem.getTeamId())) {
                    teamSystemRest.add(teamSystem);
                }
            });
            item.setSystemNames(teamSystemRest);
            //团队所有成员
            List<TeamUserDTO> teanUserRest = Lists.newArrayList();
            teanUsers.forEach(teamUser -> {
                if (item.getTeamId().equals(teamUser.getTeamId())) {
                    teanUserRest.add(teamUser);
                }
            });
            item.setTeamUsers(teanUserRest);
        });
        return teams;
    }

    /**
     * 新增团队
     *
     * @param team 团队
     * @return {@link String}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertTeam(STeam team) {
        if (null == team.getTeamId() ) {
            IdGenerator idGenerator = new IdGenerator();
            team.setTeamId(idGenerator.nextId());
        }
        if (sTeamMapper.teamNameNumber(team.getTeamName(), team.getTeamId()) > 0) {
            throw new BusinessException("团队名称已存在");
        }
        List<Long> teamPo = team.getTeamPoS();
        List<Long> teamSm = team.getTeamSmS();
        List<STeamUser> teamUser = team.getTeamUsers();
        List<Long> collect = teamPo.stream().filter(t -> teamSm.contains(t)).collect(Collectors.toList());
        if (collect.size() != 0) {
            throw new BusinessException("团队po,sm重复");
        }
        //插入团队
        sTeamMapper.insertSelective(team);
        //团队绑定系统
        teamSystemMapper.bindingTeamAndSystem(team.getTeamId(), team.getSystemIds());
        //团队绑定po
        List<SsoUser> teamPoUsers = iFacadeSystemApi.queryUserList(teamPo);
        teamPoMapper.bindingTeamAndPo(team.getTeamId(), teamPoUsers);
        //团队绑定sm
        List<SsoUser> teamSmUsers = iFacadeSystemApi.queryUserList(teamSm);
        teamSmMapper.bindingTeamAndSm(team.getTeamId(), teamSmUsers);
        //绑定团队用户
        teamUserMapper.bindingTeamAndUser(team.getTeamId(), teamUser);
        return "新建团队成功";
    }

    /**
     * 删除团队
     *
     * @param teamId 团队id
     * @return {@link String}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteTeam(long teamId) {
        //解除团队系统绑定
        teamSystemMapper.removeBindingTeamAndSystem(teamId);
        //解除团队po绑定
        teamPoMapper.removeBindingTeamAndUser(teamId);
        //解除团队sm绑定
        teamSmMapper.removeBindingTeamAndUser(teamId);
        //解除团队成员绑定
        teamUserMapper.removeBindingTeamAndUser(teamId);
        //逻辑删除团队
        sTeamMapper.updateStateById(teamId, StateEnum.E.getValue());
        return "删除团队成功";
    }

    /**
     * 更新团队
     *
     * @param team 团队
     * @return {@link String}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateTeam(STeam team) {
        Long teamId = team.getTeamId();
        //解除团队系统绑定
        teamSystemMapper.removeBindingTeamAndSystem(teamId);
        //解除团队po绑定
        teamPoMapper.removeBindingTeamAndUser(teamId);
        //解除团队sm绑定
        teamSmMapper.removeBindingTeamAndUser(teamId);
        //解除团队成员绑定
        teamUserMapper.removeBindingTeamAndUser(teamId);

        List<Long> teamPo = team.getTeamPoS();
        List<Long> teamSm = team.getTeamSmS();
        List<STeamUser> teamUser = team.getTeamUsers();
        List<Long> collect = teamPo.stream().filter(t -> teamSm.contains(t)).collect(Collectors.toList());
        if (collect.size() != 0) {
            throw new BusinessException("团队po,sm重复");
        }
        //插入团队
        sTeamMapper.updateByPrimaryKeySelective(team);
        //团队绑定系统
        teamSystemMapper.bindingTeamAndSystem(team.getTeamId(), team.getSystemIds());
        //团队绑定po
        List<SsoUser> teamPoUsers = iFacadeSystemApi.queryUserList(teamPo);
        teamPoMapper.bindingTeamAndPo(team.getTeamId(), teamPoUsers);
        //团队绑定sm
        List<SsoUser> teamSmUsers = iFacadeSystemApi.queryUserList(teamSm);
        teamSmMapper.bindingTeamAndSm(team.getTeamId(), teamSmUsers);
        //绑定团队用户
        teamUserMapper.bindingTeamAndUser(team.getTeamId(), teamUser);
        return "更新团队成功";
    }

    /**
     * 查询团队
     *
     * @param teamId 团队id
     * @return {@link QueryTeamResponse}
     */
    @Override
    public QueryTeamResponse queryTeam(long teamId) {
        STeam teamV2 = sTeamMapper.queryTeam(teamId);
        if (ObjectUtil.isEmpty(teamV2)) {
            throw new BusinessException("暂无该团队信息");
        }
        //构建返回值
        QueryTeamResponse queryTeamResponse = new QueryTeamResponse();
        queryTeamResponse.setSTeam(teamV2);
        List<Long> list = teamSystemMapper.querySystemIdByTeamId(teamId);
        if (list.size() > 0) {
            queryTeamResponse.setSystems(iFacadeSystemApi.querySsoSystem(list));
        }
        //查询po
        queryTeamResponse.setTeamPoS(teamPoMapper.selectByTeamIds(Arrays.asList(teamId)));
        //查询sm
        queryTeamResponse.setTeamSmS(teamSmMapper.selectByTeamIds(Arrays.asList(teamId)));
        //查询成员
        queryTeamResponse.setTeamUsers(teamUserMapper.selectByTeamIds(Arrays.asList(teamId)));
        return queryTeamResponse;
    }

    @Override
    public List<STeam> getTeamLikeNameOrCode(String team) {
        List<STeam> list = sTeamMapper.getTeamLikeNameOrCode(team);
        return list;
    }

}
