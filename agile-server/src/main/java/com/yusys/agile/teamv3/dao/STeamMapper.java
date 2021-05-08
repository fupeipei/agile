package com.yusys.agile.teamv3.dao;

import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamExample;

import java.util.HashMap;
import java.util.List;
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
     * @return {@link TeamV2}
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
     * 条件查询我参与的团队列表
     * @author zhaofeng
     * @param params
     * @return
     */
    List<TeamListDTO> queryMyJoinTeam(@Param("params") HashMap<String, Object> params);
    /**
     * 条件查询我创建的团队列表
     * @author zhaofeng
     * @param params
     * @return
     */
    List<TeamListDTO> queryMyCreatedTeam(@Param("params") HashMap<String, Object> params);
    /**
     * 条件查询与我相关的团队列表
     * @author zhaofeng
     * @param params
     * @return
     */
    List<TeamListDTO> queryMyHiveTeam(@Param("params") HashMap<String, Object> params);

    /**
     * 查询租户下团队，不条件不分页
     * @author zhaofeng
     * @return
     */
    List<TeamListDTO> selectAll();
}