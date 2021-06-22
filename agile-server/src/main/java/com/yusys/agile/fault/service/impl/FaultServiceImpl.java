package com.yusys.agile.fault.service.impl;

import com.yusys.agile.commission.dto.SCommissionDTO;
import com.yusys.agile.commission.service.CommissionService;
import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.constant.StringConstant;
import com.yusys.agile.consumer.constant.AgileConstant;
import com.yusys.agile.consumer.dto.IssueMailSendDto;
import com.yusys.agile.fault.dao.FaultLevelMapper;
import com.yusys.agile.fault.dao.FaultTypeMapper;
import com.yusys.agile.fault.domain.FaultLevel;
import com.yusys.agile.fault.domain.FaultLevelExample;
import com.yusys.agile.fault.domain.FaultType;
import com.yusys.agile.fault.domain.FaultTypeExample;
import com.yusys.agile.fault.dto.FaultFixDTO;
import com.yusys.agile.fault.dto.FaultStatusDTO;
import com.yusys.agile.fault.dto.UserDTO;
import com.yusys.agile.fault.service.FaultService;
import com.yusys.agile.headerfield.dao.HeaderFieldMapper;
import com.yusys.agile.headerfield.domain.HeaderField;
import com.yusys.agile.headerfield.domain.HeaderFieldExample;
import com.yusys.agile.headerfield.util.HeaderFieldUtil;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.SIssueCustomField;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.issue.dto.IssueCustomFieldDTO;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueCustomFieldService;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.issue.utils.IssueRichTextFactory;
import com.yusys.agile.issue.utils.IssueRuleFactory;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprint.service.SprintService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yusys.agile.fault.enums.*;
import com.yusys.agile.utils.YuItUtil;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.date.DateUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 缺陷service实现类
 *
 * @create 2020-04-10 16:28
 */
@Service("faultService")
public class FaultServiceImpl implements FaultService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FaultServiceImpl.class);

    @Resource
    private FaultLevelMapper faultLevelMapper;

    @Resource
    private FaultTypeMapper faultTypeMapper;

    @Resource
    private IssueMapper issueMapper;

    @Autowired
    private IFacadeUserApi iFacadeUserApi;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueRichTextFactory issueRichTextFactory;

    @Autowired
    private IssueRuleFactory ruleFactory;
    @Resource
    private HeaderFieldMapper headerFieldMapper;

    private static final String CREATE_TIME_DESC = "CREATE_TIME DESC";

    @Resource
    private CommissionService commissionService;

    @Resource
    private IssueFactory issueFactory;

    @Resource
    private IssueCustomFieldService issueCustomFieldService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 分页阈值
     */
    private static final int THRESHOLD = 1000;

    /**
     * 默认个数为0
     */
    private static final int DEFAULT_NUMBER_ZERO = 0;

    private static final String TOTAL = "total";

    /**
     * 修复率
     */
    private static final String REPAIR_RATE = "repairRate";

    @Override
    public void addFault(IssueDTO issueDTO) {
        Issue issue = ReflectUtil.copyProperties(issueDTO, Issue.class);
        issue.setIssueType(IssueTypeEnum.TYPE_FAULT.CODE);
        issue.setState(StateEnum.U.getValue());
        issue.setCreateTime(new Date());
        issue.setStageId(FaultStatusEnum.NEW.getCode());
        if (null == issueDTO.getSource()) {
            // 本系统
            issue.setSource(FaultSourceEnum.YuDO2.CODE);
        } else {
            issue.setSource(issueDTO.getSource());
        }

        issueMapper.insertSelective(issue);

        // todo 附件

        SCommissionDTO sCommissionDTO = issueFactory.assembleCommissionObject(issue);
        commissionService.saveCommission(sCommissionDTO);

        //邮件发送
        SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
        IssueMailSendDto issueMailSendDto = new IssueMailSendDto(issue, NumberConstant.ZERO, userInfo);
        rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE, issueMailSendDto);

    }

    /**
     * 功能描述: 删除缺陷
     *
     * @param issueId
     * @return void
     * @date 2020/4/11
     */
    @Override
    public void deleteFault(Long issueId) {
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        // 逻辑删除缺陷
        issue.setState(StateEnum.E.getValue());
        issueMapper.updateByPrimaryKeySelective(issue);

        // todo 删除历史记录
        // todo 删除附件

       // commissionService.updateCommissionState(issueId, StateEnum.E.getValue());
        //邮件发送
        SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
        IssueMailSendDto issueMailSendDto = new IssueMailSendDto(issue, NumberConstant.TWO, userInfo);
        rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE, issueMailSendDto);
    }


    /**
     * 功能描述: 根据缺陷id查询详情
     *
     * @param issueId
     * @return com.yusys.agile.fault.dto.FaultDTO
     * @date 2020/4/11
     */
    @Override
    public IssueDTO getFault(Long issueId) {
        Issue issue = issueMapper.selectByPrimaryKey(issueId);
        Long systemId = issue.getSystemId();
        // 不存在或者是删除状态
        if (null == issue || !StringUtils.equals(issue.getState(), StateEnum.U.getValue())) {
            return null;
        }
        IssueDTO issueDTO = new IssueDTO();
        ReflectUtil.copyProperties(issue, issueDTO);
        // --组装相关参数--
        // 创建人
        if (null != issueDTO.getCreateUid()) {
            issueDTO.setCreateName(getUserName(issueDTO.getCreateUid()));
        }
        // 修改人
        if (null != issueDTO.getHandler()) {
            issueDTO.setHandlerName(getUserName(issueDTO.getHandler()));
        }
        // 验证人
        if (null != issueDTO.getTestUid()) {
            issueDTO.setTestName(getUserName(issueDTO.getTestUid()));
        }
        // 缺陷等级
        if (null != issueDTO.getFaultLevel()) {
            //issueDTO.setFaultLevelName(getOperationValue(issueDTO.getFaultLevel(), HeaderFieldUtil.FAULTLEVEL));
            issueDTO.setFaultLevelName(FaultLevelEnum.getMsgById(issueDTO.getFaultLevel()));
        }

        // 缺陷类型
        if (null != issueDTO.getFaultType()) {
            //issueDTO.setFaultTypeName(getOperationValue(issueDTO.getFaultType(), HeaderFieldUtil.FAULTTYPE));
            issueDTO.setFaultTypeName(FaultTypeEnum.getMsgById(issueDTO.getFaultType()));
        }

        // 迭代
        if (null != issueDTO.getSprintId() && null != issueDTO.getProjectId()) {
            SprintDTO sprintDTO = sprintService.viewEdit(issueDTO.getSprintId(), issueDTO.getProjectId());
            if (null != sprintDTO) {
                issueDTO.setSprintName(sprintDTO.getSprintName());
            }
        }
        // 用例
        if (null != issueDTO.getCaseId()) {

        }
        // 故事
        if (null != issue.getParentId()) {
            Issue story = issueMapper.selectByPrimaryKey(issue.getParentId());
            if (null != story) {
                issueDTO.setParentName(story.getTitle());
            }
        }
        //LOGGER.info("查询描述前的issueDTO={}", JSON.toJSONString(issueDTO));
        //查询富文本内容
        issueRichTextFactory.queryIssueRichText(issueDTO);
        //LOGGER.info("查询描述后的issueDTO={}", JSON.toJSONString(issueDTO));

        //查询自定义字段
        issueDTO.setCustomFieldDetailDTOList(issueCustomFieldService.listCustomFieldByIssueId(issueId));
        return issueDTO;
    }

    /**
     * 功能描述:
     *
     * @param key
     * @param fieldCode
     * @return java.lang.String
     * @date 2021/2/25
     */
    private String getOperationValue(Long key, String fieldCode) {
        Map map = new HashMap<String, String>();
        if (key != null) {
            HeaderFieldExample headerFieldExample = new HeaderFieldExample();
            headerFieldExample.createCriteria().andFieldCodeEqualTo(fieldCode);
            List<HeaderField> headerFields = headerFieldMapper.selectByExampleWithBLOBs(headerFieldExample);
            JSONObject jsonObject = JSON.parseObject(headerFields.get(0).getFieldContent());
            JSONArray jsonArray = jsonObject.getJSONArray("optionList");
            for (int i = 0; i < jsonArray.size(); i++) {
                if (jsonArray.getJSONObject(i).getString("key").equals(key)) {
                    map.put("name", jsonArray.getJSONObject(i).getString("value"));
                    map.put("id", key);
                }
            }
        }
        return MapUtils.getString(map, HeaderFieldUtil.NAME);
    }

    /**
     * 功能描述: 获取用户姓名
     *
     * @param userId
     * @return java.lang.String
     * @date 2020/4/13
     */
    private String getUserName(Long userId) {
        if (null == userId) {
            return null;
        }

        SsoUser user = iFacadeUserApi.queryUserById(userId);
        if (null != user) {
            return user.getUserName();
        }
        return null;
    }

    /**
     * 功能描述: 修改缺陷
     *
     * @param faultDTO
     * @return void
     * @date 2020/4/13
     */
    @Override
    public void updateFault(IssueDTO faultDTO) throws Exception {

        if (faultDTO == null || null == faultDTO.getIssueId()) {
            LOGGER.error("修改缺陷id为空！");
            throw new BusinessException("修改缺陷id不能为空！");
        }

        Long handler = faultDTO.getHandler();
        Long stageId = faultDTO.getStageId();

        Issue fault = new Issue();
        fault.setIssueId(faultDTO.getIssueId());
        // 预计工时 工时时间可以进行修改，但是最后如果是完成时，剩余时间必须改为0
        fault.setPlanWorkload(faultDTO.getPlanWorkload());
        // 实际工时
        fault.setReallyWorkload(faultDTO.getReallyWorkload());
        // 剩余工时
        fault.setRemainWorkload(faultDTO.getRemainWorkload());
        fault.setStageId(stageId);
        // 迭代允许修改
        fault.setSprintId(faultDTO.getSprintId());

        Issue oldFault = issueMapper.selectByPrimaryKey(faultDTO.getIssueId());

        /** 判断工作项流转规则是否允许 */
        Byte issueType = oldFault.getIssueType();
        if (!ruleFactory.getIssueRulesCheckFlag(issueType, oldFault.getStageId(), null, stageId, null, oldFault.getProjectId())) {
            throw new BusinessException("该工作项不允许流转到目标阶段！");
        }

        // 历史记录处理
        List<IssueHistoryRecord> historyRecords = Lists.newArrayList();

        // yuIt同步时允许修改处理人和状态
        if (YuItUtil.yuItSync()) {

            if (null == oldFault || !IssueTypeEnum.TYPE_FAULT.CODE.equals(oldFault.getIssueType())
                    || !StateEnum.U.getValue().equalsIgnoreCase(oldFault.getState())) {
                throw new BusinessException("不存在该缺陷！");
            }

//            // 只有待修复和修复中才允许调整处理人,已修复可以修改实际工时
//            if (!(oldFault.getStageId().equals(FaultStatusEnum.NEW.CODE) || oldFault.getStageId().equals(FaultStatusEnum.PROCESSING.CODE)
//                    || oldFault.getStageId().equals(FaultStatusEnum.FIXED.CODE))) {
//                throw new BusinessException("已关闭的缺陷不允许修改！");
//            }

            // 原先就已经修复了，那么允许修改实际工时，其他的都不允许改
            if (oldFault.getStageId().equals(FaultStatusEnum.FIXED.CODE)) {
                Issue reallyWorkLoadFault = new Issue();
                reallyWorkLoadFault.setIssueId(faultDTO.getIssueId());
                reallyWorkLoadFault.setReallyWorkload(faultDTO.getReallyWorkload());
                issueMapper.updateByPrimaryKeySelective(reallyWorkLoadFault);
            } else {
                // 修改迭代的话后台直接要把修改人清掉，状态改为待修复
                if (null != faultDTO.getSprintId() && null != oldFault.getSprintId() && !faultDTO.getSprintId().equals(oldFault.getSprintId())
                        && (!FaultStatusEnum.NEW.CODE.equals(stageId) || null != handler)) {
                    stageId = FaultStatusEnum.NEW.CODE;
                    fault.setStageId(stageId);
                    fault.setHandler(null);
                }

                // 如果传处理人，那么状态传来必须是修复中或已修复，如果不传处理人，那么操作状态传来必须是待修复或已经关闭
                if (null != handler) {
                    if (!(FaultStatusEnum.PROCESSING.CODE.equals(stageId) || FaultStatusEnum.FIXED.CODE.equals(stageId)
                            || FaultStatusEnum.CLOSED.CODE.equals(stageId))) {
                        throw new BusinessException("当前状态只能设置为修复中或已修复！");
                    }
                } else {
                    if (!FaultStatusEnum.NEW.CODE.equals(stageId)) {
                        throw new BusinessException("当前状态只能设置为待修复！");
                    }
                }

                // 修改时不需要管实际工时，只有拖拽才需要处理实际工时等于预计工时
                if (FaultStatusEnum.FIXED.CODE.equals(stageId)) {
                    // 拖到已修复时要把剩余时长设为0
                    fault.setRemainWorkload(NumberConstant.ZERO);
                }

                generateFaultByStageId(fault, stageId, handler, oldFault);
                issueMapper.updateFaultByPrimaryKeySelectiveWithNull(fault);

                issueFactory.dealHistory(historyRecords);
            }
        } else {
            ReflectUtil.copyProperties(faultDTO, fault);
        }

        // 自定义字段
        if (CollectionUtils.isNotEmpty(faultDTO.getCustomFieldDetailDTOList())) {
            List<SIssueCustomField> customFields = Lists.newArrayList();
            // IssueCustomFieldDTO转换成IssueCustomField
            for (IssueCustomFieldDTO temp : faultDTO.getCustomFieldDetailDTOList()) {
                SIssueCustomField issueCustomField = new SIssueCustomField();
                issueCustomField.setExtendId(temp.getDetailId());
                issueCustomField.setFieldId(temp.getFieldId());
                issueCustomField.setFieldValue(temp.getFieldValue());
                issueCustomField.setIssueId(faultDTO.getIssueId());
                issueCustomField.setState(StateEnum.U.getValue());
                customFields.add(issueCustomField);
            }
            // 修改自定义字段明细数据
            issueCustomFieldService.editCustomFields(customFields);
        }
        //处理缺陷待办
        issueFactory.dealCommissionEdit(faultDTO.getIssueId());

        //发送邮件通知
        SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
        IssueMailSendDto issueMailSendDto = new IssueMailSendDto(fault, NumberConstant.ONE, userInfo);
        rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE, issueMailSendDto);
    }


    /**
     * 功能描述: 根据改变的状态返回对应缺陷的处理人信息
     *
     * @param fault
     * @param to
     * @param handler
     * @return com.yusys.agile.issue.domain.Issue
     * @date 2021/2/25
     */
    private Issue generateFaultByStageId(Issue fault, Long to, Long handler, Issue oldFault) {
        // 拖到待修复
        if (FaultStatusEnum.NEW.CODE.equals(to)) {
            fault.setHandler(null);
            fault.setFixedUid(null);
            fault.setFixedTime(null);

        }
        // 拖到修复中
        if (FaultStatusEnum.PROCESSING.CODE.equals(to)) {
            fault.setHandler(handler);
            fault.setFixedUid(handler);
            fault.setFixedTime(null);

        }
        // 拖到已解决
        if (FaultStatusEnum.FIXED.CODE.equals(to)) {
            fault.setHandler(handler);
            fault.setFixedUid(handler);
            fault.setFixedTime(new Date());
            // 拖到已修复时要把剩余时长设为0
            fault.setRemainWorkload(NumberConstant.ZERO);

        }

        // 在关闭时修改自定义字段
        if (FaultStatusEnum.CLOSED.CODE.equals(to)) {
            fault.setHandler(handler);
            fault.setFixedUid(handler);
            fault.setFixedTime(oldFault.getFixedTime());
            fault.setRemainWorkload(NumberConstant.ZERO);
        }

        return fault;
    }

    /**
     * 功能描述:查询所有缺陷级别
     *
     * @param
     * @return java.util.List<com.yusys.agile.fault.domain.FaultLevel>
     * @date 2020/4/11
     */
    @Override
    public List<FaultLevel> listAllFaultLevel() {
        FaultLevelExample example = new FaultLevelExample();
        example.setOrderByClause("LEVEL_ID ASC");
        return faultLevelMapper.selectByExample(example);
    }

    /**
     * 功能描述: 查询所有缺陷类别
     *
     * @param
     * @return java.util.List<com.yusys.agile.fault.domain.FaultType>
     * @date 2020/4/11
     */
    @Override
    public List<FaultType> listAllFaultType() {
        FaultTypeExample example = new FaultTypeExample();
        example.setOrderByClause("TYPE_ID ASC");
        return faultTypeMapper.selectByExample(example);
    }

    /**
     * 功能描述: 查询项目下所有的缺陷提出人
     *
     * @param projectId
     * @return java.util.List<com.yusys.agile.fault.dto.UserDTO>
     * @date 2020/4/14
     */
    @Override
    public List<UserDTO> listAllCreateUsers(Long projectId) {
        List<Long> createUserIds = issueMapper.listAllCreateUsers(projectId);
        return getUserDTOS(createUserIds);
    }


    /**
     * 功能描述: 查询项目下所有的缺陷修改人
     *
     * @param projectId
     * @return java.util.List<com.yusys.agile.fault.dto.UserDTO>
     * @date 2020/4/14
     */
    @Override
    public List<UserDTO> listAllFixedUsers(Long projectId) {
        List<Long> createUserIds = issueMapper.listAllFixedUsers(projectId);
        return getUserDTOS(createUserIds);
    }

    /**
     * 功能描述: 查询项目下所有的缺陷验收人
     *
     * @param projectId
     * @return java.util.List<com.yusys.agile.fault.dto.UserDTO>
     * @date 2020/4/14
     */
    @Override
    public List<UserDTO> listAllTestUsers(Long projectId) {
        List<Long> createUserIds = issueMapper.listAllTestUsers(projectId);
        return getUserDTOS(createUserIds);

    }

    /**
     * 功能描述: 列表查询缺陷
     *
     * @param faultLevel 缺陷级别
     * @param faultType  缺陷类型
     * @param stageId    状态
     * @param sprintId   迭代id
     * @param createUid  创建人id
     * @param fixedUid   修复人id
     * @param testUid    验证人id
     * @param projectId  项目id
     * @param pageNum    每页数量
     * @param pageSize   页数
     * @return java.util.List<com.yusys.agile.fault.dto.FaultDTO>
     * @date 2020/4/14
     * * @param faultId     缺陷id
     * * @param faultName   缺陷名称
     */
    @Override
    public List<IssueDTO> listFaults(String idOrName, Long faultLevel, Long faultType, Long stageId, Long sprintId, Long createUid, String createDate, Long fixedUid, Long testUid, Long projectId, Integer pageNum, Integer pageSize) throws Exception {
        if (null == projectId) {
            LOGGER.info("列表查询缺陷项目id为空！");
            return new ArrayList<>();
        }

        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        IssueExample example = new IssueExample();
        example.setOrderByClause(CREATE_TIME_DESC);
        IssueExample.Criteria criteria = example.createCriteria().andStateEqualTo(StateEnum.U.getValue()).andProjectIdEqualTo(projectId)
                .andIssueTypeEqualTo(IssueTypeEnum.TYPE_FAULT.CODE);
        IssueExample.Criteria criteria2 = example.createCriteria().andStateEqualTo(StateEnum.U.getValue()).andProjectIdEqualTo(projectId)
                .andIssueTypeEqualTo(IssueTypeEnum.TYPE_FAULT.CODE);

        if (null != faultLevel) {
            criteria.andFaultLevelEqualTo(faultLevel);
            criteria2.andFaultLevelEqualTo(faultLevel);
        }
        if (null != faultType) {
            criteria.andFaultTypeEqualTo(faultType);
            criteria2.andFaultTypeEqualTo(faultType);
        }
        if (null != stageId) {
            criteria.andStageIdEqualTo(stageId);
            criteria2.andStageIdEqualTo(stageId);
        }
        if (null != sprintId) {
            criteria.andSprintIdEqualTo(sprintId);
            criteria2.andSprintIdEqualTo(sprintId);
        }
        if (null != createUid) {
            criteria.andCreateUidEqualTo(createUid);
            criteria2.andCreateUidEqualTo(createUid);
        }
        if (null != fixedUid) {
            criteria.andFixedUidEqualTo(fixedUid);
            criteria2.andFixedUidEqualTo(fixedUid);
        }
        if (null != testUid) {
            criteria.andTestUidEqualTo(testUid);
            criteria2.andTestUidEqualTo(testUid);
        }
        if (StringUtils.isNotBlank(createDate)) {
            String start = createDate + " 00:00:00";
            String end = createDate + " 23:59:59";
            criteria.andCreateTimeBetween(DateUtil.formatStrToDate(start), DateUtil.formatStrToDate(end));
            criteria2.andCreateTimeBetween(DateUtil.formatStrToDate(start), DateUtil.formatStrToDate(end));
        }

        // 判断是根据id还是name
        if (StringUtils.isNotBlank(idOrName)) {
            try {
                Long id = Long.valueOf(idOrName);
                // 能转成long，说明可能是id，也可能是name
                criteria.andIssueIdEqualTo(id);
                // 同时赋值给标题
                criteria2.andTitleLike(StringConstant.PERCENT_SIGN + idOrName + StringConstant.PERCENT_SIGN);
                example.or(criteria2);
            } catch (Exception e) {
                LOGGER.error(Marker.ANY_MARKER, "缺陷列表转换异常e:{}", e);
                // 存在异常说明只能查name
                criteria.andTitleLike(StringConstant.PERCENT_SIGN + idOrName + StringConstant.PERCENT_SIGN);
            }
        }

        List<IssueDTO> faultList = issueMapper.selectByExampleDTO(example);
        return assembleFaultDTOs(faultList, projectId);
    }

    /**
     * 功能描述: 查询未关联迭代的缺陷或故事
     *
     * @param filter
     * @param issueType
     * @param projectId
     * @param pageNum
     * @param pageSize
     * @return java.util.List<IssueDTO>
     * @date 2020/4/14
     */
    @Override
    public List<IssueDTO> listFaultsOrStorysNotLinkSprint(String filter, Byte issueType, Long projectId, Integer pageNum, Integer pageSize) {
        if (null == projectId) {
            LOGGER.info("查询未关联迭代的缺陷项目id为空！");
            return new ArrayList<>();
        }

        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }


        IssueExample example = new IssueExample();
        example.setOrderByClause(CREATE_TIME_DESC);
        IssueExample.Criteria criteria = example.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                .andProjectIdEqualTo(projectId).andSprintIdIsNull().andIssueTypeEqualTo(issueType);
        IssueExample.Criteria criteria2 = example.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                .andProjectIdEqualTo(projectId).andSprintIdIsNull().andIssueTypeEqualTo(issueType);

        // 判断是根据id还是name
        if (StringUtils.isNotBlank(filter)) {
            try {
                Long id = Long.valueOf(filter);
                // 能转成long，说明可能是id，也可能是name
                criteria.andIssueIdEqualTo(id);
                // 同时赋值给标题
                criteria2.andTitleLike(StringConstant.PERCENT_SIGN + filter + StringConstant.PERCENT_SIGN);
                example.or(criteria2);
            } catch (Exception e) {
                LOGGER.info("转换异常e:{}", e);
                // 存在异常说明只能查name
                criteria.andTitleLike(StringConstant.PERCENT_SIGN + filter + StringConstant.PERCENT_SIGN);
            }
        }

        List<IssueDTO> faultList = issueMapper.selectByExampleDTO(example);
        return assembleFaultDTOs(faultList, projectId);
    }

    @Override
    public List<FaultStatusDTO> listAllStatus() {

        List<FaultStatusDTO> list = Lists.newArrayList();
        for (FaultStatusEnum e : FaultStatusEnum.values()) {
            FaultStatusDTO dto = new FaultStatusDTO();
            dto.setStageId(e.getCode());
            dto.setStatus(e.MSG);
            list.add(dto);
        }
        return list;
    }


    /**
     * 功能描述: 拖拽卡片改变缺陷状态
     *
     * @param issueDTO
     * @return void
     * @date 2020/4/26
     */
    @Override
    public void dragFault(IssueDTO issueDTO) throws Exception {
        if (null == issueDTO || null == issueDTO.getIssueId() || null == issueDTO.getStageId()) {
            throw new BusinessException("入参错误！");
        }

        Long issueId = issueDTO.getIssueId();
        Long to = issueDTO.getStageId();
        // 先判断缺陷是否存在
        Issue oldFault = issueMapper.selectByPrimaryKey(issueId);
        if (null == oldFault || !IssueTypeEnum.TYPE_FAULT.CODE.equals(oldFault.getIssueType())
                || !StateEnum.U.getValue().equalsIgnoreCase(oldFault.getState())) {
            throw new BusinessException("不存在该缺陷!");
        }
        Long from = oldFault.getStageId();
        // 拖拽的from必须和数据库中一致
        if (from.equals(to)) {
            throw new BusinessException("拖拽状态不正确！");
        }

        /** 判断工作项流转规则是否允许 */
        Byte issueType = oldFault.getIssueType();
        if (!ruleFactory.getIssueRulesCheckFlag(issueType, oldFault.getStageId(), null, to, null, oldFault.getProjectId())) {
            throw new BusinessException("该工作项不允许流转到目标阶段！");
        }
        Long loginUserId = UserThreadLocalUtil.getUserInfo().getUserId();
        Issue record = new Issue();
        record.setIssueId(oldFault.getIssueId());
        record.setStageId(to);

        generateFaultByStageId(record, to, loginUserId, oldFault);

        //yuIt缺陷模式
        if (YuItUtil.yuItSync() && oldFault.getBugId() != null) {
            if (!(oldFault.getStageId().equals(FaultStatusEnum.NEW.CODE) || oldFault.getStageId().equals(FaultStatusEnum.PROCESSING.CODE))) {
                throw new BusinessException("已解决的缺陷不允许拖拽！");
            }

            // 不允许拖到关闭和验证中状态
            if (FaultStatusEnum.CLOSED.CODE.equals(to) || FaultStatusEnum.CHECK.CODE.equals(to)) {
                throw new BusinessException("不允许拖到已关闭状态！");
            }

            // 工时设置，在拖拽中时，不涉及到工时的设置，拖拽已完成时，如果实际工时为0那么将实际工时=预计工时
            if (FaultStatusEnum.FIXED.CODE.equals(to)) {
                Integer planWorkload = oldFault.getPlanWorkload();
                if (null == oldFault.getReallyWorkload() || 0 == oldFault.getReallyWorkload()) {
                    record.setReallyWorkload(planWorkload);
                }
                // 拖到已修复时要把剩余时长设为0
                record.setRemainWorkload(NumberConstant.ZERO);
            }

            // 修改数据库
            issueMapper.updateFaultByPrimaryKeySelectiveWithNull(record);

            // 已解决的通知yuIt
            if (FaultStatusEnum.FIXED.CODE.equals(to)) {
                syncFixedFault(oldFault.getIssueId(), oldFault.getBugId(), loginUserId);
                //关闭代办状态
             //   commissionService.updateCommissionState(issueId, StateEnum.E.getValue());
            }

        } else {
            // 修改数据库
            issueMapper.updateFaultByPrimaryKeySelectiveWithNull(record);
        }

        //发送邮件通知
        SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
        IssueMailSendDto issueMailSendDto = new IssueMailSendDto(record, NumberConstant.THREE, userInfo);
        rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE, issueMailSendDto);
    }

    /**
     * 功能描述:
     *
     * @param issueId 缺陷id
     * @param bugId   yuIt id
     * @param handler 处理人
     * @return void
     * @date 2021/2/22
     */
    private void syncFixedFault(Long issueId, Long bugId, Long handler) throws Exception {
        FaultFixDTO faultFixDTO = new FaultFixDTO();
        faultFixDTO.setSystemCode(FaultSourceEnum.getName(FaultSourceEnum.YuDO2.CODE));
        faultFixDTO.setBugId(bugId);
        faultFixDTO.setResolution(StringConstant.FIXED);
        faultFixDTO.setResolved_time(DateUtil.toString(DateUtil.FORMAT, new Date()));
        faultFixDTO.setHandleUserId(handler);
        faultFixDTO.setHandle_user_id(handler);
        Issue record = new Issue();
        record.setIssueId(issueId);
        issueMapper.updateByPrimaryKeySelective(record);
    }


    /**
     * 功能描述: 查询出得缺陷组装返回dto
     *
     * @param faultList
     * @param projectId
     * @return java.util.List<com.yusys.agile.fault.dto.FaultDTO>
     * @date 2020/4/14
     */
    private List<IssueDTO> assembleFaultDTOs(List<IssueDTO> faultList, Long projectId) {
        if (CollectionUtils.isEmpty(faultList)) {
            return faultList;
        }

        // 处理用户数据，
        Map<Long, String> userMap = getUserMapByProjectId(projectId);
        for (IssueDTO tempFault : faultList) {

            // 缺陷类型
            if (null != tempFault.getFaultType()) {
                tempFault.setFaultTypeName(FaultTypeEnum.getMsgById(tempFault.getFaultType()));
            }
            // 缺陷等级
            if (null != tempFault.getFaultLevel()) {
                tempFault.setFaultLevelName(FaultLevelEnum.getMsgById(tempFault.getFaultLevel()));
            }
            // 提出人
            if (null != tempFault.getCreateUid()) {
                tempFault.setCreateName(getUserNameById(userMap, tempFault.getCreateUid()));
            }
            // 解决人
            if (null != tempFault.getFixedUid()) {
                tempFault.setFixedName(getUserNameById(userMap, tempFault.getFixedUid()));
            }
            // 解决人
            if (null != tempFault.getHandler()) {
                tempFault.setHandlerName(getUserNameById(userMap, tempFault.getHandler()));
            }
            // 验证人
            if (null != tempFault.getTestUid()) {
                tempFault.setTestName(getUserNameById(userMap, tempFault.getTestUid()));
            }
            //处理人
            if (null != tempFault.getHandler()) {
                tempFault.setHandlerName(getUserNameById(userMap, tempFault.getHandler()));
            }
        }
        return faultList;
    }


    /**
     * 功能描述: 如果map中不存在用户，直接查用户
     *
     * @param userMap
     * @param userId
     * @return java.lang.String
     * @date 2021/2/1
     */
    private String getUserNameById(Map<Long, String> userMap, Long userId) {
        String userName = MapUtils.getString(userMap, userId);
        if (StringUtils.isNotBlank(userName)) {
            return userName;
        }

        try {
            SsoUser userDTO = iFacadeUserApi.queryUserById(userId);
            if (null != userDTO) {
                return userDTO.getUserName();
            }
        } catch (Exception e) {
            LOGGER.info("error={}", e);
            return null;
        }
        return null;
    }

    /**
     * 功能描述: 获取项目下所有的用户数据
     * <p>
     * 不考虑本地缓存，如果对用户信息进行了修改，本地缓存查询出的数据会出错。
     * 考虑性能，避免循环调fiegn服务，由于绝大多数用户都是在项目中采用一次性查出所有用户然后进行转换
     * 目前确认人员都是项目中 如果发现用户数据很多不是来自项目，就采用查询三次的方式，把create、fix、test用户信息拉过来
     *
     * @param projectId
     * @return java.util.Map<java.lang.Long, com.yusys.portal.model.facade.entity.SsoUser>
     * @date 2020/4/14
     */
    @Override
    public Map<Long, String> getUserMapByProjectId(Long projectId) {
        Map<Long, String> userMap = Maps.newHashMap();
        // 查询所有人员信息
        List<SsoUser> users = iFacadeUserApi.ListMemberUsers(projectId, UserRelateTypeEnum.PROJECT.getValue());
        for (SsoUser tempUser : users) {
            userMap.put(tempUser.getUserId(), tempUser.getUserName());
        }
        return userMap;
    }

    /**
     * 功能描述: 根据用户集合获取所有的用户数据
     *
     * @param UserIdList
     * @return java.util.Map<java.lang.Long, com.yusys.portal.model.facade.entity.SsoUser>
     * @date 2020/10/21
     */
    @Override
    public Map<Long, String> getUserMapByUserIdList(List<Long> UserIdList) {
        Map<Long, String> userMap = Maps.newHashMap();
        // 查询所有人员信息
        List<SsoUser> users = iFacadeUserApi.listUsersByIds(UserIdList);
        for (SsoUser tempUser : users) {
            userMap.put(tempUser.getUserId(), tempUser.getUserName());
        }
        return userMap;
    }

    /**
     * 功能描述: 缺陷修改阻塞状态
     *
     * @param issueDTO
     * @return void
     * @date 2021/2/1
     */
    @Override
    public void editBlockState(IssueDTO issueDTO) {
        if (null == issueDTO || null == issueDTO.getIssueId() || null == issueDTO.getBlockState()) {
            LOGGER.info("入参错误");
            throw new BusinessException("入参错误");
        }

        Issue record = new Issue();
        record.setIssueId(issueDTO.getIssueId());
        record.setBlockState(issueDTO.getBlockState());

        issueMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 功能描述: 根据id获取对应的用户信息
     *
     * @param createUserIds
     * @return java.util.List<com.yusys.agile.fault.dto.UserDTO>
     * @date 2020/4/14
     */
    private List<UserDTO> getUserDTOS(List<Long> createUserIds) {
        if (CollectionUtils.isEmpty(createUserIds)) {
            return new ArrayList<UserDTO>();
        }

        List<SsoUser> users = iFacadeUserApi.listUsersByIds(createUserIds);
        if (CollectionUtils.isEmpty(users)) {
            return new ArrayList<UserDTO>();
        }
        return assembleUserDTOs(users);
    }

    /**
     * 功能描述: 把用户对象只返回id和name
     *
     * @param users
     * @return java.util.List<com.yusys.agile.fault.dto.UserDTO>
     * @date 2020/4/14
     */
    private List<UserDTO> assembleUserDTOs(List<SsoUser> users) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (SsoUser tempUser : users) {
            UserDTO userDTO = ReflectUtil.copyProperties(tempUser, UserDTO.class);
            userDTOList.add(userDTO);
        }

        return userDTOList;
    }

    /**
     * @param projectId
     * @param sprintId
     * @return
     * @description 查询未绑定故事且状态是未关闭的缺陷
     * @date 2020/08/31
     */
    @Override
    public List<IssueDTO> getUnBindStoryAndUnFinishedFaultList(Long projectId, Long sprintId) {
        List<IssueDTO> issueList = null;
        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSprintIdEqualTo(sprintId)
                .andIssueTypeEqualTo(IssueTypeEnum.TYPE_FAULT.CODE)
                .andStateEqualTo(StateEnum.U.getValue())
                .andStageIdNotEqualTo(FaultStatusEnum.CLOSED.CODE)
                .andParentIdIsNull();
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        if (CollectionUtils.isNotEmpty(issues)) {
            try {
                issueList = ReflectUtil.copyProperties4List(issues, IssueDTO.class);
            } catch (Exception e) {
                LOGGER.error("convert issues to issueList occur exception, message:{}", e.getMessage());
            }
        }
        return issueList;
    }

    @Override
    public Map<String, Object> statisticFaults(Long projectId) {
        Map<String, Object> result = Maps.newHashMap();
        IssueExample issueExample = assembleFaultConditions(projectId);
        long count = issueMapper.countByExample(issueExample);
        LOGGER.info("statisticFaults param projectId:{},return count:{}", projectId, count);
        if (count == 0) {
            completeResultMap(result);
            return result;
        } else {
            //返回结果集
            if (count <= THRESHOLD) {
                List<Issue> issues = issueMapper.selectByExample(issueExample);
                Map<String, List<Object>> faultMapList = Maps.newHashMap();
                dealFaultList(faultMapList, issues);
                result = calculateFaultResult(faultMapList, (int) count);
            } else {
                Map<String, List<Object>> faultMapList = Maps.newHashMap();
                int page = (int) ((int) count % THRESHOLD == 0 ? count / THRESHOLD : count / THRESHOLD + 1);
                for (int i = 0; i < page; i++) {
                    int start = i * THRESHOLD;
                    List<Issue> issues = issueMapper.getFaultIssuesByPage(projectId, start, THRESHOLD);
                    dealFaultList(faultMapList, issues);
                }
                result = calculateFaultResult(faultMapList, (int) count);
            }
            return result;
        }
    }

    /**
     * @param projectId
     * @return
     * @description 组装缺陷查询条件
     * @date 2020/09/07
     */
    private IssueExample assembleFaultConditions(Long projectId) {
        IssueExample issueExample = new IssueExample();
        issueExample.setOrderByClause("issue_id asc");
        issueExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andIssueTypeEqualTo(IssueTypeEnum.TYPE_FAULT.CODE)
                .andStateEqualTo(StateEnum.U.getValue());
        return issueExample;
    }

    /**
     * @param faultMap
     * @param issues
     * @return
     * @description 处理缺陷集合
     * @date 2020/09/08
     */
    private Map<String, List<Object>> dealFaultList(Map<String, List<Object>> faultMap, List<Issue> issues) {
        if (CollectionUtils.isNotEmpty(issues)) {
            for (Issue issue : issues) {
                Long issueId = issue.getIssueId();
                Long stageId = issue.getStageId();
                LOGGER.info("dealFaultList issueId:{},stageId:{}", issueId, stageId);
                if (null != stageId) {
                    String faultState = String.valueOf(stageId);
                    if (FaultStatusEnum.NEW.CODE.equals(stageId)) {
                        dealFaultMap(faultMap, faultState, issueId);
                    } else if (FaultStatusEnum.PROCESSING.CODE.equals(stageId)) {
                        dealFaultMap(faultMap, faultState, issueId);
                    } else if (FaultStatusEnum.FIXED.CODE.equals(stageId)) {
                        dealFaultMap(faultMap, faultState, issueId);
                    } else if (FaultStatusEnum.CHECK.CODE.equals(stageId)) {
                        dealFaultMap(faultMap, faultState, issueId);
                    } else if (FaultStatusEnum.CLOSED.CODE.equals(stageId)) {
                        dealFaultMap(faultMap, faultState, issueId);
                    }
                }
            }
        }
        return faultMap;
    }

    /**
     * @param faultMap
     * @param faultState
     * @param issueId
     * @description 处理缺陷Map
     * @date 2020/09/08
     */
    private void dealFaultMap(Map<String, List<Object>> faultMap, String faultState, Long issueId) {
        LOGGER.info("dealFaultMap before param faultMap:{}, faultState:{},issueId:{}", faultMap, faultState, issueId);
        if (faultMap.containsKey(faultState)) {
            List<Object> faultList = faultMap.get(faultState);
            faultList.add(issueId);
        } else {
            List<Object> faultList = Lists.newArrayList();
            faultList.add(issueId);
            faultMap.put(faultState, faultList);
        }
        LOGGER.info("dealFaultMap after param faultMap:{}", faultMap);
    }

    /**
     * @param faultMapList
     * @param size
     * @return
     * @description 计算缺陷结果
     * @date 2020/09/08
     */
    private Map<String, Object> calculateFaultResult(Map<String, List<Object>> faultMapList, int size) {
        Map<String, Object> result = new HashMap<>();
        result.put(TOTAL, size);
        if (MapUtils.isNotEmpty(faultMapList)) {
            for (Map.Entry<String, List<Object>> entry : faultMapList.entrySet()) {
                String key = entry.getKey();
                List<Object> value = entry.getValue();
                LOGGER.info("calculateFaultResult key:{},value:{}", key, value);
                result.put(key, CollectionUtils.isNotEmpty(value) ? value.size() : 0);
            }
            if (result.containsKey(String.valueOf(FaultStatusEnum.CLOSED.CODE))) {
                String closeCount = result.get(String.valueOf(FaultStatusEnum.CLOSED.CODE)).toString();
                BigDecimal close = new BigDecimal(closeCount);
                BigDecimal total = new BigDecimal(String.valueOf(size));
                String repaireRate = close.divide(total, 2, BigDecimal.ROUND_HALF_UP).toString();
                result.put(REPAIR_RATE, repaireRate);
            }
        }
        LOGGER.info("completeResultMap before result:{}", result);
        completeResultMap(result);
        LOGGER.info("completeResultMap after result:{}", result);
        return result;
    }

    /**
     * @param result
     * @description 补全字段为空的值为0
     * @date 2020/09/09
     */
    private void completeResultMap(Map<String, Object> result) {
        if (!result.containsKey(String.valueOf(FaultStatusEnum.NEW.CODE))) {
            result.put(String.valueOf(FaultStatusEnum.NEW.CODE), DEFAULT_NUMBER_ZERO);
        }
        if (!result.containsKey(String.valueOf(FaultStatusEnum.PROCESSING.CODE))) {
            result.put(String.valueOf(FaultStatusEnum.PROCESSING.CODE), DEFAULT_NUMBER_ZERO);
        }
        if (!result.containsKey(String.valueOf(FaultStatusEnum.FIXED.CODE))) {
            result.put(String.valueOf(FaultStatusEnum.FIXED.CODE), DEFAULT_NUMBER_ZERO);
        }
        if (!result.containsKey(String.valueOf(FaultStatusEnum.CHECK.CODE))) {
            result.put(String.valueOf(FaultStatusEnum.CHECK.CODE), DEFAULT_NUMBER_ZERO);
        }
        if (!result.containsKey(String.valueOf(FaultStatusEnum.CLOSED.CODE))) {
            result.put(String.valueOf(FaultStatusEnum.CLOSED.CODE), DEFAULT_NUMBER_ZERO);
        }
        if (!result.containsKey(TOTAL)) {
            result.put(TOTAL, DEFAULT_NUMBER_ZERO);
        }
        if (!result.containsKey(REPAIR_RATE)) {
            result.put(REPAIR_RATE, String.valueOf(DEFAULT_NUMBER_ZERO));
        }
    }
}