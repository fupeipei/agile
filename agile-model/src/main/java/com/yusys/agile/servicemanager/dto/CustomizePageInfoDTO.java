package com.yusys.agile.servicemanager.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @description 服务治理平台分页实体类
 * @date 2020/10/27
 */
public class CustomizePageInfoDTO<T> implements Serializable {

    private static final long serialVersionUID = 1775721488211103857L;

    //当前页
    private int pageNum = 1;
    //每页的数量
    private int pageSize = 20;
    //总记录数
    private long total;
    //当前页的数量
    private long size = 0;
    //每页记录
    private List<T> list;

    public CustomizePageInfoDTO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CustomizePageInfoDTO{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", size=" + size +
                ", list=" + list +
                '}';
    }
}
