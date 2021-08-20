package com.yusys.agile.set.stage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.StoryStatusEnum;
import com.yusys.agile.issue.enums.TaskStatusEnum;
import com.yusys.agile.issue.enums.TaskTypeEnum;
import com.yusys.agile.leankanban.domain.SLeanKanban;
import com.yusys.agile.leankanban.dto.SLeanKanbanDTO;
import com.yusys.agile.leankanban.enums.LaneKanbanStageConstant;
import com.yusys.agile.leankanban.service.LeanKanbanService;
import com.yusys.agile.redis.service.RedissonService;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.KanbanStageInstanceExample;
import com.yusys.agile.set.stage.domain.StageInstance;
import com.yusys.agile.set.stage.dto.KanbanStageInstanceDTO;
import com.yusys.agile.set.stage.enums.StageTypeEnum;
import com.yusys.agile.set.stage.exception.StageException;
import com.yusys.agile.set.stage.service.IStageService;
import com.yusys.portal.util.code.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *  @Description: 平台级别阶段实现类
 *  @author: zhao_yd
 *  @Date: 2021/5/24 3:31 下午
 *
 */
@Service
public class PlatformStageServiceImpl implements IStageService {

    private static final Logger log = LoggerFactory.getLogger(PlatformStageServiceImpl.class);
    private static final String ORDER_ID_ASC = "order_id asc";



    @Resource
    private KanbanStageInstanceMapper kanbanStageInstanceMapper;
    @Resource
    private LeanKanbanService leanKanbanService;


    /**
     * 精益敏捷阶段查询
     *
     * @param stageType 类型 1:epic 2:feature 3:story 4:task
     * @param teamId 非必填,查询精益阶段要传
     * @return
     */
    @Override
    public List<StageInstance> getStages(Integer stageType, Long teamId,Integer taskType) throws Exception {
        List<StageInstance> result = Lists.newArrayList();
        switch (stageType){
            case 1:
                result = getStageList(stageType);
                break;
            case 2:
            case 3:
            case 4:
                result = getStagesByTeamId(stageType,teamId,taskType);
                break;
            default:
                break;
        }
        return result;
    }



   private List<StageInstance> getStagesByTeamId(Integer stageType,Long teamId,Integer taskType) throws Exception {
        SLeanKanbanDTO sLeanKanban = null;
        if(Optional.ofNullable(teamId).isPresent()){
            sLeanKanban = leanKanbanService.queryLeanKanbanInfo(teamId);
        }

        if(Optional.ofNullable(sLeanKanban).isPresent()){
            List<KanbanStageInstanceDTO> kanbanStageInstances = sLeanKanban.getKanbanStageInstances();
            /**如果是故事 阶段：开发阶段-未开始、开发阶段-进行中、开发阶段-已完成，测试阶段-测试中、测试阶段-测试完成*/
            if(IssueTypeEnum.TYPE_STORY.CODE.intValue() == stageType){
                List<KanbanStageInstanceDTO> result = kanbanStageInstances.stream().filter(k ->
                        StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue().equals(k.getStageId()) ||
                                StageConstant.FirstStageEnum.TEST_STAGE.getValue().equals(k.getStageId())).collect(Collectors.toList());
                //过滤二级状态信息
                if(CollectionUtils.isNotEmpty(result)){
                    for(KanbanStageInstanceDTO instanceDTO : result){
                        List<KanbanStageInstanceDTO> secondStages = instanceDTO.getSecondStages();
                        if(StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue().equals(instanceDTO.getStageId())){
                            List<KanbanStageInstanceDTO> secondStageResults = secondStages.stream().filter(k ->
                                    IssueTypeEnum.TYPE_STORY.CODE.equals(k.getAppType())).collect(Collectors.toList());
                            String s = JSON.toJSONString(secondStageResults);
                            log.info("故事阶段过滤后数据为:{}",s);

                            instanceDTO.setSecondStages(secondStageResults);
                        }
                    }
                }
                List<StageInstance> stageInstances = ReflectUtil.copyProperties4List(result, StageInstance.class);
                return stageInstances;

                /**测试类任务，展示测试阶段进行中、已完成。否则 开发阶段进行中已完成*/
            }else if(IssueTypeEnum.TYPE_TASK.CODE.intValue() == stageType){

                //如果任务类型为null返回所有的任务状态
                if(!Optional.ofNullable(taskType).isPresent()){

                    List<KanbanStageInstanceDTO> result = kanbanStageInstances.stream().filter(k ->
                            StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue().equals(k.getStageId())||
                                    StageConstant.FirstStageEnum.TEST_STAGE.getValue().equals(k.getStageId()) ).collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(result)){
                        for(KanbanStageInstanceDTO instanceDTO : result){
                            List<KanbanStageInstanceDTO> secondStages = instanceDTO.getSecondStages();
                            List<KanbanStageInstanceDTO> secondStageResults = secondStages.stream().filter(k ->
                                    IssueTypeEnum.TYPE_TASK.CODE.equals(k.getAppType())).collect(Collectors.toList());
                            instanceDTO.setSecondStages(secondStageResults);

                        }
                    }
                    List<StageInstance> stageInstances = ReflectUtil.copyProperties4List(result, StageInstance.class);
                    return stageInstances;
                }

                if(TaskTypeEnum.TEST.CODE.equals(taskType)){
                    List<KanbanStageInstanceDTO> result = kanbanStageInstances.stream().filter(k ->
                            StageConstant.FirstStageEnum.TEST_STAGE.getValue().equals(k.getStageId())).collect(Collectors.toList());
                    List<StageInstance> stageInstances = ReflectUtil.copyProperties4List(result, StageInstance.class);
                    return stageInstances;
                }

                List<KanbanStageInstanceDTO> result = kanbanStageInstances.stream().filter(k ->
                        StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue().equals(k.getStageId())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(result)){
                    for(KanbanStageInstanceDTO instanceDTO : result){
                        List<KanbanStageInstanceDTO> secondStages = instanceDTO.getSecondStages();
                        List<KanbanStageInstanceDTO> secondStageResults = secondStages.stream().filter(k ->
                                IssueTypeEnum.TYPE_TASK.CODE.equals(k.getAppType())).collect(Collectors.toList());
                        String s = JSON.toJSONString(secondStageResults);
                        log.info("任务阶段过滤后的数据为：{}",s);
                        instanceDTO.setSecondStages(secondStageResults);

                    }
                }
                List<StageInstance> stageInstances = ReflectUtil.copyProperties4List(result, StageInstance.class);
                return stageInstances;

                /**feature中状态展示一级阶段中除去开发阶段的 开发中、开发完成状态的所有阶段*/
            }else if(IssueTypeEnum.TYPE_FEATURE.CODE.intValue() == stageType){

                if(CollectionUtils.isNotEmpty(kanbanStageInstances)){
                    //过滤二级状态信息
                    for(KanbanStageInstanceDTO kanbanStageInstanceDTO:kanbanStageInstances){

                        if(StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue().equals(kanbanStageInstanceDTO.getStageId())){
                            List<KanbanStageInstanceDTO> secondStages = kanbanStageInstanceDTO.getSecondStages();
                            if(CollectionUtils.isNotEmpty(secondStages)){
                                List<KanbanStageInstanceDTO> secondStageResults = secondStages.stream().filter(k ->
                                        IssueTypeEnum.TYPE_STORY.CODE.equals(k.getAppType()) ).collect(Collectors.toList());
                                kanbanStageInstanceDTO.setSecondStages(secondStageResults);
                            }
                        }
                    }
                }
                List<StageInstance> stageInstances = ReflectUtil.copyProperties4List(kanbanStageInstances, StageInstance.class);
                return stageInstances;
            }
        }
        List<StageInstance> stageList = getStageList(stageType);
        return stageList;
   }

    /**
     * 敏捷看板获取平台级别的阶段信息
     *
     * @param stageType 类型 1:epic 2:feature 3:story 4:task
     * @return
     */
    @Override
    public List<StageInstance> getStageList(Integer stageType) {
        List<Long> stageIds = Lists.newArrayList();
        switch (stageType){
            case 3:
                stageIds.add(StageConstant.FirstStageEnum.TEST_STAGE.getValue());
                stageIds.add(StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue());
                break;
            case 4:
                stageIds.add(StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue());
                break;
            default:
                break;
        }
        //一级阶段集合
        List<StageInstance> tempStageInstanceList = Lists.newArrayList();
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        KanbanStageInstanceExample.Criteria criteria = kanbanStageInstanceExample.createCriteria()
                .andParentIdEqualTo(StageConstant.PARENT_STAGE_ID)
                .andLevelEqualTo(StageConstant.StageLevelEnum.FIRST_LEVEL_STAGE.getValue())
                .andStateEqualTo(StageConstant.STATE_VALIDATE)
                .andStageTypeEqualTo(StageTypeEnum.AGILE.CODE);
        if(CollectionUtils.isNotEmpty(stageIds)){
            criteria.andStageIdIn(stageIds);
        }
        kanbanStageInstanceExample.setOrderByClause(ORDER_ID_ASC);
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

    /**
     * 敏捷看板根据一级阶段获取二级阶段集合
     *
     * @param stageParentId
     * @return
     */
    @Override
    public List<StageInstance> getSecondStageListByParentId(Long stageParentId) {
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        kanbanStageInstanceExample.createCriteria()
                .andParentIdNotEqualTo(StageConstant.PARENT_STAGE_ID)
                .andLevelEqualTo(StageConstant.StageLevelEnum.SECOND_LEVEL_STAGE.getValue())
                .andStateEqualTo(StageConstant.STATE_VALIDATE)
                .andParentIdEqualTo(stageParentId)
                .andStageTypeEqualTo(StageTypeEnum.AGILE.CODE);
        kanbanStageInstanceExample.setOrderByClause(ORDER_ID_ASC);
        List<KanbanStageInstance> kanbanStageInstances = kanbanStageInstanceMapper.selectByExampleWithBLOBs(kanbanStageInstanceExample);
        List<StageInstance> stageInstances = Lists.newArrayList();
        try {
            stageInstances = ReflectUtil.copyProperties4List(kanbanStageInstances, StageInstance.class);
        } catch (Exception e) {
            log.info("stageParentId :{} 获取二级阶段异常：{}",stageParentId,e.getMessage());
        }
        return stageInstances;
    }

    /**
     * 敏捷看板根据阶段Id获取阶段实例信息
     *
     * @param stageId
     * @return
     */
    @Override
    public KanbanStageInstance getStageInfoByStageId(Long stageId) {
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        kanbanStageInstanceExample.createCriteria()
                .andStateEqualTo(StageConstant.STATE_VALIDATE)
                .andStageIdEqualTo(stageId)
                .andStageTypeEqualTo(StageTypeEnum.AGILE.CODE);
        kanbanStageInstanceExample.setOrderByClause(ORDER_ID_ASC);
        List<KanbanStageInstance> kanbanStageInstances = kanbanStageInstanceMapper.selectByExampleWithBLOBs(kanbanStageInstanceExample);
        if(CollectionUtils.isNotEmpty(kanbanStageInstances)){
            return kanbanStageInstances.get(0);
        }
        return null;
    }


    /**
     * 敏捷看板批量处理二级状态数据
     *
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
                    .andStageTypeEqualTo(StageTypeEnum.AGILE.CODE)
                    .andParentIdEqualTo(stageId);
            kanbanStageInstanceExample.setOrderByClause(ORDER_ID_ASC);
            List<KanbanStageInstance> kanbanStageInstances = kanbanStageInstanceMapper.selectByExampleWithBLOBs(kanbanStageInstanceExample);
            List<KanbanStageInstance> result = Lists.newArrayList();

            for(KanbanStageInstance kanbanStageInstance:kanbanStageInstances){
                //如果查询类型为story的并且阶段为 开发中 或者查询类型为feature 并且 阶段为开发中
                if((IssueTypeEnum.TYPE_STORY.CODE.intValue() == stageType
                        && StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue().equals(kanbanStageInstance.getParentId())) ||
                        (StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue().equals(kanbanStageInstance.getParentId())
                                && IssueTypeEnum.TYPE_FEATURE.CODE.intValue() == stageType)){
                    if(IssueTypeEnum.TYPE_STORY.CODE.equals(kanbanStageInstance.getAppType())){
                        result.add(kanbanStageInstance);
                        continue;
                    }

                    //如果查询类型为 task 并且 为开发阶段
                }else if(IssueTypeEnum.TYPE_TASK.CODE.intValue() == stageType &&
                        StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue().equals(kanbanStageInstance.getParentId())){
                    if(IssueTypeEnum.TYPE_TASK.CODE.equals(kanbanStageInstance.getAppType())){
                        result.add(kanbanStageInstance);
                        continue;
                    }
                }else {
                    result.add(kanbanStageInstance);
                }
            }
            stageInstance.setSecondStages(result.isEmpty() ? null : result);
        }
    }


    /**
     * 精益看板查询二级阶段信息
     *
     * @param stageParentId
     * @param kanbanId
     * @return
     */
    @Override
    public List<KanbanStageInstance> getSecondStageList(Long stageParentId, Long kanbanId) {
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        kanbanStageInstanceExample.createCriteria()
                .andStateEqualTo(StageConstant.STATE_VALIDATE)
                .andParentIdEqualTo(stageParentId)
                .andKanbanIdEqualTo(kanbanId)
                .andStageTypeEqualTo(StageTypeEnum.LEAN.CODE);
        kanbanStageInstanceExample.setOrderByClause(ORDER_ID_ASC);
        List<KanbanStageInstance> kanbanStageInstances = kanbanStageInstanceMapper.selectByExampleWithBLOBs(kanbanStageInstanceExample);
        return kanbanStageInstances;
    }

    @Override
    public Map<Long, String> getStageMapByTeamId(Long teamId) {
        Map<Long,String> map = new HashMap<>();
        log.info("查询阶段信息入参数 teamId ：{}",teamId);
        SLeanKanbanDTO sLeanKanban = leanKanbanService.queryLeanKanbanInfo(teamId);
        if(Optional.ofNullable(sLeanKanban).isPresent()){
            List<KanbanStageInstanceDTO> kanbanStageInstances = sLeanKanban.getKanbanStageInstances();
            if(CollectionUtils.isNotEmpty(kanbanStageInstances)){
                for(KanbanStageInstanceDTO kanbanStageInstanceDTO:kanbanStageInstances){
                    map.put(kanbanStageInstanceDTO.getStageId(),kanbanStageInstanceDTO.getStageName());
                    List<KanbanStageInstanceDTO> secondStages = kanbanStageInstanceDTO.getSecondStages();
                    if(CollectionUtils.isNotEmpty(secondStages)){
                        for(KanbanStageInstanceDTO secondStage:secondStages){
                            map.put(secondStage.getStageId(),secondStage.getStageName());
                        }
                    }
                }
            }
        }else {
            KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
            kanbanStageInstanceExample.createCriteria()
                    .andStageTypeEqualTo(StageTypeEnum.AGILE.CODE)
                    .andStateEqualTo(StageConstant.STATE_VALIDATE);

            List<KanbanStageInstance> kanbanStageInstances = kanbanStageInstanceMapper.selectByExample(kanbanStageInstanceExample);
            if(CollectionUtils.isNotEmpty(kanbanStageInstances)){
                for(KanbanStageInstance kanbanStageInstance:kanbanStageInstances){
                    map.put(kanbanStageInstance.getStageId(),kanbanStageInstance.getStageName());
                }

            }
        }
        String s = JSON.toJSONString(map);
        log.info("查询阶段信息返回数据 map:{}",s );
        return map;
    }


}
