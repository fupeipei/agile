package com.yusys.agile.vcenter.domain;

import java.io.Serializable;
import java.util.Date;

public class VcenterApplication implements Serializable {
    private Long id;

    private Long devId;

    private String templateName;

    private String virtualMachineName;

    private Byte cpuNum;

    private Long memoryNum;

    private Long diskCapacity;

    private String ipAddress;

    private String subnetMask;

    private String gateway;

    private String dns;

    private String domain;

    private String dataCenter;

    private String customizationSpecName;

    private String folderName;

    private String hostName;

    private Byte state;

    private String tenantCode;

    private Long createUid;

    private String createName;

    private Date createTime;

    private Date updateTime;


    private Integer pageSize;

    private Integer pageNum;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getVirtualMachineName() {
        return virtualMachineName;
    }

    public void setVirtualMachineName(String virtualMachineName) {
        this.virtualMachineName = virtualMachineName == null ? null : virtualMachineName.trim();
    }

    public Byte getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(Byte cpuNum) {
        this.cpuNum = cpuNum;
    }

    public Long getMemoryNum() {
        return memoryNum;
    }

    public void setMemoryNum(Long memoryNum) {
        this.memoryNum = memoryNum;
    }

    public Long getDiskCapacity() {
        return diskCapacity;
    }

    public void setDiskCapacity(Long diskCapacity) {
        this.diskCapacity = diskCapacity;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask == null ? null : subnetMask.trim();
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway == null ? null : gateway.trim();
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns == null ? null : dns.trim();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter == null ? null : dataCenter.trim();
    }

    public String getCustomizationSpecName() {
        return customizationSpecName;
    }

    public void setCustomizationSpecName(String customizationSpecName) {
        this.customizationSpecName = customizationSpecName == null ? null : customizationSpecName.trim();
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName == null ? null : folderName.trim();
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName == null ? null : hostName.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}