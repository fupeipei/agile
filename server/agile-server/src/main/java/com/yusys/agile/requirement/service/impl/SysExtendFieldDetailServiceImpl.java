package com.yusys.agile.requirement.service.impl;

import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.requirement.dao.SysExtendFieldDetailMapper;
import com.yusys.agile.requirement.domain.SysExtendFieldDetail;
import com.yusys.agile.requirement.domain.SysExtendFieldDetailExample;
import com.yusys.agile.requirement.service.SysExtendFieldDetailService;
import com.yusys.agile.requirement.util.SytExtendFieldDetailFactory;
import com.yusys.agile.utils.DateTools;
import com.yusys.agile.versionmanager.constants.VersionConstants;
import com.yusys.agile.versionmanager.enums.IssueApproveStatusEnum;
import com.google.common.collect.Lists;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.date.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *   :
 * @Date: 2021/3/10
 * @Description:
 */
@Service
public class SysExtendFieldDetailServiceImpl implements SysExtendFieldDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysExtendFieldDetailServiceImpl.class);

    @Resource
    private SysExtendFieldDetailMapper sysExtendFieldDetailMapper;
    @Resource
    private SytExtendFieldDetailFactory sytExtendFieldDetailFactory;
    @Resource
    private IssueService issueService;

    @Override
    public int save(SysExtendFieldDetail sysExtendFieldDetail) {
        return sysExtendFieldDetailMapper.insertSelective(sysExtendFieldDetail);
    }

    @Override
    public List<SysExtendFieldDetail> getIssueExtendDetailList(List<Long> issueIds) {
        if(issueIds==null||(issueIds.isEmpty())){
            return Lists.newArrayList();
        }
        SysExtendFieldDetailExample example = new SysExtendFieldDetailExample();
        SysExtendFieldDetailExample.Criteria criteria = example.createCriteria();
        criteria.andIssueIdIn(issueIds);
        return sysExtendFieldDetailMapper.selectByExample(example);
    }

    @Override
    public void batchSave(List<SysExtendFieldDetail> sysExtendFieldDetailList) {
        sysExtendFieldDetailMapper.batchSave(sysExtendFieldDetailList);
    }

    @Override
    public void updateApprovalStatusByBizNums(List<String> bizNumList, Byte approveFailedStatus) {
        sysExtendFieldDetailMapper.updateApprovalStatusByBizNums(bizNumList,approveFailedStatus);
    }

    @Override
    public List<SysExtendFieldDetail> getEpicSysExtendDetailByBizNums(List<String> bizNumList) {
        SysExtendFieldDetailExample example = new SysExtendFieldDetailExample();
        SysExtendFieldDetailExample.Criteria criteria = example.createCriteria();
        criteria.andFieldIdEqualTo("bizNum");
        criteria.andValueIn(bizNumList);
        return sysExtendFieldDetailMapper.selectByExample(example);
    }

    @Override
    public int countEmptyApprovalResultByBizBacklogIds(List<Long> bizBacklogIds) {
        return sysExtendFieldDetailMapper.countEmptyApprovalResultByBizBacklogIds(bizBacklogIds);
    }

    @Override
    public int countApprovalFailedBizBacklog(List<Long> bizBacklogIds) {
        return sysExtendFieldDetailMapper.countApprovalFailedBizBacklog(bizBacklogIds);
    }

    @Override
    public SysExtendFieldDetail getSysExtendFieldDetail(Long bizBacklogId, String fieldId) {
        SysExtendFieldDetailExample example = new SysExtendFieldDetailExample();
        SysExtendFieldDetailExample.Criteria criteria = example.createCriteria();
        criteria.andIssueIdEqualTo(bizBacklogId);
        criteria.andFieldIdEqualTo(fieldId);
        List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(sysExtendFieldDetailList)){
            return sysExtendFieldDetailList.get(0);
        }
        return null;
    }
    @Override
    public List<SysExtendFieldDetail> getSysExtendFieldDetails(List<Long> bizBacklogId, String fieldId) {
        SysExtendFieldDetailExample example = new SysExtendFieldDetailExample();
        SysExtendFieldDetailExample.Criteria criteria = example.createCriteria();
        criteria.andIssueIdIn(bizBacklogId);
        if(fieldId!=null){
            criteria.andFieldIdEqualTo(fieldId);
        }

        return sysExtendFieldDetailMapper.selectByExample(example);
    }
    @Override
    public int update(SysExtendFieldDetail sysExtendFieldDetail) {
        return sysExtendFieldDetailMapper.updateByPrimaryKeySelectiveWithNull(sysExtendFieldDetail);
    }

    @Override
    public int updateApproveResultByPKs(List<Long> notUnbindingApprovalFailedBizBacklogIds, String approveFailed) {
        int result = 0;
        for(Long issueId : notUnbindingApprovalFailedBizBacklogIds){
            result += sytExtendFieldDetailFactory.insertOrUpdateIssueExtendFieldDetail(issueId,
                    VersionConstants.SysExtendFiledConstant.APPROVAL_RESULT, VersionConstants.SysExtendFiledConstant.APPROVAL_RESULT_NAME
                    ,approveFailed);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApprovalEndTime(List<Long> failedBizBacklogIds, Date date) {
        for(Long issueId : failedBizBacklogIds){
            sytExtendFieldDetailFactory.insertOrUpdateIssueExtendFieldDetail(issueId,
                    VersionConstants.SysExtendFiledConstant.APPROVAL_END_TIME, VersionConstants.SysExtendFiledConstant.APPROVAL_END_TIME_NAME
                    , DateUtil.formatDateToStr(new Date()));
            sytExtendFieldDetailFactory.insertOrUpdateIssueExtendFieldDetail(issueId,
                    VersionConstants.SysExtendFiledConstant.APPROVAL_STATUS, VersionConstants.SysExtendFiledConstant.APPROVAL_STATUS_NAME
                    , IssueApproveStatusEnum.APPROVED.CODE);
        }
    }

    @Override
    public void resetActualAskLineTimeByPKs(List<Long> approvalSuccessUnbindingBizBacklogIds) {
        for(Long issueId : approvalSuccessUnbindingBizBacklogIds){
            sytExtendFieldDetailFactory.insertOrUpdateIssueExtendFieldDetail(issueId,
                    VersionConstants.SysExtendFiledConstant.ACTUAL_ASK_LINE_TIME, VersionConstants.SysExtendFiledConstant.ACTUAL_ASK_LINE_TIME_NAME
                    ,null);
        }
    }

    @Override
    public void resetApprovalAllByPKs(List<Long> bindingBizBacklogIds) {
        for(Long issueId : bindingBizBacklogIds){
            sytExtendFieldDetailFactory.insertOrUpdateIssueExtendFieldDetail(issueId,
                    VersionConstants.SysExtendFiledConstant.APPROVAL_RESULT, VersionConstants.SysExtendFiledConstant.APPROVAL_RESULT_NAME
                    ,null);
            sytExtendFieldDetailFactory.insertOrUpdateIssueExtendFieldDetail(issueId,
                    VersionConstants.SysExtendFiledConstant.APPROVAL_START_TIME, VersionConstants.SysExtendFiledConstant.APPROVAL_START_TIME_NAME
                    ,null);
            sytExtendFieldDetailFactory.insertOrUpdateIssueExtendFieldDetail(issueId,
                    VersionConstants.SysExtendFiledConstant.APPROVAL_END_TIME, VersionConstants.SysExtendFiledConstant.APPROVAL_END_TIME_NAME
                    ,null);
            sytExtendFieldDetailFactory.insertOrUpdateIssueExtendFieldDetail(issueId,
                    VersionConstants.SysExtendFiledConstant.ACTUAL_ASK_LINE_TIME, VersionConstants.SysExtendFiledConstant.ACTUAL_ASK_LINE_TIME_NAME
                    ,null);
            sytExtendFieldDetailFactory.insertOrUpdateIssueExtendFieldDetail(issueId,
                    VersionConstants.SysExtendFiledConstant.APPROVAL_STATUS, VersionConstants.SysExtendFiledConstant.APPROVAL_STATUS_NAME
                    ,IssueApproveStatusEnum.NOT_APPROVE.CODE);
        }
    }

    @Override
    public int updateSuccessBizBacklogDeployType(Byte deployType, List<Long> successBizBacklogIds) {
        return sysExtendFieldDetailMapper.updateSuccessBizBacklogDeployType(deployType,successBizBacklogIds);
    }

    @Override
    public void resetUnbindingBizBacklogByPk(Long unbindingBizBacklogId) {
        sytExtendFieldDetailFactory.insertOrUpdateIssueExtendFieldDetail(unbindingBizBacklogId,
                VersionConstants.SysExtendFiledConstant.APPROVAL_STATUS, VersionConstants.SysExtendFiledConstant.APPROVAL_STATUS_NAME
                ,IssueApproveStatusEnum.NOT_APPROVE.CODE);
    }

    @Override
    public void updateApproveStartTimeAndApproveState(List<Long> reviewingBizBacklogIds,String startTime) {
        for(Long issueId : reviewingBizBacklogIds){
            sytExtendFieldDetailFactory.insertOrUpdateIssueExtendFieldDetail(issueId,
                    VersionConstants.SysExtendFiledConstant.APPROVAL_START_TIME, VersionConstants.SysExtendFiledConstant.APPROVAL_START_TIME_NAME
                    ,startTime);
            sytExtendFieldDetailFactory.insertOrUpdateIssueExtendFieldDetail(issueId,
                    VersionConstants.SysExtendFiledConstant.APPROVAL_STATUS, VersionConstants.SysExtendFiledConstant.APPROVAL_STATUS_NAME
                    ,IssueApproveStatusEnum.APPROVING.CODE);
        }
    }

    @Override
    public List<SysExtendFieldDetail> getSysExtendFieldDetail(Long issueId) {
        SysExtendFieldDetailExample example = new SysExtendFieldDetailExample();
        example.setOrderByClause("id asc");
        example.createCriteria().andIssueIdEqualTo(issueId);
        List<SysExtendFieldDetail> details = sysExtendFieldDetailMapper.selectByExample(example);
        return details;
    }

    @Override
    public int batchUpdate(List<SysExtendFieldDetail> sysExtendFieldDetailListUpdate) {
        return sysExtendFieldDetailMapper.batchUpdate(sysExtendFieldDetailListUpdate);
    }

    @Override
    public List<Long> getSysExtendFieldDetailByIds(List<Long> bizBacklogIds, List<SysExtendFieldDetail>  sysExtendFieldDetails) {
        return sysExtendFieldDetailMapper.getSysExtendFieldDetailByIds(bizBacklogIds,sysExtendFieldDetails);

    }

    @Override
    public int delSysExtendFieldDetail(Long issueId, String type) {
        SysExtendFieldDetailExample example = new SysExtendFieldDetailExample();
        example.createCriteria().andIssueIdEqualTo(issueId).andFieldIdEqualTo(type);
        return sysExtendFieldDetailMapper.deleteByExample(example);
    }

    @Override
    public SysExtendFieldDetail getEpicIdByBizNum(String issueId) {
        SysExtendFieldDetailExample example = new SysExtendFieldDetailExample();
        example.createCriteria().andFieldIdEqualTo(VersionConstants.SysExtendFiledConstant.BIZNUM).andValueEqualTo(issueId);
        return sysExtendFieldDetailMapper.selectByExample(example).get(0);
    }

    @Override
    public List<SysExtendFieldDetail> getSysExtendFieldDetailByProp(String prop, String propValue) {
        SysExtendFieldDetailExample example = new SysExtendFieldDetailExample();
        example.createCriteria().andFieldIdEqualTo(prop).andValueEqualTo(propValue);
        return sysExtendFieldDetailMapper.selectByExample(example);
    }

    @Override
    public List<SysExtendFieldDetail> getSysExtendFieldDetailList(Long issueId, List<String> extendFieldIdList) {
        SysExtendFieldDetailExample example = new SysExtendFieldDetailExample();
        example.createCriteria().andIssueIdEqualTo(issueId).andFieldIdIn(extendFieldIdList);
        return sysExtendFieldDetailMapper.selectByExample(example);
    }

    @Override
    public List<Long> getSysExtendFieldDetail(Map<String, Object> map, List issueIds) {
        return sysExtendFieldDetailMapper.getSysExtendFieldDetail(map, issueIds);
    }

    @Override
    public List<SysExtendFieldDetail> getSysExtendFieldDetailForEpicList(List<Long> epicIdList, String fieldId) {
        SysExtendFieldDetailExample example = new SysExtendFieldDetailExample();
        example.createCriteria().andIssueIdIn(epicIdList).andFieldIdEqualTo(fieldId);
        return sysExtendFieldDetailMapper.selectByExample(example);
    }

    @Override
    public List<String> getPlanDeployDateListByFormalReqCode(String formalReqCode) {
        List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailMapper.getPlanDeployDateListByFormalReqCode(formalReqCode);
        if(CollectionUtils.isEmpty(sysExtendFieldDetailList)){
            throw new BusinessException("异常数据，没有部署批次【planDeployDate】！");
        }
        return sysExtendFieldDetailList.stream().map(SysExtendFieldDetail::getValue).collect(Collectors.toList());
    }

    @Override
    public List<String> getBizNumListByFormalReqCode(String formalReqCode) {
        List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailMapper.getBizNumListByFormalReqCode(formalReqCode);
        if(CollectionUtils.isEmpty(sysExtendFieldDetailList)){
            throw new BusinessException("异常数据，局方需求编号："+formalReqCode+",没有拆分任务客户需求！");
        }
        return sysExtendFieldDetailList.stream().map(SysExtendFieldDetail::getValue).collect(Collectors.toList());
    }

    @Override
    public void batchUpdateEpicActualOnlineTime(List<Long> epicIdList, String actualOnlineTime) {
        if (CollectionUtils.isNotEmpty(epicIdList)) {
            sysExtendFieldDetailMapper.batchDelEpicActualOnlineTime(epicIdList);
            List<SysExtendFieldDetail> sysExtendFieldDetailList = Lists.newArrayList();
            for (Long epicId : epicIdList) {
                SysExtendFieldDetail sysExtendFieldDetail = new SysExtendFieldDetail();
                sysExtendFieldDetail.setIssueId(epicId);
                sysExtendFieldDetail.setFieldId("actualOnlineTime");
                sysExtendFieldDetail.setFieldName("需求实际上线时间");
                if (StringUtils.isNotBlank(actualOnlineTime)) {
                    sysExtendFieldDetail.setValue(actualOnlineTime);
                } else {
                    sysExtendFieldDetail.setValue(DateTools.getCurrentDate());
                }
                sysExtendFieldDetail.setState(StateEnum.U.getValue());
                sysExtendFieldDetail.setCreateTime(new Date());
                sysExtendFieldDetailList.add(sysExtendFieldDetail);
            }
            int count = sysExtendFieldDetailMapper.batchInsertEpicActualOnlineTime(sysExtendFieldDetailList);
            LOGGER.info("batchUpdateEpicActualOnlineTime return affect row:{}", count);
        }
    }

    @Override
    public boolean checkEpicIsAllOnline(String formalReqCode) {
        List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailMapper.checkEpicIsNotAllOnline(formalReqCode);
        return sysExtendFieldDetailList.size() > 0 ? false : true;
    }

    @Override
    public List<SysExtendFieldDetail> getNotOnlineEpic(String formalReqCode) {
        return sysExtendFieldDetailMapper.checkEpicIsNotAllOnline(formalReqCode);
    }

    @Override
    public List<SysExtendFieldDetail> getNoDeployAndToBePublish(String fieldId, String fieldValue) {
        return sysExtendFieldDetailMapper.getNoDeployAndToBePublish(fieldId,fieldValue);
    }

}
