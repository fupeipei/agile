package com.yusys.agile.set.permission.rest.impl;

import com.yusys.agile.set.permission.constant.UserRoleConstant;
import com.yusys.agile.set.permission.service.UserRoleService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userRole")
public class UserRoleControllerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleControllerImpl.class);

    @Resource
    private UserRoleService userRoleService;

    /**
     * @description 查询项目级角色列表
     *  
     * @date 2020/05/20
     * @return
     */
    @GetMapping("/queryProjectRoleList")
    public ControllerResponse queryProjectRoleList(@RequestParam(name = "pageNum") Integer pageNum, @RequestParam(name = "pageSize") Integer pageSize) {
        try {
            return ControllerResponse.success(userRoleService.queryProjectRoleList(UserRoleConstant.UserRoleEnum.PROJECT_ROLE.getCode(), pageNum, pageSize));
        } catch (Exception e) {
            LOGGER.error("queryProjectRoleList occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("查询角色列表异常");
        }
    }
}