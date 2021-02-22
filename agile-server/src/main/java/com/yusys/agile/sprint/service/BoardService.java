package com.yusys.agile.sprint.service;

import com.yusys.agile.sprint.dto.BoardStoryParam;
import com.yusys.agile.issue.dto.IssueDTO;

import java.util.List;

/**
 * @Date 2020/4/30
 */
public interface BoardService {

    /**
     * @param param
     * @Date 2020/4/30
     * @Description 看板搜索故事和任务
     * @Return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> getStoryWithTask(BoardStoryParam param);
}
