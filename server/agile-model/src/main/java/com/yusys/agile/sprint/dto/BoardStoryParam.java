package com.yusys.agile.sprint.dto;

import java.util.List;

/**
 *    maxp
 * @Date 2020/4/30
 * @Description 看板搜索条件
 */
public class BoardStoryParam {
    private Long sprintId;
    private String storyKeyWord;
    private String taskKeyWord;
    private List<Integer> userIds;
    private List<Integer> moduleIds;
    private List<Integer> taskTypeIds;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public String getStoryKeyWord() {
        return storyKeyWord;
    }

    public void setStoryKeyWord(String storyKeyWord) {
        this.storyKeyWord = storyKeyWord;
    }

    public String getTaskKeyWord() {
        return taskKeyWord;
    }

    public void setTaskKeyWord(String taskKeyWord) {
        this.taskKeyWord = taskKeyWord;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public List<Integer> getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(List<Integer> moduleIds) {
        this.moduleIds = moduleIds;
    }

    public List<Integer> getTaskTypeIds() {
        return taskTypeIds;
    }

    public void setTaskTypeIds(List<Integer> taskTypeIds) {
        this.taskTypeIds = taskTypeIds;
    }
}
