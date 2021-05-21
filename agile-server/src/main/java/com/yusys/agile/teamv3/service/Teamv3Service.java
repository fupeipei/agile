package com.yusys.agile.teamv3.service;

import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.response.QueryTeamResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/8 10:51
 */
public interface Teamv3Service {
    /**
     * 条件查询团队列表
     * @author zhaofeng
     * @date 2021/5/8 11:02
     * @param dto
     * @param security
     * @return java.util.List<com.yusys.agile.team.dto.TeamListDTO>
     */
    List<TeamListDTO> listTeam(TeamQueryDTO dto, SecurityDTO security);

    /**
     * 新增团队
     *
     * @param team 团队
     * @return {@link String}
     */
    void insertTeam(STeam team);

    /**
     * 删除团队
     *
     * @param teamId 团队id
     * @return {@link String}
     */
    void deleteTeam(long teamId);

    /**
     * 更新团队
     *
     * @param team 团队
     * @return {@link String}
     */
    void updateTeam(STeam team);

    /**
     * 查询团队
     *
     * @param teamId 团队id
     * @return {@link String}
     */
    QueryTeamResponse queryTeam(long teamId);

    /**
     * 按团队名称或编号模糊查询
     * @author zhaofeng
     * @date 2021/5/11 20:54
     * @param team
     */
    List<STeam> getTeamLikeNameOrCode(String team);
}
