package com.yusys.agile.sprintv3.rest;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintV3.dto.SprintQueryDTO;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/11 14:50
 */
@RestController
@RequestMapping("/v3/sprint")
public class Sprintv3Controller {

    @Autowired
    private Sprintv3Service sprintv3Service;

    /**
     * 条件-分页查询迭代列表
     * @author zhaofeng
     * @date 2021/5/11 15:56
     * @param dto
     * @param security
     */
    @PostMapping("/listSprint")
    public ControllerResponse listSprint(@RequestBody SprintQueryDTO dto, SecurityDTO security){
        List<SprintListDTO> list = sprintv3Service.listSprint(dto, security);
        return ControllerResponse.success(new PageInfo<SprintListDTO>(list));
    }





}
