package com.yusys.agile.issue.utils;

import com.yusys.agile.commission.domain.SCommission;
import com.yusys.agile.commission.dto.SCommissionDTO;
import com.yusys.agile.commission.service.CommissionService;
import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.consumer.constant.AgileConstant;
import com.yusys.agile.consumer.dto.IssueMailSendDto;
import com.yusys.agile.fault.enums.FaultStatusEnum;
import com.yusys.agile.headerfield.enums.IsCustomEnum;
import com.yusys.agile.headerfield.service.HeaderFieldService;
import com.yusys.agile.issue.dao.IssueAcceptanceMapper;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.dao.SIssueRichtextMapper;
import com.yusys.agile.issue.domain.*;
import com.yusys.agile.issue.dto.IssueAttachmentDTO;
import com.yusys.agile.issue.dto.IssueCustomFieldDTO;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.*;
import com.yusys.agile.issue.service.*;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sysextendfield.domain.SysExtendField;
import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetail;
import com.yusys.agile.sysextendfield.service.SysExtendFieldDetailService;
import com.yusys.agile.sysextendfield.service.SysExtendFieldService;
import com.yusys.agile.sprint.enums.SprintStatusEnum;
import com.yusys.agile.utils.ObjectUtil;
import com.yusys.agile.versionmanager.constants.VersionConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeProjectApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Date: 13:34
 */
@Component
public class IssueFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueFactory.class);

    private static final String EMPTY_STR = "[]";

    private static final String DATE_FORMMAT = "yyyy-MM-dd";

    /**
     * 百分号
     */
    private static final String PERCENT_SIGN = "%";

    @Resource
    private IssueMapper issueMapper;
    @Resource
    private IFacadeProjectApi iFacadeProjectApi;
    @Resource
    private IssueAttachmentService issueAttachmentService;
    @Resource
    private IssueHistoryRecordService issueHistoryRecordService;
    @Resource
    private IssueCustomFieldService issueCustomFieldService;
    @Resource
    private HeaderFieldService headerFieldService;
    @Resource
    private IssueSystemRelpService issueSystemRelpService;
    @Resource
    private IFacadeUserApi iFacadeUserApi;
    @Resource
    private IssueAcceptanceMapper issueAcceptanceMapper;
    @Resource
    private IssueRichTextFactory issueRichTextFactory;
    @Resource
    private IssueModuleRelpService issueModuleRelpService;
    @Resource
    private IssueRuleFactory ruleFactory;
    @Resource
    private CommissionService commissionService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private SSprintMapper sSprintMapper;
    @Autowired
    private SysExtendFieldDetailService sysExtendFieldDetailService;
    @Resource
    private SysExtendFieldService sysExtendFieldService;
    @Resource
    private IssueTemplateService issueTemplateService;
    @Resource
    private SIssueRichtextMapper sIssueRichtextMapper;


    @Transactional(rollbackFor = Exception.class)
    public Long createIssue(IssueDTO issueDTO, String checkErrMsg, String newMsg, Byte issueType) {
        Issue issue = ReflectUtil.copyProperties(issueDTO, Issue.class);
        issue.setIssueType(issueType);

        //处理阶段
        Long[] stages = issueDTO.getStages();
        if (Optional.ofNullable(stages).isPresent()) {
            issue.setStageId(stages[0]);
            if (stages.length > 1) {
                issue.setLaneId(stages[1]);
            }
        }

        //处理任务
        if (IssueTypeEnum.TYPE_TASK.CODE.equals(issueType)) {
            //从故事获取迭代放入到任务上
            //故事ID
            Long parentId = issueDTO.getParentId();
            //获取迭代ID
            Issue story = issueMapper.selectByPrimaryKey(parentId);
            //如果有parentId，需要从parentId上查询systemId，sprintId，存入task 否则从本系统内
            List<Long> systemIds = Lists.newArrayList();
            if (null != story) {
                issue.setSprintId(story.getSprintId());
                issue.setSystemId(story.getSystemId());
                systemIds.add(story.getSystemId());
                issueDTO.setSystemId(story.getSystemId());
            } else {
                Long systemId = UserThreadLocalUtil.getUserInfo().getSystemId();
                systemIds.add(systemId);
                issueDTO.setSystemId(systemId);
            }
            issueDTO.setSystemIds(systemIds);
            issue.setReallyWorkload(0);
        }

        //处理预计工时，默认8小时且剩余工时等于预计工时
        Integer planWorklaod = issue.getPlanWorkload();
        planWorklaod = Optional.ofNullable(planWorklaod).orElse(8);
        issue.setPlanWorkload(planWorklaod);
        issue.setRemainWorkload(planWorklaod);
        issue.setReallyWorkload(0);

        if (null != issue.getSprintId()) {
            SSprintWithBLOBs sprint = sSprintMapper.selectByPrimaryKeyNotText(issue.getSprintId());
            if (null != sprint) {
                if (sprint.getStatus().equals(SprintStatusEnum.TYPE_FINISHED_STATE.CODE) ||
                        sprint.getStatus().equals(SprintStatusEnum.TYPE_CANCEL_STATE.CODE)) {
                    throw new BusinessException("只有未开始/进行中的迭代才能关联工作项");
                }
                if (sprint.getEndTime().before(new Date())) {
                    throw new BusinessException("迭代结束日期小于当前时间的迭代，不允许关联/取消关联用户故事");
                }
            }
        }
        issue.setUpdateTime(new Date());

        //如果系统为空取当前系统
        if (!Optional.ofNullable(issueDTO.getSystemId()).isPresent()) {
            Long systemId = UserThreadLocalUtil.getUserInfo().getSystemId();
            issue.setSystemId(systemId);
            List<Long> systemIds = Lists.newArrayList(systemId);
            issueDTO.setSystemIds(systemIds);
        }

        issueMapper.insertSelective(issue);

        /**  赋值issue ,保存富文本 */
        //处理富文本信息、验收标准
        if (StringUtils.isBlank(issueDTO.getDescription())) {
            IssueTemplate issueTemplate = issueTemplateService.getTemplateByProjectAndType(issueDTO.getSystemId(), issueType);
            if (Optional.ofNullable(issueTemplate).isPresent()) {
                issueDTO.setDescription(issueTemplate.getDescription());
            }
        }
        issueRichTextFactory.dealIssueRichText(issue.getIssueId(), issueDTO.getDescription(), issueDTO.getAcceptanceCriteria(), null);

        final Long issueId = issue.getIssueId();
        issueDTO.setIssueId(issueId);


        //处理附件信息
        List<IssueAttachment> attachments;
        try {
            attachments = ReflectUtil.copyProperties4List(issueDTO.getAttachments(), IssueAttachment.class);
        } catch (Exception e) {
            throw new BusinessException("附件转换失败！{}", e.getMessage());
        }
        int count = 0;
        if (CollectionUtils.isNotEmpty(attachments)) {
            for (IssueAttachment ba : attachments) {
                ba.setIssueId(issueId);
                ba.setUploadUid(issue.getCreateUid());
            }
            count = issueAttachmentService.createBatchAttachment(attachments);
            if (count != attachments.size()) {
                throw new BusinessException("新增附件失败！");
            }
        }

        // 处理自定义字段
        List<IssueCustomFieldDTO> list = issueDTO.getCustomFieldDetailDTOList();
        if (CollectionUtils.isNotEmpty(list)) {
            String json = JSON.toJSONString(list);
            createIssueCustomFields(issueId, json);
        }

        //处理历史记录
        count = createHistory(issueId, newMsg);
        if (count != 1) {
            throw new BusinessException("新增历史记录失败！");
        }

        //处理模块信息
        dealModules(issueId, issueDTO.getModuleIds());

        //处理系统信息
        dealSystems(issueId, issueDTO.getSystemIds());

        //发送邮件通知
        SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
        IssueMailSendDto issueMailSendDto = new IssueMailSendDto(issue, NumberConstant.ZERO, userInfo);
        rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE, issueMailSendDto);
        return issueId;
    }


    private void dealSystems(Long issueId, List<Long> systemIds) {
        if (CollectionUtils.isNotEmpty(systemIds)) {
            issueSystemRelpService.batchInsert(issueId, systemIds);
        }
    }

    private void dealModules(Long issueId, List<Long> moduleIds) {
        if (CollectionUtils.isNotEmpty(moduleIds)) {
            issueModuleRelpService.batchInsert(issueId, moduleIds);
        }
    }


    /**
     * @param issue
     * @return
     * @description 组装代办对象
     * @date 2020/07/09
     */
    public SCommissionDTO assembleCommissionObject(Issue issue) {
        SCommissionDTO sCommissionDTO = new SCommissionDTO();
        sCommissionDTO.setIssueId(issue.getIssueId());
        sCommissionDTO.setProjectId(issue.getProjectId());
        sCommissionDTO.setTitle(issue.getTitle());
        sCommissionDTO.setCurrentHandler(issue.getHandler());
        sCommissionDTO.setStageId(issue.getStageId());
        sCommissionDTO.setLaneId(issue.getLaneId());
        sCommissionDTO.setType(issue.getIssueType());
        sCommissionDTO.setState(StateEnum.U.getValue());
        return sCommissionDTO;
    }

    @ExceptionHandler(value = Throwable.class)
    public Issue editIssue(IssueDTO issueDTO, Issue oldIssue, Long projectId) {
        final Long issueId = issueDTO.getIssueId();
        final String state = oldIssue.getState();
        Byte issueType = oldIssue.getIssueType();
        Issue issue = null;
        if (IssueStateEnum.TYPE_VALID.CODE.equals(state)) {
            issue = ReflectUtil.copyProperties(issueDTO, Issue.class);
            issue.setParentId(oldIssue.getParentId());
            Long[] stages = issueDTO.getStages();

            /** 判断当前业务需求、研发需求、用户故事、子任务制品数是否大于阶段下的制品数 */
//            if (!IssueTypeEnum.TYPE_TASK.CODE.equals(issue.getIssueType()) && !IssueTypeEnum.TYPE_FAULT.CODE.equals(issue.getIssueType())) {
//                Long stageId = stages[0];
//                Long landId = null;
//                if (stages.length > 1) {
//                    landId = stages[1];
//                }
//                /** 判断工作项流转规则是否允许*/
//                if (!ruleFactory.getIssueRulesCheckFlag(issueType, oldIssue.getStageId(), oldIssue.getLaneId(), stageId, landId, projectId)) {
//                    throw new BaseBusinessException(601, "该工作项不允许流转到目标阶段！");
//                }
//                /** 判断泳道ID不为空的情况*/
//                if ((!stageId.equals(oldIssue.getStageId()) && !Optional.ofNullable(landId).isPresent())
//                        || !(stageId.equals(oldIssue.getStageId()) && (Optional.ofNullable(landId).isPresent()
//                        && landId.equals(oldIssue.getLaneId())))) {
//                    issueRichTextFactory.dealStagesProductLimit(stageId, landId, projectId, issueType);
//                }
//            }

            List<IssueHistoryRecord> history = new ArrayList<>();
            //处理阶段
            dealStages(issue, oldIssue, stages, history);

            //处理系统
            dealEditSystems(issueId, issueDTO.getSystemIds(), history);

            //处理模块
            dealEditModules(issueId, issueDTO.getModuleIds(), history);

            //组织工作项常规字段的变更记录
            history = generateHistory(issueDTO, oldIssue, history);

            //处理附件并组织附件的变更的历史记录
            dealAttachments(issueDTO, issueId, history);

            //更新自定义字段并组织自定义字段历史记录
            //old custom field value
            if (Optional.ofNullable(projectId).isPresent()) {

            }
            List<IssueCustomFieldDTO> fieldsBeforeEdit = issueCustomFieldService.listCustomField(issueId, issueType);
            List<IssueCustomFieldDTO> list = issueDTO.getCustomFieldDetailDTOList();
            if (CollectionUtils.isNotEmpty(list)) {
                    List<SIssueCustomField> fieldsAfterEdit = Lists.newArrayList();
                    // IssueCustomFieldDTO转换成IssueCustomField
                    for (IssueCustomFieldDTO temp : list) {
                        SIssueCustomField issueCustomField = new SIssueCustomField();
                        issueCustomField.setExtendId(temp.getDetailId());
                        issueCustomField.setFieldId(temp.getFieldId());
                        issueCustomField.setFieldValue(temp.getFieldValue());
                        issueCustomField.setIssueId(issueId);
                        fieldsAfterEdit.add(issueCustomField);
                    }
                    dealCustomFieldAndFieldHistory(issueId, history, fieldsBeforeEdit, fieldsAfterEdit, issueType);
                }


            //历史记录处理
            dealHistory(history);

            //富文本单独保存
            issueRichTextFactory.dealIssueRichText(issue.getIssueId(), issueDTO.getDescription(), issueDTO.getAcceptanceCriteria(), null);
            issue.setProjectId(projectId);

            issue.setIssueType(issueType);
            dealCommissionEdit(issueId);

            SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
            IssueMailSendDto issueMailSendDto = new IssueMailSendDto(issue, NumberConstant.ONE, userInfo);
            rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE, issueMailSendDto);
        }
        return issue;
    }

    public void dealCustomFieldAndFieldHistory( Long issueId, List<IssueHistoryRecord> history, List<IssueCustomFieldDTO> fieldsBeforeEdit, List<SIssueCustomField> fieldsAfterEdit, Byte issueType) {

        // 修改自定义字段明细数据
        issueCustomFieldService.editCustomFields(fieldsAfterEdit);

        List<IssueHistoryRecord> fieldHistory = headerFieldService.generateHistory(fieldsAfterEdit, fieldsBeforeEdit, issueType, issueId);
        if (CollectionUtils.isNotEmpty(fieldHistory)) {
            history.addAll(fieldHistory);
        }
    }

    private List<IssueHistoryRecord> dealEditModules(Long issueId, List<Long> newModuleList, List<IssueHistoryRecord> records) {
        List<IssueModuleRelp> oldModuleList = issueModuleRelpService.listIssueModuleRelp(issueId);
        List<Long> oldModuledList = new ArrayList<>();
        for (IssueModuleRelp issueModuleRelp : oldModuleList) {
            oldModuledList.add(issueModuleRelp.getModuleId());
        }
        if (CollectionUtils.isNotEmpty(newModuleList) && !CollectionUtils.isEqualCollection(oldModuledList, newModuleList)) {
            IssueHistoryRecord attachHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.MODULE.getDesc());
            try {
                attachHistory.setOldValue(JSON.toJSONString(oldModuledList));
                attachHistory.setNewValue(JSON.toJSONString(newModuleList));
            } catch (Exception e) {
                LOGGER.error("历史记录模块列表转换失败！");
            }
            records.add(attachHistory);
            issueModuleRelpService.deleteByIssueId(issueId);
            issueModuleRelpService.batchInsert(issueId, newModuleList);
        }
        return records;
    }

    private List<IssueHistoryRecord> dealEditSystems(Long issueId, List<Long> newSystemList, List<IssueHistoryRecord> records) {
        List<IssueSystemRelp> oldSystemList = issueSystemRelpService.listIssueSystemRelp(issueId);
        List<Long> oldSystemIdList = new ArrayList<>();
        for (IssueSystemRelp issueSystemRelp : oldSystemList) {
            oldSystemIdList.add(issueSystemRelp.getSystemId());
        }
        if (CollectionUtils.isNotEmpty(newSystemList) && !CollectionUtils.isEqualCollection(oldSystemIdList, newSystemList)) {
            IssueHistoryRecord systemHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.SYSTEM.getDesc());
            try {
                systemHistory.setOldValue(JSON.toJSONString(oldSystemIdList));
                systemHistory.setNewValue(JSON.toJSONString(newSystemList));
            } catch (Exception e) {
                LOGGER.error("历史记录系统列表转换失败！");
            }
            records.add(systemHistory);
            issueSystemRelpService.deleteByIssueId(issueId);
            issueSystemRelpService.batchInsert(issueId, newSystemList);
        }
        return records;
    }

    public List<IssueHistoryRecord> dealStages(Issue issue, Issue oldEpic, Long[] stages, List<IssueHistoryRecord> records) {
        if (null != stages) {
            issue.setStageId(stages[0]);
            //对状态处理，如果有二级状态更新，没有就清除
            if (stages.length > 1) {
                issue.setLaneId(stages[1]);
            } else {
                issue.setLaneId(null);
            }
            if (!ObjectUtil.equals(oldEpic.getStageId(), issue.getStageId())) {
                IssueHistoryRecord stageHistory = IssueHistoryRecordFactory.createHistoryRecord(issue.getIssueId(), IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.STAGEID.getDesc());
                if (null != oldEpic.getStageId()) {
                    String stageId = oldEpic.getStageId().toString();
                    if (Optional.ofNullable(oldEpic.getLaneId()).isPresent()) {
                        stageId = stageId + "-" + oldEpic.getLaneId();
                    }
                    stageHistory.setOldValue(stageId);
                }
                if (null != stages[0]) {
                    if (stages.length == 1) {
                        stageHistory.setNewValue(stages[0].toString());
                    } else {
                        stageHistory.setNewValue(stages[0] + "-" + stages[1]);
                    }
                }
                records.add(stageHistory);
            } else {
                if (stages.length > 1) {
                    if (!ObjectUtil.equals(oldEpic.getLaneId(), issue.getLaneId())) {
                        IssueHistoryRecord laneHistory = IssueHistoryRecordFactory.createHistoryRecord(issue.getIssueId(), IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.LANEID.getDesc());
                        if (null != oldEpic.getLaneId()) {
                            String stageId = oldEpic.getStageId().toString();
                            if (Optional.ofNullable(oldEpic.getLaneId()).isPresent()) {
                                stageId = stageId + "-" + oldEpic.getLaneId();
                            }
                            laneHistory.setOldValue(stageId);
                        }
                        if (null != stages[1]) {
                            laneHistory.setNewValue(stages[0] + "-" + stages[1]);
                        }
                        records.add(laneHistory);
                    }
                }

            }
        }
        return records;
    }

    public void dealHistory(List<IssueHistoryRecord> history) {
        int count;
        if (CollectionUtils.isNotEmpty(history)) {
            count = issueHistoryRecordService.createBatchHistory(history);
            if (count != history.size()) {
                throw new BusinessException("批量新增历史记录失败");
            }
        }
    }

    /**
     * @param issueId
     * @description 处理代办编辑
     * @date 2020/07/09
     */
    public void dealCommissionEdit(Long issueId) {
        LOGGER.info("dealCommissionEdit param issueId:{}", issueId);
        boolean exist = false;
        SCommission sCommission = commissionService.getCommissionByIssueId(issueId);
        if (Optional.ofNullable(sCommission).isPresent()) {
            exist = true;
        }
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        if (Optional.ofNullable(issue).isPresent()) {
            Long currentHandler = issue.getHandler();
            SCommissionDTO sCommissionDTO = assembleCommissionObject(issue);
            //工作项对应代办存在
            if (exist) {
                sCommissionDTO.setState(sCommission.getState());
                sCommissionDTO.setUpdateTime(new Date());
                sCommissionDTO.setUpdateUid(UserThreadLocalUtil.getUserInfo().getUserId());
                if (null != currentHandler) {
                    Long oldCurrentHandler = sCommission.getCurrentHandler();
                    //更新代办
                    if (Optional.ofNullable(currentHandler).isPresent() && !currentHandler.equals(oldCurrentHandler)) {
                        commissionService.updateCommission(sCommissionDTO);
                    } else if (Optional.ofNullable(oldCurrentHandler).isPresent() && !oldCurrentHandler.equals(currentHandler)) {
                        commissionService.updateCommission(sCommissionDTO);
                    }
                }
            }
            //工作项对应代办不存在
            else {
                //保存代办
                if (null != currentHandler) {
                    commissionService.saveCommission(sCommissionDTO);
                }
            }
        }
    }

    public void dealAttachments(IssueDTO issueDTO, Long issueId, List<IssueHistoryRecord> history) {
        // attachment
        List<IssueAttachment> attachments = new ArrayList<>();
        try {
            attachments = ReflectUtil.copyProperties4List(issueDTO.getAttachments(), IssueAttachment.class);
        } catch (Exception e) {
            LOGGER.error("附件列表转换失败！");
        }
        List<IssueAttachmentDTO> oldAttachments = issueAttachmentService.listIssueAttachment(issueId);
        boolean isEdit = false;
        int count;
        if (CollectionUtils.isNotEmpty(attachments)) {
            List<IssueAttachment> newAttachment = new ArrayList<>();
            List<IssueAttachment> updateAttachments = new ArrayList<>();
            for (IssueAttachmentDTO temp : oldAttachments) {
                if (!isContain(attachments, temp)) {
                    //删除该附件
                    count = issueAttachmentService.deleteAttach(temp.getAttachmentId());
                    if (count != 1) {
                        throw new BusinessException("删除附件失败！");
                    }
                    isEdit = true;
                }
            }
            for (IssueAttachment ba : attachments) {
                if (ba.getAttachmentId() == null) {
                    ba.setIssueId(issueId);
                    ba.setUploadUid(1L);
                    newAttachment.add(ba);
                } else {
                    updateAttachments.add(ba);
                }
            }

            if (!updateAttachments.isEmpty()) {
                int updateCount = 0;
                for (IssueAttachment attachment : updateAttachments) {
                    int num = issueAttachmentService.updateByPrimaryKeySelective(attachment);
                    updateCount = updateCount + num;
                }
                if (updateCount != updateAttachments.size()) {
                    throw new BusinessException("更新附件描述失败！");
                }
            }

            if (!newAttachment.isEmpty()) {
                count = issueAttachmentService.createBatchAttachment(newAttachment);
                if (count != newAttachment.size()) {
                    throw new BusinessException("创建附件失败！");
                }
                isEdit = true;
            }
        } else if (CollectionUtils.isNotEmpty(oldAttachments)) {
            issueAttachmentService.deleteAttachmentByIssueId(issueId);
            isEdit = true;
        }
        if (isEdit) {
            IssueHistoryRecord attachHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_ATTACHMENT.CODE, IssueField.ATTACHMENT.getDesc());
            try {
                attachHistory.setOldValue(JSON.toJSONString(ReflectUtil.copyProperties4List(oldAttachments, Attachment.class)));
                attachHistory.setNewValue(JSON.toJSONString(ReflectUtil.copyProperties4List(attachments, Attachment.class)));
            } catch (Exception e) {
                LOGGER.error("历史记录附件列表转换失败！{}", e.getMessage());
            }
            history.add(attachHistory);
        }
    }

    public void deleteIssue(Long issueId, Boolean deleteChild) {
        if (null != deleteChild && deleteChild) {
            //解除关联关系
            disassociate(issueId);
        }

        //删除附件
        issueAttachmentService.deleteAttachmentByIssueId(issueId);

        //删除自定义字段
        issueCustomFieldService.deleteCustomFileByIssueId(issueId);

        //更新历史记录表，状态从有效变为无效
        createHistory(issueId);

        //解除子工作项的关联关系
        Long sprintId = null;
        Issue storyIssue = issueMapper.selectByPrimaryKey(issueId);
        if (null != storyIssue) {
            Byte issueType = storyIssue.getIssueType();
            Assert.notNull(issueType, "issueId:[" + issueId + "]工作项类型不能为空");
            if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueType)) {
                sprintId = storyIssue.getSprintId();
            }
            //dealEpicFeatureData(storyIssue);
            //如果不删除子任务  处理子任务
            this.dealTaskData(issueId, deleteChild);
        }
        issueMapper.deleteAllChildRelation(issueId, sprintId);
        //更新工作项为失效
        upateIssue(issueId);

        commissionService.deleteCommission(issueId);

        Issue issue = issueMapper.selectByPrimaryKey(issueId);

        SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
        IssueMailSendDto issueMailSendDto = new IssueMailSendDto(issue, NumberConstant.TWO, userInfo);
        rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE, issueMailSendDto);
    }

    /**
     * 任务状态未领取，人删除，系统保留，迭代清空如果有
     *
     * @param issueId
     * @param deleteChild
     */
    private void dealTaskData(Long issueId, Boolean deleteChild) {
        if (!deleteChild) {
            IssueExample issueExample = new IssueExample();
            issueExample.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                    .andParentIdEqualTo(issueId);
            List<Issue> taskIssueList = issueMapper.selectByExample(issueExample);
            if (CollectionUtils.isNotEmpty(taskIssueList)) {
                for (Issue issue : taskIssueList) {
                    issue.setSprintId(null);
                    issue.setHandler(null);
                    issue.setStageId(StageConstant.FirstStageEnum.DEVELOP_STAGE.getValue());
                    issue.setLaneId(TaskStatusEnum.TYPE_ADD_STATE.CODE);
                    issueMapper.updateByPrimaryKey(issue);
                }
            }
        }
    }

    private void upateIssue(Long issueId) {
        Issue record = new Issue();
        record.setIssueId(issueId);
        record.setState(IssueStateEnum.TYPE_INVALID.CODE);
        issueMapper.updateByPrimaryKeySelective(record);
    }


    public void createHistory(Long issueId) {
        IssueHistoryRecord nameHistory = new IssueHistoryRecord();
        IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.TYPE.getDesc());
        nameHistory.setOldValue(IssueStateEnum.TYPE_VALID.CODE);
        nameHistory.setNewValue(IssueStateEnum.TYPE_INVALID.CODE);
        issueHistoryRecordService.createHistory(nameHistory);
    }

    public IssueDTO queryIssue(Long issueId, Long systemId) {
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        IssueDTO issueDTO = null;
        if (null != issue) {
            issueDTO = ReflectUtil.copyProperties(issue, IssueDTO.class);
//            SsoProject ssoProject = iFacadeProjectApi.getProjectInfoById(issue.getProjectId());
//            issueDTO.setProjectName(ssoProject.getProjectName());

            //系统
            List<Long> systemIds = new ArrayList<>();
            systemIds.add(issue.getSystemId());
            issueDTO.setSystemIds(systemIds);

            //模块
            List<Long> moduleIds = new ArrayList<>();
            List<IssueModuleRelp> listIssueModuleRelp = issueModuleRelpService.listIssueModuleRelp(issueId);
            for (IssueModuleRelp issuemoduleRelp : listIssueModuleRelp) {
                moduleIds.add(issuemoduleRelp.getModuleId());
            }
            issueDTO.setModuleIds(moduleIds);

            /** 处理 需校验是否满足最大在制品数量的限制，大于等于后不允许再次进入在阶段泳道上已分子/分母显示。分子表示当前泳道的卡片，分母表示设定的制品数量*/
            //dealStateMaxNumber(issueDTO,issue);
            /** 查询富文本 */
            issueRichTextFactory.queryIssueRichText(issueDTO);
            //处理处理人
            dealHandlerNameAndAccount(issue, issueDTO);

            //阶段
            Long[] stages;
            if (null != issue.getStageId()) {
                stages = new Long[1];
                stages[0] = issue.getStageId();
                if (null != issue.getLaneId()) {
                    stages = new Long[2];
                    stages[0] = issue.getStageId();
                    stages[1] = issue.getLaneId();
                }
                issueDTO.setStages(stages);
            }

            //查询附件信息
            List<IssueAttachmentDTO> issueAttachmentDTOList = issueAttachmentService.listIssueAttachment(issueId);
            issueDTO.setAttachments(issueAttachmentDTOList);

            //查询自定义字段
//            List<IssueCustomFieldDTO> issueCustomFieldDTOList = issueCustomFieldService.listCustomField(issueId, issue.getIssueType(), projectId);
//            issueDTO.setCustomFieldDetailDTOList(issueCustomFieldDTOList);
            //查询故事验收标准信息
            getAcceptanceList(issueId, issueDTO);

        }
        return issueDTO;
    }

    /**
     * @param issueId
     * @param issueDTO
     * @Date 2021/2/1
     * @Description 查询故事验收标准信息
     * @Return void
     */
    public void getAcceptanceList(Long issueId, IssueDTO issueDTO) {
        if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueDTO.getIssueType())) {
            SIssueRichtextExample issueRichtextExample = new SIssueRichtextExample();
            SIssueRichtextExample.Criteria criteria = issueRichtextExample.createCriteria();
            criteria.andIssueIdEqualTo(issueId).andStateEqualTo(StateEnum.U.getValue());
            List<SIssueRichtextWithBLOBs> sIssueRichtextWithBLOBs = sIssueRichtextMapper.selectByExampleWithBLOBs(issueRichtextExample);
            if (CollectionUtils.isNotEmpty(sIssueRichtextWithBLOBs)) {
                    SIssueRichtextWithBLOBs richText = sIssueRichtextWithBLOBs.get(0);
                    String acceptanceCriteria = richText.getAcceptanceCriteria();
                    issueDTO.setAcceptanceCriteria(acceptanceCriteria);
            }

//            IssueAcceptanceExample issueAcceptanceExample = new IssueAcceptanceExample();
//            IssueAcceptanceExample.Criteria criteria = issueAcceptanceExample.createCriteria();
//            criteria.andIssueIdEqualTo(issueId).andStateEqualTo(StateEnum.U.getValue());
//            List<IssueAcceptance> issueAcceptances = issueAcceptanceMapper.selectByExample(issueAcceptanceExample);
//            List<IssueAcceptanceDTO> issueAcceptanceDTOS = new ArrayList<>();
//            if (CollectionUtils.isNotEmpty(issueAcceptances)) {
//                for (IssueAcceptance issueAcceptance : issueAcceptances) {
//                    IssueAcceptanceDTO issueAcceptanceDTO = ReflectUtil.copyProperties(issueAcceptance, IssueAcceptanceDTO.class);
//                    issueAcceptanceDTOS.add(issueAcceptanceDTO);
//                    issueDTO.setIssueAcceptanceDTOS(issueAcceptanceDTOS);
//                }
//            }
        }
    }

    public void dealHandlerNameAndAccount(Issue issue, IssueDTO issueDTO) {
        Long handler = issue.getHandler();
        if (null != handler) {
            try {
                SsoUser ssoUser = iFacadeUserApi.queryUserById(handler);
                issueDTO.setHandlerName(ssoUser.getUserName());
                issueDTO.setHandlerAccount(ssoUser.getUserAccount());
            } catch (Exception e) {
                LOGGER.error("调用门户用户微服务失败:{}", e.toString());
            }
        }
    }

    private List<IssueHistoryRecord> generateHistory(IssueDTO issueDTO, Issue oldIssue, List<IssueHistoryRecord> records) {
        Long issueId = issueDTO.getIssueId();
        /** 标题 */
        if (!ObjectUtil.equals(issueDTO.getTitle(), oldIssue.getTitle())) {
            IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.TITLE.getDesc());
            if (null != oldIssue.getTitle()) {
                nameHistory.setOldValue(oldIssue.getTitle());
            }
            if (null != issueDTO.getTitle()) {
                nameHistory.setNewValue(issueDTO.getTitle());
            }
            records.add(nameHistory);
        }
        /** 迭代ID */
        if (!ObjectUtil.equals(issueDTO.getSprintId(), oldIssue.getSprintId())) {
            IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.SPRINT.getDesc());
            if (null != oldIssue.getSprintId()) {
                nameHistory.setOldValue(String.valueOf(oldIssue.getSprintId()));
            }
            if (null != issueDTO.getSprintId()) {
                nameHistory.setNewValue(String.valueOf(issueDTO.getSprintId()));
            }
            records.add(nameHistory);
        }
        /** 预计工时 */
        if (!ObjectUtil.equals(issueDTO.getPlanWorkload(), oldIssue.getPlanWorkload())) {
            IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.PLANWORKLOAD.getDesc());
            if (null != oldIssue.getPlanWorkload()) {
                nameHistory.setOldValue(String.valueOf(oldIssue.getPlanWorkload()));
            }
            if (null != issueDTO.getPlanWorkload()) {
                nameHistory.setNewValue(String.valueOf(issueDTO.getPlanWorkload()));
            }
            records.add(nameHistory);
        }
        /** 优先级 */
        if (!ObjectUtil.equals(issueDTO.getPriority(), oldIssue.getPriority())) {
            IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.priority.getDesc());
            if (null != oldIssue.getPriority()) {
                nameHistory.setOldValue(String.valueOf(oldIssue.getPriority()));
            }
            if (null != issueDTO.getPriority()) {
                nameHistory.setNewValue(String.valueOf(issueDTO.getPriority()));
            }
            records.add(nameHistory);
        }
        /** 重要程度 */
        if (!ObjectUtil.equals(issueDTO.getImportance(), oldIssue.getImportance())) {
            IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.IMPORTANCE.getDesc());
            if (null != oldIssue.getImportance()) {
                nameHistory.setOldValue(String.valueOf(oldIssue.getImportance()));
            }
            if (null != issueDTO.getImportance()) {
                nameHistory.setNewValue(String.valueOf(issueDTO.getImportance()));
            }
            records.add(nameHistory);
        }
        /** 结束时间 */
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMMAT);
        if (!ObjectUtil.equals(issueDTO.getEndDate(), oldIssue.getEndDate())) {
            IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.ENDDATE.getDesc());
            if (null != oldIssue.getEndDate()) {
                nameHistory.setOldValue(formatter.format(oldIssue.getEndDate()));
            }
            if (null != issueDTO.getEndDate()) {
                nameHistory.setNewValue(formatter.format(issueDTO.getEndDate()));
            }
            records.add(nameHistory);
        }
        /** 开始时间 */
        if (!ObjectUtil.equals(issueDTO.getBeginDate(), oldIssue.getBeginDate())) {
            IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.BEGINDATE.getDesc());
            if (null != oldIssue.getBeginDate()) {
                nameHistory.setOldValue(formatter.format(oldIssue.getBeginDate()));
            }
            if (null != issueDTO.getBeginDate()) {
                nameHistory.setNewValue(formatter.format(issueDTO.getBeginDate()));
            }
            records.add(nameHistory);
        }
        /** 处理人 */
        if (!ObjectUtil.equals(issueDTO.getHandler(), oldIssue.getHandler())) {
            IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.HANDLER.getDesc());
            if (null != oldIssue.getHandler()) {
                nameHistory.setOldValue(oldIssue.getHandler().toString());
            }
            if (null != issueDTO.getHandler()) {
                nameHistory.setNewValue(issueDTO.getHandler().toString());
            }
            records.add(nameHistory);
        }
        /** 富文本 */
        SIssueRichtextWithBLOBs issueRichText = issueRichTextFactory.getIssueRichText(issueDTO.getIssueId());
        if (Optional.ofNullable(issueRichText).isPresent()) {
            if (!ObjectUtil.equals(issueDTO.getDescription(), issueRichText.getDescription())) {
                IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_RICH_TEXT.CODE, IssueField.DESCRIPTION.getDesc());
                if (null != issueRichText.getDescription()) {
                    nameHistory.setOldValue(issueRichText.getDescription());
                }
                if (null != issueDTO.getDescription()) {
                    nameHistory.setNewValue(issueDTO.getDescription());
                }
                records.add(nameHistory);
            }
        }
        /** 业务价值 */
        if (!ObjectUtil.equals(issueDTO.getOrder(), oldIssue.getOrder())) {
            IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.ORDER.getDesc());
            if (null != oldIssue.getOrder()) {
                nameHistory.setOldValue(oldIssue.getOrder().toString());
            }
            if (null != issueDTO.getOrder()) {
                nameHistory.setNewValue(issueDTO.getOrder().toString());
            }
            records.add(nameHistory);
        }

        /** 完成度 */
        if (!ObjectUtil.equals(issueDTO.getCompletion(), oldIssue.getCompletion())) {
            IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.COMPLETION.getDesc());
            if (null != oldIssue.getCompletion()) {
                nameHistory.setOldValue(String.valueOf(oldIssue.getCompletion()));
            }
            if (null != issueDTO.getCompletion()) {
                nameHistory.setNewValue(String.valueOf(issueDTO.getCompletion()));
            }
            records.add(nameHistory);
        }
        /**
         * 实际工时
         */
        if (!ObjectUtil.equals(issueDTO.getReallyWorkload(), oldIssue.getReallyWorkload())) {
            IssueHistoryRecord nameHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, IssueField.REALLYWORKLOAD.getDesc());
            if (null != oldIssue.getReallyWorkload()) {
                nameHistory.setOldValue(String.valueOf(oldIssue.getReallyWorkload()));
            }
            if (null != issueDTO.getReallyWorkload()) {
                nameHistory.setNewValue(String.valueOf(issueDTO.getReallyWorkload()));
            }
            records.add(nameHistory);
        }
        return records;
    }

    private boolean isContain(List<IssueAttachment> attachments, IssueAttachmentDTO attachment) {
        boolean res = false;
        for (IssueAttachment ba : attachments) {
            if (attachment.getAttachmentId().equals(ba.getAttachmentId())) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * @Date: 10:12
     * @Description: 删除子任务
     * @Param: * @param issueId
     * @Return: void
     */
    private void disassociate(Long issueId) {
        //查询子工作项
        IssueExample example = new IssueExample();
        IssueExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(issueId);
        List<Issue> listChildren = issueMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(listChildren)) {
            for (Issue childIssue : listChildren) {
                Long childIssueId = childIssue.getIssueId();
                //删除附件
                issueAttachmentService.deleteAttachmentByIssueId(childIssueId);
                //删除自定义字段
                issueCustomFieldService.deleteCustomFileByIssueId(childIssueId);
                //置为失效
                childIssue.setIssueId(childIssueId);
                childIssue.setState(IssueStateEnum.TYPE_INVALID.CODE);
                issueMapper.updateByPrimaryKeySelective(childIssue);

                disassociate(childIssueId);
            }
        }

    }

    /**
     * 需求编号: task_200409_174822
     *
     * @Description: 批量新增自定义字段
     */
    private void createIssueCustomFields(Long issueId, String json) {
        if (!StringUtils.isEmpty(json) && !EMPTY_STR.equals(json)) {
            List<FieldJson> fieldJsonDTOS = JSON.parseArray(json, FieldJson.class);
            if (fieldJsonDTOS != null && !fieldJsonDTOS.isEmpty()) {
                List<SIssueCustomField> fields = new ArrayList<>();
                for (FieldJson fieldJsonDTO : fieldJsonDTOS) {
                    createIssueCustomField(issueId, fields, fieldJsonDTO.getFieldId(), fieldJsonDTO.getFieldValue());
                }
                if (issueCustomFieldService.createBatch(fields) <= 0) {
                    throw new BusinessException("新增工作项自定义字段失败！");
                }
            }
        }
    }


    private void createIssueCustomField(Long issueId, List<SIssueCustomField> fields, Long fieldId, String fieldValue) {
        SIssueCustomField issueCustomField = new SIssueCustomField();
        issueCustomField.setIssueId(issueId);
        issueCustomField.setFieldId(fieldId);
        issueCustomField.setFieldValue(fieldValue);
        fields.add(issueCustomField);
    }

    /**
     * 需求编号: task_200409_174822
     *
     * @Description: 新增历史记录
     */
    private int createHistory(Long issueId, String msg) {
        IssueHistoryRecord history = new IssueHistoryRecord();
        history.setOperationField(msg);
        history.setIssueId(issueId);
        history.setNewValue(msg);
        history.setIsCustom(NumberConstant.ZERO.byteValue());
        history.setRecordType(NumberConstant.ZERO.byteValue());
        int count = issueHistoryRecordService.createHistory(history);
        return count;
    }


    /**
     * @Date: 9:42
     * @Description: 复制工作项
     * @Param: * @param epicId
     * @Return: void
     */
    @Transactional(rollbackFor = Exception.class)
    public Long copyIssue(Long issueId, Long projectId, String errMsg, String checkErrMsg, String newMsg, Byte issueType) {
        IssueExample example = new IssueExample();
        IssueExample.Criteria criteria = example.createCriteria();
        criteria.andIssueIdEqualTo(issueId);
        criteria.andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        List<Issue> issueList = issueMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(issueList)) {
            throw new BusinessException(errMsg);
        }
        Issue issue = issueList.get(0);

        IssueDTO issueDTO = ReflectUtil.copyProperties(issue, IssueDTO.class);
        /** 查询当前Issue的富文本内容,并赋值 */
        issueRichTextFactory.queryIssueRichText(issueDTO);
        issueDTO.setIssueId(null);

        //查询自定义字段并塞入对象中
        if (Optional.ofNullable(projectId).isPresent()) {
            List<IssueCustomFieldDTO> issueCustomFieldDTOList = issueCustomFieldService.listCustomField(issueId, issue.getIssueType());
            issueDTO.setCustomFieldDetailDTOList(issueCustomFieldDTOList);
        }
        //查询工作项和产品关系表并保存
        List<IssueSystemRelp> issueSystemRelpList = issueSystemRelpService.listIssueSystemRelp(issueId);
        if (CollectionUtils.isNotEmpty(issueSystemRelpList)) {
            issueDTO.setSystemIds(issueSystemRelpList.stream().map(IssueSystemRelp::getSystemId).collect(Collectors.toList()));
        }

        Long newIssueId = createIssue(issueDTO, checkErrMsg, newMsg, issueType);
        //处理扩展字段
        dealSysExtendFieldDetail(issueId, newIssueId, issueType);
        return newIssueId;
    }

    private void dealSysExtendFieldDetail(Long issueId, Long newIssueId, Byte issueType) {
        List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailService.getSysExtendFieldDetail(issueId);
        if (CollectionUtils.isNotEmpty(sysExtendFieldDetailList)) {
            for (SysExtendFieldDetail sysExtendFieldDetail : sysExtendFieldDetailList) {
                sysExtendFieldDetail.setIssueId(newIssueId);
                if (VersionConstants.SysExtendFiledConstant.BIZNUM.equals(sysExtendFieldDetail.getFieldId())) {
                    String bizNum = sysExtendFieldDetail.getValue();
                    bizNum = bizNum.substring(0, bizNum.lastIndexOf("_") + 1) + newIssueId.toString();
                    sysExtendFieldDetail.setValue(bizNum);
                }

                if (VersionConstants.SysExtendFiledConstant.PLANSTATES.equals(sysExtendFieldDetail.getFieldId())) {
                    sysExtendFieldDetail.setValue(PlanStatesEnum.ON_TIME.CODE);
                }

                if (VersionConstants.SysExtendFiledConstant.ACTUAL_ONLINE_TIME.equals(sysExtendFieldDetail.getFieldId())) {
                    sysExtendFieldDetail.setValue("");
                }

                if (VersionConstants.SysExtendFiledConstant.RELATEDSYSTEM.equals(sysExtendFieldDetail.getFieldId())) {
                    sysExtendFieldDetail.setValue("");
                }

                if (VersionConstants.SysExtendFiledConstant.APPROVAL_START_TIME.equals(sysExtendFieldDetail.getFieldId())) {
                    sysExtendFieldDetail.setValue("");
                }

                if (VersionConstants.SysExtendFiledConstant.APPROVAL_END_TIME.equals(sysExtendFieldDetail.getFieldId())) {
                    sysExtendFieldDetail.setValue("");
                }

                if (VersionConstants.SysExtendFiledConstant.APPROVAL_STATUS.equals(sysExtendFieldDetail.getFieldId())) {
                    sysExtendFieldDetail.setValue("");
                }

                if (VersionConstants.SysExtendFiledConstant.APPROVAL_RESULT.equals(sysExtendFieldDetail.getFieldId())) {
                    sysExtendFieldDetail.setValue("");
                }
            }
            sysExtendFieldDetailService.batchSave(sysExtendFieldDetailList);
        }

    }

    /**
     * @Date: 13:24
     * @Description: 查询未关联的子工作项
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    public List<IssueDTO> queryUnlinkedIssue(Long projectId, Byte issueType, Integer pageNum, Integer pageSize, String title, Long parentId) {
        return getIssueDTOS(projectId, issueType, pageNum, pageSize, false, title, parentId);
    }

    /**
     * @param issueType
     * @param pageNum
     * @param pageSize
     * @param parentId
     * @Date: 2021/2/3 14:59
     * @Description: 查询所有子工作项
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    public List<IssueDTO> queryAllIssue(Long projectId, Byte issueType, Integer pageNum, Integer pageSize, String title, Long parentId) {
        return getIssueDTOS(projectId, issueType, pageNum, pageSize, true, title, parentId);
    }

    public List<IssueDTO> getIssueDTOS(Long projectId, Byte issueType, Integer pageNum, Integer pageSize, boolean isAll, String title, Long parentId) {
        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }

        IssueExample example = new IssueExample();
        IssueExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        criteria.andIssueTypeEqualTo(issueType);

        if (null != projectId) {
            criteria.andProjectIdEqualTo(projectId);
        }

        if (null != parentId) {
            criteria.andParentIdEqualTo(parentId);
        }

        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike(PERCENT_SIGN + title + PERCENT_SIGN);
        }
        //true表示全量查询，false表示只查询未关联父工作项的
        if (!isAll) {
            criteria.andParentIdIsNull();
        }
        example.setOrderByClause("`order` desc,create_time desc");
        List<IssueDTO> issueDTOList = issueMapper.selectByExampleDTO(example);
        List<Long> handlers = new ArrayList<>();
        setHandlersAndStageName(issueDTOList, handlers, issueType);
        setHandlerName(handlers, issueDTOList);
        return issueDTOList;
    }

    /**
     * @param handlers
     * @param issueType
     * @Date: 2021/2/4 16:11
     * @Description: 设置处理人id列表及状态名称
     * @Param: * @param issueDTOList
     * @Return: void
     */
    public void setHandlersAndStageName(List<IssueDTO> issueDTOList, List<Long> handlers, Byte issueType) {
        for (IssueDTO issueDTO : issueDTOList) {
            if (null != issueDTO.getHandler()) {
                handlers.add(issueDTO.getHandler());
            }

            if (IssueTypeEnum.TYPE_TASK.CODE.equals(issueType) || IssueTypeEnum.TYPE_FAULT.CODE.equals(issueType)) {
                if (IssueTypeEnum.TYPE_TASK.CODE.equals(issueType)) {
                    issueDTO.setStageName(TaskStatusEnum.getName(issueDTO.getStageId()));
                } else {
                    issueDTO.setStageName(FaultStatusEnum.getMsg(issueDTO.getStageId()));
                }
            } else {
                if (StringUtils.isNotBlank(issueDTO.getLaneName()) && StringUtils.isNotBlank(issueDTO.getStageName())) {
                    issueDTO.setStageName(issueDTO.getStageName() + "/" + issueDTO.getLaneName());
                }
            }
        }
    }

    /**
     * @param issueDTOList
     * @Date: 2021/2/3 14:10
     * @Description: 处理人名称设置
     * @Param: * @param handlers
     * @Return: void
     */
    public void setHandlerName(List<Long> handlers, List<IssueDTO> issueDTOList) {
        List<SsoUser> ssoUserList = iFacadeUserApi.listUsersByIds(handlers);
        for (IssueDTO issueDTO : issueDTOList) {
            for (SsoUser ssoUser : ssoUserList) {
                if (null != issueDTO.getHandler() && null != ssoUser.getUserId() && issueDTO.getHandler().equals(ssoUser.getUserId())) {
                    issueDTO.setHandlerName(ssoUser.getUserName());
                    break;
                }
            }
        }

    }

    /**
     * 功能描述: 根据业务需求或研发需求查询对应的故事id集合
     *
     * @param id   业需或研需id
     * @param type 1 业务需求 2研发需求
     * @return java.lang.Object
     * @date 2021/2/22
     */
    public List<Long> queryStroyIds(Long id, Byte type) {
        if (IssueTypeEnum.TYPE_EPIC.CODE.equals(type)) {
            return issueMapper.getStoryIdsByEpic(id);
        } else if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(type)) {
            return issueMapper.getStroyIdsByFeature(id);
        }
        return new ArrayList();
    }

    /**
     * 根据工作项id查询项目id
     *
     * @param issueId
     * @return
     */
    public Long getProjectIdByIssueId(Long issueId) {
        Long systemId = null;
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        if (null != issue) {
            systemId = issue.getSystemId();
        }
        return systemId;
    }

    public void batchSaveOrUpdateSysExtendFieldDetail(JSONObject jsonObject, IssueDTO issueDTO) {
        LOGGER.info("接收到前端的参数如下：{}", jsonObject);
        Long issueId = issueDTO.getIssueId();

        //操作历史记录
        List<IssueHistoryRecord> recordHistory = new ArrayList<>();
        //获取扩展字段
        List<SysExtendField> sysExtendFieldList = sysExtendFieldService.getAllSysExtendField(issueDTO.getIssueType());
        Map<String, List<SysExtendField>> listMap = sysExtendFieldList.stream().collect(Collectors.groupingBy(SysExtendField::getFieldId));
        Iterator it = jsonObject.keySet().iterator();
        List<SysExtendFieldDetail> sysExtendFieldDetailList = Lists.newArrayList();
        while (it.hasNext()) {
            String key = it.next().toString();
            if (listMap.containsKey(key)) {
                List<SysExtendField> sysExtendFieldList1 = listMap.get(key);
                for (int i = 0; i < sysExtendFieldList1.size(); i++) {
                    if (StringUtils.isNotEmpty(jsonObject.getString(key)) && !"[]".equals(jsonObject.getString(key))) {
                        SysExtendFieldDetail sysExtendFieldDetail = new SysExtendFieldDetail();
                        sysExtendFieldDetail.setIssueId(issueId);
                        sysExtendFieldDetail.setFieldId(sysExtendFieldList1.get(i).getFieldId());
                        sysExtendFieldDetail.setFieldName(sysExtendFieldList1.get(i).getFieldName());
                        sysExtendFieldDetail.setValue(jsonObject.getString(key));
                        sysExtendFieldDetail.setState(IssueStateEnum.getName(IssueStateEnum.TYPE_VALID.CODE));
                        sysExtendFieldDetailList.add(sysExtendFieldDetail);
                    }
                }
            }
        }

        if (CollectionUtils.isEmpty(sysExtendFieldDetailList)) {
            return;
        }

        //更新
        List<SysExtendFieldDetail> sysExtendFieldDetailList1 = sysExtendFieldDetailService.getSysExtendFieldDetail(issueDTO.getIssueId());
        if (CollectionUtils.isEmpty(sysExtendFieldDetailList1)) {
            //批量新增
            sysExtendFieldDetailService.batchSave(sysExtendFieldDetailList);
        } else {
            Map<String, List<SysExtendFieldDetail>> updateListMap = sysExtendFieldDetailList1.stream().collect(Collectors.groupingBy(SysExtendFieldDetail::getFieldId));
            List<SysExtendFieldDetail> sysExtendFieldDetailListUpdate = Lists.newArrayList();
            List<SysExtendFieldDetail> sysExtendFieldDetailListAdd = Lists.newArrayList();
            /**
             * 测试负责人历史记录生成
             */
            dealIssueSysExtendFieldDetailHistory(issueId, updateListMap, sysExtendFieldDetailList, recordHistory);

            for (SysExtendFieldDetail sysExtendFieldDetail : sysExtendFieldDetailList) {
                sysExtendFieldDetail.setIssueId(issueId);
                //如果存在就走更新，否则批量插入。
                if (updateListMap.containsKey(sysExtendFieldDetail.getFieldId())) {
                    //过滤出表中已有数据
                    sysExtendFieldDetailListUpdate.add(sysExtendFieldDetail);
                    //对sysExtendFieldDetailListUpdate批量更新
                    sysExtendFieldDetailService.batchUpdate(sysExtendFieldDetailListUpdate);

                } else {
                    //批量新增
                    sysExtendFieldDetailListAdd.add(sysExtendFieldDetail);

                    sysExtendFieldDetailService.batchSave(sysExtendFieldDetailListAdd);
                }
            }
        }
        //历史记录处理
        dealHistory(recordHistory);
    }

    /**
     * @param issueId                  工作项ID
     * @param updateListMap            工作项Old数据
     * @param sysExtendFieldDetailList 更新的工作项New数据
     * @param recordHistory            历史记录
     */
    private void dealIssueSysExtendFieldDetailHistory(Long issueId, Map<String, List<SysExtendFieldDetail>> updateListMap, List<SysExtendFieldDetail> sysExtendFieldDetailList, List<IssueHistoryRecord> recordHistory) {
        sysExtendFieldDetailList.forEach(sysExtendFieldDetail -> {
            String fieldId = sysExtendFieldDetail.getFieldId();
            String newValue = sysExtendFieldDetail.getValue();
            List<SysExtendFieldDetail> sysExtendFieldDetailsMap = updateListMap.get(fieldId);
            if (IssueField.EXTERNALHANDLERID.getKey().equals(fieldId)) {
                setCommonRecordHistory(issueId, fieldId, newValue, IssueField.EXTERNALHANDLERID.getDesc(), sysExtendFieldDetailsMap, recordHistory);
            }
            if (IssueField.DEVLOPMANAGER.getKey().equals(fieldId)) {
                setCommonRecordHistory(issueId, fieldId, newValue, IssueField.DEVLOPMANAGER.getDesc(), sysExtendFieldDetailsMap, recordHistory);
            }
        });
    }

    private void setCommonRecordHistory(Long issueId, String fieldId, String newValue, String fieldDesc, List<SysExtendFieldDetail> sysExtendFieldDetailsMap, List<IssueHistoryRecord> recordHistory) {
        String oldValue = "";
        if (CollectionUtils.isNotEmpty(sysExtendFieldDetailsMap)) {
            SysExtendFieldDetail sysExt = sysExtendFieldDetailsMap.get(0);
            oldValue = sysExt.getValue();
        }
        if (!ObjectUtil.equals(oldValue, newValue)) {
            IssueHistoryRecord sysExtHistory = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.FALSE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, fieldDesc);
            sysExtHistory.setOldValue(oldValue);
            sysExtHistory.setNewValue(newValue);
            recordHistory.add(sysExtHistory);
        }

    }

    /**
     * 功能描述 feature变更为/拖到需求分析分析完成状态时，需校验feature是否在版本内
     *
     * @param stages
     * @param featureId
     * @param epicId
     * @return boolean
     * @date 2020/10/27
     */
    public boolean checkFeatureInVersion(Long[] stages, Long featureId, Long epicId) {
        return false;
    }


    public Long getEpicIdForPlanDeployDate(String issueId, String bizNum) {
        Long epicId = null;
        if (issueId.indexOf("CRM") != -1) {
            SysExtendFieldDetail sysExtendFieldDetail = sysExtendFieldDetailService.getEpicIdByBizNum(issueId);
            if (!Optional.ofNullable(sysExtendFieldDetail).isPresent()) {
                throw new BusinessException("根据客户需求编号：" + issueId + "找不到对应的客户需求");
            } else {
                epicId = sysExtendFieldDetail.getIssueId();
            }
        } else if (issueId.indexOf("BOSS") != -1) {
            epicId = getEpicIdForPlanDeployDate(bizNum, null);
        } else {
            Long lIssueId = Long.parseLong(issueId);
            Issue issue = issueMapper.selectByPrimaryKey(lIssueId);
            if (null != issue) {
                epicId = getEpicId(lIssueId, issue.getIssueType());
            }
        }
        return epicId;
    }

    public Long getEpicId(Long issueId, Byte issueType) {
        Long epicId = null;
        IssueTypeEnum issueTypeEnum = IssueTypeEnum.getByCode(issueType);
        switch (issueTypeEnum) {
            case TYPE_EPIC:
                epicId = issueId;
                break;
            case TYPE_FEATURE:
                epicId = getEpic(issueId);
                break;
            case TYPE_STORY:
                epicId = getEpic(getEpic(issueId));
                break;
            case TYPE_TASK:
                epicId = getEpic(getEpic(getEpic(issueId)));
                break;
            case TYPE_FAULT:
                epicId = getEpic(getEpic(getEpic(issueId)));
                break;
        }
        return epicId;
    }

    private Long getEpic(Long issueId) {
        if (Optional.ofNullable(issueId).isPresent()) {
            Issue issue = issueMapper.selectByPrimaryKey(issueId);
            if (null != issue) {
                return issue.getParentId();
            } else {
                return null;
            }
        }
        return null;
    }

}
