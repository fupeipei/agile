package com.yusys.agile.servicemanager.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description服务治理平台类
 * @date 2020/10/26
 */
public class ServiceManageIssueDTO implements Serializable {

    private static final long serialVersionUID = -4937936231233838850L;

    /**
     * 默认第一页
     */
    private int page = 1;

    /**
     * 默认每页20条
     */
    private int pageSize = 20;

    /**
     * 需求创建开始时间
     */
    private Date createTimeBegin;

    /**
     * 需求创建截止时间
     */
    private Date createTimeEnd;

    /**
     * 需求更新开始时间
     */
    private Date updateTimeBegin;

    /**
     * 需求更新截止时间
     */
    private Date updateTimeEnd;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Date getCreateTimeBegin() {
        return createTimeBegin;
    }

    public void setCreateTimeBegin(Date createTimeBegin) {
        this.createTimeBegin = createTimeBegin;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Date getUpdateTimeBegin() {
        return updateTimeBegin;
    }

    public void setUpdateTimeBegin(Date updateTimeBegin) {
        this.updateTimeBegin = updateTimeBegin;
    }

    public Date getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(Date updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

    @Override
    public String toString() {
        return "ServiceManageIssueDTO{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", createTimeBegin=" + createTimeBegin +
                ", createTimeEnd=" + createTimeEnd +
                ", updateTimeBegin=" + updateTimeBegin +
                ", updateTimeEnd=" + updateTimeEnd +
                '}';
    }
}
