package com.yusys.agile.versionmanager.state;

import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import com.yusys.agile.versionmanager.enums.VersionStateEnum;

import java.util.List;

/**
 * : rock
 *
 * @description: 有变更状态
 * @date:Create：in 2020/4/4
 */
public class ChangedState extends AbstractState {

    @Override
    public List<VersionManagerDTO> getVersionPlanData(List<VersionManagerDTO> versionPlanAddedRequirements,
                                                      List<VersionManagerDTO> versionPlanDeletedRequirements) {
        // 生成要发送的审批数据
        List<VersionManagerDTO> addedRequestData = convertToVersionPlanApprovalRequestData(versionPlanAddedRequirements,
                VersionStateEnum.VERSION_STATE_CHANGED.CODE);
        for (VersionManagerDTO versionManagerDTO : versionPlanDeletedRequirements) {
            generateSendRMSReqDeleteData(versionManagerDTO);
        }
        List<VersionManagerDTO> requestData = mergeRequestData(addedRequestData, versionPlanDeletedRequirements);
        return requestData;
    }
}
