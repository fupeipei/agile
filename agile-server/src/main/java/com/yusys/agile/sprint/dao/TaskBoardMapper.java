package com.yusys.agile.sprint.dao;

import com.yusys.agile.sprint.domain.TaskBoardUnionItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date 2020/4/30
 * @Description 看板故事及任务信息查询
 */
public interface TaskBoardMapper {

    /**
     * @param sprintId     迭代id
     * @param storyKeyWord 故事id或名称
     * @param moduleIds    模块
     * @param taskKeyWord  任务id或名称
     * @param userIds      用户id
     * @param taskTypeIds  任务类型
     * @Date 2020/4/30
     * @Description 看板故事及任务信息查询
     * @Return java.util.List<com.yusys.agile.board.domain.TaskBoardUnionItem>
     */
    List<TaskBoardUnionItem> search(@Param("sprintId") Long sprintId, @Param("storyKeyWord") String storyKeyWord,
                                    @Param("moduleIds") List<Integer> moduleIds, @Param("taskKeyWord") String taskKeyWord,
                                    @Param("userIds") List<Integer> userIds, @Param("taskTypeIds") List<Integer> taskTypeIds);
}
