package com.yusys.agile.excel.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @description excel工作项
 * @Date 2021/2/1
 */
public class ExcelIssue implements Serializable {

    private static final long serialVersionUID = 5606270242308127259L;

    //工作项编号
    private Long issueId;

    //名称
    private String title;

    //开始日期
    private String beginDate;

    //结束日期
    private String endDate;

    //预计工时
    private String planWorkload;

    //优先级
    private String priority;

    //重要程度
    private String importance;

    //业务价值
    private String order;

    //迭代编号
    private Long sprintId;

    //迭代名称
    private String sprintName;

    //系统编号
    private Long systemId;

    //系统名称
    private String systemName;

    /**
     * 系统id集合
     */
    private List<Long> systemIds;


    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPlanWorkload() {
        return planWorkload;
    }

    public void setPlanWorkload(String planWorkload) {
        this.planWorkload = planWorkload;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public List<Long> getSystemIds() {
        return systemIds;
    }

    public void setSystemIds(List<Long> systemIds) {
        this.systemIds = systemIds;
    }

    @Override
    public String toString() {
        return "ExcelIssue{" +
                "issueId=" + issueId +
                ", title='" + title + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", planWorkload='" + planWorkload + '\'' +
                ", priority='" + priority + '\'' +
                ", importance='" + importance + '\'' +
                ", order='" + order + '\'' +
                ", sprintId=" + sprintId +
                ", sprintName='" + sprintName + '\'' +
                ", systemId=" + systemId +
                ", systemName='" + systemName + '\'' +
                ", systemIds=" + systemIds +
                '}';
    }
}
