package com.yusys.agile.team.rest.impl;

import com.yusys.agile.fault.enums.UserRelateTypeEnum;
import com.yusys.agile.team.service.TeamService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *    maxp
 */
@RestController
public class TeamController {
    @Autowired
    private TeamService teamService;
    /**
     *    maxp
     * @Date 2020/4/13
     * @Description 获取项目下所有团队
     * @param projectId
     * @Return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/team/listTeam")
    public ControllerResponse getListTeam(@RequestHeader(name = "projectId") Long projectId) {
        return ControllerResponse.success(teamService.getListTeam(projectId));
    }

    /**
     *    maxp
     * @Date 2020/4/14
     * @Description 获取项目中的人员信息
      * @param projectId
     * @Return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/user/listMemberUsers")
    public ControllerResponse listMemberUsers(@RequestHeader(name = "projectId") Long projectId) {
        return ControllerResponse.success(teamService.listMemberUsers(projectId, UserRelateTypeEnum.PROJECT.getValue()));
    }
}
