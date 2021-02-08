package com.yusys.agile.set.stage.service;

import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.StageInstance;
import com.yusys.agile.set.stage.dto.KanbanStageInstanceDTO;

import java.util.List;

/**
 * @description 阶段业务接口
 *  
 * @create 2020-04-10 16:28
 */
public interface StageService {

    public List<StageInstance> getStageList(Long projectId);

    public List<StageInstance> getFirstStageFirstLane(Long projectId);

    public KanbanStageInstance getStageInfo(Long projectId, Long stateId);

    public  List<KanbanStageInstance> getAllStageInfo(Long projectId);

    public int configFirstStages(List<KanbanStageInstanceDTO> stageInstanceDTOList);

    public int addSecondStage(Long projectId, KanbanStageInstanceDTO kanbanStageInstanceDTO);

    public int modifySecondStage(Long projectId, KanbanStageInstanceDTO kanbanStageInstanceDTO);

    public int deleteSecondStage(Long projectId, KanbanStageInstanceDTO kanbanStageInstanceDTO);

    public int sortSecondStage(Long projectId, List<Long> instanceIds);

    public int initKanbanStageList(List<KanbanStageInstanceDTO> kanbanStageInstanceDTOList);

    public int addStagePopUpInfos(Long projectId, KanbanStageInstanceDTO kanbanStageInstanceDTO);

    public int modifyStagePopUpInfos(KanbanStageInstanceDTO kanbanStageInstanceDTO);

    public int modifyFirstStageHandler(Long instanceId, Long handler);

    /**
     * 功能描述: 根据项目id和一阶段id、二阶段名查询二阶段信息
     *
     * @date 2020/10/28
     * @param projectId
     * @param firstStageId
     * @return java.util.List<com.yusys.agile.set.stage.domain.KanbanStageInstance>
     */
    Long getSecondStageIdByFirstStageIdAndSecondName(Long projectId,Long firstStageId,String secondName);
}
