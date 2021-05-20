package com.yusys.agile.teamv3.dao;

import com.yusys.agile.team.dto.TeamUserDTO;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamSm;
import com.yusys.agile.teamv3.domain.STeamSmExample;
import java.util.List;

import com.yusys.portal.model.facade.entity.SsoUser;
import org.apache.ibatis.annotations.Param;

public interface STeamSmMapper {
    long countByExample(STeamSmExample example);

    int deleteByExample(STeamSmExample example);

    int deleteByPrimaryKey(Long id);

    int insert(STeamSm record);

    int insertSelective(STeamSm record);

    List<STeamSm> selectByExample(STeamSmExample example);

    STeamSm selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") STeamSm record, @Param("example") STeamSmExample example);

    int updateByExample(@Param("record") STeamSm record, @Param("example") STeamSmExample example);

    int updateByPrimaryKeySelective(STeamSm record);

    int updateByPrimaryKey(STeamSm record);

    int bindingTeamAndSm(@Param("team") STeam team, @Param("users") List<SsoUser> users);

    List<TeamUserDTO> selectByTeamIds(@Param("teamIds") List<Long> teamIds);

    void removeBindingTeamAndUser(@Param("teamId") long teamId);

    List<Long> queryTeamIdByUserId(@Param("userId") Long userId);
}