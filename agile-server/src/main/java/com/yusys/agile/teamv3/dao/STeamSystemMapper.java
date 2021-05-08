package com.yusys.agile.teamv3.dao;

import com.yusys.agile.team.dto.TeamSystemDTO;
import com.yusys.agile.teamv3.domain.STeamSystem;
import com.yusys.agile.teamv3.domain.STeamSystemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
}