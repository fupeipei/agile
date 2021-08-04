package com.yusys.agile.projectmanager.rest;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.projectmanager.dto.ProjectUserDayDto;
import com.yusys.agile.projectmanager.dto.ProjectUserHourDto;
import com.yusys.agile.projectmanager.dto.ProjectUserTotalHourDto;
import com.yusys.agile.projectmanager.service.SProjectUserHourService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: SProjectUserHourController
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/03 16:55
 */
@RestController
@RequestMapping("/project/userHour")
@Slf4j
public class SProjectUserHourController {

    @Autowired
    private SProjectUserHourService sProjectUserHourService;

    /**
     * @Author fupp1
     * @Description 获取项目下成员报工统计列表(分页)
     * @Date 17:51 2021/8/3
     * @Param [projectId, userId, startDate, endDate]
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     **/
    @PostMapping("/listProjectUserHour")
    public ControllerResponse listProjectUserHour(@RequestBody ProjectUserHourDto projectUserHourDto){
        try {
            return ControllerResponse.success(
                    new PageInfo<ProjectUserTotalHourDto>(sProjectUserHourService.listProjectUserHour(projectUserHourDto)));
        } catch (Exception e) {
            log.info("获取项目下成员报工统计列表:{}", e.getMessage());
            return ControllerResponse.fail(e.getMessage());
        }
    }

    /**
     * @Author fupp1
     * @Description 添加成员登记工时
     * @Date 20:07 2021/8/3
     * @Param [projectUserHourDto, securityDTO]
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     **/
    @PostMapping("/editProjectUserHour")
    public ControllerResponse editProjectUserHour(@RequestBody ProjectUserDayDto projectUserDayDto,
                                                  SecurityDTO securityDTO){
        try {
            sProjectUserHourService.editProjectUserHour(projectUserDayDto,securityDTO);
        } catch (Exception e) {
            log.info("添加成员登记工时请求:{}", e.getMessage());
            return ControllerResponse.fail(e.getMessage());
        }
        return ControllerResponse.success();
    }
    /**
     * @Author fupp1
     * @Description 获取成员项目报工详情
     * @Date 20:24 2021/8/3
     * @Param [hourId]
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     **/
    @GetMapping("/getProjectUserHourInfo")
    public ControllerResponse getProjectUserHourInfo(@RequestParam("projectId") Long projectId,
                                                     @RequestParam("workDate") String workDate,
                                                     @RequestParam("userId") Long userId){
        try {
            return ControllerResponse.success(sProjectUserHourService.getProjectUserHourInfo(projectId,workDate,userId));
        } catch (Exception e) {
            log.info("获取项目下成员报工统计列表:{}", e.getMessage());
            return ControllerResponse.fail(e.getMessage());
        }
    }

}
