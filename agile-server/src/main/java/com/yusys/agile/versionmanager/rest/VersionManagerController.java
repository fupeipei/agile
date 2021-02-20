package com.yusys.agile.versionmanager.rest;

import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import com.yusys.agile.versionmanager.service.VersionManagerService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName VersionManagerController
 * @Description 版本计划管理类
 * @Date 2021/2/7 16:13
 * @Version 1.0
 */
@RestController
public class VersionManagerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionManagerController.class);

    @Resource
    private VersionManagerService versionManagerService;

    /**
     * 获取版本管理列表
     * @param versionName 版本名称
     * @param securityDTO
     * @return
     */
    @GetMapping("/version/manager/all")
    public ControllerResponse getAllVersions(@RequestParam(value = "versionName",required=false ) String versionName,
                                             SecurityDTO securityDTO,
                                             @RequestParam(name = "projectId",required = false) Long paramProjectId,
                                             @RequestHeader(name = "projectId") Long projectId,
                                             @RequestParam(value = "issueName", required = false) String issueName,
                                             @RequestParam(value = "bizNum", required = false)  String bizNum,
                                             @RequestParam(value = "approvalStatus", required = false)  String approvalStatus){
        Long finalProjectId;
        if (paramProjectId != null) {
            finalProjectId = paramProjectId;
        } else {
            finalProjectId = projectId;
        }
        List<VersionManagerDTO> result = versionManagerService.getAllVersions(versionName,finalProjectId,issueName,bizNum,approvalStatus);
        return ControllerResponse.success(result);
    }

    /**
     * 新增/编辑版本
     * @param managerDTO
     * @param securityDTO
     * @return
     */
    @PostMapping("/version/manager")
    public ControllerResponse createOrUpdate(@RequestBody VersionManagerDTO managerDTO, SecurityDTO securityDTO){
        versionManagerService.createOrUpdate(managerDTO,securityDTO);
        return ControllerResponse.success("版本操作成功");
    }

    /**
     * 删除版本
     * @param versionId
     * @return
     */
    @DeleteMapping("/version/manager/{versionId}")
    public ControllerResponse deleteVersion(@PathVariable Long versionId,@RequestHeader(name = "projectId") Long projectId){
        versionManagerService.deleteVersion(versionId,projectId);
        return ControllerResponse.success("版本删除成功");
    }
    /**
     * 根据版本号获取版本计划
     * @param versionId
     * @return
     */
    @GetMapping("/version/manager/{versionId}")
    public ControllerResponse getVersionInfo(@PathVariable Long versionId){
        VersionManagerDTO versionManagerDTO = versionManagerService.getVersionInfo(versionId);
        return ControllerResponse.success(versionManagerDTO);
    }



    @PostMapping("/version/plan/agileApprovalResult")
    public JSONObject receiveVersionPlanReqApprovalResult(@RequestBody JSONObject jsonObject) {
        if (jsonObject == null) {
            return versionManagerService.createFailedResult("返回的响应内容不是JSON格式,YuDO无法解析");
        }
        String tenantName = UserThreadLocalUtil.getTenantCode();
        return jsonObject;
    }

    /**
     * @Description:根据项目id获取版本信息
     * @param versionName 版本号名称
     * @param pageNum 当前页码
     * @param pageSize 每页条数
     * @param projectId 项目id
     *
     * @date 2021/3/18
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/version/manager/getAllByVersionNameAndProjectId")
    public ControllerResponse versionManagerList(@RequestParam(name = "versionName", required = false) String versionName,
                                                 @RequestParam(name = "pageNum") Integer pageNum,
                                                 @RequestParam(name = "pageSize") Integer pageSize,
                                                 @RequestHeader(name = "projectId") Long projectId){
        List<VersionManagerDTO> result = versionManagerService.getAllByVersionNameAndProjectId(versionName,pageNum,pageSize,projectId);
        return ControllerResponse.success(new PageInfo<>(result));
    }


    /**
    * 根据版本号获取其他版本计划
     * @param versionId
     * @return
             */
    @GetMapping("/version/otherManager/{versionId}")
    public ControllerResponse getOtherVersionInfo(@PathVariable Long versionId){
        return ControllerResponse.success(versionManagerService.getOtherVersionInfo(versionId));
    }

    /**
     * @description版本计划上线后更新版本状态
     *  
     * @date  2021/2/7
     * @return
     */
    @PutMapping("/version/manager/syncVersionLaunchState/{versionId}")
    public ControllerResponse syncVersionLaunchState(@PathVariable Long versionId) {
        try {

            return ControllerResponse.success("更新版本计划上线状态成功");
        } catch (Exception e) {
            LOGGER.error("updateVersionState occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("更新版本计划上线状态失败");
        }
    }

    @GetMapping("/version/plan/reasonClassify")
    public ControllerResponse getReasonClassify() {
        return ControllerResponse.success(versionManagerService.getReasonClassifyList());
    }

    @GetMapping("/version/plan/reasonClassify/{classifyId}")
    public ControllerResponse getReasonClassifyValues(@PathVariable("classifyId") Integer classifyId) {
        return ControllerResponse.success(versionManagerService.getReasonClassifyValuesMap(classifyId));
    }

    @GetMapping("/version/plan/countIssue/{versionPlanId}")
    public ControllerResponse countIssue(@PathVariable("versionPlanId") Long versionPlanId) {
        return ControllerResponse.success(versionManagerService.countIssue(versionPlanId));
    }
}
