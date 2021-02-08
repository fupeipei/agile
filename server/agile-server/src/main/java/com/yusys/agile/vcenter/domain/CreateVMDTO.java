package com.yusys.agile.vcenter.domain;

import java.util.List;

/**
 *   :   
 * @Date: 2021/2/2
 * @Description: TODO
 */
public class CreateVMDTO {
    private Long devId;
    private String datacenterName;
    private String CustomizationSpecName;
    private String templateVMName;
    private String resourcePoolName;
    private String datastoreName;
    private String folderName;
    private String clusterComputeResourceName;
    private String hostName;
    private List<VcenterApplication> vcenterApplicationList;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Long getDevId() {
        return devId;
    }

    public void setdevId(Long devId) {
        this.devId = devId;
    }

    public String getDatacenterName() {
        return datacenterName;
    }

    public void setDatacenterName(String datacenterName) {
        this.datacenterName = datacenterName;
    }

    public String getCustomizationSpecName() {
        return CustomizationSpecName;
    }

    public void setCustomizationSpecName(String customizationSpecName) {
        CustomizationSpecName = customizationSpecName;
    }

    public String getTemplateVMName() {
        return templateVMName;
    }

    public void setTemplateVMName(String templateVMName) {
        this.templateVMName = templateVMName;
    }

    public String getResourcePoolName() {
        return resourcePoolName;
    }

    public void setResourcePoolName(String resourcePoolName) {
        this.resourcePoolName = resourcePoolName;
    }

    public String getDatastoreName() {
        return datastoreName;
    }

    public void setDatastoreName(String datastoreName) {
        this.datastoreName = datastoreName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getClusterComputeResourceName() {
        return clusterComputeResourceName;
    }

    public void setClusterComputeResourceName(String clusterComputeResourceName) {
        this.clusterComputeResourceName = clusterComputeResourceName;
    }

    public List<VcenterApplication> getVcenterApplicationList() {
        return vcenterApplicationList;
    }

    public void setVcenterApplicationList(List<VcenterApplication> vcenterApplicationList) {
        this.vcenterApplicationList = vcenterApplicationList;
    }
}
