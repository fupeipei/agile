package com.yusys.agile.vcenter.service;

import com.yusys.agile.vcenter.domain.CreateVMDTO;
import com.yusys.agile.vcenter.domain.VcenterApplication;
import com.yusys.agile.vcenter.domain.VcenterDev;
import com.yusys.agile.vcenter.domain.VcenterVmIp;

import java.util.List;
import java.util.Map;

public interface VCenterService {

    /**
      *功能描述  查询环境信息
      *
      * @date 2020/5/29
      * @param
      * @return com.yusys.agile.vcenter.domain.VcenterDev
     */
    List<VcenterDev> getDev();

    /**
      *功能描述  获取所有的模板
      *
      * @date 2020/5/29
      * @param devId
      * @return java.util.List<java.lang.String>
     */
    List<Map> getTemplateName(Integer devId);

    /**
      *功能描述  获取所有的数据中心
      *
      * @date 2020/5/29
      * @param devId
      * @return java.util.List<java.lang.String>
     */
    List<Map > getDatacenter(Integer devId);

    /**
     *功能描述  获取所有的集群
     *
     * @date 2020/5/29
     * @param devId
     * @return java.util.List<java.lang.String>
     */
    List<Map > getClusterComputeResource(Integer devId);


    /**
     *功能描述  获取集群下的主机
     *
     * @date 2020/5/29
     * @param devId
     * @return java.util.List<java.lang.String>
     */
    List<Map > getHost(Integer devId,String clusterComputeResourceName);

    /**
     *功能描述  选择存放位置
     *
     * @date 2020/5/29
     * @param devId
     * @return java.util.List<java.lang.String>
     */
    List<Map > getFolder(Integer devId);
    /**
     *功能描述  选择自定义规范
     *
     * @date 2020/5/29
     * @param devId
     * @return java.util.List<java.lang.String>
     */
    List<Map > getCustomizationSpec(Integer devId);
    /**
     *功能描述  选择存储器
     *
     * @date 2020/5/29
     * @param devId
     * @return java.util.List<java.lang.String>
     */
    List<Map > getDatastore(Integer devId,String clusterComputeResourceName);
    /**
     *功能描述  选择资源池
     *
     * @date 2020/5/29
     * @param devId
     * @return java.util.List<java.lang.String>
     */
    List<Map > getPoolName(Integer devId);
    /**
     *功能描述  查询ip地址
     *
     * @date 2020/5/29
     * @param devId
     * @return java.util.List<java.lang.String>
     */
    List<VcenterVmIp> getIP(Long devId);
    /**
     *功能描述  查询列表
     *
     * @date 2020/5/29
     * @param vcenterApplication
     * @return java.util.List<java.lang.String>
     */
    List<VcenterApplication> getList(VcenterApplication vcenterApplication);
    /**
     *功能描述  查询列表
     *
     * @date 2020/5/29
     * @param createVMDTO
     * @return java.util.List<java.lang.String>
     */
    void CreateVMByTemplate(CreateVMDTO createVMDTO);
}
