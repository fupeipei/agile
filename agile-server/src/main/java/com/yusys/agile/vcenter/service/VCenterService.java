package com.yusys.agile.vcenter.service;

import com.yusys.agile.vcenter.domain.CreateVMDTO;
import com.yusys.agile.vcenter.domain.VcenterApplication;
import com.yusys.agile.vcenter.domain.VcenterDev;
import com.yusys.agile.vcenter.domain.VcenterVmIp;

import java.util.List;
import java.util.Map;

public interface VCenterService {

    /**
     * 功能描述  查询环境信息
     *
     * @param
     * @return com.yusys.agile.vcenter.domain.VcenterDev
     * @date 2021/2/25
     */
    List<VcenterDev> getDev();

    /**
     * 功能描述  获取所有的模板
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    List<Map> getTemplateName(Integer devId);

    /**
     * 功能描述  获取所有的数据中心
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    List<Map> getDatacenter(Integer devId);

    /**
     * 功能描述  获取所有的集群
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    List<Map> getClusterComputeResource(Integer devId);


    /**
     * 功能描述  获取集群下的主机
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    List<Map> getHost(Integer devId, String clusterComputeResourceName);

    /**
     * 功能描述  选择存放位置
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    List<Map> getFolder(Integer devId);

    /**
     * 功能描述  选择自定义规范
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    List<Map> getCustomizationSpec(Integer devId);

    /**
     * 功能描述  选择存储器
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    List<Map> getDatastore(Integer devId, String clusterComputeResourceName);

    /**
     * 功能描述  选择资源池
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    List<Map> getPoolName(Integer devId);

    /**
     * 功能描述  查询ip地址
     *
     * @param devId
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    List<VcenterVmIp> getIP(Long devId);

    /**
     * 功能描述  查询列表
     *
     * @param vcenterApplication
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    List<VcenterApplication> getList(VcenterApplication vcenterApplication);

    /**
     * 功能描述  查询列表
     *
     * @param createVMDTO
     * @return java.util.List<java.lang.String>
     * @date 2021/2/25
     */
    void CreateVMByTemplate(CreateVMDTO createVMDTO);
}
