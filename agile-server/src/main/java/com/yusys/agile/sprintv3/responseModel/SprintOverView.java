package com.yusys.agile.sprintv3.responseModel;

import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 迭代视图 -迭代概览
 */
@Data
public class SprintOverView {
    /**
     * 状态
     */
    private Byte status;
    /**
     * 迭代的名字
     */
    private String sprintName;
    /**
     * 迭代id
     */
    private long sprintId;
    /**
     * 工作时间
     */
    private Integer workHours;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 团队的名字
     */
    private String teamName;
    /**
     * 迭代系统
     */
    private List<SsoSystemRestDTO> sprintSystem;
    /**
     * 迭代用户
     */
    List<STeamMember> sprintUSer;
}
