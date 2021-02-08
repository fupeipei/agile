package com.yusys.agile.versionmanager.state;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.versionmanager.dto.RequirementUnbindDTO;
import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import com.yusys.agile.versionmanager.enums.OperateTypeEnum;
import com.yusys.agile.versionmanager.enums.VersionStateEnum;

import java.util.List;

/**
 * @description: 基线版本状态
 * @date:Create: 2021/2/10
 */
public class BaselineState extends AbstractState {

    @Override
    public List<VersionManagerDTO> getVersionPlanData(List<VersionManagerDTO> versionPlanAddedRequirements, List<VersionManagerDTO> versionPlanDeletedRequirements) {
        // 生成要发送的审批数据
        List<VersionManagerDTO> addedRequestData = convertToVersionPlanApprovalRequestData(versionPlanAddedRequirements,
                VersionStateEnum.VERSION_STATE_BASELINE.CODE);
        List<VersionManagerDTO> requestData = mergeRequestData(addedRequestData, versionPlanDeletedRequirements);
        return requestData;
    }

    @Override
    public List<VersionManagerDTO> convertToVersionPlanApprovalRequestData(List<VersionManagerDTO> versionPlanList, String versionState) {
        for (VersionManagerDTO versionManagerDTO : versionPlanList) {
            List<IssueDTO> epicIssueList = versionManagerDTO.getRelateIssueList();
            for (IssueDTO dmpBizBacklogDTO  : epicIssueList) {
                dmpBizBacklogDTO.setOperateType(OperateTypeEnum.OPERATE_TYPE_ADD.VALUE);
            }
            versionManagerDTO.setOperateType(OperateTypeEnum.OPERATE_TYPE_MODIFY.VALUE);
            versionManagerDTO.setRelateIssueList(epicIssueList);
        }
        return versionPlanList;
    }
}
