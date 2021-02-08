package com.yusys.agile.leankanban.dto;

/**
 *    赵英东
 */
public class PageResultDTO {

    private Long stageId;
    private Byte stageType;
    private Integer page;
    private Integer pageSize;

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public Byte getStageType() {
        return stageType;
    }

    public void setStageType(Byte stageType) {
        this.stageType = stageType;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getFrom() {
        this.page = page==null?1:page;
        this.pageSize = pageSize==null?10:pageSize;
        return pageSize * (page - 1);
    }
}
