package com.yusys.agile.versionmanager.state;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.sysextendfield.SysExtendFieldDetailDTO;
import com.yusys.agile.sysextendfield.enums.ReqGroupEnum;
import com.yusys.agile.versionmanager.constants.VersionConstants;
import com.yusys.agile.versionmanager.dto.SendRMSRequestDTO;
import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import com.yusys.agile.versionmanager.enums.DeployTypeEnum;
import com.yusys.agile.versionmanager.enums.OperateTypeEnum;
import com.yusys.agile.versionmanager.enums.VersionStateEnum;
import com.google.common.collect.Lists;
import com.yusys.portal.util.date.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * : rock
 *
 * @description:
 * @date:Create：in 2020/4/4
 */
public abstract class AbstractState implements IState {
    private static final Logger log = LoggerFactory.getLogger(AbstractState.class);


    public abstract List<VersionManagerDTO> getVersionPlanData(List<VersionManagerDTO> versionPlanAddedRequirements,
                                                               List<VersionManagerDTO> versionPlanDeletedRequirements);

    public List<VersionManagerDTO> convertToVersionPlanApprovalRequestData(List<VersionManagerDTO> versionPlanList, String versionState) {
        for (VersionManagerDTO versionManagerDTO : versionPlanList) {
            generateSendRMSReqData(versionState, versionManagerDTO);
        }
        return versionPlanList;
    }

    private void generateSendRMSReqData(String versionState, VersionManagerDTO versionManagerDTO) {
        List<SendRMSRequestDTO> sendRMSRequestDTOList = Lists.newArrayList();
        List<IssueDTO> relateIssueList = versionManagerDTO.getRelateIssueList();
        SendRMSRequestDTO sendRMSRequestDTO;
        OperateTypeEnum operateTypeEnum = OperateTypeEnum.OPERATE_TYPE_ADD;
        for (IssueDTO issueDTO : relateIssueList) {
            sendRMSRequestDTO = new SendRMSRequestDTO();
            setSendRMSRequestData(issueDTO, sendRMSRequestDTO, versionManagerDTO.getChangeReason(), operateTypeEnum, null);
            sendRMSRequestDTOList.add(sendRMSRequestDTO);
        }
        if (VersionStateEnum.VERSION_STATE_UNCONFIRMED.CODE.equals(versionState)) {
            versionManagerDTO.setOperateType(OperateTypeEnum.OPERATE_TYPE_ADD.VALUE);
        } else {
            versionManagerDTO.setOperateType(OperateTypeEnum.OPERATE_TYPE_MODIFY.VALUE);
        }
        versionManagerDTO.setRelatedRequirementList(sendRMSRequestDTOList);
    }

    private void setSendRMSRequestData(IssueDTO issueDTO, SendRMSRequestDTO sendRMSRequestDTO, String reason, OperateTypeEnum operateTypeEnum, Byte versionDeployType) {
        List<SysExtendFieldDetailDTO> sysExtendFieldDetailDTOList = issueDTO.getSysExtendFieldDetailDTOList();
        sendRMSRequestDTO.setOperateType(operateTypeEnum.VALUE);
        sendRMSRequestDTO.setBizId(issueDTO.getIssueId());
        sendRMSRequestDTO.setCreateTime(issueDTO.getCreateTime());
        sendRMSRequestDTO.setCreateUid(issueDTO.getCreateUid());
        sendRMSRequestDTO.setStatus(issueDTO.getStageId());
        sendRMSRequestDTO.setUpdateTime(issueDTO.getUpdateTime());
        sendRMSRequestDTO.setUpdateUid(issueDTO.getUpdateUid());
        sendRMSRequestDTO.setId(issueDTO.getIssueId());
        sendRMSRequestDTO.setBizName(issueDTO.getTitle());
        sendRMSRequestDTO.setReason(reason);
        sendRMSRequestDTO.setAnalyst(issueDTO.getHandlerName());
        if (!DeployTypeEnum.OTHER.getCode().equals(versionDeployType)) {
            sendRMSRequestDTO.setMoveToVersionPlanId(issueDTO.getMoveToVersionPlanId());
        }
        for (SysExtendFieldDetailDTO sysExtendFieldDetailDTO : sysExtendFieldDetailDTOList) {
            if (VersionConstants.SysExtendFiledConstant.BIZNUM.equals(sysExtendFieldDetailDTO.getFieldId())) {
                sendRMSRequestDTO.setBizNum(sysExtendFieldDetailDTO.getValue());
            }
            if (VersionConstants.SysExtendFiledConstant.PLANSTATES.equals(sysExtendFieldDetailDTO.getFieldId())) {
                if (StringUtils.isNotBlank(sysExtendFieldDetailDTO.getValue())) {
                    sendRMSRequestDTO.setBizPlanStatus(Long.valueOf(sysExtendFieldDetailDTO.getValue()));
                }
            }
            if (VersionConstants.SysExtendFiledConstant.REQSCHEDULING.equals(sysExtendFieldDetailDTO.getFieldId())) {
                sendRMSRequestDTO.setBizScheduling(sysExtendFieldDetailDTO.getValue());
            }
            if (VersionConstants.SysExtendFiledConstant.SOURCE.equals(sysExtendFieldDetailDTO.getFieldId())) {
                sendRMSRequestDTO.setBizSource(sysExtendFieldDetailDTO.getValue());
            }
            if (VersionConstants.SysExtendFiledConstant.MAKESECONDARYDEP.equals(sysExtendFieldDetailDTO.getFieldId())) {
                sendRMSRequestDTO.setMakeDepart(sysExtendFieldDetailDTO.getValue());
            }
            if (VersionConstants.SysExtendFiledConstant.MAKEMAN.equals(sysExtendFieldDetailDTO.getFieldId())) {
                sendRMSRequestDTO.setMakeMan(sysExtendFieldDetailDTO.getValue());
            }
            if (VersionConstants.SysExtendFiledConstant.REQGROUP.equals(sysExtendFieldDetailDTO.getFieldId())) {
                sendRMSRequestDTO.setOfficialPrescriptionGroup(ReqGroupEnum.getName(sysExtendFieldDetailDTO.getValue()));
            }
            if (VersionConstants.SysExtendFiledConstant.FORMALREQCODE.equals(sysExtendFieldDetailDTO.getFieldId())) {
                sendRMSRequestDTO.setPartaReqnum(sysExtendFieldDetailDTO.getValue());
            }
            if (VersionConstants.SysExtendFiledConstant.ASK_LINE_TIME.equals(sysExtendFieldDetailDTO.getFieldId())) {
                try {
                    sendRMSRequestDTO.setAskLineTime(DateUtil.formatStrToDate(sysExtendFieldDetailDTO.getValue()));
                } catch (Exception e) {
                    log.error("setSendRMSRequestData setAskLineTime error:{}", e.getMessage());
                }
            }
            if (VersionConstants.SysExtendFiledConstant.ACTUAL_ASK_LINE_TIME.equals(sysExtendFieldDetailDTO.getFieldId())) {
                try {
                    sendRMSRequestDTO.setActualAskLineTime(DateUtil.formatStrToDate(sysExtendFieldDetailDTO.getValue()));
                } catch (Exception e) {
                    log.error("setSendRMSRequestData setAskLineTime error:{}", e.getMessage());
                }
            }
        }
    }

    protected List<VersionManagerDTO> mergeRequestData(List<VersionManagerDTO> addedRequestData,
                                                       List<VersionManagerDTO> deletedRequestData) {
        if (CollectionUtils.isNotEmpty(addedRequestData) && CollectionUtils.isNotEmpty(deletedRequestData)) {
            for (VersionManagerDTO outerVersionPlan : addedRequestData) {
                for (VersionManagerDTO innerVersionPlan : deletedRequestData) {
                    if (outerVersionPlan.getId().equals(innerVersionPlan.getId())) {
                        outerVersionPlan.getRelatedRequirementList().addAll(innerVersionPlan.getRelatedRequirementList());
                    }
                }
            }
            return addedRequestData;
        } else {
            if (CollectionUtils.isEmpty(addedRequestData)) {
                return deletedRequestData;
            }
            addedRequestData.addAll(deletedRequestData);
            return addedRequestData;
        }
    }

    public void generateSendRMSReqDeleteData(VersionManagerDTO versionManagerDTO) {
        List<SendRMSRequestDTO> sendRMSRequestDTOList = Lists.newArrayList();
        List<IssueDTO> relateIssueList = versionManagerDTO.getRelateIssueList();
        OperateTypeEnum operateTypeEnum = OperateTypeEnum.OPERATE_TYPE_DELETE;
        SendRMSRequestDTO sendRMSRequestDTO = new SendRMSRequestDTO();
        for (IssueDTO issueDTO : relateIssueList) {
            setSendRMSRequestData(issueDTO, sendRMSRequestDTO, versionManagerDTO.getChangeReason(), operateTypeEnum, versionManagerDTO.getMoveToDeployType());
            sendRMSRequestDTOList.add(sendRMSRequestDTO);
        }
        versionManagerDTO.setRelatedRequirementList(sendRMSRequestDTOList);
    }
}
