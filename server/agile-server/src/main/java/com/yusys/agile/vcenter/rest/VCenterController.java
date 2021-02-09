package com.yusys.agile.vcenter.rest;

import com.yusys.agile.vcenter.domain.CreateVMDTO;
import com.yusys.agile.vcenter.domain.VcenterApplication;
import com.yusys.agile.vcenter.service.VCenterService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Date: 2021/2/25
 * @Description: vm相关接口
 */
@RestController
@RequestMapping("/vcenter")
public class VCenterController {
    @Resource
    private VCenterService vCenterService;

    /**
     * 功能描述  查询环境信息
     *
     * @param
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2021/2/25
     */
    @GetMapping("/getDev")
    public ControllerResponse getDev() {
        return ControllerResponse.success(vCenterService.getDev());
    }

    /**
     * 功能描述  获取所有的模板
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    @GetMapping("/getTemplateName")
    public ControllerResponse getTemplateName(Integer devId) {
        return ControllerResponse.success(vCenterService.getTemplateName(devId));
    }

    /**
     * 功能描述  获取所有的数据中心
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    @GetMapping("/getDatacenter")
    public ControllerResponse getDatacenter(Integer devId) {
        return ControllerResponse.success(vCenterService.getDatacenter(devId));
    }

    /**
     * 功能描述  获取所有的集群名
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    @GetMapping("/getClusterComputeResource")
    public ControllerResponse getClusterComputeResource(Integer devId) {
        return ControllerResponse.success(vCenterService.getClusterComputeResource(devId));
    }

    /**
     * 功能描述  获取主机名称
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    @GetMapping("/getHost")
    public ControllerResponse getHost(Integer devId, String clusterComputeResourceName) {
        return ControllerResponse.success(vCenterService.getHost(devId, clusterComputeResourceName));
    }

    /**
     * 功能描述  获取所有的集群名
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    @GetMapping("/getFolder")
    public ControllerResponse getFolder(Integer devId) {
        return ControllerResponse.success(vCenterService.getFolder(devId));
    }

    /**
     * 功能描述  选择自定义规范
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    @GetMapping("/getCustomizationSpec")
    public ControllerResponse getCustomizationSpec(Integer devId) {
        return ControllerResponse.success(vCenterService.getCustomizationSpec(devId));
    }

    /**
     * 功能描述  选择存储器
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    @GetMapping("/getDatastore")
    public ControllerResponse getDatastore(Integer devId, String clusterComputeResourceName) {
        return ControllerResponse.success(vCenterService.getDatastore(devId, clusterComputeResourceName));
    }

    /**
     * 功能描述  选择资源池
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    @GetMapping("/getPoolName")
    public ControllerResponse getPoolName(Integer devId) {
        return ControllerResponse.success(vCenterService.getPoolName(devId));
    }

    /**
     * 功能描述  选择ip
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    @GetMapping("/getIP")
    public ControllerResponse getIP(Long devId) {
        return ControllerResponse.success(vCenterService.getIP(devId));
    }

    /**
     * 功能描述  获取申清单列表
     *
     * @param vcenterApplication
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    @GetMapping("/getList")
    public ControllerResponse getList(VcenterApplication vcenterApplication) {
        return ControllerResponse.success(new PageInfo<>(vCenterService.getList(vcenterApplication)));
    }

    /**
     * 功能描述  根据模板创建虚拟机
     *
     * @param createVMDTO
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    @PostMapping("/CreateVMByTemplate")
    public ControllerResponse CreateVMByTemplate(CreateVMDTO createVMDTO) {
        try {
            vCenterService.CreateVMByTemplate(createVMDTO);
        } catch (Exception e) {
            ControllerResponse.fail("虚拟机申请失败！");
        }
        return ControllerResponse.success();
    }
}
