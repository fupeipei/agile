package com.yusys.agile.sprintv3.responseModel;

import com.yusys.agile.sprintv3.domain.SSprint;
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
    List<SsoSystem> sprintSystem;

    /**
     * 迭代用户
     */
    List<SsoUser> sprintUSer;

    /**
     * 迭代
     */
    SSprint sprint;
}
