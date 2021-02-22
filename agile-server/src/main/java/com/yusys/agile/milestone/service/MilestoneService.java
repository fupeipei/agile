package com.yusys.agile.milestone.service;

import com.yusys.agile.milestone.dto.MilestoneDTO;

import java.util.List;

/**
 * 里程碑service
 *
 * @create 2020-08-12 15:49
 */
public interface MilestoneService {
    /**
     * 功能描述: 创建里程碑
     *
     * @param milestoneDTO
     * @return void
     * @date 2020/8/12
     */
    MilestoneDTO addMilestone(MilestoneDTO milestoneDTO);

    /**
     * 功能描述: 删除里程碑
     *
     * @param milestoneId
     * @return void
     * @date 2020/8/12
     */
    void deleteMilestone(Long milestoneId);

    /**
     * 功能描述:
     *
     * @param milestoneDTO
     * @return void
     * @date 2020/8/13
     */
    MilestoneDTO editMilestone(MilestoneDTO milestoneDTO);

    /**
     * 功能描述: 查询里程碑
     *
     * @param milestoneId
     * @return com.yusys.agile.milestone.dto.MilestoneDTO
     * @date 2020/8/13
     */
    MilestoneDTO getMilestone(Long milestoneId);

    /**
     * 功能描述:
     *
     * @param projectId
     * @return List<MilestoneDTO>
     * @date 2020/8/17
     */
    List<MilestoneDTO> listMilestones(Long projectId);
}
