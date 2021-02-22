package com.yusys.agile.cases.dto;

import java.util.List;

/**
 * 测试用例入参DTO
 *
 * @create 2021/2/1
 */
public class CaseParamDTO {
    /**
     * 用例类型 0用例 1自动化用例
     */
    private Integer caseType;
    /**
     * 迭代id
     */
    private Long externalId;
    /**
     * 故事id集合
     */
    private List<Long> externalIds;
    /**
     * 页码
     */
    private Integer page_no;
    /**
     * 每页条数
     */
    private Integer page_size;

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public List<Long> getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(List<Long> externalIds) {
        this.externalIds = externalIds;
    }

    public Integer getPage_no() {
        return page_no;
    }

    public void setPage_no(Integer page_no) {
        this.page_no = page_no;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }

    public Integer getCaseType() {
        return caseType;
    }

    public void setCaseType(Integer caseType) {
        this.caseType = caseType;
    }

    @Override
    public String toString() {
        return "CaseParamDTO{" +
                "caseType=" + caseType +
                ", externalId=" + externalId +
                ", externalIds=" + externalIds +
                ", page_no=" + page_no +
                ", page_size=" + page_size +
                '}';
    }
}