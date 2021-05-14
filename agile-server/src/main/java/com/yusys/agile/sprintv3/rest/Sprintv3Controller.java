package com.yusys.agile.sprintv3.rest;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintV3.dto.SprintQueryDTO;
import com.yusys.agile.sprintV3.dto.SprintV3DTO;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
     * @param sprintId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2021/5/10
     * @Description查看迭代编辑页面
     */
    @GetMapping("/getSprint")
    public ControllerResponse viewEdit( Long sprintId) {
        return ControllerResponse.success(sprintv3Service.viewEdit(sprintId));
    }

    /**
     * 条件-分页查询迭代列表
     *
     * @param dto
     * @param security
     * @author zhaofeng
     * @date 2021/5/11 15:56
     */
    @PostMapping("/listSprint")
    public ControllerResponse listSprint(@RequestBody SprintQueryDTO dto, SecurityDTO security) {
        List<SprintListDTO> list = sprintv3Service.listSprint(dto, security);
        return ControllerResponse.success(new PageInfo<SprintListDTO>(list));
    }

    /**
     * 新建迭代
     *
     * @param sprintV3DTO 迭代v3dto
     * @return {@link ControllerResponse}
     */
    @PostMapping("/createSprint")
    public ControllerResponse createSprint(@RequestBody SprintV3DTO sprintV3DTO) {
        return ControllerResponse.success(sprintv3Service.createSprint(sprintV3DTO));

    }

    @PostMapping("/updateSprint")
    public ControllerResponse updateSprint(@RequestBody SprintDTO sprintDTO){
        try {
            sprintv3Service.updateSprint(sprintDTO);
        } catch (Exception e) {
            return ControllerResponse.fail("编辑迭代失败：" + e.getMessage());
        }
        return ControllerResponse.success("编辑成功");
    }


}
