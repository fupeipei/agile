package com.yusys.agile.teamv3.rest;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/8 10:54
 */
@RestController
@RequestMapping("/v3/team")
public class Teamv3Controller {

    @Autowired
    private Teamv3Service teamv3Service;

    /**
     * 条件、分页查询团队列表
     * @author zhaofeng
     * @date 2021/5/8 11:15
     * @param dto
     * @param security
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/listTeam")
    public ControllerResponse listTeam(@RequestBody TeamQueryDTO dto, SecurityDTO security) {
        List<TeamListDTO> result = teamv3Service.listTeam(dto, security);
        return ControllerResponse.success(new PageInfo<TeamListDTO>(result));
    }

    /**
     * 新增团队
     * @author zhaofeng
     * @date 2021/5/21 11:34
     * @param team
     */
    @PostMapping("/insertTeam")
    public ControllerResponse insertTeam(@RequestBody STeam team) {
        teamv3Service.insertTeam(team);
        return ControllerResponse.success();
    }

    /**
     * 删除团队
     * @author zhaofeng
     * @date 2021/5/21 11:34
     * @param teamId
     */
    @DeleteMapping("/deleteTeam/{teamId}")
    public ControllerResponse deleteTeam(@PathVariable("teamId") long teamId) {
        teamv3Service.deleteTeam(teamId);
        return ControllerResponse.success();
    }

    /**
     * 更新团队
     * @author zhaofeng
     * @date 2021/5/21 11:35
     * @param team
     */
    @PostMapping("/updateTeam")
    public ControllerResponse updateTeam(@RequestBody STeam team) {
        teamv3Service.updateTeam(team);
        return ControllerResponse.success();
    }

    /**
     * 查询团队详情
     * @author zhaofeng
     * @date 2021/5/21 11:35
     * @param teamId
     */
    @GetMapping("/queryTeam/{teamId}")
    public ControllerResponse queryTeam(@PathVariable("teamId") long teamId) {
        return ControllerResponse.success(teamv3Service.queryTeam(teamId));
    }




}
