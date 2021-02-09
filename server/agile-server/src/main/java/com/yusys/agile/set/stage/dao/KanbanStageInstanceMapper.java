package com.yusys.agile.set.stage.dao;

import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.KanbanStageInstanceExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @description 看板阶段实例数据访问层
 * @create 2020-04-10 16:28
 */
public interface KanbanStageInstanceMapper {
    long countByExample(KanbanStageInstanceExample example);

    int deleteByExample(KanbanStageInstanceExample example);

    int deleteByPrimaryKey(Long instanceId);

    int insert(KanbanStageInstance record);

    int insertSelective(KanbanStageInstance record);

    List<KanbanStageInstance> selectByExampleWithBLOBs(KanbanStageInstanceExample example);

    List<KanbanStageInstance> selectByExample(KanbanStageInstanceExample example);

    KanbanStageInstance selectByPrimaryKey(Long instanceId);

    int updateByExampleSelective(@Param("record") KanbanStageInstance record, @Param("example") KanbanStageInstanceExample example);

    int updateByExampleWithBLOBs(@Param("record") KanbanStageInstance record, @Param("example") KanbanStageInstanceExample example);

    int updateByExample(@Param("record") KanbanStageInstance record, @Param("example") KanbanStageInstanceExample example);

    int updateByPrimaryKeySelective(KanbanStageInstance record);

    int updateByPrimaryKeyWithBLOBs(KanbanStageInstance record);

    int updateByPrimaryKey(KanbanStageInstance record);

    int updateSelectStatusByPrimaryKeyList(@Param("list") List<Long> list, @Param("status") int status, @Param("updateUid") Long updateUid, @Param("updateTime") Date updateTime);

    int validateSecondStageExists(KanbanStageInstance record);

    Integer selectMaxOrderId();

    Long selectMaxStateId();

    List<KanbanStageInstance> getSecondStageInstances(@Param("projectId") Long projectId, @Param("parentId") Long parentStageId);

    List<KanbanStageInstance> getSecondStageOrderList(@Param("instanceIds") List<Long> instanceIds);

    int updateSecondStageSort(KanbanStageInstance kanbanStageInstance);

    List<KanbanStageInstance> selectBatchSecondStages(@Param("projectId") Long projectId, @Param("list") List<Long> parentStageIds);

    int batchInsert(@Param("list") List<KanbanStageInstance> list);

    int updateStagePopUpInfo(KanbanStageInstance kanbanStageInstance);

    List<KanbanStageInstance> getSecondStagesByOrderId(@Param("oldStageId") Long oldStageId,
                                                       @Param("oldLaneId") Long oldLaneId,
                                                       @Param("projectId") Long projectId);
}