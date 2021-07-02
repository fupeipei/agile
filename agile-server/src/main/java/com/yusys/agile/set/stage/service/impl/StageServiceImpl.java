package com.yusys.agile.set.stage.service.impl;

import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.utils.IssueRuleFactory;
import com.yusys.agile.redis.constant.RedisCacheKeyConstant;
import com.yusys.agile.redis.service.RedissonService;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.KanbanStageInstanceExample;
import com.yusys.agile.set.stage.domain.StageInstance;
import com.yusys.agile.set.stage.dto.KanbanStageInstanceDTO;
import com.yusys.agile.set.stage.exception.StageException;
import com.yusys.agile.set.stage.service.StageService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description 阶段业务实现类
 * @create 2020-04-10 16:28
 */
@Service
public class StageServiceImpl implements StageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StageServiceImpl.class);

    public static final int PER_PAGE_SIZE = 200;

    @Autowired
    private RedissonService redissonService;

    @Resource
    private KanbanStageInstanceMapper kanbanStageInstanceMapper;

    @Resource
    private IssueMapper issueMapper;

    @Autowired
    private IssueRuleFactory ruleFactory;


    /**
     * @param projectId
     * @return java.util.List
     * @description 根据项目id查询阶段列表
     */
    @Override
    public List<StageInstance> getStageList(Long projectId) {
        if (projectId == null) {
            throw new StageException("项目编号不能为空");
        }
        //阶段集合
        List<StageInstance> stageInstances = Lists.newArrayList();
        //一级阶段集合
        List<StageInstance> tempStageInstanceList = Lists.newArrayList();
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        kanbanStageInstanceExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andParentIdEqualTo(StageConstant.PARENT_STAGE_ID)
                .andLevelEqualTo(StageConstant.StageLevelEnum.FIRST_LEVEL_STAGE.getValue())
                .andStateEqualTo(StageConstant.STATE_VALIDATE);
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
            //处理一级阶段下二级状态
            dealSecondLevelStatusDatas(projectId, tempStageInstanceList);
        }
        stageInstances.addAll(tempStageInstanceList);
        return stageInstances;
    }

    /**
     * 批量处理二级状态数据
     *
     * @param projectId
     * @param stageInstanceList
     */
    private void dealSecondLevelStatusDatas(Long projectId, List<StageInstance> stageInstanceList) {
        List<Long> firstStageIdList = Lists.newArrayList();
        for (StageInstance stageInstance : stageInstanceList) {
            firstStageIdList.add(stageInstance.getStageId());
        }
        List<KanbanStageInstance> kanbanStageInstanceList = null;
        List<KanbanStageInstance> secondStageList = kanbanStageInstanceMapper.selectBatchSecondStages(projectId, firstStageIdList);
        if (CollectionUtils.isNotEmpty(secondStageList)) {
            //二级状态集合
            Map<Long, List<KanbanStageInstance>> map = Maps.newHashMap();
            for (KanbanStageInstance kanbanStageInstance : secondStageList) {
                Long parentId = kanbanStageInstance.getParentId();
                if (MapUtils.isNotEmpty(map)) {
                    if (map.containsKey(parentId)) {
                        List<KanbanStageInstance> tempList = map.get(parentId);
                        tempList.add(kanbanStageInstance);
                    } else {
                        kanbanStageInstanceList = Lists.newArrayList();
                        kanbanStageInstanceList.add(kanbanStageInstance);
                        map.put(parentId, kanbanStageInstanceList);
                    }
                } else {
                    kanbanStageInstanceList = Lists.newArrayList();
                    kanbanStageInstanceList.add(kanbanStageInstance);
                    map.put(parentId, kanbanStageInstanceList);
                }
            }
            for (StageInstance stageInstance : stageInstanceList) {
                Long stageId = stageInstance.getStageId();
                List<KanbanStageInstance> secondStageInstanceList = map.get(stageId);
                //取消勾选 0:可以 1:不可以
                byte enableCancel = 1;
                if (CollectionUtils.isNotEmpty(secondStageInstanceList)) {
                    for (KanbanStageInstance secondStageInstance : secondStageInstanceList) {
                        projectId = secondStageInstance.getProjectId();
                        Long laneId = secondStageInstance.getStageId();
                        IssueExample issueExample = new IssueExample();
                        issueExample.createCriteria()
                                .andProjectIdEqualTo(projectId)
                                .andStageIdEqualTo(stageId)
                                .andLaneIdEqualTo(laneId)
                                .andStateEqualTo(StageConstant.STATE_VALIDATE);
                        List<Issue> issues = issueMapper.selectByExample(issueExample);
                        if (CollectionUtils.isNotEmpty(issues)) {
                            enableCancel = 1;
                            break;
                        } else {
                            enableCancel = 0;
                        }
                    }
                } else {
                    enableCancel = 0;
                }
                stageInstance.setEnableCancel(enableCancel);
                stageInstance.setSecondStages(secondStageInstanceList);
            }
        }
    }

    /**
     * @param projectId
     * @return java.util.List
     * @description 根据项目id查询第一个阶段及该阶段下第一个泳道
     */
    @Override
    public List<StageInstance> getFirstStageFirstLane(Long projectId) {
        if (projectId == null) {
            throw new StageException("项目id不能为空");
        }
        //阶段实例
        List<StageInstance> stageInstances = Lists.newArrayList();
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        kanbanStageInstanceExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andLevelEqualTo(StageConstant.StageLevelEnum.FIRST_LEVEL_STAGE.getValue())
                .andStateEqualTo(StageConstant.STATE_VALIDATE);
        kanbanStageInstanceExample.setOrderByClause("order_id asc");
        //一级阶段
        List<KanbanStageInstance> firstStageInstanceList = kanbanStageInstanceMapper.selectByExampleWithBLOBs(kanbanStageInstanceExample);
        if (CollectionUtils.isNotEmpty(firstStageInstanceList)) {
            KanbanStageInstance firstStageInstance = firstStageInstanceList.get(0);
            StageInstance stageInstance = new StageInstance();
            ReflectUtil.copyProperties(firstStageInstance, stageInstance);
            //根据一级阶段查询二级状态下第一个泳道
            dealSecondStageFirstInstance(stageInstance);
            stageInstances.add(stageInstance);
        }
        return stageInstances;
    }

    /**
     * @param stageInstance 一级阶段
     * @description 处理二级状态下第一个泳道
     */
    private void dealSecondStageFirstInstance(StageInstance stageInstance) {
        Long parentStateId = stageInstance.getStageId();
        KanbanStageInstanceExample secondKanbanStageInstanceExample = new KanbanStageInstanceExample();
        secondKanbanStageInstanceExample.createCriteria()
                .andProjectIdEqualTo(stageInstance.getProjectId())
                .andParentIdEqualTo(parentStateId)
                .andLevelEqualTo(StageConstant.StageLevelEnum.SECOND_LEVEL_STAGE.getValue())
                .andStateEqualTo(StageConstant.STATE_VALIDATE);//.andIsShowEqualTo((byte)0)
        secondKanbanStageInstanceExample.setOrderByClause("order_id asc");
        List<KanbanStageInstance> secondInstanceList = kanbanStageInstanceMapper.selectByExampleWithBLOBs(secondKanbanStageInstanceExample);
        if (CollectionUtils.isNotEmpty(secondInstanceList)) {
            List<KanbanStageInstance> secondStageFirstLaneList = Lists.newArrayList();
            KanbanStageInstance secondStageFirstLane = secondInstanceList.get(0);
            secondStageFirstLaneList.add(secondStageFirstLane);
            stageInstance.setSecondStages(secondStageFirstLaneList);
        }
    }

    /**
     * @param projectId
     * @return
     * @description 根据项目id和阶段id查询阶段信息
     */
    @Override
    public KanbanStageInstance getStageInfo(Long projectId, Long stageId) {
        if (projectId == null || stageId == null) {
            throw new StageException("项目id和阶段id不能为空");
        }
        KanbanStageInstance stageInstance = null;
        KanbanStageInstanceExample stageInstanceExample = new KanbanStageInstanceExample();
        stageInstanceExample.createCriteria()
                .andProjectIdEqualTo(projectId).andStageIdEqualTo(stageId)
                .andStateEqualTo(StageConstant.STATE_VALIDATE);//.andIsShowEqualTo((byte)0)
        List<KanbanStageInstance> stageInstances = kanbanStageInstanceMapper.selectByExampleWithBLOBs(stageInstanceExample);
        if (CollectionUtils.isNotEmpty(stageInstances)) {
            stageInstance = stageInstances.get(0);
        }
        return stageInstance;
    }

    /**
     * @param projectId
     * @return
     * @description 根据项目id查询阶段信息
     */
    @Override
    public List<KanbanStageInstance> getAllStageInfo(Long projectId) {

        KanbanStageInstanceExample stageInstanceExample = new KanbanStageInstanceExample();
        stageInstanceExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStateEqualTo(StageConstant.STATE_VALIDATE);//.andIsShowEqualTo((byte)0)
        return kanbanStageInstanceMapper.selectByExampleWithBLOBs(stageInstanceExample);

    }

    /**
     * @param stageInstanceDTOList
     * @return
     * @description 配置一阶段
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int configFirstStages(List<KanbanStageInstanceDTO> stageInstanceDTOList) {
        if (CollectionUtils.isEmpty(stageInstanceDTOList)) {
            throw new StageException("一阶段设置信息不能为空");
        }
        int affect = 0;
        //选中列表
        List<Long> checkList = Lists.newArrayList();
        //取消例表
        List<Long> unCheckList = Lists.newArrayList();
        for (KanbanStageInstanceDTO kanbanStageInstanceDTO : stageInstanceDTOList) {
            Long stageId = kanbanStageInstanceDTO.getStageId();
            //排除就绪和已完成阶段
            if (StageConstant.FirstStageEnum.READY_STAGE.getValue().equals(stageId) || StageConstant.FirstStageEnum.FINISH_STAGE.getValue().equals(stageId)) {
                continue;
            }
            /*Long primaryKey = kanbanStageInstanceDTO.getInstanceId();
            Byte enableCancel = kanbanStageInstanceDTO.getEnableCancel();
            //是否选中
            byte selectStatus = kanbanStageInstanceDTO.getIsShow();
            if (selectStatus == 0) {
                checkList.add(primaryKey);
            } else if (selectStatus == 1) {
                if (null != enableCancel && 0 == enableCancel) {
                    unCheckList.add(primaryKey);
                }
            }*/
            Long primaryKey = kanbanStageInstanceDTO.getInstanceId();
            Long projectId = kanbanStageInstanceDTO.getProjectId();
            //0:显示 1:不显示
            byte selectStatus = kanbanStageInstanceDTO.getIsShow();
            if (selectStatus == 0) {
                checkList.add(primaryKey);
            } else if (selectStatus == 1) {
                //判断一阶段是否包含工作项 包含:1:不允许取消 不包含:0:允许取消
                boolean enableCancel = validateEnableCancelFirstStages(projectId, stageId);
                if (enableCancel) {
                    unCheckList.add(primaryKey);
                } else {
                    throw new StageException(StageConstant.FirstStageEnum.getFirstStageName(stageId).concat("下包含工作项"));
                }
            }
        }
        if (CollectionUtils.isNotEmpty(checkList)) {
            affect = kanbanStageInstanceMapper.updateSelectStatusByPrimaryKeyList(checkList, 0, UserThreadLocalUtil.getUserInfo().getUserId(), new Date());
        }
        if (CollectionUtils.isNotEmpty(unCheckList)) {
            affect = kanbanStageInstanceMapper.updateSelectStatusByPrimaryKeyList(unCheckList, 1, UserThreadLocalUtil.getUserInfo().getUserId(), new Date());
        }
        return affect;
    }

    /**
     * @param projectId
     * @param stageId
     * @return
     * @description 校验一阶段能否取消
     * @date 2020/07/23
     */
    private Boolean validateEnableCancelFirstStages(Long projectId, Long stageId) {
        Boolean enableCancel = null;
        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStageIdEqualTo(stageId)
                .andStateEqualTo(StageConstant.STATE_VALIDATE);
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        if (CollectionUtils.isNotEmpty(issues)) {
            enableCancel = false;
        } else {
            enableCancel = true;
        }
        return enableCancel;
    }

    /**
     * @param projectId
     * @param kanbanStageInstanceDTO
     * @return
     * @description 新增二阶段
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSecondStage(Long projectId, KanbanStageInstanceDTO kanbanStageInstanceDTO) {
        int count = validateSecondStageExists(projectId, kanbanStageInstanceDTO);
        if (count > 0) {
            throw new StageException("该二级状态已存在");
        }
        RLock rLock = null;
        try {
            rLock = redissonService.getLock(RedisCacheKeyConstant.SECOND_STAGE_STATUS_LOCK_KEY);
            boolean result = false;
            for (int i = 0; i < 3; i++) {
                result = rLock.tryLock(0, 50, TimeUnit.SECONDS);
                if (result) {
                    break;
                } else {
                    Thread.sleep(10);
                }
            }
            if (result) {
                //阶段序值
                Long stageId = kanbanStageInstanceMapper.selectMaxStateId();
                stageId++;
                //排序序值
                Integer orderId = kanbanStageInstanceMapper.selectMaxOrderId();
                orderId++;
                //一阶段id
                Long parentId = kanbanStageInstanceDTO.getParentId();
                KanbanStageInstance kanbanStageInstance = new KanbanStageInstance();
                kanbanStageInstance.setStageId(stageId);
                kanbanStageInstance.setParentId(parentId);
                kanbanStageInstance.setProjectId(projectId);
                kanbanStageInstance.setLevel(StageConstant.StageLevelEnum.SECOND_LEVEL_STAGE.getValue());
                kanbanStageInstance.setStageName(kanbanStageInstanceDTO.getStageName());
                kanbanStageInstance.setState(StageConstant.STATE_VALIDATE);
                kanbanStageInstance.setOrderId(orderId);//kanbanStageInstance.setIsShow((byte)0);
                count = kanbanStageInstanceMapper.insertSelective(kanbanStageInstance);
                if (count != 1) {
                    throw new StageException("保存二级状态失败");
                }
                //查询当前一阶段下二阶段数
                List<KanbanStageInstance> secondStageInstanceList = kanbanStageInstanceMapper.getSecondStageInstances(projectId, parentId);
                if (CollectionUtils.isNotEmpty(secondStageInstanceList)) {
                    //查看二级状态为空的项目集
                    List<Issue> issues = getLaneIssueNullList(projectId, parentId);
                    if (CollectionUtils.isNotEmpty(issues)) {
                        KanbanStageInstance secondStage = secondStageInstanceList.get(0);
                        Long laneId = secondStage.getStageId();
                        //更新工作项二级泳道
                        batchUpdateIssueLaneId(projectId, parentId, laneId, issues);
                    }
                    //处理弹框信息设置
                    //二级状态主键
                    Long secondInstanceId = kanbanStageInstance.getInstanceId();
                    transferFirstStagePopUpInfo2SecondState(projectId, parentId, secondInstanceId, secondStageInstanceList);
                }
            }
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                try {
                    rLock.unlock();
                } catch (Exception e) {
                    LOGGER.error("release lock occur exception:{}", e.getMessage());
                }
            }
        }
    }

    /**
     * 判断二阶段是否存在
     *
     * @param projectId              项目id
     * @param kanbanStageInstanceDTO 二阶段对象
     * @return
     */
    private int validateSecondStageExists(Long projectId, KanbanStageInstanceDTO kanbanStageInstanceDTO) {
        Assert.notNull(kanbanStageInstanceDTO, "二级状态参数不能为空");
        //根据项目id、父阶段id和二阶段名判断二阶段是否存在
        KanbanStageInstance kanbanStageInstance = new KanbanStageInstance();
        kanbanStageInstance.setProjectId(projectId);
        kanbanStageInstance.setParentId(kanbanStageInstanceDTO.getParentId());
        kanbanStageInstance.setStageName(kanbanStageInstanceDTO.getStageName());
        kanbanStageInstance.setLevel(StageConstant.StageLevelEnum.SECOND_LEVEL_STAGE.getValue());
        kanbanStageInstance.setState(StageConstant.STATE_VALIDATE);//kanbanStageInstance.setIsShow((byte)0);
        int count = kanbanStageInstanceMapper.validateSecondStageExists(kanbanStageInstance);
        return count;
    }

    /**
     * 查询二级状态为空的项目集
     *
     * @param projectId
     * @param stageId
     * @return
     */
    private List<Issue> getLaneIssueNullList(Long projectId, Long stageId) {
        List<Issue> issueList = issueMapper.getSecondStageNullIssueList(projectId, stageId);
        return issueList;
    }

    /**
     * 批量更新工作项泳道
     *
     * @param issues
     * @param laneId
     */
    private void batchUpdateIssueLaneId(Long projectId, Long stageId, Long laneId, List<Issue> issues) {
        //工作项主键
        List<Long> issueIdList = Lists.newArrayList();
        //批量更新
        for (int i = 0; i < issues.size(); i++) {
            issueIdList.add(issues.get(i).getIssueId());
            if (issueIdList.size() == PER_PAGE_SIZE || i == issues.size() - 1) {
                int count = issueMapper.batchUpdateIssueLaneId(issueIdList, projectId, stageId, laneId);
                if (count < 1) {
                    throw new RuntimeException("批量更新工作项泳道[项目id:{" + projectId + "},一阶段状态id:{" + stageId + "},二级状态id:{" + laneId + "},工作项idList:{" + StringUtils.join(issueIdList, ",") + "}]失败");
                }
                issueIdList.clear();
            }
        }
    }

    /**
     * 将一阶段弹框信息转移到二级状态
     *
     * @param projectId
     * @param parentStageId
     * @param secondInstanceId
     * @param secondStageInstanceList
     * @return
     */
    private void transferFirstStagePopUpInfo2SecondState(Long projectId, Long parentStageId, Long secondInstanceId, List<KanbanStageInstance> secondStageInstanceList) {
        int size = secondStageInstanceList.size();
        if (size == 1) {
            KanbanStageInstance firstStageInstance = getStageInfo(projectId, parentStageId);
            Integer maxNumber = firstStageInstance.getMaxNumbers();
            Integer stayDays = firstStageInstance.getStayDays();
            String admittanceRule = firstStageInstance.getAdmittanceRule();
            if (null != maxNumber || null != stayDays || StringUtils.isNotBlank(admittanceRule)) {
                Long instanceId = firstStageInstance.getInstanceId();
                firstStageInstance = new KanbanStageInstance();
                firstStageInstance.setInstanceId(instanceId);
                //移除一阶段弹框信息
                int count = kanbanStageInstanceMapper.updateStagePopUpInfo(firstStageInstance);
                if (count < 1) {
                    throw new StageException("置空一阶段弹框信息失败");
                }
                //转移一阶段弹框信息到二级状态
                KanbanStageInstance secondStageInstance = new KanbanStageInstance();
                secondStageInstance.setInstanceId(secondInstanceId);
                secondStageInstance.setMaxNumbers(maxNumber);
                secondStageInstance.setStayDays(stayDays);
                secondStageInstance.setAdmittanceRule(admittanceRule);
                count = kanbanStageInstanceMapper.updateStagePopUpInfo(secondStageInstance);
                if (count < 1) {
                    throw new StageException("转移一阶段弹框信息到二级状态失败");
                }
            }
        }
    }

    /**
     * @param projectId
     * @param kanbanStageInstanceDTO
     * @return
     * @description 修改二级状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int modifySecondStage(Long projectId, KanbanStageInstanceDTO kanbanStageInstanceDTO) {
        LOGGER.info("modifySecondStage method param projectId:{}, kanbanStageInstanceDTO:{}", projectId, kanbanStageInstanceDTO);
        KanbanStageInstance originalStageInstance = ReflectUtil.copyProperties(kanbanStageInstanceDTO, KanbanStageInstance.class);
        KanbanStageInstance kanbanStageInstance = kanbanStageInstanceMapper.selectByPrimaryKey(originalStageInstance.getInstanceId());
        if (null == kanbanStageInstance) {
            throw new StageException("该二级状态不存在");
        }
        String originalStageName = originalStageInstance.getStageName();
        if (StringUtils.isBlank(originalStageName)) {
            throw new StageException("二级状态名称不能为空");
        }
        if (originalStageName.equals(kanbanStageInstance.getStageName())) {
            return 1;
        }
        Long parentId = kanbanStageInstance.getParentId();
        kanbanStageInstance = new KanbanStageInstance();
        kanbanStageInstance.setProjectId(projectId);
        kanbanStageInstance.setParentId(parentId);
        kanbanStageInstance.setStageName(kanbanStageInstanceDTO.getStageName());
        kanbanStageInstance.setLevel(StageConstant.StageLevelEnum.SECOND_LEVEL_STAGE.getValue());//kanbanStageInstance.setIsShow(kanbanStageInstanceDTO.getIsShow());
        kanbanStageInstance.setState(StageConstant.STATE_VALIDATE);
        //查看二阶段名是否存在
        int count = kanbanStageInstanceMapper.validateSecondStageExists(kanbanStageInstance);
        if (count > 0) {
            throw new StageException("该二级状态已存在");
        }
        originalStageInstance.setUpdateUid(UserThreadLocalUtil.getUserInfo().getUserId());
        originalStageInstance.setUpdateTime(new Date());
        count = kanbanStageInstanceMapper.updateByPrimaryKeySelective(originalStageInstance);
        if (count != 1) {
            throw new StageException("二级状态编辑失败");
        }
        return count;
    }

    /**
     * @param projectId
     * @param kanbanStageInstanceDTO
     * @return
     * @description 删除二阶段
     * 删除条件:
     * 1、一阶段下仅有一个二阶段
     * 2、一阶段下包含多个二阶段并且当前二阶段工作项为空
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSecondStage(Long projectId, KanbanStageInstanceDTO kanbanStageInstanceDTO) {
        Assert.notNull(kanbanStageInstanceDTO, "二级状态入参对象不能为空");
        LOGGER.info("deleteSecondStage method param kanbanStageInstanceDTO:{}", kanbanStageInstanceDTO);
        Long instanceId = kanbanStageInstanceDTO.getInstanceId();
        Long stageId = kanbanStageInstanceDTO.getStageId();
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        kanbanStageInstanceExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andInstanceIdEqualTo(instanceId)
                .andStageIdEqualTo(stageId)
                .andLevelEqualTo(StageConstant.StageLevelEnum.SECOND_LEVEL_STAGE.getValue())
                .andStateEqualTo(StageConstant.STATE_VALIDATE);
        List<KanbanStageInstance> kanbanStageInstances = kanbanStageInstanceMapper.selectByExample(kanbanStageInstanceExample);
        if (CollectionUtils.isEmpty(kanbanStageInstances)) {
            throw new StageException("该二级状态不存在");
        }
        int count = 0;
        //查看一阶段下包含的二阶段数
        Long parentStageId = kanbanStageInstanceDTO.getParentId();
        kanbanStageInstanceExample = new KanbanStageInstanceExample();
        kanbanStageInstanceExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andParentIdEqualTo(parentStageId).
                andLevelEqualTo(StageConstant.StageLevelEnum.SECOND_LEVEL_STAGE.getValue())
                .andStateEqualTo(StageConstant.STATE_VALIDATE);//andIsShowEqualTo((byte)0);
        kanbanStageInstances = kanbanStageInstanceMapper.selectByExample(kanbanStageInstanceExample);
        if (CollectionUtils.isNotEmpty(kanbanStageInstances)) {
            KanbanStageInstance record = new KanbanStageInstance();
            record.setInstanceId(instanceId);
            record.setState(StageConstant.STATE_INVALIDATE);
            int size = kanbanStageInstances.size();
            if (size == 1) {
                KanbanStageInstance kanbanStageInstance = kanbanStageInstances.get(0);
                //二级泳道id
                String laneId = String.valueOf(stageId);
                if (laneId.equals(String.valueOf(kanbanStageInstance.getStageId()))) {
                    //删除二阶段
                    count = kanbanStageInstanceMapper.updateByPrimaryKeySelective(record);
                    if (count != 1) {
                        throw new StageException("删除二级状态失败");
                    }
                    int result = 0;
                    //查询当前二级状态包含的工作项
                    List<Issue> issueList = issueMapper.getSecondLaneBindedIssues(projectId, parentStageId, stageId);
                    if (CollectionUtils.isNotEmpty(issueList)) {
                        result = modifyIssueLaneStateNull(projectId, parentStageId, stageId);
                        LOGGER.info("[modifyIssueLaneStateNull] method param projectId:{}, parentStageId:{}, stageId:{}, return affect row:{}", projectId, parentStageId, stageId, result);
                        if (result < 1) {
                            throw new StageException("置空工作项关联的二级状态失败");
                        }
                    }
                    //将当前二级状态的弹框信息转移到对应的一阶段
                    result = transferSecondStatePopUpInfoToFirstStage(projectId, parentStageId, kanbanStageInstance);
                    LOGGER.info("[transferSecondStatePopUpInfoToFirstStage] method param projectId:{}, firstStageId:{}, kanbanStageInstance:{}, return affect row:{}", projectId, parentStageId, kanbanStageInstance, result);
                    if (result < 1) {
                        throw new StageException("当前二级状态弹框信息分配给一级阶段失败");
                    }
                } else {
                    throw new StageException("当前项目下无此二级状态");
                }
            } else {
                //查询当前二级状态包含的工作项
                List<Issue> issueList = issueMapper.getSecondLaneBindedIssues(projectId, parentStageId, stageId);
                if (CollectionUtils.isNotEmpty(issueList)) {
                    throw new StageException("当前二级状态有工作项关联");
                } else {
                    count = kanbanStageInstanceMapper.updateByPrimaryKeySelective(record);
                    if (count != 1) {
                        throw new StageException("删除二级状态失败");
                    }
                }
            }
        } else {
            throw new StageException("该二级状态不存在");
        }
        return count;
    }

    /**
     * 修改工作项状态
     *
     * @param projectId
     * @param stageId
     * @param laneId
     * @return
     */
    private int modifyIssueLaneStateNull(Long projectId, Long stageId, Long laneId) {
        Issue issue = new Issue();
        issue.setProjectId(projectId);
        issue.setStageId(stageId);
        issue.setLaneId(laneId);
        //变更工作项二阶段状态
        int count = issueMapper.updateIssueLaneStateNull(issue);
        return count;
    }

    /**
     * @param projectId
     * @param parentStageId
     * @param secondKanbanStageInstance
     * @description 将二级状态弹框信息转移到一阶段
     * @date 2020/06/02
     */
    private int transferSecondStatePopUpInfoToFirstStage(Long projectId, Long parentStageId, KanbanStageInstance secondKanbanStageInstance) {
        int count = 0;
        Integer maxNumber = secondKanbanStageInstance.getMaxNumbers();
        Integer stayDays = secondKanbanStageInstance.getStayDays();
        String admittanceRule = secondKanbanStageInstance.getAdmittanceRule();
        KanbanStageInstanceExample firstStageInstanceExample = new KanbanStageInstanceExample();
        firstStageInstanceExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStageIdEqualTo(parentStageId)
                .andLevelEqualTo(StageConstant.StageLevelEnum.FIRST_LEVEL_STAGE.getValue())
                .andStateEqualTo(StageConstant.STATE_VALIDATE);
        List<KanbanStageInstance> firstStageInstanceList = kanbanStageInstanceMapper.selectByExample(firstStageInstanceExample);
        if (CollectionUtils.isNotEmpty(firstStageInstanceList)) {
            KanbanStageInstance firstStageInstance = firstStageInstanceList.get(0);
            Long firstStageInstanceId = firstStageInstance.getInstanceId();
            KanbanStageInstance kanbanStageInstance = new KanbanStageInstance();
            kanbanStageInstance.setInstanceId(firstStageInstanceId);
            kanbanStageInstance.setMaxNumbers(maxNumber);
            kanbanStageInstance.setStayDays(stayDays);
            kanbanStageInstance.setAdmittanceRule(admittanceRule);
            count = kanbanStageInstanceMapper.updateByPrimaryKeySelective(kanbanStageInstance);
        }
        return count;
    }

    /**
     * 排序二阶段状态
     *
     * @param projectId
     * @param instanceIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int sortSecondStage(Long projectId, List<Long> instanceIds) {
        LOGGER.info("sortSecondStage method param projectId:{}, instanceIds:{}", projectId, instanceIds);
        if (projectId == null || CollectionUtils.isEmpty(instanceIds)) {
            throw new StageException("项目id和二阶段状态实例不能为空");
        }
        List<KanbanStageInstance> kanbanStageInstanceList = getSecondStageOrderList(instanceIds);
        if (CollectionUtils.isEmpty(kanbanStageInstanceList) || kanbanStageInstanceList.size() != 2) {
            throw new StageException("二阶段状态实例不满足排序条件");
        }
        int count = 0;
        List<KanbanStageInstance> kanbanStageInstances = Lists.newArrayList();
        KanbanStageInstance firstStageInstance = kanbanStageInstanceList.get(0);
        Integer firstOrderId = firstStageInstance.getOrderId();
        KanbanStageInstance secondStageInstance = kanbanStageInstanceList.get(1);
        Integer secondOrderId = secondStageInstance.getOrderId();
        firstStageInstance.setOrderId(secondOrderId);
        kanbanStageInstances.add(firstStageInstance);
        secondStageInstance.setOrderId(firstOrderId);
        kanbanStageInstances.add(secondStageInstance);
        for (KanbanStageInstance kanbanStageInstance : kanbanStageInstances) {
            count = kanbanStageInstanceMapper.updateSecondStageSort(kanbanStageInstance);
            if (count != 1) {
                throw new StageException("二级状态排序失败");
            }
        }
        return count;
    }

    /**
     * 获取二阶段序值列表
     *
     * @param instanceIds
     * @return
     */
    private List<KanbanStageInstance> getSecondStageOrderList(List<Long> instanceIds) {
        List<KanbanStageInstance> kanbanStageInstances = kanbanStageInstanceMapper.getSecondStageOrderList(instanceIds);
        return kanbanStageInstances;
    }

    /**
     * @param kanbanStageInstanceDTOList
     * @return
     * @description 初始化看板阶段
     * @date 2020/05/18
     */
    @Override
    public int initKanbanStageList(List<KanbanStageInstanceDTO> kanbanStageInstanceDTOList) {
        int count = 0;
        List<KanbanStageInstance> kanbanStageInstanceList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(kanbanStageInstanceDTOList)) {
            try {
                kanbanStageInstanceList = ReflectUtil.copyProperties4List(kanbanStageInstanceDTOList, KanbanStageInstance.class);
                count = kanbanStageInstanceMapper.batchInsert(kanbanStageInstanceList);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }

    /**
     * @param projectId
     * @param kanbanStageInstanceDTO
     * @return
     * @description 新增超时天数、最大制品数、准入规则字段
     * @date 2020/06/01
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addStagePopUpInfos(Long projectId, KanbanStageInstanceDTO kanbanStageInstanceDTO) {
        if (null == projectId || null == kanbanStageInstanceDTO) {
            throw new RuntimeException("新增阶段弹框信息不能为空");
        }
        int count = 0;
        KanbanStageInstance kanbanStageInstance = ReflectUtil.copyProperties(kanbanStageInstanceDTO, KanbanStageInstance.class);
        int level = kanbanStageInstance.getLevel();
        if (level == 1) {
            /**
             * 判断当前一阶段是否有多个二级状态
             * 1、单个:把一阶段弹框设置信息转移到二级状态
             * 2、多个:在对应二级状态添加
             */
            Long parentId = kanbanStageInstance.getStageId();
            KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
            kanbanStageInstanceExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andParentIdEqualTo(parentId)
                    .andLevelEqualTo(StageConstant.StageLevelEnum.SECOND_LEVEL_STAGE.getValue())
                    .andStateEqualTo(StageConstant.STATE_VALIDATE);
            kanbanStageInstanceExample.setOrderByClause("order_id asc");
            List<KanbanStageInstance> kanbanStageInstanceList = kanbanStageInstanceMapper.selectByExample(kanbanStageInstanceExample);
            if (CollectionUtils.isNotEmpty(kanbanStageInstanceList)) {
                if (kanbanStageInstanceList.size() == 1) {
                    KanbanStageInstance stageInstance = kanbanStageInstanceList.get(0);
                    Long instanceId = stageInstance.getInstanceId();
                    kanbanStageInstance = new KanbanStageInstance();
                    kanbanStageInstance.setInstanceId(instanceId);
                    kanbanStageInstance.setMaxNumbers(kanbanStageInstanceDTO.getMaxNumbers());
                    kanbanStageInstance.setStayDays(kanbanStageInstanceDTO.getStayDays());
                    kanbanStageInstance.setAdmittanceRule(kanbanStageInstanceDTO.getAdmittanceRule());
                    count = kanbanStageInstanceMapper.updateStagePopUpInfo(kanbanStageInstance);
                    if (count != 1) {
                        throw new RuntimeException("新增一阶段弹框信息失败");
                    }
                    //置空一阶段弹框信息
                    instanceId = kanbanStageInstanceDTO.getInstanceId();
                    KanbanStageInstance firstStageInstance = new KanbanStageInstance();
                    firstStageInstance.setInstanceId(instanceId);
                    int result = kanbanStageInstanceMapper.updateStagePopUpInfo(firstStageInstance);
                    if (result != 1) {
                        throw new RuntimeException("置空一阶段弹框信息失败");
                    }
                } else {
                    throw new RuntimeException("当前一阶段包含多个二级状态，请在对应的二级状态进行设置");
                }
            } else {
                count = kanbanStageInstanceMapper.updateStagePopUpInfo(kanbanStageInstance);
                if (count != 1) {
                    throw new RuntimeException("新增一阶段弹框信息失败");
                }
            }
        } else if (level == 2) {
            count = kanbanStageInstanceMapper.updateStagePopUpInfo(kanbanStageInstance);
            if (count != 1) {
                throw new RuntimeException("新增二级状态弹框信息失败");
            }
        }
        return count;
    }

    /**
     * @param kanbanStageInstanceDTO
     * @return
     * @description 更新超时天数、最大制品数、准入规则字段
     * @date 2020/06/01
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int modifyStagePopUpInfos(KanbanStageInstanceDTO kanbanStageInstanceDTO) {
        Assert.notNull(kanbanStageInstanceDTO, "弹框信息变更入参对象不能为空");
        KanbanStageInstance kanbanStageInstance = ReflectUtil.copyProperties(kanbanStageInstanceDTO, KanbanStageInstance.class);
        int count = kanbanStageInstanceMapper.updateStagePopUpInfo(kanbanStageInstance);
        return count;
    }

    @Override
    public int modifyFirstStageHandler(Long instanceId, Long handler) {
        Assert.notNull(instanceId, "一阶段实例编号不能为空");
        KanbanStageInstance kanbanStageInstance = new KanbanStageInstance();
        kanbanStageInstance.setInstanceId(instanceId);
        kanbanStageInstance.setPrincipal(handler);
        int count = kanbanStageInstanceMapper.updateByPrimaryKeySelective(kanbanStageInstance);
        return count;
    }

    /**
     * 功能描述:
     *
     * @param projectId
     * @param firstStageId
     * @param secondName
     * @return java.lang.Long
     * @date 2020/10/28
     */
    @Override
    public Long getSecondStageIdByFirstStageIdAndSecondName(Long projectId, Long firstStageId, String secondName) {
        // 查询二阶
        KanbanStageInstanceExample kanbanStageInstanceExample2 = new KanbanStageInstanceExample();
        kanbanStageInstanceExample2.createCriteria()
                .andProjectIdEqualTo(projectId).andParentIdEqualTo(firstStageId)
                .andStateEqualTo(StateEnum.U.getValue());
        List<KanbanStageInstance> secondStageInstanceList = kanbanStageInstanceMapper.selectByExampleWithBLOBs(kanbanStageInstanceExample2);
        Long laneId = null;
        if (CollectionUtils.isEmpty(secondStageInstanceList)) {
            return laneId;
        }
        for (KanbanStageInstance temp : secondStageInstanceList) {
            if (StringUtils.equals(temp.getStageName(), secondName)) {
                laneId = temp.getStageId();
                continue;
            }
        }

        return laneId;
    }
}
