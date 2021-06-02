package com.yusys.agile.teamv3.service;

import com.yusys.agile.teamv3.domain.STeamSystem;

import java.util.List;

/**
 * @author shenfeng
 * @description
 * @date 2021/6/1
 */
public interface STeamSystemService {


    /**
     *功能描述 根据系统查询团队
     * @author shenfeng
     * @date 2021/6/1
      * @param systemId
     * @return java.util.List<com.yusys.agile.teamv3.domain.STeamSystem>
     */
    List<STeamSystem> listTeamBySystem(Long systemId);
}
