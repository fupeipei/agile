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
     * 查询团队列表
     * @author zhaofeng
     * @date 2021/5/8 11:16
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/list")
    public ControllerResponse list() {
        List<TeamListDTO> result = teamv3Service.list();
        return ControllerResponse.success(result);
    }

    /**
     * 新增团队
     *
     * @param team 团队
     * @return {@link ControllerResponse}
     */
    @PostMapping("/insertTeam")
    public ControllerResponse insertTeam(STeam team) {
        return ControllerResponse.success(teamv3Service.insertTeam(team));
    }

    /**
     * 删除团队
     *
     * @param teamId 团队id
     * @return {@link ControllerResponse}
     */
    @DeleteMapping("/deleteTeam")
    public ControllerResponse deleteTeam(long teamId) {
        return ControllerResponse.success(teamv3Service.deleteTeam(teamId));
    }

    /**
     * 更新团队
     *
     * @param team 团队
     * @return {@link ControllerResponse}
     */
    @PostMapping("/updateTeam")
    public ControllerResponse updateTeam(STeam team) {
        return ControllerResponse.success(teamv3Service.updateTeam(team));
    }

    /**
     * 查询团队
     *
     * @param teamId 团队id
     * @return {@link ControllerResponse}
     */
    @GetMapping("/queryTeam")
    public ControllerResponse queryTeam(long teamId) {
        return ControllerResponse.success(teamv3Service.queryTeam(teamId));
    }




}
