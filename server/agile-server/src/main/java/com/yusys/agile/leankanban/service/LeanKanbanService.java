package com.yusys.agile.leankanban.service;

import com.yusys.agile.leankanban.dto.IssueResultDTO;
import com.yusys.agile.leankanban.dto.PageResultDTO;
import com.yusys.agile.set.stage.dto.KanbanStageInstanceDTO;
import com.yusys.agile.utils.page.PageQuery;

import java.util.List;
import java.util.Map;

/**
 *    赵英东
 */
public interface LeanKanbanService {


    /**
     * 查询看板信息
     * @param projectId
     * @param queries
     * @return
     */
    List<KanbanStageInstanceDTO> selectKanbanStageInstanceDTOList(Long projectId,List<PageQuery<Long>> queries);

    /**
     * 移动看板卡片
     * @param issueId
     * @param toStageId
     * @return
     */
    int moveIssue(Long issueId,Long toStageId,Long toLaneId);


    /**
     *   : zhaoyd6
     * @Date: 2020/5/19
     * @Description: 获取精益看板视图
     * @Param: [projectId, queries,selectType]
     * @Return: com.yusys.agile.leankanban.dto.IssueResultDTO
     *
     */
    List<IssueResultDTO> selectIssueViewInfo(Long projectId, List<PageResultDTO> pageResultDTOList, Map<String,Object> map) throws Exception;


}
