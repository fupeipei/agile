package com.yusys.agile.user.rest.impl;

import com.yusys.agile.user.service.ProjectUserService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**

 * @Date: 2021/2/6
 */
@RestController
public class ProjectUserController {
    @Resource
    private ProjectUserService projectUserService;

    /**
     * @param projectId
     * @param pageNum
     * @param pageSize

     * @Date 2021/2/6
     * @Description 项目概览中获取人员信息
     * @Return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/project/userInfo")
    public ControllerResponse projectUserInfo(@RequestHeader(name = "projectId") Long projectId, Integer pageNum, Integer pageSize) {
        return ControllerResponse.success(projectUserService.projectUserInfo(projectId, pageNum, pageSize));
    }
}
