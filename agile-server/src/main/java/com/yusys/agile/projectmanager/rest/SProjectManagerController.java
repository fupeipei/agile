package com.yusys.agile.projectmanager.rest;


import com.github.pagehelper.PageInfo;
import com.yusys.agile.projectmanager.domain.SProjectManager;
import com.yusys.agile.projectmanager.dto.ProjectDataDto;
import com.yusys.agile.projectmanager.dto.ProjectDemandDto;
import com.yusys.agile.projectmanager.dto.ProjectManagerDto;
import com.yusys.agile.projectmanager.service.ProjectManagerService;
import com.yusys.agile.projectmanager.service.ProjectSystemRelService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;
import com.yusys.portal.model.facade.entity.SsoUser;
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

    /**
     * @Author fupp1
     * @Description 获取所有项目信息
     * @Date 20:12 2021/8/4
     * @Param []
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     **/
    @GetMapping("/queryAllProjectManager")
    public ControllerResponse queryProjectManagers(){
        List<SProjectManager> sProjectManagers = projectManagerService.queryProjectManagers();
        return ControllerResponse.success(sProjectManagers);
    }

    /**
     * @Author fupp1
     * @Description 获取项目下所有成员
     * @Date 20:20 2021/8/4
     * @Param [projectId]
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     **/
    @GetMapping("/queryUserByProjectId")
    public ControllerResponse queryUserByProjectId(@RequestParam("projectId") Long projectId){
        List<SsoUser>  ssoUsers = projectManagerService.queryUserByProjectId(projectId);
        return ControllerResponse.success(ssoUsers);
    }
    /**
     * 查询需求列表
     * @return
     */
    @GetMapping("/queryProjectDemandList")
    public ControllerResponse queryProjectDemandList(@RequestParam(name = "projectId") Long projectId){
        List<ProjectDemandDto> projectDemandDtos = projectManagerService.queryProjectDemandList(projectId);
        return ControllerResponse.success(projectDemandDtos);
    }


    @GetMapping("/queryProjectManagerList")
    public ControllerResponse queryProjectManagerList(@RequestParam(name = "pageNum")Integer pageNum,
                                                      @RequestParam(name = "pageSize")Integer pageSize,
                                                      @RequestParam(name = "searchKey",required = false)String searchKey){
        try {
            PageInfo<ProjectManagerDto> projectManagerDtoPageInfo = projectManagerService.queryProjectManagerList(pageNum, pageSize, searchKey);
            return ControllerResponse.success(projectManagerDtoPageInfo);
        } catch (Exception e) {
            return ControllerResponse.fail("查询失败"+e);
        }
    }






}
