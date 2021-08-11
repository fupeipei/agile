package com.yusys.agile.issue.dto;

import lombok.Data;


import java.util.List;
@Data
public class IssueConditionDTO {

    private String title;
    private Integer pageNum;
    private Integer pageSize;
    private List<Long> systemIds;
}
