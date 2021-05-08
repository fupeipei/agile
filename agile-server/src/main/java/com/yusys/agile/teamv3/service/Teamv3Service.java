package com.yusys.agile.teamv3.service;

import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
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
     * 查询所有
     * @author zhaofeng
     * @date 2021/5/8 11:03
     * @return java.util.List<com.yusys.agile.team.dto.TeamListDTO>
     */
    List<TeamListDTO> list();
}
