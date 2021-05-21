package com.yusys.agile.teamv3.dao;

import com.yusys.agile.team.dto.TeamUserDTO;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.agile.teamv3.domain.STeamMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface STeamMemberMapper {
    long countByExample(STeamMemberExample example);

    int deleteByExample(STeamMemberExample example);

    int deleteByPrimaryKey(Long id);

    int insert(STeamMember record);

    int insertSelective(STeamMember record);

    List<STeamMember> selectByExample(STeamMemberExample example);

    STeamMember selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") STeamMember record, @Param("example") STeamMemberExample example);

    int updateByExample(@Param("record") STeamMember record, @Param("example") STeamMemberExample example);

    int updateByPrimaryKeySelective(STeamMember record);

    int updateByPrimaryKey(STeamMember record);

    void batchInsert(@Param("team") STeam team,@Param("users") List<STeamMember> teamUsers,@Param("roleId") int roleId);

    void deleteByTeamId(@Param("teamId") Long teamId);

    List<TeamUserDTO> selectByTeamIdAndRoleId(@Param("teamId") long teamId,@Param("roleId") int roleId);

    List<TeamUserDTO> selectByTeamIds(@Param("teamIds") List<Long> teamIds);
}