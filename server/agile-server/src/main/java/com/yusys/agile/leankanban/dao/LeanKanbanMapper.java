package com.yusys.agile.leankanban.dao;

import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.utils.page.PageQuery;

import java.util.List;

/**
 * 赵英东
 */
public interface LeanKanbanMapper {

    /**
     * 根据条件查询泳道卡片，分页展示
     *
     * @param query
     * @return
     */
    List<Issue> selectByLeanState(PageQuery<Long> query);

}
