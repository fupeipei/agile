package com.yusys.agile.sprintv3.responseModel;

import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintUserHour;
import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.model.facade.entity.SsoUser;
import lombok.Data;

import java.util.List;

/**
 * 迭代视图 -迭代概览
 */
@Data
public class SprintOverView {

    /**
     * 迭代系统
     */
    List<SsoSystemRestDTO> sprintSystem;

    /**
     * 迭代用户
     */
    List<STeamMember> sprintUSer;

    /**
     * 迭代
     */
    SSprint sprint;

    /**
     * 团队的名字
     */
    String teamName;
}
