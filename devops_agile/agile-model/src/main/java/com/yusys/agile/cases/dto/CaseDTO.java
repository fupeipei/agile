package com.yusys.agile.cases.dto;

import java.util.List;

/**
 * 测试用例返回DTO
 *
 * @create 2021/2/1
 */
public class CaseDTO {

    /**
     * 总记录数
     */
    private Integer row_count;
    /**
     * 总页数
     */
    private Integer page_count;

    /**
     * 当前页数据
     */
    private List<Case> page_data;

    public Integer getRow_count() {
        return row_count;
    }

    public void setRow_count(Integer row_count) {
        this.row_count = row_count;
    }

    public Integer getPage_count() {
        return page_count;
    }

    public void setPage_count(Integer page_count) {
        this.page_count = page_count;
    }

    public List<Case> getPage_data() {
        return page_data;
    }

    public void setPage_data(List<Case> page_data) {
        this.page_data = page_data;
    }

    @Override
    public String toString() {
        return "CaseDTO{" +
                "row_count=" + row_count +
                ", page_count=" + page_count +
                ", page_data=" + page_data +
                '}';
    }
}