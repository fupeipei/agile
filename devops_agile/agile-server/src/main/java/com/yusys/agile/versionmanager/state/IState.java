package com.yusys.agile.versionmanager.state;

import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import java.util.List;

/**
 *   : rock
 * @description:
 * @date:Create：in 2020/4/4
 */
public interface IState {

    List<VersionManagerDTO> getVersionPlanData(List<VersionManagerDTO> versionPlanAddedRequirements,
                                               List<VersionManagerDTO> versionPlanDeletedRequirements);
}
