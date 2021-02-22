package com.yusys.agile.versionmanager.state;

import com.yusys.agile.versionmanager.dto.RequirementUnbindDTO;
import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import com.yusys.agile.versionmanager.enums.VersionStateEnum;

import java.util.Collections;
import java.util.List;

/**
 * : rock
 *
 * @description: 未确认状态
 * @date:Create：in 2020/4/4
 */
public class UnConfirmedState extends AbstractState {

    @Override
    public List<VersionManagerDTO> getVersionPlanData(List<VersionManagerDTO> versionPlanAddedRequirements,
                                                      List<VersionManagerDTO> versionPlanDeletedRequirements) {
        // 生成要发送的审批数据
        List<VersionManagerDTO> requestData = convertToVersionPlanApprovalRequestData(versionPlanAddedRequirements,
                VersionStateEnum.VERSION_STATE_UNCONFIRMED.CODE);
        return requestData;
    }
}
