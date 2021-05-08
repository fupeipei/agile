package com.yusys.agile.teamv3.rest;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
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

    /*
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

    /*
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
}
