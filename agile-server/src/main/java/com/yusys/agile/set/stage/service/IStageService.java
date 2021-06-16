package com.yusys.agile.set.stage.service;

import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.StageInstance;

import java.util.List;

/**
 *  @Description: 阶段service
 *  @author: zhao_yd
 *  @Date: 2021/5/24 3:04 下午
 *
 */

public interface IStageService {


    /**
     * 根据查询类型和团队id查询阶段信息
     *
     * @param stageType
     * @param teamId
     * @return
     */
    List<StageInstance> getStages(Integer stageType,Long teamId) throws Exception;

    /**
     * 敏捷看板根据工作项类型获取阶段信息
     *
     * @param stageType
     * @return
     */
    List<StageInstance> getStageList(Integer stageType);

    /**
     * 敏捷看板根据父阶段id查询二级阶段信息
     *
     * @param stageParentId
     * @return
     */
    List<StageInstance> getSecondStageListByParentId(Long stageParentId);

    /**
     * 敏捷看板根据阶段Id获取阶段信息
     *
     * @param stageId
     * @return
     */
    KanbanStageInstance  getStageInfoByStageId(Long stageId);

    /**
     * 精益看板根据父阶段ID和看板Id获取二级阶段信息
     *
     * @param stageParentId
     * @param kanbanId
     * @return
     */
    List<KanbanStageInstance> getSecondStageList(Long stageParentId,Long kanbanId);



}
