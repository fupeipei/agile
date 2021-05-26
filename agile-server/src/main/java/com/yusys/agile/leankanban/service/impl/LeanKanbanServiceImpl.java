package com.yusys.agile.leankanban.service.impl;

import com.yusys.agile.commission.domain.SCommission;
import com.yusys.agile.commission.service.CommissionService;
import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.issue.dao.IssueHistoryRecordMapper;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.issue.domain.IssueHistoryRecordExample;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.IssueStringDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.utils.IssueRichTextFactory;
import com.yusys.agile.issue.utils.IssueRuleFactory;
import com.yusys.agile.leankanban.dto.IssueResultDTO;
import com.yusys.agile.leankanban.dto.PageResultDTO;
import com.yusys.agile.leankanban.service.LeanKanbanService;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.KanbanStageInstanceExample;
import com.yusys.agile.set.stage.dto.KanbanStageInstanceDTO;
import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.domain.SprintWithBLOBs;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yusys.agile.utils.page.PageQuery;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 *
 */
@Service
public class LeanKanbanServiceImpl implements LeanKanbanService {

    private static final Logger loggr = LoggerFactory.getLogger(LeanKanbanServiceImpl.class);
    @Resource
    private KanbanStageInstanceMapper kanbanStageInstanceMapper;
    @Resource
    private IssueMapper issueMapper;
    @Resource
    private IssueHistoryRecordMapper issueHistoryRecordMapper;
    @Resource
    private SprintMapper sprintMapper;
    @Resource
    private IFacadeUserApi iFacadeUserApi;
    @Autowired
    private IssueRichTextFactory issueRichTextFactory;
    @Resource
    private IssueService issueService;

    @Autowired
    private IssueRuleFactory ruleFactory;
    @Autowired
    private CommissionService commissionService;

    @Resource
    private StoryService storyService;

    private static final String CREATE_TIME_DESC = "CREATE_TIME DESC";

    @Override
    public List<KanbanStageInstanceDTO> selectKanbanStageInstanceDTOList(Long projectId, List<PageQuery<Long>> queries) {
        List<KanbanStageInstanceDTO> kanbanStageInstanceDTOS = Lists.newArrayList();
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        kanbanStageInstanceExample.createCriteria().andProjectIdEqualTo(projectId).andLevelEqualTo((byte) 1);
        kanbanStageInstanceExample.setOrderByClause("order_id asc");
        //一级阶段
        List<KanbanStageInstance> firstInstanceList = kanbanStageInstanceMapper.selectByExample(kanbanStageInstanceExample);
        if (!firstInstanceList.isEmpty()) {
            //懒加载泳道下卡片信息
            for (KanbanStageInstance kanbanStageInstance : firstInstanceList) {
                for (PageQuery<Long> query : queries) {
                    if (query != null && query.getQuery().equals(kanbanStageInstance.getStageId())) {
                        KanbanStageInstanceDTO kanbanStageInstanceDTO =
                                ReflectUtil.copyProperties(kanbanStageInstance, KanbanStageInstanceDTO.class);
                        IssueExample issueExample = new IssueExample();
                        issueExample.createCriteria().andStageIdEqualTo(kanbanStageInstance.getStageId());
                        //计算总记录数
                        long total = issueMapper.countByExample(issueExample);
                        issueExample.setStartRow(query.getFrom());
                        issueExample.setPageSize(query.getPageSize());
                        issueExample.setOrderByClause("priority asc");
                        List<Issue> issues = issueMapper.selectByExample(issueExample);
                        int count = query.getFrom() + query.getPageSize();
                        kanbanStageInstanceDTO.setHasNext(total >= count ? true : false);
                        List<IssueDTO> issueDTOS = Lists.newArrayList();
                        ReflectUtil.copyProperties(issues, issueDTOS);
                        kanbanStageInstanceDTO.setIssueDTOS(issueDTOS);
                        kanbanStageInstanceDTOS.add(kanbanStageInstanceDTO);
                        break;
                    }
                }
            }
        }
        return kanbanStageInstanceDTOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int moveIssue(Long issueId, Long toStageId, Long toLaneId) {

        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        Long stageId = issue.getStageId();
        Long laneId = issue.getLaneId();
        Byte issueType = issue.getIssueType();
        Long projectId = issue.getProjectId();

        /** 判断工作项流转规则是否允许 */
        if (!ruleFactory.getIssueRulesCheckFlag(issueType, issue.getStageId(), issue.getLaneId(), toStageId, toLaneId, projectId)) {
            throw new BusinessException("该工作项不允许流转到目标阶段！");
        }
        /** 拖拽卡片验证制品数是否超过预定值*/
        issueRichTextFactory.dealStagesProductLimit(toStageId, toLaneId, issue.getProjectId(), issueType);

        //校验故事下有未完成任务或未修复缺陷不允许改为完成阶段
        if (IssueTypeEnum.TYPE_STORY.CODE.equals(issue.getIssueType()) && StageConstant.FirstStageEnum.FINISH_STAGE.getValue().equals(toStageId)) {
            if (storyService.checkHasUnfinishOrNotRepairIssue(issueId)) {
                throw new BusinessException("故事下有未完成任务或未修复缺陷不允许改为完成阶段！");
            }
        }

        issue.setStageId(toStageId);
        issue.setLaneId(toLaneId);
        issue.setUpdateTime(new Date());
        int i = issueMapper.updateByPrimaryKey(issue);

        //计算oldValue 如果泳道为Null，则取阶段ID
        String oldValue = stageId.toString();
        if (Optional.ofNullable(laneId).isPresent()) {
            oldValue = oldValue + "-" + laneId;
        }
        //计算新值,如果toLaneId不为空，则取值toLaneId。
        //如果toLaneId为空， 则判断toStageId
        String newValue = toStageId.toString();
        if (Optional.ofNullable(toLaneId).isPresent()) {
            newValue = newValue + "-" + toLaneId;
        }

        IssueHistoryRecordExample example = new IssueHistoryRecordExample();
        IssueHistoryRecordExample.Criteria criteria = example.createCriteria();
        criteria.andIssueIdEqualTo(issueId).andNewValueEqualTo(oldValue);
        example.setOrderByClause(CREATE_TIME_DESC);
        List<IssueHistoryRecord> issueHistoryRecords = issueHistoryRecordMapper.selectByExample(example);
        //更新工作项上一阶段的停留天数
        if (CollectionUtils.isNotEmpty(issueHistoryRecords)) {
            IssueHistoryRecord historyRecord = issueHistoryRecords.get(0);
            Date createTime = historyRecord.getCreateTime();
            Long days = (System.currentTimeMillis() - createTime.getTime()) / (24 * 60 * 60 * 1000);
            historyRecord.setStayDays(days.intValue());
            issueHistoryRecordMapper.updateByPrimaryKeyWithBLOBs(historyRecord);
        }
        //保存历史记录
        IssueHistoryRecord record = new IssueHistoryRecord();
        record.setOldValue(oldValue);
        record.setNewValue(newValue);
        record.setIssueId(issueId);
        record.setOperationField("阶段id");
        record.setStayDays(NumberConstant.ZERO);
        record.setIsCustom(NumberConstant.ZERO.byteValue());
        record.setRecordType(NumberConstant.ZERO.byteValue());
        issueHistoryRecordMapper.insert(record);

        //todo 更新代办状态
        SCommission sCommission = commissionService.getCommissionByIssueId(issueId);
        if (null != sCommission) {
            String state = null;
            if (String.valueOf(StageConstant.FirstStageEnum.FINISH_STAGE.getValue()).equals(String.valueOf(toStageId))) {
                state = StateEnum.E.getValue();
            } else {
                state = StateEnum.U.getValue();
            }
            //commissionService.updateCommissionState(issueId, state);
        }
        return i;
    }

    @Override
    public List<IssueResultDTO> selectIssueViewInfo(Long projectId,
                                                    List<PageResultDTO> pageResultDTOList,
                                                    Map<String, Object> map) throws Exception {
        IssueStringDTO issueStringDTO = JSON.parseObject(JSON.toJSONString(map), IssueStringDTO.class);
        List<IssueResultDTO> issueResultDTOList = Lists.newArrayList();
        String stageIds = issueStringDTO.getStageId();

        if (stageIds != null && stageIds.contains("9999")) {
            PageResultDTO pageResultDTO = new PageResultDTO();
            pageResultDTO.setStageId(9999L);
            pageResultDTO.setStageType((byte) 1);
            pageResultDTO.setPage(1);
            pageResultDTO.setPageSize(100000);
            pageResultDTOList.add(pageResultDTO);
        }

        if (CollectionUtils.isNotEmpty(pageResultDTOList)) {
            for (PageResultDTO pageResultDTO : pageResultDTOList) {
                Long stageId = pageResultDTO.getStageId();
                IssueResultDTO resultDTO = new IssueResultDTO();
                List<Issue> issues = Lists.newArrayList();
                List<Issue> issueTotal = Lists.newArrayList();
                if ((stageIds != null && stageIds.contains(stageId.toString())) || stageIds == null) {
                    map.put("stageId", stageId.toString());
                    map.put("queryFlag", "leankanban");
                    //计算总记录数
                    issues = issueService.queryIssueList(map);
                    map.put("pageNum", null);
                    map.put("pageSize", null);
                    issueTotal = issueService.queryIssueList(map);
                }
                int count = pageResultDTO.getFrom() + pageResultDTO.getPageSize();
                resultDTO.setHasNext(issueTotal.size() >= count ? true : false);
                List<IssueDTO> issueDTOS = ReflectUtil.copyProperties4List(issues, IssueDTO.class);
                //处理 handlerNaem
                Map<Long, String> userMap = getUserMap(issueDTOS);
                if (CollectionUtils.isNotEmpty(issueDTOS)) {
                    for (IssueDTO issueDTO : issueDTOS) {
                        //计算卡片停留天数
                        issueDTO.setStayDays(calueCardStayDays(issueDTO));

                        issueDTO.setHandlerName(userMap.get(issueDTO.getHandler()));
                        //获取子工作项
                        IssueExample issueExample = new IssueExample();
                        issueExample.createCriteria().andParentIdEqualTo(issueDTO.getIssueId());
                        List<Issue> issueList = issueMapper.selectByExample(issueExample);
                        List<IssueDTO> children = ReflectUtil.copyProperties4List(issueList, IssueDTO.class);
                        issueDTO.setChildren(children);
                        //查询故事，获取迭代信息
                        if (issueStringDTO.getIssueType().equals(IssueTypeEnum.TYPE_STORY.CODE)) {
                            if (issueDTO.getSprintId() != null) {
                                SprintWithBLOBs sprintWithBLOBs = sprintMapper.selectByPrimaryKey(issueDTO.getSprintId());
                                SprintDTO sprintDTO = ReflectUtil.copyProperties(sprintWithBLOBs, SprintDTO.class);
                                issueDTO.setSprintName(sprintWithBLOBs.getSprintName());
                                issueDTO.setSprintDTO(sprintDTO);
                            }
                        }
                    }
                }
                resultDTO.setIssueDTOList(issueDTOS);
                resultDTO.setStageId(stageId);
                resultDTO.setStageType(pageResultDTO.getStageType());
                issueResultDTOList.add(resultDTO);
            }
            return issueResultDTOList;
        }
        return issueResultDTOList;
    }


    private Integer calueCardStayDays(IssueDTO issueDTO) {
        Integer stayDays = 0;
        Long stageId = issueDTO.getStageId();
        Long laneId = issueDTO.getLaneId();
        Long issueId = issueDTO.getIssueId();
        Long operationField = Optional.ofNullable(laneId).orElse(stageId);


        IssueHistoryRecordExample example = new IssueHistoryRecordExample();
        IssueHistoryRecordExample.Criteria criteria = example.createCriteria();
        criteria.andIssueIdEqualTo(issueId).andNewValueEqualTo(operationField + "");
        example.setOrderByClause(CREATE_TIME_DESC);
        List<IssueHistoryRecord> issueHistoryRecords = issueHistoryRecordMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(issueHistoryRecords)) {
            IssueHistoryRecord issueHistoryRecord = issueHistoryRecords.get(0);
            Date createTime = issueHistoryRecord.getCreateTime();
            Long days = (System.currentTimeMillis() - createTime.getTime()) / (24 * 60 * 60 * 1000);
            stayDays = days.intValue();
            for (IssueHistoryRecord historyRecord : issueHistoryRecords) {
                stayDays = stayDays + Optional.ofNullable(historyRecord.getStayDays()).orElse(0);
            }
        }

        return stayDays;
    }

    private Map<Long, String> getUserMap(List<IssueDTO> issueDTOS) {
        List<Long> userIds = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(issueDTOS)) {
            issueDTOS.forEach(issueDTO -> {
                if (issueDTO.getHandler() != null) {
                    userIds.add(issueDTO.getHandler());
                }
            });
        }
        Map<Long, String> userMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<SsoUser> ssoUsers = iFacadeUserApi.listUsersByIds(userIds);
            ssoUsers.forEach(ssoUser -> {
                userMap.put(ssoUser.getUserId(), ssoUser.getUserName());
            });
        }
        return userMap;
    }
}
