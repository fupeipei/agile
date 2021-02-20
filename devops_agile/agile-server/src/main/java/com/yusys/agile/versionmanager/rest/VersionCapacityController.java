package com.yusys.agile.versionmanager.rest;

import com.yusys.agile.versionmanager.domain.VersionCapacity;
import com.yusys.agile.versionmanager.service.VersionCapacityService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName VersionCapacityController
 * @Description 设置系统团队容量 控制类
 *
 * @Date 2020/8/19 10:35
 * @Version 1.0
 */
@RestController
@RequestMapping("/version/capacity")
public class VersionCapacityController {

    @Resource
    private VersionCapacityService capacityService;

    /**
     * 版本容量设置
     * @param versionCapacity
     * @param securityDTO
     * @return
     */
    @GetMapping("/settings")
    public ControllerResponse settings(@RequestBody VersionCapacity versionCapacity, SecurityDTO securityDTO){

        return ControllerResponse.success();
    }

    /**
     * 获取版本容量详情
     * @param id    版本容量主键Id
     * @return
     */
    @GetMapping("/settings/{id}")
    public ControllerResponse settings(@PathVariable Long id){

        return ControllerResponse.success();
    }

    /**
     * 根据版本ID获取版本容量占比
     * @param versionId 版本ID
     * @return
     */
    @GetMapping("/settings/anasly/{versionId}")
    public ControllerResponse settings(@PathVariable Long versionId, SecurityDTO securityDTO){

        return ControllerResponse.success();
    }

}
