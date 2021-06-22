package com.yusys.agile.teamv3.service;

import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.response.QueryTeamResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;

import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/8 10:51
 */
public interface Teamv3Service {
    /**
     * 条件查询团队列表
     * @author zhaofeng
     * @date 2021/5/8 11:02
     * @param dto
     * @param security
     * @return java.util.List<com.yusys.agile.team.dto.TeamListDTO>
     */
    List<TeamListDTO> listTeam(TeamQueryDTO dto, SecurityDTO security);

    /**
     * 新增团队
     *
     * @param team 团队
     * @return {@link String}
     */
    void insertTeam(STeam team);

    /**
     * 删除团队
     *
     * @param teamId 团队id
     * @return {@link String}
     */
    void deleteTeam(long teamId);

    /**
     * 更新团队
     *
     * @param team 团队
     * @return {@link String}
     */
    void updateTeam(STeam team);

    /**
     * 查询团队
     *
     * @param teamId 团队id
     * @return {@link String}
     */
    QueryTeamResponse queryTeam(long teamId);

    /**
     * 按团队名称或编号模糊查询
     * @author zhaofeng
     * @date 2021/5/11 20:54
     * @param team
     */
    List<STeam> getTeamLikeNameOrCode(String team);

    /**
     * 通过团队id查询团队下的系统
     * @param teamId 团队id
     * @return
     */
    List<SsoSystemRestDTO> querySystemByTeamId(long teamId);
    /**
     * 新增精益类型团队
     * @author zhaofeng
     * @date 2021/6/11 16:07
     * @param team
     */
    void insertTeamForLean(STeam team);
    /**
     * 更新精益类型团队
     * @author zhaofeng
     * @date 2021/6/11 16:07
     * @param team
     */
    void updateTeamForLean(STeam team);

    STeam getTeamById(long teamId);
    /**
     * 删除精益类型团队
     * @author zhaofeng
     * @date 2021/6/15 13:43
     * @param teamId
     */
    void deleteTeamForLean(Long teamId);
    /**
     * 租户下的团队列表
     * @author wangsh17
     * @date 2021/6/17 11:02
     * @param tenantCode
     * @return java.util.List<com.yusys.agile.team.dto.TeamListDTO>
     */
    List<STeam> listTeamByTenantCode(String tenantCode);

    /**
     *功能描述 查询系统下所有团队
     * @author shenfeng
     * @date 2021/6/16
      * @param dto
     * @param security
     * @return java.util.List<com.yusys.agile.team.dto.TeamListDTO>
     */
    List<TeamListDTO> listAllTeam(TeamQueryDTO dto, SecurityDTO security);

    /**
     *功能描述 根据teamId列表获取团队信息
     * @author shenfeng
     * @date 2021/6/17
      * @param teamIdList
     * @return java.util.List<com.yusys.agile.teamv3.domain.STeam>
     */
    List<STeam> listTeamByIds(List<Long> teamIdList);


    List<TeamListDTO> queryTeams(List<Long> teamIds,String teamName,Integer pageNum, Integer pageSize);


}
