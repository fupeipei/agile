package com.yusys.agile.projectmanager.rest;


import com.yusys.agile.projectmanager.dto.ProjectManagerDto;
import com.yusys.agile.projectmanager.service.ProjectManagerService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project/projectmanager")
public class SProjectManagerController {


    @Autowired
    private ProjectManagerService projectManagerService;

    @PostMapping("/insertProjectManager")
    public ControllerResponse insertProjectManager(@RequestBody ProjectManagerDto projectManagerDto){
        ProjectManagerDto projectManagerDto1 = projectManagerService.insertProjectManager(projectManagerDto);
        return ControllerResponse.success(projectManagerDto1);

    }



}
