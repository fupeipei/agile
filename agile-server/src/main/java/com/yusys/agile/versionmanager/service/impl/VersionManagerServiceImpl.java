package com.yusys.agile.versionmanager.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.Lists;
import com.yusys.agile.constant.StringConstant;
import com.yusys.agile.externalapiconfig.dao.util.ExternalApiConfigUtil;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.service.FeatureService;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.service.IssueSystemRelpService;
import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetail;
import com.yusys.agile.sysextendfield.service.SysExtendFieldDetailService;
import com.yusys.agile.sysextendfield.util.SytExtendFieldDetailFactory;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.versionmanager.constants.VersionConstants;
import com.yusys.agile.versionmanager.dao.ReasonClassifyDao;
import com.yusys.agile.versionmanager.dao.ReasonClassifyValuesDao;
import com.yusys.agile.versionmanager.dao.VersionManagerMapper;
import com.yusys.agile.versionmanager.domain.ReasonClassify;
import com.yusys.agile.versionmanager.domain.ReasonClassifyValues;
import com.yusys.agile.versionmanager.domain.VersionManager;
import com.yusys.agile.versionmanager.domain.VersionManagerExample;
import com.yusys.agile.versionmanager.dto.ReasonDTO;
import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import com.yusys.agile.versionmanager.enums.DeployTypeEnum;
import com.yusys.agile.versionmanager.enums.OperateTypeEnum;
import com.yusys.agile.versionmanager.enums.VersionStateEnum;
import com.yusys.agile.versionmanager.enums.VersionStatusEnum;
import com.yusys.agile.versionmanager.service.VersionIssueRelateService;
import com.yusys.agile.versionmanager.service.VersionManagerService;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.date.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName VersionManagerServiceImpl
 * @Description
 * @Date 2020/8/18 16:17
 * @Version 1.0
 */
@Service
public class VersionManagerServiceImpl implements VersionManagerService {

    private final static Logger log = LoggerFactory.getLogger(VersionManagerServiceImpl.class);

    @Resource
    private VersionManagerMapper versionManagerMapper;
    @Resource
    private VersionIssueRelateService versionIssueRelateService;
    @Resource
    private SysExtendFieldDetailService sysExtendFieldDetailService;
    @Resource
    private IssueService issueService;

    @Resource
    private SytExtendFieldDetailFactory sytExtendFieldDetailFactory;
    @Resource
    private ReasonClassifyDao reasonClassifyDao;
    @Resource
    private ReasonClassifyValuesDao reasonClassifyValuesDao;
    @Resource
    private IssueMapper issueMapper;
    @Resource
    private ExternalApiConfigUtil externalApiConfigUtil;
    @Resource
    private FeatureService featureService;
    @Resource
    private IssueSystemRelpService issueSystemRelpService;

    private static final String PLAN_RELEASE_DATE = "plan_release_date DESC";

    @Override
    public List<VersionManagerDTO> getAllVersions(String versionName, Long projectId, String issueName, String bizNum, String approvalStatus) {
        VersionManagerExample managerExample = new VersionManagerExample();
        VersionManagerExample.Criteria criteria = managerExample.createCriteria();
        //增加state状态过滤
        criteria.andProjectIdEqualTo(projectId).andStateEqualTo(StateEnum.U.getValue());
        if (StringUtils.isEmpty(versionName) && StringUtils.isEmpty(issueName) && StringUtils.isEmpty(bizNum) && StringUtils.isEmpty(approvalStatus)) {
            criteria.andVersionStateNotEqualTo(VersionStateEnum.VERSION_STATE_RELEASED.CODE);
        } else if (StringUtils.isNotEmpty(versionName) && StringUtils.isEmpty(issueName) && StringUtils.isEmpty(bizNum) && StringUtils.isEmpty(approvalStatus)) {
            criteria.andVersionNameLike(StringUtils.join(StringConstant.PERCENT_SIGN, versionName, StringConstant.PERCENT_SIGN));
        }
        managerExample.setOrderByClause(PLAN_RELEASE_DATE + "," + StringConstant.CREATE_TIME_DESC);
        List<VersionManager> versionManagers;
        List<Long> issueIdList = Lists.newArrayList();
        List<Long> issueIds = Lists.newArrayList();
        if (StringUtils.isNotEmpty(issueName) || StringUtils.isNotEmpty(bizNum) || StringUtils.isNotEmpty(approvalStatus)) {
            //增加需求扩展字段查询======start
            if (StringUtils.isNotEmpty(issueName)) {
                issueIds = issueService.selectIssueIdByProjectId(projectId, issueName);
            }
            if (issueIds.size() > 0) {
                issueIdList.addAll(issueIds);
            }
            List<SysExtendFieldDetail> sysExtendFieldDetails = Lists.newArrayList();
            if (StringUtils.isNotBlank(bizNum)) {
                SysExtendFieldDetail sysExtendFieldDetail = new SysExtendFieldDetail();
                sysExtendFieldDetail.setFieldId("bizNum");
                sysExtendFieldDetail.setValue(bizNum);
                sysExtendFieldDetails.add(sysExtendFieldDetail);
            }
            if (StringUtils.isNotBlank(approvalStatus)) {
                SysExtendFieldDetail sysExtendFieldDetail = new SysExtendFieldDetail();
                sysExtendFieldDetail.setFieldId("approvalStatus");
                sysExtendFieldDetail.setValue(approvalStatus);
                sysExtendFieldDetails.add(sysExtendFieldDetail);
            }
            if (sysExtendFieldDetails.size() > 0) {
                List<Long> longList;
                longList = sysExtendFieldDetailService.getSysExtendFieldDetailByIds(null, sysExtendFieldDetails);
                if (longList.size() == 0) {
                    return Lists.newArrayList();
                }
                issueIdList.addAll(longList);
            }
            versionManagers = versionManagerMapper.selectVersionByBizNumAndTitle(projectId, versionName, issueIdList);
            //增加需求扩展字段查询======end
        } else {
            versionManagers = versionManagerMapper.selectByExample(managerExample);
        }
        List<VersionManagerDTO> versionManagerDTOS = new ArrayList<>();
        try {
            versionManagerDTOS = ReflectUtil.copyProperties4List(versionManagers, VersionManagerDTO.class);
            if (CollectionUtils.isNotEmpty(versionManagerDTOS)) {
                versionManagerDTOS.forEach(versionManager -> {
                    if (Optional.ofNullable(versionManager.getDeployType()).isPresent()) {
                        versionManager.setDeployTypeName(DeployTypeEnum.getName(versionManager.getDeployType()));
                    }
                    if (Optional.ofNullable(versionManager.getVersionState()).isPresent()) {
                        versionManager.setVersionStateName(VersionStateEnum.getName(versionManager.getVersionState()));
                    }
                });
            }
        } catch (Exception e) {
            log.error("获取版本管理异常：{}", e.getMessage());
        }
        return versionManagerDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrUpdate(VersionManagerDTO managerDTO, SecurityDTO securityDTO) {
        VersionManager versionManager = ReflectUtil.copyProperties(managerDTO, VersionManager.class);
        versionManager.setProjectId(securityDTO.getProjectId());
        Long versionId = versionManager.getId();

        // 校验同一项目下版本名是否相同
        VersionManagerExample example = new VersionManagerExample();
        VersionManagerExample.Criteria criteria = example.createCriteria().andVersionNameEqualTo(managerDTO.getVersionName())
                .andProjectIdEqualTo(securityDTO.getProjectId());

        if (Optional.ofNullable(versionId).isPresent()) {

            criteria.andIdNotEqualTo(versionId);
            List<VersionManager> sameVersionNameList = versionManagerMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(sameVersionNameList)) {
                throw new BusinessException("该版本名称已存在！");
            }

            VersionManager versionOld = versionManagerMapper.selectByPrimaryKey(versionId);
            if(!Optional.ofNullable(versionOld).isPresent()){
                throw new BusinessException("变更的版本计划不存在");
            }
            versionManagerMapper.updateByPrimaryKeySelective(versionManager);
        } else {

            List<VersionManager> sameVersionNameList = versionManagerMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(sameVersionNameList)) {
                throw new BusinessException("该版本名称已存在！");
            }

            versionManager.setCreateName(securityDTO.getUserName());
            versionManager.setVersionState(VersionStateEnum.getName(VersionStateEnum.VERSION_STATE_UNCONFIRMED.CODE));
            versionManagerMapper.insert(versionManager);
            managerDTO.setId(versionManager.getId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteVersion(Long versionId, Long projectId) {
        //versionManagerMapper.deleteByPrimaryKey(versionId);
        // 删除改为逻辑删除
        VersionManager record = new VersionManager();
        record.setId(versionId);
        record.setState(StateEnum.E.getValue());
        versionManagerMapper.updateByPrimaryKeySelective(record);


        versionIssueRelateService.removeRelateIssuesByVersionId(versionId);

        // 同步给itc
        VersionManager versionManager = new VersionManager();
        versionManager.setId(versionId);
        versionManager.setProjectId(projectId);
    }

    @Override
    public VersionManagerDTO getVersionInfo(Long versionId) {
        VersionManager versionOld = versionManagerMapper.selectByPrimaryKey(versionId);
        VersionManagerDTO versionManagerDTO = new VersionManagerDTO();
        if (Optional.ofNullable(versionOld).isPresent()) {
            versionManagerDTO = ReflectUtil.copyProperties(versionOld, VersionManagerDTO.class);
            if (Optional.ofNullable(versionManagerDTO.getDeployType()).isPresent()) {
                versionManagerDTO.setDeployTypeName(DeployTypeEnum.getName(versionManagerDTO.getDeployType()));
            }
            if (Optional.ofNullable(versionManagerDTO.getVersionState()).isPresent()) {
                versionManagerDTO.setVersionStateName(VersionStateEnum.getName(versionManagerDTO.getVersionState()));
            }
        }
        return versionManagerDTO;
    }

    @Override
    public int updateVersionPlanStatusAfterSendingReview(Long versionPlanId, String versionState, int reviewCount, Long userId) {
        reviewCount = reviewCount + 1;
        VersionManager versionPlan = new VersionManager();
        versionPlan.setId(versionPlanId);
        versionPlan.setReviewCount(reviewCount);
        versionPlan.setOperationUid(userId.intValue());
        versionPlan.setVersionState(VersionStateEnum.VERSION_STATE_REVIEW.CODE);
        versionPlan.setSendToRmp(VersionConstants.VersionManagerConstant.SYNC_SUCCESSFULLY);
        // 第一次发送版本审批成功后 begin
        if (reviewCount == 1) {
            // 设置版本审批开始时间
            versionPlan.setReviewStartTime(new Date());
            // 将版本设置为基线版本
            versionPlan.setBaselineFlag(VersionConstants.VersionManagerConstant.BASELINE_FLAG);
            VersionManager versionPlanData = new VersionManager();
            versionPlanData.setId(versionPlanId);
            versionManagerMapper.updatePlanDeliveryNumber(versionPlanData);
        }
        // 第一次发送版本审批成功后 end
        return versionManagerMapper.updateByPrimaryKeySelective(versionPlan);
    }

    @Override
    public int updateSyncToRmpStatus(Long versionPlanId, Integer status) {
        VersionManager versionPlan = new VersionManager();
        versionPlan.setId(versionPlanId);
        versionPlan.setSendToRmp(status);
        versionPlan.setUpdateTime(new Date());
        return versionManagerMapper.updateByPrimaryKeySelective(versionPlan);
    }

    @Override
    public void syncVersionPlanInfo(Long versionPlanId, String tenantName) {
        // 版本基线创建成功后，向ITC和CMP同步版本信息
        VersionManager versionManager = versionManagerMapper.selectSyncVersionPlanInfoByPrimaryKey(versionPlanId);
        VersionManagerDTO versionManagerDTO = ReflectUtil.copyProperties(versionManager, VersionManagerDTO.class);
        if (versionManagerDTO == null) {
            log.info("根据版本计划ID {} 没有找到对应的版本信息，无法同步版本", versionPlanId);
            return;
        }
        versionManagerDTO.setRelatedRequirementList(Collections.emptyList());
        versionManagerDTO.setOperateType(OperateTypeEnum.OPERATE_TYPE_ADD.VALUE);
        List<VersionManagerDTO> syncDataList = new ArrayList<>();
        syncDataList.add(versionManagerDTO);
    }

    @Override
    public JSONObject createFailedResult(String reason) {
        JSONObject failedResult = new JSONObject();
        failedResult.put("code", VersionConstants.VersionManagerConstant.RESPONSE_FAILURE_CODE);
        failedResult.put("data", VersionConstants.VersionManagerConstant.RESPONSE_FAILURE_VALUE);
        failedResult.put("msg", reason);
        return failedResult;
    }


    @Override
    public String getVersionStateById(Long versionPlanId) {
        VersionManager versionPlan = versionManagerMapper.selectByPrimaryKey(versionPlanId);
        return versionPlan.getVersionState();
    }

    @Override
    public JSONObject createSuccessResult() {
        JSONObject successResult = new JSONObject();
        successResult.put("code", VersionConstants.VersionManagerConstant.RESPONSE_SUCCESS_CODE);
        successResult.put("data", VersionConstants.VersionManagerConstant.RESPONSE_SUCCESS_VALUE);
        successResult.put("msg", "");
        return successResult;
    }

    @Override
    public String checkRequirementManagementPlatformResponseMsg(JSONObject jsonObject) {
        StringBuilder errorMsg = new StringBuilder();
        if (org.apache.commons.lang.StringUtils.isBlank(jsonObject.getString(VersionConstants.VersionManagerConstant.APPROVE_RESULT_ID))) {
            errorMsg.append("返回结果中未包含[" + VersionConstants.VersionManagerConstant.APPROVE_RESULT_ID + "]属性的值 ");
        } else if (!org.apache.commons.lang.StringUtils.isNumeric(jsonObject.getString(VersionConstants.VersionManagerConstant.APPROVE_RESULT_ID))) {
            errorMsg.append("返回结果中[" + VersionConstants.VersionManagerConstant.APPROVE_RESULT_ID + "]属性的值不是数字 ");
        } else if (Objects.isNull(jsonObject.get(VersionConstants.VersionManagerConstant.APPROVE_RESULT_RELATED_REQUIREMENT))) {
            errorMsg.append("返回结果中未包含[" + VersionConstants.VersionManagerConstant.APPROVE_RESULT_RELATED_REQUIREMENT + "]属性的值");
        }
        return errorMsg.toString();
    }


    /**
     * @param versionName 版本号名称
     * @param pageNum     当前页码
     * @param pageSize    每页条数
     * @param projectId   项目id
     * @return
     * @Description:根据项目id获取版本信息
     * @date 2021/3/18
     */
    @Override
    public List<VersionManagerDTO> getAllByVersionNameAndProjectId(String versionName, Integer pageNum, Integer pageSize, Long projectId) {
        if (null == projectId) {
            log.info("查询迭代的项目id为空！");
            return new ArrayList<>();
        }
        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageMethod.startPage(pageNum, pageSize);
        }

        VersionManagerExample managerExample = new VersionManagerExample();
        VersionManagerExample.Criteria criteria = managerExample.createCriteria();
        //增加state状态过滤
        criteria.andProjectIdEqualTo(projectId).andStateEqualTo(StateEnum.U.getValue());
        if (StringUtils.isNotEmpty(versionName)) {
            criteria.andVersionNameLike(StringUtils.join(StringConstant.PERCENT_SIGN, versionName, StringConstant.PERCENT_SIGN));
        }
        managerExample.setOrderByClause(StringConstant.CREATE_TIME_DESC);
        List<VersionManager> versionManagers = versionManagerMapper.selectByExample(managerExample);
        List<VersionManagerDTO> versionManagerDTOS = new ArrayList<>();
        try {
            versionManagerDTOS = ReflectUtil.copyProperties4List(versionManagers, VersionManagerDTO.class);
            if (CollectionUtils.isNotEmpty(versionManagerDTOS)) {
                versionManagerDTOS.forEach(versionManager -> {
                    if (Optional.ofNullable(versionManager.getDeployType()).isPresent()) {
                        versionManager.setDeployTypeName(DeployTypeEnum.getName(versionManager.getDeployType()));
                    }
                    if (Optional.ofNullable(versionManager.getVersionState()).isPresent()) {
                        versionManager.setVersionStateName(VersionStatusEnum.getName(versionManager.getVersionState()));
                    }
                });
            }
        } catch (Exception e) {
            log.error("获取版本管理异常：{}", e);
        }
        return versionManagerDTOS;
    }

    @Override
    public List<VersionManagerDTO> getOtherVersionInfo(Long versionId) {
        VersionManager versionOld = versionManagerMapper.selectByPrimaryKey(versionId);
        if(!Optional.ofNullable(versionOld).isPresent()){
            throw new BusinessException("版本计划不存在");
        }
        //Optional.ofNullable(versionOld).orElseThrow(() -> new BusinessException("版本计划不存在"));
        List<VersionManagerDTO> versionManagerDTOS = Lists.newArrayList();
        VersionManagerExample versionManagerExample = new VersionManagerExample();
        VersionManagerExample.Criteria criteria = versionManagerExample.createCriteria();
        criteria.andIdNotEqualTo(versionId);
        List<VersionManager> versionManagers = versionManagerMapper.selectByExample(versionManagerExample);
        generateVersionManagerDTOS(versionManagerDTOS, versionManagers);

        return versionManagerDTOS;
    }

    private void generateVersionManagerDTOS(List<VersionManagerDTO> versionManagerDTOS, List<VersionManager> versionManagers) {
        for (int i = 0; i < versionManagers.size(); i++) {
            VersionManagerDTO versionManagerDTO = ReflectUtil.copyProperties(versionManagers.get(i), VersionManagerDTO.class);
            if (Optional.ofNullable(versionManagerDTO.getDeployType()).isPresent()) {
                versionManagerDTO.setDeployTypeName(DeployTypeEnum.getName(versionManagerDTO.getDeployType()));
            }
            if (Optional.ofNullable(versionManagerDTO.getVersionState()).isPresent()) {
                versionManagerDTO.setVersionStateName(VersionStatusEnum.getName(versionManagerDTO.getVersionState()));
            }
            versionManagerDTOS.add(versionManagerDTO);
        }
    }

    @Override
    public int updateVersionPlanStateById(Long versionPlanId, String state) {
        synchronized (this) {
            VersionManager versionPlan = new VersionManager();
            versionPlan.setId(versionPlanId);
            versionPlan.setVersionState(state);
            versionPlan.setUpdateTime(new Date());
            return versionManagerMapper.updateByPrimaryKeySelective(versionPlan);
        }
    }

    @Override
    public List<ReasonDTO> getReasonClassifyList() {
        List<ReasonDTO> reasonDTOList = Lists.newArrayList();
        List<ReasonClassify> reasonClassifyList = reasonClassifyDao.selectAllReasonClassify();
        if (CollectionUtils.isNotEmpty(reasonClassifyList)) {
            ReasonDTO reasonDTO;
            for (ReasonClassify reasonClassify : reasonClassifyList) {
                reasonDTO = new ReasonDTO();
                reasonDTO.setId(reasonClassify.getClassifyId());
                reasonDTO.setReasonDesc(reasonClassify.getClassifyName());
                reasonDTOList.add(reasonDTO);
            }
        }
        return reasonDTOList;
    }

    @Override
    public List<ReasonDTO> getReasonClassifyValuesMap(Integer classifyId) {
        List<ReasonDTO> reasonDTOList = Lists.newArrayList();
        List<ReasonClassifyValues> reasonClassifyValues = reasonClassifyValuesDao.selectReasonClassifyValuesByClassifyId(classifyId);
        if (CollectionUtils.isNotEmpty(reasonClassifyValues)) {
            ReasonDTO reasonDTO;
            ;
            for (ReasonClassifyValues reasonClassifyValue : reasonClassifyValues) {
                reasonDTO = new ReasonDTO();
                reasonDTO.setId(reasonClassifyValue.getChangeReasonId());
                reasonDTO.setReasonDesc(reasonClassifyValue.getChangeReason());
                reasonDTOList.add(reasonDTO);
            }
        }
        return reasonDTOList;
    }

    /**
     * 根据项目id获取版本计划
     *
     * @param projectId
     * @return
     * @date 2021/3/30
     */
    @Override
    public List<VersionManagerDTO> getAllVersionInfo(Long projectId) {
        List<VersionManagerDTO> versionManagerDTOS = Lists.newArrayList();
        List<VersionManager> versionManagers = versionManagerMapper.selectGroupVersion(projectId);
        if (CollectionUtils.isNotEmpty(versionManagers)) {
            generateVersionManagerDTOS(versionManagerDTOS, versionManagers);
        }
        return versionManagerDTOS;
    }

    /**
     * @param issueId
     * @return
     * @description 根据工作项编号查询工作项
     */
    private List<Issue> getIssueList(Long issueId) {
        IssueExample example = new IssueExample();
        example.createCriteria()
                .andIssueIdEqualTo(issueId)
                .andStateEqualTo(StateEnum.U.getValue());
        List<Issue> issueList = issueMapper.selectByExample(example);
        return issueList;
    }

    /**
     * @param parentIdList
     * @return
     * @description 根据父工作项编号查询子工作项
     */
    private List<Issue> getChildIssueList(List<Long> parentIdList) {
        IssueExample example = new IssueExample();
        example.createCriteria()
                .andParentIdIn(parentIdList)
                .andStateEqualTo(StateEnum.U.getValue());
        List<Issue> issueList = issueMapper.selectByExample(example);
        return issueList;
    }

    /**
     * @param issueIdList
     * @description 更新工作项上线状态
     */
    private int updateIssueOnlineStatus(List<Long> issueIdList) {
        int row = 0;
        int size = issueIdList.size();
        if (size > VersionConstants.PAGE_SIZE) {
            List<Long> issueIds = Lists.newArrayList();
            for (int i = 0; i < issueIdList.size(); i++) {
                issueIds.add(issueIdList.get(i));
                if (issueIds.size() % VersionConstants.PAGE_SIZE == 0 || i == issueIdList.size() - 1) {
                    row = issueMapper.batchUpdateIssueStageStatus(issueIds, StageConstant.FirstStageEnum.FINISH_STAGE.getValue(), null);
                    if (row < 1) {
                        throw new RuntimeException("更新工作项上线状态失败");
                    }
                    issueIds.clear();
                }
            }
        } else {
            row = issueMapper.batchUpdateIssueStageStatus(issueIdList, StageConstant.FirstStageEnum.FINISH_STAGE.getValue(), null);
            if (row < 1) {
                throw new RuntimeException("更新工作项上线状态失败");
            }
        }
        return row;
    }

    @Override
    public List<VersionManager> gerVersionInfoByDeployType(String planDeployDate, Byte deployType) throws Exception {


        VersionManagerExample example = new VersionManagerExample();
        VersionManagerExample.Criteria criteria = example.createCriteria();
        SimpleDateFormat sdf = new SimpleDateFormat("");
        criteria.andPlanDeployDateEqualTo(DateUtil.formatStrToDate(planDeployDate))
                //增加state状态过滤
                .andDeployTypeEqualTo(deployType).andStateEqualTo(StateEnum.U.getValue());
        return versionManagerMapper.selectByExample(example);
    }

    @Override
    public VersionManager versionInfoById(Long newVersionPlanId) {
        return versionManagerMapper.selectByPrimaryKey(newVersionPlanId);
    }

    @Override
    public List<Long> getAllVersionPlan() {
        List<Long> versionIdList = Lists.newArrayList();
        VersionManagerExample example = new VersionManagerExample();
        // 增加state状态过滤
        example.createCriteria().andStateEqualTo(StateEnum.U.getValue());
        List<VersionManager> versionManagerList = versionManagerMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(versionManagerList)) {
            versionIdList = versionManagerList.stream().map(VersionManager::getId).collect(Collectors.toList());
        }
        return versionIdList;
    }


    @Override
    public List<Long> getVersionPlanByState() {
        List<Long> versionIdList = Lists.newArrayList();
        VersionManagerExample example = new VersionManagerExample();
        VersionManagerExample.Criteria criteria = example.createCriteria();
        //增加state状态过滤
        criteria.andVersionStateNotEqualTo(VersionStateEnum.VERSION_STATE_RELEASED.CODE).andStateEqualTo(StateEnum.U.getValue());
        List<VersionManager> versionManagerList = versionManagerMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(versionManagerList)) {
            versionIdList = versionManagerList.stream().map(VersionManager::getId).collect(Collectors.toList());
        }
        return versionIdList;
    }

    @Override
    public VersionManagerDTO queryVersionManageInfo(Long epicId) {
        VersionManagerDTO versionManagerDTO = null;

        return versionManagerDTO;
    }

    @Override
    public Map<String, Integer> countIssue(Long versionPlanId) {
        Map<String, Integer> countMap = new HashMap();
        countMap.put("epicCount", 0);
        countMap.put("bossCount", 0);
        countMap.put("crmCount", 0);
        countMap.put("eCommerceCount", 0);
        String ISSUE_COUNT_CONFIG = externalApiConfigUtil.getPropValue("ISSUE_COUNT_CONFIG");
        Map<Long, String> systemIdMap = new HashMap<>();
        if (StringUtils.isNotBlank(ISSUE_COUNT_CONFIG)) {
            dealSystemMap(systemIdMap, ISSUE_COUNT_CONFIG);
        }
        return countMap;
    }

    private void dealSystemMap(Map<Long, String> systemIdMap, String issue_count_config) {
        String[] systemIdAndName = issue_count_config.split(",");
        for (int i = 0; i < systemIdAndName.length; i++) {
            String[] tempStr = systemIdAndName[i].split(":");
            systemIdMap.put(Long.valueOf(tempStr[1]), tempStr[0]);
        }
    }
}
