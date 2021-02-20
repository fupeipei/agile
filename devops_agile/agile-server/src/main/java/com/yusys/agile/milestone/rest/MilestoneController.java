package com.yusys.agile.milestone.rest;

import com.yusys.agile.milestone.dto.MilestoneDTO;
import com.yusys.agile.milestone.service.MilestoneService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 里程碑
 *

 * @create 2020-08-12 15:38
 */
@RestController
@RequestMapping("/milestone")
public class MilestoneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MilestoneController.class);

    @Autowired
    private MilestoneService milestoneService;

    /**
     * 功能描述:新增里程碑
     *
     * @param milestoneDTO
     * @param projectId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;

     * @date 2020/8/12
     */
    @PostMapping("/addMilestone")
    public ControllerResponse addMilestone(@RequestBody MilestoneDTO milestoneDTO, @RequestHeader(name = "projectId") Long projectId) {

        milestoneDTO.setProjectId(projectId);
        MilestoneDTO returnMilestoneDTO = milestoneService.addMilestone(milestoneDTO);
        return ControllerResponse.success(returnMilestoneDTO);
    }


    /**
     * 功能描述: 删除里程碑
     *
     * @param milestoneId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;

     * @date 2020/8/12
     */
    @DeleteMapping("/deleteMilestone/{milestoneId}")
    public ControllerResponse deleteMilestone(@PathVariable Long milestoneId) {
        milestoneService.deleteMilestone(milestoneId);
        return ControllerResponse.success();
    }


    /**
     * 功能描述:修改里程碑
     *
     * @param milestoneDTO
     * @param projectId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;

     * @date 2020/8/13
     */
    @PostMapping("/editMilestone")
    public ControllerResponse editMilestone(@RequestBody MilestoneDTO milestoneDTO, @RequestHeader(name = "projectId") Long projectId) {
        try {
            milestoneDTO.setProjectId(projectId);
            return ControllerResponse.success(milestoneService.editMilestone(milestoneDTO));
        } catch (Exception e) {
            LOGGER.error("修改里程碑失败！e:{}" + e);
            return ControllerResponse.fail("修改里程碑失败！" + e.getMessage());
        }


    }

    /**
     * 功能描述:  查询里程碑
     *
     * @param milestoneId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;

     * @date 2020/8/13
     */
    @GetMapping("/getMilestone/{milestoneId}")
    public ControllerResponse getMilestone(@PathVariable Long milestoneId) {
        return ControllerResponse.success(milestoneService.getMilestone(milestoneId));
    }

    /**
     * 功能描述: 按时间顺序展示里程碑

     * @date 2020/8/17
     * @param projectId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/listMilestones")
    public ControllerResponse listMilestones( @RequestHeader(name = "projectId") Long projectId) {
        return ControllerResponse.success(milestoneService.listMilestones(projectId));
    }


}