package com.yusys.agile.projectmanager.rest;

import com.yusys.agile.projectmanager.service.SProjectUserHourService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: SProjectUserHourController
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/03 16:55
 */
@RestController
@RequestMapping("/project/userHour")
public class SProjectUserHourController {

    @Autowired
    private SProjectUserHourService sProjectUserHourService;

    @GetMapping("/listProjectUserHour")
    public ControllerResponse listProjectUserHour(@RequestParam("projectId") Long projectId,
                                                  @RequestParam("userId") Long userId,
                                                  @RequestParam("startDate") String startDate,
                                                  @RequestParam("endDate") String endDate){
        return new ControllerResponse().success(sProjectUserHourService.listProjectUserHour(projectId,userId,startDate,endDate));

    }
}
