package com.yusys.agile.sysextendfield.rest;

import com.yusys.agile.sysextendfield.service.SysExtendFieldService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Date: 2021/2/9
 */
@RestController
@RequestMapping("/require")
public class sysExtendFieldController {
    @Resource
    private SysExtendFieldService sysExtendFieldService;

    @GetMapping("/field/{issueType}")
    public ControllerResponse getField(@PathVariable("issueType") Byte issueType) {
        return ControllerResponse.success(sysExtendFieldService.getField(issueType));

    }
}
