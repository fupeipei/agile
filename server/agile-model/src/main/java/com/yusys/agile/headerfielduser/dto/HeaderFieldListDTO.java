package com.yusys.agile.headerfielduser.dto;

import java.util.List;

/**
 * @Date: 2020/4/17
 * @Description: TODO
 */
public class HeaderFieldListDTO {
    private List<Long> updateList;
    private Byte category;
    private Byte isFilter;
    private String tenantCode;

    public List<Long> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<Long> updateList) {
        this.updateList = updateList;
    }

    public Byte getCategory() {
        return category;
    }

    public void setCategory(Byte category) {
        this.category = category;
    }

    public Byte getIsFilter() {
        return isFilter;
    }

    public void setIsFilter(Byte isFilter) {
        this.isFilter = isFilter;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}
