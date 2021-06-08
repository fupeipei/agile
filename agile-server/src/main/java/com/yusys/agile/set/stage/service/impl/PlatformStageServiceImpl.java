package com.yusys.agile.set.stage.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.StoryStatusEnum;
import com.yusys.agile.issue.enums.TaskStatusEnum;
import com.yusys.agile.redis.service.RedissonService;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.KanbanStageInstanceExample;
import com.yusys.agile.set.stage.domain.StageInstance;
import com.yusys.agile.set.stage.exception.StageException;
import com.yusys.agile.set.stage.service.IStageService;
import com.yusys.portal.util.code.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *  @Description: 平台级别阶段实现类
 *  @author: zhao_yd
 *  @Date: 2021/5/24 3:31 下午
 *
 */
@Service
public class PlatformStageServiceImpl implements IStageService {

    private static final Logger log = LoggerFactory.getLogger(PlatformStageServiceImpl.class);

    @Resource
    private KanbanStageInstanceMapper kanbanStageInstanceMapper;

    /**
     * 获取平台级别的阶段信息
     * @param stageType 类型 1:epic 2:feature 3:story 4:task
     * @return
     */
    @Override
    public List<StageInstance> getStageList(Integer stageType) {
        List<Long> stageIds = Lists.newArrayList();
        switch (stageType){
            case  1:
                stageIds.add(StageConstant.FirstStageEnum.READY_STAGE.getValue());
                break;
            case 2:
                stageIds.add(StageConstant.FirstStageEnum.ANALYSIS_STAGE.getValue());
                stageIds.add(StageConstant.FirstStageEnum.DESIGN_STAGE.getValue());
                break;
            case 3:
            case 4:
                stageIds.add(StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue());
                break;
        }
        //一级阶段集合
        List<StageInstance> tempStageInstanceList = Lists.newArrayList();
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        kanbanStageInstanceExample.createCriteria()
                .andParentIdEqualTo(StageConstant.PARENT_STAGE_ID)
                .andLevelEqualTo(StageConstant.StageLevelEnum.FIRST_LEVEL_STAGE.getValue())
                .andStateEqualTo(StageConstant.STATE_VALIDATE).andStageIdIn(stageIds);
        kanbanStageInstanceExample.setOrderByClause("order_id asc");
        //一级阶段
        List<KanbanStageInstance> firstStageInstanceList = kanbanStageInstanceMapper.selectByExampleWithBLOBs(kanbanStageInstanceExample);
        if (CollectionUtils.isNotEmpty(firstStageInstanceList)) {
            StageInstance firstStageInstance = null;
            for (KanbanStageInstance kanbanStageInstance : firstStageInstanceList) {
                firstStageInstance = new StageInstance();
                ReflectUtil.copyProperties(kanbanStageInstance, firstStageInstance);
                tempStageInstanceList.add(firstStageInstance);
            }
            //处理一级阶段下二级状态,如果是就绪阶段下面没有二级状态
            if(IssueTypeEnum.TYPE_EPIC.CODE.intValue() !=stageType){
                dealSecondLevelStatusDatas(tempStageInstanceList,stageType);
            }
        }
        return tempStageInstanceList;
    }

    @Override
    public List<StageInstance> getSecondStageListByParentId(Long stageParentId) {
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        kanbanStageInstanceExample.createCriteria()
                .andParentIdNotEqualTo(StageConstant.PARENT_STAGE_ID)
                .andLevelEqualTo(StageConstant.StageLevelEnum.SECOND_LEVEL_STAGE.getValue())
                .andStateEqualTo(StageConstant.STATE_VALIDATE)
                .andParentIdEqualTo(stageParentId);
        kanbanStageInstanceExample.setOrderByClause("order_id asc");
        List<KanbanStageInstance> kanbanStageInstances = kanbanStageInstanceMapper.selectByExampleWithBLOBs(kanbanStageInstanceExample);
        List<StageInstance> stageInstances = Lists.newArrayList();
        try {
            stageInstances = ReflectUtil.copyProperties4List(kanbanStageInstances, StageInstance.class);
        } catch (Exception e) {
            log.info("stageParentId :{} 获取二级阶段异常：{}",stageParentId,e.getMessage());
        }
        return stageInstances;
    }

    @Override
    public KanbanStageInstance getStageInfoByStageId(Long stageId) {
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        kanbanStageInstanceExample.createCriteria()
                .andStateEqualTo(StageConstant.STATE_VALIDATE)
                .andStageIdEqualTo(stageId);
        kanbanStageInstanceExample.setOrderByClause("order_id asc");
        List<KanbanStageInstance> kanbanStageInstances = kanbanStageInstanceMapper.selectByExampleWithBLOBs(kanbanStageInstanceExample);
        if(CollectionUtils.isNotEmpty(kanbanStageInstances)){
            return kanbanStageInstances.get(0);
        }
        return null;
    }


    /**
     * 批量处理二级状态数据
     * @param stageInstanceList
     */
    private void dealSecondLevelStatusDatas(List<StageInstance> stageInstanceList,Integer stageType) {
        for (StageInstance stageInstance : stageInstanceList) {
            Long stageId = stageInstance.getStageId();
            KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
            kanbanStageInstanceExample.createCriteria()
                    .andParentIdNotEqualTo(StageConstant.PARENT_STAGE_ID)
                    .andLevelEqualTo(StageConstant.StageLevelEnum.SECOND_LEVEL_STAGE.getValue())
                    .andStateEqualTo(StageConstant.STATE_VALIDATE)
                    .andParentIdEqualTo(stageId);
            kanbanStageInstanceExample.setOrderByClause("order_id asc");
            List<KanbanStageInstance> kanbanStageInstances = kanbanStageInstanceMapper.selectByExampleWithBLOBs(kanbanStageInstanceExample);
            List<KanbanStageInstance> result = Lists.newArrayList();
            for(KanbanStageInstance kanbanStageInstance:kanbanStageInstances){
                if(IssueTypeEnum.TYPE_STORY.CODE.intValue() == stageType){
                    if(StoryStatusEnum.TYPE_ADD_STATE.CODE == kanbanStageInstance.getStageId() ||
                            StoryStatusEnum.TYPE_MODIFYING_STATE.CODE == kanbanStageInstance.getStageId() ||
                            StoryStatusEnum.TYPE_CLOSED_STATE.CODE == kanbanStageInstance.getStageId()){
                        result.add(kanbanStageInstance);
                        continue;
                    }
                }else if(IssueTypeEnum.TYPE_TASK.CODE.intValue() == stageType){
                    if(TaskStatusEnum.TYPE_ADD_STATE.CODE == kanbanStageInstance.getStageId() ||
                            TaskStatusEnum.TYPE_RECEIVED_STATE.CODE == kanbanStageInstance.getStageId() ||
                            TaskStatusEnum.TYPE_MODIFYING_STATE.CODE == kanbanStageInstance.getStageId()||
                            TaskStatusEnum.TYPE_CLOSED_STATE.CODE == kanbanStageInstance.getStageId()){
                        result.add(kanbanStageInstance);
                        continue;
                    }
                }else {
                    result.add(kanbanStageInstance);
                }
            }
            stageInstance.setSecondStages(result);
        }
    }
}
