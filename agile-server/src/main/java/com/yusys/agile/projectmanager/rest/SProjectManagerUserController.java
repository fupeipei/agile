package com.yusys.agile.projectmanager.rest;


import com.github.pagehelper.PageInfo;
import com.yusys.agile.projectmanager.service.ProjectUserRelService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/project/projectManagerUser")
public class SProjectManagerUserController {




    @Autowired
    private ProjectUserRelService projectUserRelService;


    @GetMapping("/queryUserInfoByCondition")
    public ControllerResponse queryUserInfoByProductId(@RequestParam(name = "pageSize")Integer pageSize,
                                                       @RequestParam(name = "pageNum")Integer pageNum,
                                                       @RequestParam(name = "searchKey",required = false) String searchKey,
                                                       @RequestParam(name = "projectId") Long projectId){
        try {
            PageInfo<SsoUserDTO> ssoUserDTOPageInfo = projectUserRelService.queryUserInfoByCondition(pageNum, pageSize, searchKey, projectId);
            return ControllerResponse.success(ssoUserDTOPageInfo);
        } catch (Exception e) {
            return ControllerResponse.fail("查询失败"+e);
        }
    }

}
