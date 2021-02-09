package com.yusys.agile.issue.domain;

import java.util.List;

/**
 * :
 *
 * @Date: 2021/2/31
 * @Description: TODO
 */
public class IssueCustomRelationList {
    List<IssueCustomRelation> issueCustomRelationList;
    Byte issueType;
    IssueTemplate issueTemplate;

    public List<IssueCustomRelation> getIssueCustomRelationList() {
        return issueCustomRelationList;
    }

    public void setIssueCustomRelationList(List<IssueCustomRelation> issueCustomRelationList) {
        this.issueCustomRelationList = issueCustomRelationList;
    }

    public Byte getIssueType() {
        return issueType;
    }

    public void setIssueType(Byte issueType) {
        this.issueType = issueType;
    }

    public IssueTemplate getIssueTemplate() {
        return issueTemplate;
    }

    public void setIssueTemplate(IssueTemplate issueTemplate) {
        this.issueTemplate = issueTemplate;
    }
}
