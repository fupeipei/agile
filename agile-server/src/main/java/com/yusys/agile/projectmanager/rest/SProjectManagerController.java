package com.yusys.agile.projectmanager.rest;


import com.github.pagehelper.PageInfo;
import com.yusys.agile.projectmanager.domain.SStaticProjectData;
import com.yusys.agile.projectmanager.dto.ProjectDataDto;
import com.yusys.agile.projectmanager.dto.ProjectManagerDto;
import com.yusys.agile.projectmanager.service.ProjectManagerService;
import com.yusys.agile.projectmanager.service.ProjectSystemRelService;
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
        try {
            ProjectManagerDto projectManagerDto1 = projectManagerService.insertProjectManager(projectManagerDto);
            return ControllerResponse.success(projectManagerDto1);
        } catch (Exception e) {
            return ControllerResponse.fail("添加项目失败"+e);
        }

    }

    /**
     * 根据类型查询状态等列表
     * @return
     */
    @GetMapping("/queryStaticDataByType")
    public ControllerResponse queryStaticDataByType(){
        try {
            List<ProjectDataDto> projectDataDtos = projectManagerService.queryStaticData();
            return ControllerResponse.success(projectDataDtos);
        } catch (Exception e) {
            return ControllerResponse.fail("查询异常"+e);
        }
    }


    @GetMapping("/queryProjectManagerPageInfo")
    public ControllerResponse queryProjectManagerPageInfo(@RequestParam(name = "pageNum") Integer pageNum,
                                                          @RequestParam(name = "pageSize")Integer pageSize,
                                                          @RequestParam(name = "searchKey",required = false) String searchKey){
        PageInfo<ProjectManagerDto> projectManagerDtoPageInfo = projectManagerService.queryProjectManagerPageInfo(pageNum, pageSize, searchKey);
        return ControllerResponse.success(projectManagerDtoPageInfo);

    }

    /**
     * 修改数据得回显
     * @param projectId
     * @return
     */
    @GetMapping("/queryProjectManagerByProjectId")
    public ControllerResponse queryProjectManagerByProjectId(@RequestParam(name = "projectId") Long projectId){
        ProjectManagerDto projectManagerDto = projectManagerService.queryProjectManagerByProjectId(projectId);
        return ControllerResponse.success(projectManagerDto);
    }


    /**
     * 修改项目
     * @param projectManagerDto
     * @return
     */
    @PostMapping("/updateProjectManager")
    public ControllerResponse updateProjectManager(@RequestBody ProjectManagerDto projectManagerDto){
        try {
            ProjectManagerDto projectManagerDto1 = projectManagerService.updateProjectManager(projectManagerDto);
            return ControllerResponse.success("修改成功");
        } catch (Exception e) {
            return ControllerResponse.fail("修改失败"+e);
        }

    }



}
