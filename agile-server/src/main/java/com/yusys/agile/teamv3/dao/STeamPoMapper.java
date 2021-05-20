package com.yusys.agile.teamv3.dao;

import com.yusys.agile.team.dto.TeamUserDTO;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamPo;
import com.yusys.agile.teamv3.domain.STeamPoExample;
import java.util.List;

import com.yusys.portal.model.facade.entity.SsoUser;
import org.apache.ibatis.annotations.Param;

public interface STeamPoMapper {
    long countByExample(STeamPoExample example);

    int deleteByExample(STeamPoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(STeamPo record);

    int insertSelective(STeamPo record);

    List<STeamPo> selectByExample(STeamPoExample example);

    STeamPo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") STeamPo record, @Param("example") STeamPoExample example);

    int updateByExample(@Param("record") STeamPo record, @Param("example") STeamPoExample example);

    int updateByPrimaryKeySelective(STeamPo record);

    int updateByPrimaryKey(STeamPo record);

    int bindingTeamAndPo(@Param("team") STeam team, @Param("users") List<SsoUser> users);

    List<TeamUserDTO> selectByTeamIds(@Param("teamIds") List<Long> teamIds);

    void removeBindingTeamAndUser(@Param("teamId") long teamId);

    List<Long> queryTeamIdByUserId(@Param("userId") Long userId);
}