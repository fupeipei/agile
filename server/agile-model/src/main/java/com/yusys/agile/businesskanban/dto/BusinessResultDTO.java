package com.yusys.agile.businesskanban.dto;

import java.util.List;

/**
 * @Date: 2021/2/8
 * @Description: 事务视图
 */
public class BusinessResultDTO {
    private Long id;
    private String title;
    private List<BusinessDTO> children;

    private String tenantCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<BusinessDTO> getChildren() {
        return children;
    }

    public void setChildren(List<BusinessDTO> children) {
        this.children = children;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return "BusinessResultDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", children=" + children +
                '}';
    }
}
