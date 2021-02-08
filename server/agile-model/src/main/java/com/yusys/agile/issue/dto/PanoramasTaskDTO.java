package com.yusys.agile.issue.dto;

/**
 *
 * @description
 * @date 2020/10/23
 */
public class PanoramasTaskDTO {
    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    private Long issueId;
    private String handler;
    private String taskStatus;
}
