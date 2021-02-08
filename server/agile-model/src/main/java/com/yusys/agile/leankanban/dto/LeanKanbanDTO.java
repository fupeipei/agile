package com.yusys.agile.leankanban.dto;

import java.util.List;
import java.util.Map;

/**
 *    赵英东
 */
public class LeanKanbanDTO {

    private Byte selectType;

    private List<PageResultDTO> pageResultDTOList;

    private String target;

    private Map<String,Object> issueStringDTO;

    public Byte getSelectType() {
        return selectType;
    }

    public void setSelectType(Byte selectType) {
        this.selectType = selectType;
    }

    public List<PageResultDTO> getPageResultDTOList() {
        return pageResultDTOList;
    }

    public void setPageResultDTOList(List<PageResultDTO> pageResultDTOList) {
        this.pageResultDTOList = pageResultDTOList;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Map<String, Object> getIssueStringDTO() {
        return issueStringDTO;
    }

    public void setIssueStringDTO(Map<String, Object> issueStringDTO) {
        this.issueStringDTO = issueStringDTO;
    }

    @Override
    public String toString() {
        return "LeanKanbanDTO{" +
                "selectType=" + selectType +
                ", pageResultDTOList=" + pageResultDTOList +
                '}';
    }
}
