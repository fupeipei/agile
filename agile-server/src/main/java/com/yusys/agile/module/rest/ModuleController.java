package com.yusys.agile.module.rest;

import com.ctc.wstx.io.SystemId;
import com.yusys.agile.module.dto.ModuleDTO;
import com.yusys.agile.module.service.ModuleService;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: ModuleController
 * @Description: 模块管理controller
 * :
 * @CreateDate: 2020/05/25 16:15
 * @Version 1.0
 */
@Api("模块管理")
@RestController
@RequestMapping("/module")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @ApiOperation("获取Module分页列表")
    @GetMapping("/list")
    public ControllerResponse listModule(@ApiParam("模块标题") @RequestParam(name = "moduleName", required = false) String moduleName,
                                         @RequestParam(name = "pageNum") Integer pageNum,
                                         @RequestParam(name = "pageSize") Integer pageSize,
                                         @RequestHeader(name = "systemId") Long systemId) {
        List<ModuleDTO> modules = null;
        try {
            modules = moduleService.listModule(moduleName, pageNum, pageSize, systemId);
        } catch (Exception e) {
            return ControllerResponse.fail("获取分页模块列表数据异常");
        }
        return ControllerResponse.success(new PageInfo<>(modules));
    }

    @ApiOperation("模块创建/更新")
    @PostMapping("/insert")
    public ControllerResponse createOrUpdateModule(@RequestBody ModuleDTO moduleDTO , @RequestHeader(name = "systemId") Long systemId) {
        moduleDTO.setSystemId(systemId);
        return ControllerResponse.success(moduleService.createOrUpdateModule(moduleDTO));
    }

    @ApiOperation("模块删除")
    @DeleteMapping("/delete/{moduleId}")
    public ControllerResponse deleteModule(@PathVariable Long moduleId) {
        try {
            moduleService.deleteModule(moduleId);
            return ControllerResponse.success("模块数据删除成功");
        } catch (Exception e) {
            return ControllerResponse.fail("模块数据删除失败");
        }
    }

    @ApiOperation("模块详情")
    @GetMapping("/detail/{moduleId}")
    public ControllerResponse detailModule(@PathVariable Long moduleId) {
        return ControllerResponse.success(moduleService.detailModule(moduleId));
    }

    @ApiOperation("根据产品ID获取模块列表")
    @GetMapping("/system/{systemId}")
    public ControllerResponse listAllModule(@PathVariable Long systemId) {
        return ControllerResponse.success(moduleService.listModuleBySystemId(systemId));
    }

    @ApiOperation("校验模块标题唯一性")
    @GetMapping("/unique")
    public ControllerResponse checkModuleNameUnique(
            @RequestParam(name = "moduleName") String moduleName,
            @RequestParam(name = "moduleId", required = false) Long moduleId) {
        if (moduleService.checkModuleNameUnique(moduleName, moduleId)) {
            return ControllerResponse.success(StringUtils.join("模块标题：", moduleName, ",可以使用"));
        }
        return ControllerResponse.fail(StringUtils.join("模块标题：", moduleName, "，不能重复!"));
    }

    @ApiOperation("根据产品ID集合获取模块列表")
    @PostMapping("/system/ids")
    public ControllerResponse listAllModules(@RequestBody List<Long> systemIds) {
        return ControllerResponse.success(moduleService.listModuleBySystemIds(systemIds));
    }

    /*
     * @Author wuzefei
     * @Date 16:12 2021/8/12
     * @Description
     * @param null
     * @return {@link null}
     **/
}
