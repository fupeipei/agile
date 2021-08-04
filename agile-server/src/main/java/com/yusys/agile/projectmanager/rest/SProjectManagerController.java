package com.yusys.agile.projectmanager.rest;


import com.github.pagehelper.PageInfo;
import com.yusys.agile.projectmanager.domain.SStaticProjectData;
import com.yusys.agile.projectmanager.dto.ProjectManagerDto;
import com.yusys.agile.projectmanager.service.ProjectManagerService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/projectmanager")
public class SProjectManagerController {


    @Autowired
    private ProjectManagerService projectManagerService;

    /**
     * 添加项目
     * @param projectManagerDto
     * @return
     */
    @PostMapping("/insertProjectManager")
    public ControllerResponse insertProjectManager(@RequestBody ProjectManagerDto projectManagerDto){
        ProjectManagerDto projectManagerDto1 = projectManagerService.insertProjectManager(projectManagerDto);
        return ControllerResponse.success();

    }

    /**
     * 根据类型查询状态等列表
     * @param type
     * @return
     */
    @GetMapping("/queryStaticDataByType")
    public ControllerResponse queryStaticDataByType(@RequestParam(name = "type",required = true) Integer type){
        List<SStaticProjectData> sStaticProjectData = projectManagerService.queryStaticDataByType(type);
        return ControllerResponse.success(sStaticProjectData);
    }


    @GetMapping("/queryProjectManagerPageInfo")
    public ControllerResponse queryProjectManagerPageInfo(@RequestParam(name = "pageNum") Integer pageNum,
                                                          @RequestParam(name = "pageSize")Integer pageSize,
                                                          @RequestParam(name = "searchKey",required = false) String searchKey){
        PageInfo<ProjectManagerDto> projectManagerDtoPageInfo = projectManagerService.queryProjectManagerPageInfo(pageNum, pageSize, searchKey);
        return ControllerResponse.success(projectManagerDtoPageInfo);

    }

    @GetMapping("/queryProjectManagerByProjectId")
    public ControllerResponse queryProjectManagerByProjectId(@RequestParam(name = "projectId") Long projectId){
        ProjectManagerDto projectManagerDto = projectManagerService.queryProjectManagerByProjectId(projectId);
        return ControllerResponse.success(projectManagerDto);
    }



}
