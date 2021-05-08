package com.yusys.agile.teamv3.service;

import com.yusys.agile.team.dto.TeamSystemDTO;

import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/8 10:52
 */
public interface TeamSystemv3Service {
    /*
     * 按teamids查询
     * @author zhaofeng
     * @date 2021/5/8 11:21
     * @param teamIds
     * @return java.util.List<com.yusys.agile.team.dto.TeamSystemDTO>
     */
    List<TeamSystemDTO> selectByTeamIds(List<Long> teamIds);
}
