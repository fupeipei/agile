package com.yusys.agile.issue.utils;

import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.fault.enums.FaultStatusEnum;
import com.yusys.agile.issue.dao.IssueRuleMapper;
import com.yusys.agile.issue.domain.IssueRule;
import com.yusys.agile.issue.domain.IssueRuleExample;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.TaskStageIdEnum;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.KanbanStageInstanceExample;
import com.yusys.portal.model.common.enums.StateEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName IssueRuleFactory
 * @Description 工作项流转规则封装类
 *
 * @Date 2021/2/9 11:01
 * @Version 1.0
 */
@Component
public class IssueRuleFactory {
    @Autowired
    private KanbanStageInstanceMapper kanbanStageInstanceMapper;

    @Autowired
    private IssueRuleMapper issueRuleMapper;

    /**
     * 初始化业务需求/研发需求/用户故事的流转规则初始化数据
     * @param category  类别IssueTypeEnum.java
     * @param projectId  项目ID
     */
    public void initDemandRules(Byte category, Long projectId){
        List<KanbanStageInstance>  stageInstances = getKanbanStageInstances(projectId);
        List<IssueRule> issueRules = getAllIssueRule(category,projectId);

        List<IssueRule> insertRules = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(stageInstances)){
            for(KanbanStageInstance stageInstance : stageInstances) {
                Long superParentId = stageInstance.getParentId();
                Long fromStageId = stageInstance.getStageId();
                if (superParentId != -1) {
                    continue;
                }

                for(KanbanStageInstance stageInstance2 : stageInstances) {
                    Long parentId = stageInstance2.getParentId();
                    Long toStageId = stageInstance2.getStageId();
                    if (parentId != -1) {
                        continue;
                    }

                    if (CollectionUtils.isNotEmpty(issueRules)) {
                        AtomicReference<Boolean> levelFlag = new AtomicReference<>(false);
                        issueRules.forEach(issueRule -> {
                            Byte subCategory = issueRule.getCategory();
                            Long fromStageIdTarget = issueRule.getFromStageId();
                            Long toStageIdTarget = issueRule.getToStageId();

                            if (category.equals(subCategory) && fromStageId.equals(fromStageIdTarget)
                                    && toStageId.equals(toStageIdTarget)) {
                                levelFlag.set(true);
                            }
                        });
                        if (!levelFlag.get()) {
                            setIssueRules(insertRules, projectId, fromStageId, null, toStageId, null, category);
                        }
                    } else {
                        setIssueRules(insertRules, projectId, fromStageId, null, toStageId, null, category);
                    }
                }
            }
        }
        if(CollectionUtils.isNotEmpty(insertRules)){
            issueRuleMapper.batchInsert(insertRules);
        }
    }
    /**
     * 初始化任务/缺陷的流转规则初始化数据
     * @param category  类别IssueTypeEnum.java
     * @param projectId  项目ID
     */
    public void initTaskAndFaultRules(Byte category, Long projectId){
        //1、获取工作项类型 TASK  FAULT
        //2、根据工作项类型查询已存在的工作项流转列表数据
        List<IssueRule> issueRules = getAllIssueRule(category,projectId);
        //3、获取所有的项目数据
        //3.1 遍历项目
        List<IssueRule> insertRules = new ArrayList<>();
        //3.2 区别工作项类型，遍历枚举
        if(IssueTypeEnum.TYPE_TASK.CODE.equals(category)){
            for(TaskStageIdEnum taskStageIdEnumSource : TaskStageIdEnum.values()){
                Long fromStageId = taskStageIdEnumSource.CODE;
                for(TaskStageIdEnum taskStageIdEnumTarget : TaskStageIdEnum.values()){
                    Long toStageId = taskStageIdEnumTarget.CODE;
                    //3.3 封装需要插入的流转规则数据集合
                    commonInsert(category, insertRules, projectId, issueRules, fromStageId, toStageId);
                }
            }
        }else{
            for(FaultStatusEnum faultStatusEnumSource : FaultStatusEnum.values()){
                Long fromStageId = faultStatusEnumSource.getCode();
                for(FaultStatusEnum faultStatusEnumTarget : FaultStatusEnum.values()){
                    Long toStageId = faultStatusEnumTarget.getCode();
                    commonInsert(category, insertRules, projectId, issueRules, fromStageId, toStageId);
                }
            }
        }

        if(CollectionUtils.isNotEmpty(insertRules)){
            issueRuleMapper.batchInsert(insertRules);
        }
    }
    /**
     * 判断流转规则是否存在，不存在，直接插入，存在，判断阶段ID是否相等，则进行插入
     * @param category
     * @param insertRules
     * @param projectId
     * @param issueRules
     * @param fromStageId
     * @param toStageId
     */
    private void commonInsert(Byte category, List<IssueRule> insertRules, Long projectId, List<IssueRule> issueRules, Long fromStageId, Long toStageId) {
        if(CollectionUtils.isNotEmpty(issueRules)){
            AtomicReference<Boolean> levelFlag = new AtomicReference<>(false);
            issueRules.forEach(issueRule -> {
                Long fromStageIdTarget = issueRule.getFromStageId();
                Long toStageIdTarget = issueRule.getToStageId();
                if(fromStageId.equals(fromStageIdTarget) && toStageId.equals(toStageIdTarget)){
                    levelFlag.set(true);
                }
            });
            if(!levelFlag.get()){
                setIssueRules(insertRules, projectId, fromStageId, null, toStageId, null, category);
            }
        }else{
            setIssueRules(insertRules, projectId, fromStageId, null, toStageId, null, category);
        }
    }

    /**
     * 封装集合中进行赋值
     * @param insertRules
     * @param projectId
     * @param fromStageId
     * @param fromLaneId
     * @param toStageId
     * @param toLaneId
     * @param category
     */
    private void setIssueRules(List<IssueRule> insertRules, Long projectId, Long fromStageId, Long fromLaneId, Long toStageId, Long toLaneId, Byte category) {
        IssueRule issueRule = new IssueRule();
        issueRule.setCategory(category);
        issueRule.setProjectId(projectId);
        issueRule.setFromStageId(fromStageId);
        issueRule.setFromLaneId(fromLaneId);
        issueRule.setToStageId(toStageId);
        issueRule.setToLaneId(toLaneId);
        issueRule.setIdCheck(NumberConstant.ONE.byteValue());
        insertRules.add(issueRule);
    }
    /**
     * 获取所有项目的阶段设置数据，并返回Map<Long,List<KanbanStageInstance>> Long表示projectId
     * @return
     */
    private List<KanbanStageInstance> getKanbanStageInstances(Long projectId){
        KanbanStageInstanceExample instanceExample = new KanbanStageInstanceExample();
        instanceExample.createCriteria().andStateEqualTo(StateEnum.U.toString()).andProjectIdEqualTo(projectId);
        return  kanbanStageInstanceMapper.selectByExample(instanceExample);
    }

    /**
     * 获取所有的工作项流转规则数据，并返回Map<Long,List<IssueRule>> Long表示projectId
     * @return
     */
    private List<IssueRule> getAllIssueRule(Byte category,Long projectId){
        IssueRuleExample ruleExample = new IssueRuleExample();
        IssueRuleExample.Criteria criteria = ruleExample.createCriteria();
        criteria.andCategoryEqualTo(category).andProjectIdEqualTo(projectId);
        return issueRuleMapper.selectByExample(ruleExample);
    }

    /**
     * 添加阶段状态时,设置阶段流转规则
     * @param firstStageId  表示一级阶段stageId
     * @param secondStageId 表示二级阶段laneId
     * @param projectId     项目ID
     */
    public void addStageIdToIssueRule(Long firstStageId, Long secondStageId, Long projectId){
        List<IssueRule> insertRules = new ArrayList<>();
        List<KanbanStageInstance>  stageInstances = getKanbanStageInstances(projectId);
        if(CollectionUtils.isNotEmpty(stageInstances)){
           for(KanbanStageInstance stageInstance : stageInstances){
               Long superParentId = stageInstance.getParentId();
               Long fromStageId = stageInstance.getStageId();
               if (superParentId != -1) {
                   continue;
               }
                for (IssueTypeEnum issueTypeEnum : IssueTypeEnum.values()){
                    Byte category = issueTypeEnum.CODE;
                    /** 排除工作项类型是任务和缺陷的 */
                    if(IssueTypeEnum.TYPE_TASK.CODE.equals(category) || IssueTypeEnum.TYPE_FAULT.CODE.equals(category)){
                        continue;
                    }
                    /** 设置其他阶段流转到本阶段ID的规则 */
                    if(fromStageId.equals(firstStageId)){
                        setIssueRules(insertRules, projectId, fromStageId, null, firstStageId, null, category);
                    }else {
                        setIssueRules(insertRules, projectId, fromStageId, null, firstStageId, null, category);
                        /** 设置本阶段ID流转到其他阶段的规则 */
                        setIssueRules(insertRules, projectId, firstStageId, null,fromStageId, null,  category);
                    }
                }
            }
        }
        if(CollectionUtils.isNotEmpty(insertRules)){
            issueRuleMapper.batchInsert(insertRules);
        }
    }

    /**
     * 删除阶段状态是，删除该阶段流转到其他阶段的流转规则，以及其他阶段流转到该阶段状态的流转规则。
     * @param firstStageId
     * @param secondStageId
     * @param projectId
     */
    public void delStageIdToIssueRule(Long firstStageId, Long secondStageId, Long projectId){
        /** 1、删除该阶段ID流转到其他阶段的规则*/
        IssueRuleExample ruleExample = new IssueRuleExample();
        IssueRuleExample.Criteria criteria = ruleExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId).andFromStageIdEqualTo(firstStageId);
        if(Optional.ofNullable(secondStageId).isPresent()){
            criteria.andFromLaneIdEqualTo(secondStageId);
        }
        issueRuleMapper.deleteByExample(ruleExample);

        /** 1、删除其他阶段流转到该阶段ID的规则*/
        IssueRuleExample ruleExample2 = new IssueRuleExample();
        IssueRuleExample.Criteria criteria2 = ruleExample2.createCriteria();
        criteria2.andProjectIdEqualTo(projectId).andToStageIdEqualTo(firstStageId);
        if(Optional.ofNullable(secondStageId).isPresent()){
            criteria2.andToLaneIdEqualTo(secondStageId);
        }
        issueRuleMapper.deleteByExample(ruleExample2);
    }

    /**
     * 判断流转规则是否允许 是否默认选中,0:否 1:是，1表示允许，返回true
     * @param category      工作项类型
     * @param fromStageId   源一级阶段ID
     * @param fromLaneId    源二级阶段ID
     * @param toStageId     目标一级阶段ID
     * @param toLaneId      目标二级阶段ID
     * @param projectId     项目ID
     * @return
     */
    public Boolean getIssueRulesCheckFlag(Byte category, Long fromStageId,Long fromLaneId, Long toStageId, Long toLaneId, Long projectId){
        Boolean checkFlag = true;
        IssueRuleExample ruleExample = new IssueRuleExample();
        IssueRuleExample.Criteria criteria = ruleExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId).andCategoryEqualTo(category)
                .andFromStageIdEqualTo(fromStageId).andToStageIdEqualTo(toStageId);
        /** 判断二级阶段ID的流转规则
        if(Optional.ofNullable(fromLaneId).isPresent()){
            criteria.andFromLaneIdEqualTo(fromLaneId);
        }
        if(Optional.ofNullable(toLaneId).isPresent()){
            criteria.andToLaneIdEqualTo(toLaneId);
        }*/
        List<IssueRule> issueRules = issueRuleMapper.selectByExample(ruleExample);
        if(CollectionUtils.isNotEmpty(issueRules)){
            IssueRule issueRule = issueRules.get(0);
            Byte idCheck = issueRule.getIdCheck();
            if(Byte.valueOf(NumberConstant.ZERO.toString()).equals(idCheck)){
                checkFlag = false;
            }
        }
        return  checkFlag;
    }
}
