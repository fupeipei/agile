package com.yusys.agile.issue.utils;

import com.yusys.agile.headerfield.enums.IsCustomEnum;
import com.yusys.agile.issue.dao.IssueHistoryRecordMapper;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.issue.domain.IssueHistoryRecordExample;
import com.yusys.agile.issue.enums.IssueField;
import com.yusys.agile.issue.enums.IssueHistoryRecordTypeEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.KanbanStageInstanceExample;
import com.yusys.portal.model.common.enums.StateEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName IssueUpRegularFactory
 * @Description 工作项 业务需求/研发需求/用户故事 阶段状态向上规整
 * 按悲观法则，当研发需求从【就绪状态】到下一阶段状态，依据悲观法则，
 * 根据研发需求的parent_id查询业务需求下的研发需求，按照一级阶段和二级阶段的orderId升序排序，
 * 获取第一条数据作为父级业务需求的变更状态，将业务需求状态修改为相应的阶段状态。SQL如下：
 * select * from req.issue where parent_id =10108 order by stage_id  asc, lane_id  asc;
 * @Date 2021/2/20 10:41
 * @Version 1.0
 */
@Component
public class IssueUpRegularFactory {

    private static final Logger logger = LoggerFactory.getLogger(IssueUpRegularFactory.class);
    private static final String CREATE_TIME_DESC = "CREATE_TIME DESC";

    @Resource
    private IssueMapper issueMapper;

    @Resource
    private KanbanStageInstanceMapper stageInstanceMapper;
    @Resource
    private IssueHistoryRecordMapper historyRecordMapper;

    /**
     * 工作项向上规整的封装方法
     *
     * @param issueId 工作项ID
     */
    public void commonIssueUpRegular(Long issueId) {
        logger.info("调用工作项向上规整的封装方法:commonIssueUpRegular");
        Issue issue = getParentIssue(issueId);
        if (!Optional.ofNullable(issue).isPresent()) {
            logger.error("工作项为NULL，直接return");
            return;
        }
        Byte issueType = issue.getIssueType();
        Long parentId = issue.getParentId();
        Long handler = issue.getHandler();
        Long projectId = issue.getProjectId();
        //1、判断当前工作项类型 parentId如果为null ，直接return。
        if (!Optional.ofNullable(parentId).isPresent()) {
            logger.error("调用工作项向上规整的封装方法commonIssueUpRegular，当前parentId为null，直接return");
            return;
        }
        //2、判断工作项类型是任务 或缺陷的，直接return。
        if (IssueTypeEnum.TYPE_TASK.CODE.equals(issueType) || IssueTypeEnum.TYPE_FAULT.CODE.equals(issueType)) {
            logger.error("调用工作项向上规整的封装方法commonIssueUpRegular，工作项类型是任务或缺陷，直接return");
            return;
        }
        //3、判断工作项类型是feature 研发需求的，需要更新业务需求Epic的阶段状态以及处理人。
        //4、判断工作项类型是story 用户故事的，需要更新研发需求feature的阶段状态以及处理人。
        // 在判断用户故事的父工作项ID是否为Null，不为NULL，则更新业务需求Epic的阶段状态以及处理人。
        Map<String, Integer> kanbanInstanceMap = getKanbanInstance(projectId);
        if (kanbanInstanceMap.isEmpty()) {
            logger.error("调用工作项向上规整的封装方法commonIssueUpRegular，当前项目ID:{}为null，直接return", projectId);
            return;
        }
        settings(handler, parentId, projectId, kanbanInstanceMap);

    }

    /**
     * 递归遍历上级工作项
     *
     * @param handler
     * @param parentId
     */
    private void settings(Long handler, Long parentId, Long projectId, Map<String, Integer> kanbanInstanceMap) {
        //1、查询父级工作项
        Issue pIssue = getParentIssue(parentId);
        logger.info("进入递归方法 settings,父工作项：{}, 一级阶段:{}, 二级阶段:{}", pIssue.getIssueId(), pIssue.getStageId(), pIssue.getLaneId());
        //2、查询父工作项下的最左边的子工作项
        Issue subIssue = getSubIssue(parentId, projectId, kanbanInstanceMap);
        if (Optional.ofNullable(subIssue).isPresent()) {
            logger.info("进入递归方法 settings,子工作项：{}, 一级阶段:{}, 二级阶段:{}", subIssue.getIssueId(), subIssue.getStageId(), subIssue.getLaneId());
            //3、更新父工作项阶段历史记录和停留天数
            dealIssueHistory(pIssue, subIssue);
            //4、更新父工作项阶段状态
            updateIssue(pIssue, handler, subIssue);

        }
        parentId = pIssue.getParentId();
        //4、递归遍历
        if (Optional.ofNullable(parentId).isPresent()) {
            settings(handler, parentId, projectId, kanbanInstanceMap);
        }
    }

    /**
     * 处理Issue工作项操作记录以及计算当前工作项在当前阶段的停留天数
     *
     * @param pIssue
     * @param subIssue
     */
    private void dealIssueHistory(Issue pIssue, Issue subIssue) {
        Long issueId = pIssue.getIssueId();
        String stageId = pIssue.getStageId().toString();
        if (Optional.ofNullable(pIssue.getLaneId()).isPresent()) {
            stageId = stageId + "-" + pIssue.getLaneId();
        }

        String stageIdNew = subIssue.getStageId().toString();
        if (Optional.ofNullable(subIssue.getLaneId()).isPresent()) {
            stageIdNew = stageIdNew + "-" + subIssue.getLaneId();
        }
        if (!stageId.equals(stageIdNew)) {
            IssueHistoryRecordExample example = new IssueHistoryRecordExample();
            IssueHistoryRecordExample.Criteria criteria = example.createCriteria();
            criteria.andIssueIdEqualTo(issueId).andNewValueEqualTo(stageId);
            example.setOrderByClause(CREATE_TIME_DESC);
            List<IssueHistoryRecord> issueHistoryRecords = historyRecordMapper.selectByExample(example);
            //更新工作项上一阶段的停留天数
            if (CollectionUtils.isNotEmpty(issueHistoryRecords)) {
                IssueHistoryRecord historyRecord = issueHistoryRecords.get(0);
                Date createTime = historyRecord.getCreateTime();
                Long days = (System.currentTimeMillis() - createTime.getTime()) / (24 * 60 * 60 * 1000);
                historyRecord.setStayDays(days.intValue());
                historyRecordMapper.updateByPrimaryKeyWithBLOBs(historyRecord);
            }

            IssueHistoryRecord stageHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.STAGEID.getDesc());
            stageHistory.setOldValue(stageId);
            stageHistory.setNewValue(stageIdNew);
            historyRecordMapper.insert(stageHistory);
        }
    }

    /**
     * 根据父工作项ID获取工作项列表，按照一级阶段和二级阶段升序排序，获取第一条数据。
     *
     * @param parentId 父工作项ID
     * @return
     */
    private Issue getSubIssue(Long parentId, Long projectId, Map<String, Integer> kanbanInstanceMap) {
        Issue issue = null;
        List<Issue> issueList = issueMapper.selectIssueListByParentId(parentId, projectId);
        if (CollectionUtils.isNotEmpty(issueList)) {
            issue = issueList.get(0);
        }
        return issue;
    }

    /**
     * 根据父工作项ID查询工作项详情
     *
     * @param parentId
     * @return
     */
    private Issue getParentIssue(Long parentId) {
        return issueMapper.selectByPrimaryKey(parentId);
    }

    /**
     * 根据项目ID查询阶段设置Map排序数据
     *
     * @param projectId
     * @return
     */
    private Map<String, Integer> getKanbanInstance(Long projectId) {
        Map<String, Integer> stageProInstanceMap = new LinkedHashMap<>();
        KanbanStageInstanceExample instanceExample = new KanbanStageInstanceExample();
        instanceExample.createCriteria().andStateEqualTo(StateEnum.U.toString())
                .andProjectIdEqualTo(projectId);
        instanceExample.setOrderByClause("order_id asc");
        List<KanbanStageInstance> stageInstances = stageInstanceMapper.selectByExample(instanceExample);
        if (CollectionUtils.isNotEmpty(stageInstances)) {
            stageInstances.forEach(stageInstance -> {
                Long parentId = stageInstance.getParentId();
                Long stageId = stageInstance.getStageId();
                Integer stayDays = Optional.ofNullable(stageInstance.getStayDays()).orElse(0);
                String stages = parentId.equals(-1L) ? stageId + "" : StringUtils.join(parentId, "-", stageId);
                if (stageProInstanceMap.containsKey(stages)) {
                    stageProInstanceMap.put(stages, stayDays);
                } else {
                    stageProInstanceMap.put(stages, stayDays);
                }
            });
        }
        return stageProInstanceMap;
    }


    /**
     * 更新父工作项的阶段状态以及当前处理人。
     *
     * @param pIssue   父工作项对象
     * @param handler  当前处理人
     * @param subIssue 工作项对象
     */
    private void updateIssue(Issue pIssue, Long handler, Issue subIssue) {
        if (Optional.ofNullable(pIssue).isPresent() && Optional.ofNullable(subIssue).isPresent()) {
            pIssue.setStageId(subIssue.getStageId());
            pIssue.setLaneId(subIssue.getLaneId());
//            if(Optional.ofNullable(handler).isPresent()){
//                pIssue.setHandler(handler);
//            }
            issueMapper.updateByPrimaryKey(pIssue);
            logger.info("更新父工作项的阶段状态成功:{}", pIssue.toString());
        }
    }
}
