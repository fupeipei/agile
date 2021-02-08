package com.yusys.agile.versionmanager.state;

import com.yusys.agile.versionmanager.dto.RequirementUnbindDTO;
import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import java.util.List;

/**
 *   : rock
 * @description: 版本状态上下文
 * @date:Create：in 2020/4/4
 */
public class VersionStateContext implements IState{
    public static final IState UNCONFIRMED_STATE = new UnConfirmedState();
    public static final IState APPROVAL_REJECT_STATE = new ApprovalRejectState();
    public static final IState CONFIRMED_STATE = new ConfirmedState();
    public static final IState CHANGED_STATE = new ChangedState();
    public static final IState BASELINE_STATE = new BaselineState();

    // 默认为未确认状态
    private IState currentState = UNCONFIRMED_STATE;

    public void setState(IState state) {
        this.currentState = state;
    }


    @Override
    public List<VersionManagerDTO> getVersionPlanData(List<VersionManagerDTO> versionPlanAddedRequirements,
                                                      List<VersionManagerDTO> versionPlanDeletedRequirements) {
        return this.currentState.getVersionPlanData(versionPlanAddedRequirements, versionPlanDeletedRequirements);
    }
}
