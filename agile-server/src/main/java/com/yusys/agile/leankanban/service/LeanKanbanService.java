package com.yusys.agile.leankanban.service;

import com.yusys.agile.leankanban.dto.SLeanKanbanDTO;

/**
 *  @Description: 敏捷看板服务
 *  @author: zhao_yd
 *  @Date: 2021/6/11 10:14 上午
 *
 */

public interface LeanKanbanService {

    /**
     * 创建精益看板
     *
     * @param teamid
     */
    void createLeanKanban(Long teamid);


    /**
     * 查询看板信息
     *
     * @param teamId
     * @return
     */
    SLeanKanbanDTO queryLeanKanbanInfo(Long teamId);

}
