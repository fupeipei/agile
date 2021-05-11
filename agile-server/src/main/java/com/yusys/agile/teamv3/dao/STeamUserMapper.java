package com.yusys.agile.teamv3.dao;

import com.yusys.agile.team.dto.TeamUserDTO;
import com.yusys.agile.teamv3.domain.STeamUser;
import com.yusys.agile.teamv3.domain.STeamUserExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface STeamUserMapper {
    long countByExample(STeamUserExample example);

    int deleteByExample(STeamUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(STeamUser record);

    int insertSelective(STeamUser record);

    List<STeamUser> selectByExample(STeamUserExample example);

    STeamUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") STeamUser record, @Param("example") STeamUserExample example);

    int updateByExample(@Param("record") STeamUser record, @Param("example") STeamUserExample example);

    int updateByPrimaryKeySelective(STeamUser record);

    int updateByPrimaryKey(STeamUser record);

    List<TeamUserDTO> selectByTeamIds(@Param("teamIds") List<Long> teamIds);

    /**
     * 绑定的团队和用户
     *  @param teamId 团队id
     * @param users  用户
     */
    void bindingTeamAndUser(@Param("teamId") Long teamId, @Param("users") List<STeamUser> users);

    /**
     * 删除绑定团队和用户
     *
     * @param teamId 团队id
     */
    void removeBindingTeamAndUser(Long teamId);

}