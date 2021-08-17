package com.yusys.agile.teamv3.dao;

import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamExample;
import com.yusys.agile.teamv3.domain.STeamMember;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

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
     * @param tenantCode 租户code
     * @param teamType 团队类型
     * @return
     */
    int teamNameNumber(@Param("teamId") Long teamId, @Param("teamName") String teamName, @Param("tenantCode") String tenantCode, @Param("teamType") String teamType);

    /**
     * 更新团队数据类型，U：有效数据，E:无效数据
     * @author zhaofeng
     * @date 2021/5/10 10:01
     * @param teamId 主键
     * @param state  数据状态
     */
    void updateStateById(@Param("teamId") long teamId, @Param("state") String state);

    List<STeam> getTeamsByTeamId(Long teamId);


    List<STeam> getTeamLikeNameOrCode(@Param("team") String team);

    /**
     * 查询系统团队
     *
     * @param teamId 团队id
     * @author 张宇
     */
    List<Long> queryTeamSystem(Long teamId);

    /**
     * 查询用户信息,用户id
     *
     * @param sprintId 迭代id
     * @return {@link List<STeamMember>}
     * @author 张宇
     */
    List<STeamMember> querySprintUser(@Param("sprintId") Long sprintId);
//    List<STeamMember> queryUserInfoByUserId(@Param("sprintId") Long sprintId, @Param("teamId") Long teamId);

    /**
     * 通过团队id查询团队的名字
     *
     * @param teamId 团队id
     * @return {@link String}
     * @author 张宇
     */
    String queryTeamNameByTeamId(Long teamId);


    /**
     * 模糊查询迭代下人员
     * @param sprintId
     * @param userName
     * @return
     */
    List<STeamMember>  querySprintVagueUser(@Param("sprintId") Long sprintId,@Param("userName") String userName);


    /**
     * 按ids,teamName模糊分页查团队列表
     * @author zhaofeng
     * @param teamIds
     * @return
     */
    List<TeamListDTO> queryTeams(@Param("teamIds") List<Long> teamIds,@Param("teamName") String teamName);

    /**
     * @return: java.util.List<com.yusys.agile.team.dto.TeamListDTO>
     * @Author wangpf6
     * @Description 根据id返回固定顺序team
     * @Date 15:19 2021/8/17
     * @Param [teamIds, teamName]
     **/
    List<TeamListDTO> queryTeamsByOrderIds(@Param("teamIds") List<Long> teamIds);


}