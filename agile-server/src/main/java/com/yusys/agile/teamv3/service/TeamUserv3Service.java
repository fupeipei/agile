package com.yusys.agile.teamv3.service;

import com.yusys.agile.team.dto.TeanUserDTO;

import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/8 10:52
 */
public interface TeamUserv3Service {
    /*
     * 按teamids查询
     * @author zhaofeng
     * @date 2021/5/8 11:20
     * @param teamIds
     * @return java.util.List<com.yusys.agile.team.dto.TeanUserDTO>
     */
    List<TeanUserDTO> selectByTeamIds(List<Long> teamIds);
}
