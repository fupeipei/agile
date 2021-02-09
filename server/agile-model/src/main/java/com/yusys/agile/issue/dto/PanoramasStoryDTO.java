package com.yusys.agile.issue.dto;

import java.util.List;

/**
 * @description
 * @date 2020/10/23
 */
public class PanoramasStoryDTO {
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

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<PanoramasTaskDTO> getDevTasks() {
        return devTasks;
    }

    public void setDevTasks(List<PanoramasTaskDTO> devTasks) {
        this.devTasks = devTasks;
    }

    public List<PanoramasTaskDTO> getFunTestTask() {
        return funTestTask;
    }

    public void setFunTestTask(List<PanoramasTaskDTO> funTestTask) {
        this.funTestTask = funTestTask;
    }

    private Long issueId;
    private String title;
    private String moduleName;

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    private String handler;

    public String getStoryStatus() {
        return storyStatus;
    }

    public void setStoryStatus(String storyStatus) {
        this.storyStatus = storyStatus;
    }

    private String storyStatus;

    public String getServerAnalystManager() {
        return serverAnalystManager;
    }

    public void setServerAnalystManager(String serverAnalystManager) {
        this.serverAnalystManager = serverAnalystManager;
    }

    private String serverAnalystManager;
    private List<PanoramasTaskDTO> devTasks;
    private List<PanoramasTaskDTO> funTestTask;

}
