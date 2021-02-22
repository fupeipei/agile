package com.yusys.agile.issue.service.impl;

import com.yusys.agile.fault.enums.FaultStatusEnum;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.dto.CustomizeIssueDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.TaskStageIdEnum;
import com.yusys.agile.issue.service.CustomizeIssueService;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.sysextendfield.dao.SysExtendFieldDetailMapper;
import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetail;
import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetailExample;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.KanbanStageInstanceExample;
import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.domain.Sprint;
import com.yusys.agile.sprint.domain.SprintExample;
import com.yusys.agile.sprint.enums.SprintStatusEnum;
import com.google.common.collect.Lists;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.entity.SsoUser;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 特定工作项业务类
 * @date 2020/08/18
 */
@Service
public class CustomizeIssueServiceImpl implements CustomizeIssueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizeIssueServiceImpl.class);

    @Resource
    private IssueService issueService;

    @Resource
    private IssueMapper issueMapper;

    @Resource
    private IFacadeUserApi iSsoUserApi;

    @Resource
    private SprintMapper sprintMapper;

    @Resource
    private KanbanStageInstanceMapper kanbanStageInstanceMapper;

    @Resource
    private SysExtendFieldDetailMapper sysExtendFieldDetailMapper;

    /**
     * @param issueIds
     * @return
     * @description 查询关联的工作项信息
     * @date 2020/08/18
     */
    @Override
    public List<CustomizeIssueDTO> getRelatedIssues(List<Long> issueIds) {
        LOGGER.info("getTaskRelatedIssues param taskIds:{}", issueIds);
        List<CustomizeIssueDTO> issueList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(issueIds)) {
            dealRelatedIssues(issueList, issueIds);
        }
        return issueList;
    }

    /**
     * @param issueIds
     * @return
     * @description 处理任务关联的工作项
     * @date 2020/08/18
     */
    private void dealRelatedIssues(List<CustomizeIssueDTO> issueList, List<Long> issueIds) {
        for (Long issueId : issueIds) {
            CustomizeIssueDTO issueDTO = splitCustomizeIssueByIssueId(issueId);
            if (null != issueDTO) {
                issueList.add(issueDTO);
            }
        }
    }

    /**
     * @param issueId
     * @return
     * @description 拆分工作项
     * @date 2020/09/18
     */
    private CustomizeIssueDTO splitCustomizeIssueByIssueId(Long issueId) {
        CustomizeIssueDTO customizeIssue = new CustomizeIssueDTO();
        //判断任务类型
        Byte issueType = getIssueType(issueId);
        if (null != issueType) {
            if (issueType.byteValue() == IssueTypeEnum.TYPE_TASK.CODE) {
                customizeIssue.setTaskId(issueId);
                dealTaskIssue(issueId, customizeIssue);
            } else if (issueType.byteValue() == IssueTypeEnum.TYPE_FAULT.CODE) {
                //供cmp使用
                customizeIssue.setFaultId(issueId);
                dealFaultIssue(issueId, customizeIssue);
            } else if (issueType.byteValue() == IssueTypeEnum.TYPE_STORY.CODE) {
                customizeIssue.setStoryId(issueId);
                dealStoryIssue(issueId, customizeIssue);
            } else if (issueType.byteValue() == IssueTypeEnum.TYPE_FEATURE.CODE) {
                customizeIssue.setFeatureId(issueId);
                dealFeatureIssue(issueId, customizeIssue);
            } else if (issueType.byteValue() == IssueTypeEnum.TYPE_EPIC.CODE) {
                customizeIssue.setEpicId(issueId);
                dealEpicIssue(issueId, customizeIssue);
            } else {
                LOGGER.warn("issueId:{}对应的工作项类型未知", issueId);
            }
        }
        return customizeIssue;
    }

    /**
     * @param issueId
     * @return
     * @description 根据工作项id查询工作项类型
     * @date 2020/08/18
     */
    private Byte getIssueType(Long issueId) {
        Byte issueType = null;
        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria().andIssueIdEqualTo(issueId);
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        if (CollectionUtils.isNotEmpty(issues)) {
            Issue issue = issues.get(0);
            issueType = issue.getIssueType();
        }
        return issueType;
    }

    /**
     * @param taskId
     * @param customizeIssue
     * @description 处理任务工作项
     * @date 2020/08/18
     */
    private void dealTaskIssue(Long taskId, CustomizeIssueDTO customizeIssue) {
        Issue task = issueService.selectIssueInfo(taskId, IssueTypeEnum.TYPE_TASK.CODE);
        if (null != task) {
            customizeIssue.setTaskName(task.getTitle());
            Long handler = task.getHandler();
            dealReceiver(customizeIssue, handler, IssueTypeEnum.TYPE_TASK.CODE);
            //处理任务阶段信息
            Long taskStageId = task.getStageId();
            if (null != taskStageId) {
                String taskStageName = TaskStageIdEnum.getName(taskStageId);
                customizeIssue.setTaskStageId(taskStageId);
                customizeIssue.setTaskStageName(taskStageName);
            }
            //处理阶段信息结束
            Long storyId = task.getParentId();
            if (null != storyId) {
                dealStoryIssue(storyId, customizeIssue);
            }
        }
    }

    /**
     * @param faultId
     * @param customizeIssue
     * @description 处理缺陷工作项
     * @date 2020/09/18
     */
    private void dealFaultIssue(Long faultId, CustomizeIssueDTO customizeIssue) {
        Issue fault = issueService.selectIssueInfo(faultId, IssueTypeEnum.TYPE_FAULT.CODE);
        if (null != fault) {
            customizeIssue.setFaultName(fault.getTitle());
            Long stageId = fault.getStageId();
            customizeIssue.setFaultStageId(stageId);
            if (null != stageId) {
                customizeIssue.setFaultStageName(FaultStatusEnum.getMsg(stageId));
            }
            Long handler = fault.getHandler();
            dealReceiver(customizeIssue, handler, IssueTypeEnum.TYPE_FAULT.CODE);
            Long storyId = fault.getParentId();
            if (null != storyId) {
                dealStoryIssue(storyId, customizeIssue);
            }
        }
    }

    /**
     * @param customizeIssue
     * @param handler
     * @description 处理人员信息
     * @date 2020/08/18
     */
    private void dealReceiver(CustomizeIssueDTO customizeIssue, Long handler, Byte issueType) {
        if (null != handler) {
            SsoUser ssoUser = iSsoUserApi.queryUserById(handler);
            if (null != ssoUser) {
                if (IssueTypeEnum.TYPE_FAULT.CODE.equals(issueType)) {
                    customizeIssue.setFaultReceiverName(ssoUser.getUserName());
                } else if (IssueTypeEnum.TYPE_TASK.CODE.equals(issueType)) {
                    customizeIssue.setTaskReceiverName(ssoUser.getUserName());
                    customizeIssue.setTaskReceiverAccount(ssoUser.getUserAccount());
                }
            }
        }
    }

    /**
     * @param customizeIssue
     * @param projectId
     * @param stageId
     * @param type
     * @description 处理阶段信息
     * @date 2020/08/28
     */
    private void dealStage(CustomizeIssueDTO customizeIssue, Long projectId, Long stageId, int type) {
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        kanbanStageInstanceExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStageIdEqualTo(stageId)
                .andStateEqualTo(StateEnum.U.getValue());
        List<KanbanStageInstance> kanbanStageInstances = kanbanStageInstanceMapper.selectByExample(kanbanStageInstanceExample);
        if (CollectionUtils.isNotEmpty(kanbanStageInstances)) {
            KanbanStageInstance kanbanStageInstance = kanbanStageInstances.get(0);
            if (type == IssueTypeEnum.TYPE_TASK.CODE) {
                customizeIssue.setTaskStageId(kanbanStageInstance.getStageId());
                customizeIssue.setTaskStageName(kanbanStageInstance.getStageName());
            }
        }
    }

    /**
     * @param storyId
     * @param customizeIssue
     * @description 处理故事工作项
     * @date 2020/08/18
     */
    private void dealStoryIssue(Long storyId, CustomizeIssueDTO customizeIssue) {
        Issue story = issueService.selectIssueInfo(storyId, IssueTypeEnum.TYPE_STORY.CODE);
        if (null != story) {
            customizeIssue.setStoryId(storyId);
            customizeIssue.setStoryName(story.getTitle());
            //根据故事id查询迭代信息
            dealSprintInfo(story, customizeIssue);
            Long featureId = story.getParentId();
            if (null != featureId) {
                dealFeatureIssue(featureId, customizeIssue);
            }
        }
    }

    /**
     * @param story
     * @param customizeIssue
     * @description 处理迭代信息
     * @date 2020/08/18
     */
    private void dealSprintInfo(Issue story, CustomizeIssueDTO customizeIssue) {
        Long sprintId = story.getSprintId();
        if (null == sprintId) {
            return;
        }
        SprintExample sprintExample = new SprintExample();
        sprintExample.createCriteria().andSprintIdEqualTo(sprintId);
        List<Sprint> sprints = sprintMapper.selectByExample(sprintExample);
        if (CollectionUtils.isNotEmpty(sprints)) {
            Sprint sprint = sprints.get(0);
            customizeIssue.setSprintId(sprintId);
            customizeIssue.setSprintName(sprint.getSprintName());
            Byte status = sprint.getStatus();
            if (null != status) {
                customizeIssue.setSprintState(SprintStatusEnum.getName(status));
            }
        }
    }

    /**
     * @param featureId
     * @param customizeIssue
     * @description 处理研发需求工作项
     * @date 2020/08/18
     */
    private void dealFeatureIssue(Long featureId, CustomizeIssueDTO customizeIssue) {
        Issue feature = issueService.selectIssueInfo(featureId, IssueTypeEnum.TYPE_FEATURE.CODE);
        if (null != feature) {
            customizeIssue.setFeatureId(featureId);
            customizeIssue.setFeatureName(feature.getTitle());
            Long epicId = feature.getParentId();
            if (null != epicId) {
                dealEpicIssue(epicId, customizeIssue);
            }
        }
    }

    /**
     * @param epicId
     * @param customizeIssue
     * @description 处理业务需求工作项
     * @date 2020/08/18
     */
    private void dealEpicIssue(Long epicId, CustomizeIssueDTO customizeIssue) {
        Issue epic = issueService.selectIssueInfo(epicId, IssueTypeEnum.TYPE_EPIC.CODE);
        if (null != epic) {
            customizeIssue.setEpicId(epicId);
            customizeIssue.setEpicName(epic.getTitle());
            Long firstStageId = epic.getStageId();
            customizeIssue.setEpicStageId(firstStageId);
            customizeIssue.setEpicStageName(StageConstant.FirstStageEnum.getFirstStageName(firstStageId));
            SysExtendFieldDetail detail = getSysExtendFieldDetailExample(epicId);
            if (null != detail) {
                customizeIssue.setBizNum(detail.getValue());
            }
        }
    }

    /**
     * @return
     * @description 拼接系统扩展字段详情对象
     * @date 2020/09/21
     */
    private SysExtendFieldDetail getSysExtendFieldDetailExample(Long issueId) {
        SysExtendFieldDetail fieldDetail = null;
        SysExtendFieldDetailExample example = new SysExtendFieldDetailExample();
        example.setOrderByClause("id asc");
        example.createCriteria().andIssueIdEqualTo(issueId).andFieldIdEqualTo("bizNum");
        List<SysExtendFieldDetail> details = sysExtendFieldDetailMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(details)) {
            fieldDetail = details.get(0);
        }
        return fieldDetail;
    }

    /**
     * @param issueId
     * @return
     * @description 根据任务或缺陷编号查询feature
     * @date 2020/09/17
     */
    @Override
    public CustomizeIssueDTO getFeatureByTaskOrFaultId(Long issueId) {
        CustomizeIssueDTO issueDTO = splitCustomizeIssueByIssueId(issueId);
        LOGGER.info("根据任务或缺陷编号:{},查询到的feature:{}", issueId, issueDTO);
        return issueDTO;
    }
}