package com.yusys.agile.team.dao;

import com.yusys.agile.team.domain.Team;
import com.yusys.agile.team.domain.TeamExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TeamMapper {
    long countByExample(TeamExample example);

    int deleteByExample(TeamExample example);

    int deleteByPrimaryKey(Long teamId);

    int insert(Team record);

    int insertSelective(Team record);

    List<Team> selectByExampleWithBLOBs(TeamExample example);

    List<Team> selectByExample(TeamExample example);

    Team selectByPrimaryKey(Long teamId);

    int updateByExampleSelective(@Param("record") Team record, @Param("example") TeamExample example);

    int updateByExampleWithBLOBs(@Param("record") Team record, @Param("example") TeamExample example);

    int updateByExample(@Param("record") Team record, @Param("example") TeamExample example);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKeyWithBLOBs(Team record);

    int updateByPrimaryKey(Team record);

    int updateTeamState(Long teamId);

    List<Team> getTeams4Project(Long projectId);
}