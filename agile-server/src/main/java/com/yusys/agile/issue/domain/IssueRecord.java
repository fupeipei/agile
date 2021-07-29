package com.yusys.agile.issue.domain;

import lombok.Data;

import java.util.List;

/**
 * :
 *
 * @Date: 2021/2/16
 * @Description: 高级搜索   paramter
 */
@Data
public class IssueRecord {
    private Long projectId;
    private Long issueId;
    private String title;
    private FieldJsonType issueTypes;
    private FieldJsonType prioritys;
    private FieldJsonType importances;
    private FieldJsonType completions;
    private FieldJsonType stageIds;
    private FieldJsonType laneIds;
    private FieldJsonType faultLevels;
    private FieldJsonType faultTypes;
    private FieldJsonType orders;
    private FieldJsonType sprintIds;
    private FieldJsonType systemIds;
    private FieldJsonType teamIds;
    private FieldJsonType moduleIds;
    private FieldJsonType fixedUids;
    private FieldJsonType testUids;
    private FieldJsonType createUids;
    private FieldJsonType updateUids;
    private FieldJsonType handlers;
    private FieldJsonType createTime;
    private FieldJsonType beginDate;
    private FieldJsonType endDate;
    private FieldJsonType updateTime;
    private String BAPerson;
    private String queryFlag;
    private Integer pageNum;
    private Integer pageSize;
    private Integer form;
    private List<Long> issueIds;
    private Byte blockState;
    private FieldJsonType isArchive;
    private String tenantCode;

    public int getFrom() {
        return pageSize * (pageNum - 1);
    }
}
