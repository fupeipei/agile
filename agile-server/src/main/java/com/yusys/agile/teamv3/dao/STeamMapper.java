package com.yusys.agile.teamv3.dao;

import com.yusys.agile.team.domain.Team;
import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamExample;

import java.util.HashMap;
import java.util.List;

import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.portal.model.facade.entity.SsoSystem;
import org.apache.ibatis.annotations.Param;

public interface STeamMapper {
    long countByExample(STeamExample example);

    int deleteByExample(STeamExample example);

    int deleteByPrimaryKey(Long teamId);

    int insert(STeam record);

    int insertSelective(STeam record);

    List<STeam> selectByExampleWithBLOBs(STeamExample example);

    List<STeam> selectByExample(STeamExample example);

    STeam selectByPrimaryKey(Long teamId);

    int updateByExampleSelective(@Param("record") STeam record, @Param("example") STeamExample example);

    int updateByExampleWithBLOBs(@Param("record") STeam record, @Param("example") STeamExample example);

    int updateByExample(@Param("record") STeam record, @Param("example") STeamExample example);

    int updateByPrimaryKeySelective(STeam record);

    int updateByPrimaryKeyWithBLOBs(STeam record);

    int updateByPrimaryKey(STeam record);

    /**
     * 查询团队
     *
     * @param teamId 团队id
     * @return {@link STeam}
     */
    STeam queryTeam(long teamId);

    /**
     * 按ids查团队列表
     * @author zhaofeng
     * @param teamIds
     * @return
     */
    List<STeam> listTeamByIds(@Param("teamIds") List<Long> teamIds);

    /**
     * 条件查询与我相关的团队列表
     * @author zhaofeng
     * @param params
     * @return
     */
    List<TeamListDTO> queryMyHiveTeam(@Param("params") HashMap<String, Object> params);
    /**
     * 条件查询租户下所有team
     * @author zhaofeng
     * @date 2021/5/8 14:39
     * @param params
     * @return
     */
    List<TeamListDTO> queryAllTeam(@Param("params") HashMap<String, Object> params);

    /**
     * 查询租户下团队，不条件不分页
     * @author zhaofeng
     * @return
     */
    List<TeamListDTO> selectAll();

    /**
     * 团队名称数量
     *
     *
     * @param teamId
     * @param teamName 团队的名字
     * @return int
     */
    int teamNameNumber(@Param("teamId") Long teamId, @Param("teamName") String teamName, @Param("tenantCode") String tenantCode);

    /**
     * 更新团队数据类型，U：有效数据，E:无效数据
     * @author zhaofeng
     * @date 2021/5/10 10:01
     * @param teamId 主键
     * @param state  数据状态
     */
    void updateStateById(@Param("teamId") long teamId, @Param("state") String state);

    List<Team> getTeamsByTeamId(Long teamId);


    List<STeam> getTeamLikeNameOrCode(@Param("team") String team);

    /**
     * 查询系统团队
     *
     * @param teamId 团队id
     * @return {@link List<SsoSystem>}
     */
    List<Long> queryTeamSystem(Long teamId);

    /**
     * 查询用户信息,用户id
     *
     * @param sprintId 迭代id
     * @param teamId   团队id
     * @return {@link List<STeamMember>}
     */
    List<STeamMember> queryUserInfoByUserId(@Param("sprintId") Long sprintId, @Param("teamId") Long teamId);

    /**
     * 通过团队id查询团队的名字
     *
     * @param teamId 团队id
     * @return {@link String}
     */
    String queryTeamNameByTeamId(Long teamId);
}