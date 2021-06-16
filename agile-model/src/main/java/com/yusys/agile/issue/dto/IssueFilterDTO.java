package com.yusys.agile.issue.dto;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class IssueFilterDTO {
    private Long filterId;

    private String name;

    private Byte idCheck;

    /**
     * 1：epic 2：feature 3：story 4：task 5：fault
     */
    private Byte category;

    /**
     * 过滤器类型,0:默认过滤器，1:自定义过滤器
     */
    private Byte filterType;

    private Long projectId;

    private Long createUid;

    private String createName;

    private Date createTime;

    private String state;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    private List<IssueFilterContentDTO> filterContentList;

    public Long getFilterId() {
        return filterId;
    }

    public void setFilterId(Long filterId) {
        this.filterId = filterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(Byte idCheck) {
        this.idCheck = idCheck;
    }

    public Byte getCategory() {
        return category;
    }

    public void setCategory(Byte category) {
        this.category = category;
    }

    public Byte getFilterType() {
        return filterType;
    }

    public void setFilterType(Byte filterType) {
        this.filterType = filterType;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<IssueFilterContentDTO> getFilterContentList() {
        return filterContentList;
    }

    public void setFilterContentList(List<IssueFilterContentDTO> filterContentList) {
        this.filterContentList = filterContentList;
    }
}
