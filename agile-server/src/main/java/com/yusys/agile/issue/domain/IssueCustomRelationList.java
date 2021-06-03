package com.yusys.agile.issue.domain;

import lombok.Data;

import java.util.List;

/**
 * :
 *
 * @Date: 2021/2/31
 * @Description: TODO
 */
@Data
public class IssueCustomRelationList {
    List<SIssueCustomRelation> issueCustomRelationList;
    Byte issueType;
    IssueTemplate issueTemplate;
}
