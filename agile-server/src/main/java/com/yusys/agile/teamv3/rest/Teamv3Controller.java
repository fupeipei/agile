package com.yusys.agile.teamv3.rest;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.enums.TeamTypeEnum;
import com.yusys.agile.teamv3.response.QueryTeamResponse;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
        if(Objects.equals(team.getTeamType(), TeamTypeEnum.agile_team.getCode())){
            teamv3Service.insertTeam(team);
        }else if(Objects.equals(team.getTeamType(), TeamTypeEnum.lean_team.getCode())){
            teamv3Service.insertTeamForLean(team);
        }else{
            throw new BusinessException("团队类型错误");
        }
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
        STeam team = teamv3Service.getTeamById(teamId);
        if(Objects.equals(team.getTeamType(), TeamTypeEnum.agile_team.getCode())){
            teamv3Service.deleteTeam(teamId);
        }else if(Objects.equals(team.getTeamType(), TeamTypeEnum.lean_team.getCode())){
            teamv3Service.deleteTeamForLean(teamId);
        }else{
            throw new BusinessException("团队类型错误");
        }
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
        if(Objects.equals(team.getTeamType(), TeamTypeEnum.agile_team.getCode())){
            teamv3Service.updateTeam(team);
        }else if(Objects.equals(team.getTeamType(), TeamTypeEnum.lean_team.getCode())){
            teamv3Service.updateTeamForLean(team);
        }else{
            throw new BusinessException("团队类型错误");
        }
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
        QueryTeamResponse team = teamv3Service.queryTeam(teamId);
        return ControllerResponse.success(team);
    }


    /**
     * 通过团队id查询团队下的系统
     * @param teamId 团队id
     * @return
     */
    @GetMapping("/querySystem/{teamId}")
    public ControllerResponse querySystemByTeamId(@PathVariable("teamId") long teamId){
        return ControllerResponse.success(teamv3Service.querySystemByTeamId(teamId));
    }

    /**
     *功能描述 查询所有系统下团队
     * @author shenfeng
     * @date 2021/6/16
      * @param dto
     * @param security
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/listAllTeamBySystemId")
    public ControllerResponse listAllTeam(@RequestBody TeamQueryDTO dto, SecurityDTO security) {
        List<TeamListDTO> result = teamv3Service.listAllTeam(dto, security);
        return ControllerResponse.success(result);
    }
}
