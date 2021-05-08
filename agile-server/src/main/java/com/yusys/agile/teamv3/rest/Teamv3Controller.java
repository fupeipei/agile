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

    /**
     * 查询团队列表
     *
     * @param dto
     * @param security
     * @return
     */
    @PostMapping("/listTeam")
    public ControllerResponse listTeam(@RequestBody TeamQueryDTO dto, SecurityDTO security) {
        List<TeamListDTO> result = teamv3Service.listTeam(dto, security);
        return ControllerResponse.success(new PageInfo<TeamListDTO>(result));
    }

    /**
     * 查询租户下团队，并关联系统和成员
     *
     * @return
     */
    @GetMapping("/list")
    public ControllerResponse list() {
        List<TeamListDTO> result = teamv3Service.list();
        return ControllerResponse.success(result);
    }
}
