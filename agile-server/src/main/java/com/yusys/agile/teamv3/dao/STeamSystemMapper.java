package com.yusys.agile.teamv3.dao;

import com.yusys.agile.team.dto.TeamSystemDTO;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamSystem;
import com.yusys.agile.teamv3.domain.STeamSystemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface STeamSystemMapper {
    long countByExample(STeamSystemExample example);

    int deleteByExample(STeamSystemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(STeamSystem record);

    int insertSelective(STeamSystem record);

    List<STeamSystem> selectByExample(STeamSystemExample example);

    STeamSystem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") STeamSystem record, @Param("example") STeamSystemExample example);

    int updateByExample(@Param("record") STeamSystem record, @Param("example") STeamSystemExample example);

    int updateByPrimaryKeySelective(STeamSystem record);

    int updateByPrimaryKey(STeamSystem record);

    List<TeamSystemDTO> selectByTeamIds(@Param("teamIds") List<Long> teamIds);

    /**
     * 绑定团队和系统
     *  @param team    团队
     * @param systemIds ids系统
     */
    void bindingTeamAndSystem(@Param("team") STeam team, @Param("systemIds") List<Long> systemIds);

    /**
     * 通过团队id查询系统id
     *
     * @param teamId 团队id
     * @return {@link List<Long>}
     */
    List<Long> querySystemIdByTeamId(long teamId);


    List<Long> queryTeamIdBySystemId(@Param("systemIds") List<Long> systemIds);

    void deleteByTeamId(@Param("teamId") Long teamId);
}
